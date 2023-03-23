package dev.topping.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction

@LuaClass(className = "LuaLiveData", isKotlin = true)
open class LuaLiveData {
    protected val liveData = MutableLiveData<Any?>()
    val observerMap = mutableMapOf<LuaTranslator, Observer<Any?>>()

    companion object {
        /**
         * Create live data
         */
        @LuaFunction(
            manual = false,
            methodName = "create",
            self = LuaLiveData::class
        )
        fun create(): LuaLiveData
        {
            return LuaLiveData()
        }
    }

    /**
     * observe value
     * @param lt +fun(livedata:LuaMutableLiveData obj: userdata):void
     */
    @LuaFunction(
        manual = false,
        methodName = "observe",
        arguments = [LuaLifecycleOwner::class, LuaTranslator::class]
    )
    open fun observe(owner:LuaLifecycleOwner, lt: LuaTranslator)
    {
        if(observerMap.containsKey(lt)) {
            observerMap[lt]?.let { liveData.removeObserver(it) }
            observerMap.remove(lt)
        }
        observerMap[lt] = Observer<Any?> { t -> lt.callIn(t) }
        liveData.observe(owner.getLifecycleOwner(), observerMap[lt]!!)
    }

    /**
     * remove observer
     * @param lt +fun(obj: userdata):void
     */
    @LuaFunction(
        manual = false,
        methodName = "removeObserver",
        arguments = [LuaTranslator::class]
    )
    open fun removeObserver(lt: LuaTranslator)
    {
        if(observerMap.containsKey(lt)) {
            observerMap[lt]?.let { liveData.removeObserver(it) }
            observerMap.remove(lt)
        }
    }

    /**
     * get value
     * @return value
     */
    @LuaFunction(
        manual = false,
        methodName = "getValue",
        arguments = []
    )
    open fun getValue(): Any? {
        return liveData.value
    }

    /**
     * set value
     * @param value
     */
    @LuaFunction(
        manual = false,
        methodName = "setValue",
        arguments = [Any::class]
    )
    protected open fun setValue(value: Any?)
    {
        liveData.value = value
    }

    /**
     * post value
     * @param value
     */
    @LuaFunction(
        manual = false,
        methodName = "postValue",
        arguments = [Any::class]
    )
    protected open fun postValue(value: Any?)
    {
        liveData.postValue(value)
    }
}