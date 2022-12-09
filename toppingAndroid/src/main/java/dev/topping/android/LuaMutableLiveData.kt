package dev.topping.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction

@LuaClass(className = "LuaMutableLiveData", isKotlin = true)
class LuaMutableLiveData {
    val liveData = MutableLiveData<Any?>()
    val observerMap = mutableMapOf<LuaTranslator, Observer<Any?>>()

    companion object {
        /**
         * Create mutable live data
         */
        @LuaFunction(
            manual = false,
            methodName = "create",
            self = LuaMutableLiveData::class
        )
        fun create(): LuaMutableLiveData
        {
            return LuaMutableLiveData()
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
    fun observe(owner:LuaLifecycleOwner, lt: LuaTranslator)
    {
        if(observerMap.containsKey(lt)) {
            observerMap[lt]?.let { liveData.removeObserver(it) }
            observerMap.remove(lt)
        }
        observerMap[lt] = Observer<Any?> { t -> lt.CallIn(t) }
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
    fun removeObserver(lt: LuaTranslator)
    {
        if(observerMap.containsKey(lt)) {
            observerMap[lt]?.let { liveData.removeObserver(it) }
            observerMap.remove(lt)
        }
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
    fun setValue(value: Any?)
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
    fun postValue(value: Any?)
    {
        liveData.postValue(value)
    }
}