package dev.topping.android

import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Coroutine scope
 */
@LuaClass(className = "LuaCoroutineScope", isKotlin = true)
open class LuaCoroutineScope(private val scope: CoroutineScope) {
    /**
     * Launch coroutine
     * @param lt +fun():void
     */
    @LuaFunction(
        manual = false,
        methodName = "launch",
        arguments = [LuaTranslator::class]
    )
    fun launch(lt: LuaTranslator): LuaJob {
        return LuaJob(scope.launch {
            lt.callIn()
        })
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
    fun launchDispatcher(dispatcher: Int, lt: LuaTranslator): LuaJob {
        return LuaJob(scope.launch(when(dispatcher)
        {
            LuaDispatchers.UNCONFINED -> {
                Dispatchers.Unconfined
            }
            LuaDispatchers.MAIN -> {
                Dispatchers.Main
            }
            LuaDispatchers.IO -> {
                Dispatchers.IO
            }
            else -> {
                Dispatchers.Default
            }
        }) {
            lt.callIn()
        })
    }
}