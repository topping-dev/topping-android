package dev.topping.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction

/**
 * Lifecycle class
 */
@LuaClass(className = "LuaLifecycle", isKotlin = true)
class LuaLifecycle private constructor(
    private val lifecycle: Lifecycle,
    private val luaCoroutineScope: LuaCoroutineScope
)
{
    /**
     * (Ignore)
     */
    companion object {
        fun Create(form: LuaForm) : LuaLifecycle
        {
            return LuaLifecycle(form.lifecycle, LuaCoroutineScope(form.lifecycleScope))
        }

        fun Create(fragment: LuaFragment) : LuaLifecycle
        {
            return LuaLifecycle(fragment.lifecycle, LuaCoroutineScope(fragment.lifecycleScope))
        }
    }

    /**
     * add lifecycle observer
     * @param luaLifecycleObserver
     */
    @LuaFunction(
        manual = false,
        methodName = "addObserver",
        arguments = [LuaLifecycleObserver::class]
    )
    fun addObserver(luaLifecycleObserver: LuaLifecycleObserver)
    {
        lifecycle.addObserver(luaLifecycleObserver)
    }

    /**
     * remove lifecycle observer
     * @param luaLifecycleObserver
     */
    @LuaFunction(
        manual = false,
        methodName = "removeObserver",
        arguments = [LuaLifecycleObserver::class]
    )
    fun removeObserver(luaLifecycleObserver: LuaLifecycleObserver)
    {
        lifecycle.removeObserver(luaLifecycleObserver)
    }

    /**
     * Launch coroutine
     * @param lt +fun():void
     */
    @LuaFunction(
        manual = false,
        methodName = "launch",
        arguments = [LuaTranslator::class]
    )
    fun launch(lt: LuaTranslator) {
        luaCoroutineScope.launch(lt)
    }

    /**
     * Launch coroutine
     * @param event +"LuaDispatchers.DEFAULT" | "LuaDispatchers.UNCONFINED" | "LuaDispatchers.MAIN" | "LuaDispatchers.IO"
     * @param lt +fun():void
     */
    @LuaFunction(
        manual = false,
        methodName = "launchDispatcher",
        arguments = [Int::class, LuaTranslator::class]
    )
    fun launchDispatcher(dispatcher: Int, lt: LuaTranslator) {
        luaCoroutineScope.launchDispatcher(dispatcher, lt)
    }
}