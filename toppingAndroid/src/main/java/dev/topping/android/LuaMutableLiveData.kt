package dev.topping.android

import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction

/**
 * Mutable live data
 */
@LuaClass(className = "LuaMutableLiveData", isKotlin = true)
class LuaMutableLiveData : LuaLiveData() {
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
     * set value
     * @param value
     */
    @LuaFunction(
        manual = false,
        methodName = "setValue",
        arguments = [Any::class]
    )
    public override fun setValue(value: Any?)
    {
        super.setValue(value)
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
    public override fun postValue(value: Any?)
    {
        super.postValue(value)
    }
}