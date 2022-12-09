package dev.topping.android

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.backend.LuaStaticVariable

/**
 * Lifecycle observer
 */
@LuaClass(className = "LuaLifecycleObserver", isKotlin = true)
class LuaLifecycleObserver private constructor(private val lt: LuaTranslator) : DefaultLifecycleObserver {
    companion object {
        @LuaStaticVariable
        const val ON_CREATE = 0
        @LuaStaticVariable
        const val ON_DESTROY = 1
        @LuaStaticVariable
        const val ON_RESUME = 2
        @LuaStaticVariable
        const val ON_PAUSE = 3
        @LuaStaticVariable
        const val ON_START = 4
        @LuaStaticVariable
        const val ON_STOP = 5

        /**
         * Create observer
         * lt +fun(obj: LuaNativeObject, event: number):void
         */
        @LuaFunction(
            manual = false,
            self = LuaLifecycleObserver::class,
            methodName = "create",
            arguments = [LuaTranslator::class]
        )
        fun create(lt: LuaTranslator) : LuaLifecycleObserver
        {
            return LuaLifecycleObserver(lt)
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        lt.CallIn(LuaNativeObject(owner), ON_CREATE)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        lt.CallIn(LuaNativeObject(owner), ON_DESTROY)
    }

    override fun onResume(owner: LifecycleOwner) {
        lt.CallIn(LuaNativeObject(owner), ON_RESUME)
    }

    override fun onPause(owner: LifecycleOwner) {
        lt.CallIn(LuaNativeObject(owner), ON_PAUSE)
    }

    override fun onStart(owner: LifecycleOwner) {
        lt.CallIn(LuaNativeObject(owner), ON_START)
    }

    override fun onStop(owner: LifecycleOwner) {
        lt.CallIn(LuaNativeObject(owner), ON_STOP)
    }
}