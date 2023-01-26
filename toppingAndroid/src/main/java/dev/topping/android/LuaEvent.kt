package dev.topping.android

import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.backend.LuaInterface
import dev.topping.android.backend.LuaStaticVariable
import dev.topping.android.luagui.LuaContext
import dev.topping.android.luagui.LuaRef

/**
 * Event handler
 */
@LuaClass(className = "LuaEvent", isKotlin = true)
class LuaEvent : LuaInterface {

    companion object {
        private val eventMap = mutableMapOf<String, LuaTranslator>()
        private val fragmentMap = mutableMapOf<String, LuaTranslator>()
        private val formMap = mutableMapOf<String, LuaTranslator>()

        /**
         * Fires when form or fragment is created
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_CREATE = 0

        /**
         * Fires when user interface view is created
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_VIEW_CREATE = 1

        /**
         * Fires when user interface create view called
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_FRAGMENT_CREATE_VIEW = 2

        /**
         * Fires when user interface view created
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_FRAGMENT_VIEW_CREATED = 3

        /**
         * Fires when user interface resumed
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_RESUME = 4

        /**
         * Fires when user interface paused
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_PAUSE = 5

        /**
         * Fires when user interface destroyed
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_DESTROY = 6

        /**
         * Fires when user interfaces updated
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_UPDATE = 7

        /**
         * Fires when user interface paint called
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_PAINT = 8

        /**
         * Fires when user interface tapped
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_MOUSEDOWN = 9

        /**
         * Fires when user interface tap dropped
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_MOUSEUP = 10

        /**
         * Fires when user touches and moves
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_MOUSEMOVE = 11

        /**
         * Fires when keystroke happened
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_KEYDOWN = 12

        /**
         * Fires when keystoke dropped
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_KEYUP = 13

        /**
         * Fires when nfc event happened
         */
        @LuaStaticVariable
        @JvmField
        var UI_EVENT_NFC = 14

        fun onUIEvent(self: LuaInterface, event: Int, lc: LuaContext?, vararg args: Any?): Any? {
            var ltToCall: LuaTranslator? = null
            ltToCall = eventMap[self.GetId() + event]
            if (ltToCall != null) {
                return if (args.isNotEmpty()) ltToCall.callInSelf(self, lc, *args)
                else ltToCall.callInSelf(self, lc)
            }
            return null
        }

        /**
         * Registers UI event
         * @param luaId
         * @param event +"LuaFragment.FRAGMENT_EVENT_CREATE" | "LuaFragment.FRAGMENT_EVENT_CREATE_VIEW" | "LuaFragment.FRAGMENT_EVENT_VIEW_CREATED" | "LuaFragment.FRAGMENT_EVENT_RESUME" | "LuaFragment.FRAGMENT_EVENT_PAUSE" | "LuaFragment.FRAGMENT_EVENT_DESTROY"
         * @param lt +fun(fragment: LuaFragment, context: LuaContext):void
         */
        @LuaFunction(
            manual = false,
            methodName = "registerUIEvent",
            self = LuaFragment::class,
            arguments = [LuaRef::class, Int::class, LuaTranslator::class]
        )
        fun registerUIEvent(luaId: LuaRef, event: Int, lt: LuaTranslator) {
            val strId = ToppingEngine.getInstance().getContext().resources.getResourceEntryName(luaId.ref)
            eventMap[strId + event] = lt
        }

        fun registerForm(name: String, ltInit: LuaTranslator) {
            formMap[name] = ltInit
        }

        fun getFormInstance(name: String, luaForm: LuaForm) : ILuaForm? {
            return formMap[name]?.callIn(luaForm) as ILuaForm?
        }

        fun registerFragment(name: String, ltInit: LuaTranslator) {
            fragmentMap[name] = ltInit
        }

        fun getFragmentInstance(name: String, luaFragment: LuaFragment) : ILuaFragment? {
            return fragmentMap[name]?.callIn(luaFragment) as ILuaFragment?
        }
    }

    override fun GetId(): String {
        return "LuaEvent"
    }
}