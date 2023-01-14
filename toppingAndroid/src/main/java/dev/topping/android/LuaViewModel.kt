package dev.topping.android

import androidx.lifecycle.ViewModel
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import java.io.IOException
import kotlin.coroutines.CoroutineContext

@LuaClass(className = "LuaViewModel", isKotlin = true)
class LuaViewModel : ViewModel() {
    private val mBagOfTags: MutableMap<String, Any> = mutableMapOf()
    private var mCleared = false
    private val objectMap = mutableMapOf<String, Any?>()

    /**
     * Set LuaViewModel object
     * @param key
     * @param obj
     */
    @LuaFunction(
        manual = false,
        methodName = "SetObject",
        arguments = [String::class, Any::class]
    )
    fun SetObject(key: String, obj: Any?) {
        objectMap[key] = obj
    }

    /**
     * Get LuaViewModel object
     * @param key
     * @return object
     */
    @LuaFunction(
        manual = false,
        methodName = "GetObject",
        arguments = [String::class]
    )
    fun GetObject(key: String) : Any? {
        return objectMap[key]
    }

    /**
     * Get ViewModelScope object
     * @return LuaCoroutineScope
     */
    @LuaFunction(
        manual = false,
        methodName = "GetViewModelScope",
        arguments = []
    )
    fun getLuaViewModelScope(): LuaCoroutineScope {
        return viewModelScope
    }

    override fun onCleared() {
        super.onCleared()
        mCleared = true
        synchronized(mBagOfTags) {
            for (value in mBagOfTags.values) {
                // see comment for the similar call in setTagIfAbsent
                closeWithRuntimeExceptionMine(value)
            }
        }
    }

    interface Closable
    {
        fun close()
    }

    internal class ClosableCoroutineScope(context: CoroutineContext) : Closable, LuaCoroutineScope(
        CoroutineScope(context)
    ) {
        private val coroutineContext: CoroutineContext = context

        override fun close() {
            return coroutineContext.cancel()
        }
    }

    private val JOB_KEY = "LuaViewModelScope.JOB_KEY"
    public val viewModelScope: LuaCoroutineScope
        get() {
            val scope: LuaCoroutineScope? = this.getTag(JOB_KEY)
            if(scope != null)
                return scope
            return setTagIfAbsent(JOB_KEY, ClosableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate))
        }

    fun <T : Any> setTagIfAbsent(key: String, newValue: T): T {
        var previous: T?
        synchronized(mBagOfTags) {
            previous = mBagOfTags[key] as T?
            if (previous == null) {
                mBagOfTags.put(key, newValue)
            }
        }
        val result = if (previous == null) newValue else previous!!
        if (mCleared) {
            // It is possible that we'll call close() multiple times on the same object, but
            // Closeable interface requires close method to be idempotent:
            // "if the stream is already closed then invoking this method has no effect." (c)
            closeWithRuntimeExceptionMine(result)
        }
        return result
    }

    fun <T> getTag(key: String): T? {
        synchronized(mBagOfTags) { return mBagOfTags[key] as T? }
    }

    fun closeWithRuntimeExceptionMine(obj: Any) {
        if (obj is Closeable) {
            try {
                obj.close()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }
}