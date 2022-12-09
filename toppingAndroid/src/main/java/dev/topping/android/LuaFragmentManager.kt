package dev.topping.android

import android.widget.LGView
import androidx.fragment.app.FragmentManager
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction

@LuaClass(className = "LuaFragmentManager", isKotlin = true)
class LuaFragmentManager(private val fragmentManager: FragmentManager) {

    companion object {
        /**
         * Find LuaFragment with view
         * @param view
         */
        @LuaFunction(
            manual = false,
            methodName = "findFragment",
            arguments = [LGView::class],
            self = LuaFragmentManager::class
        )
        fun findFragment(view: LGView): LuaFragment {
            return FragmentManager.findFragment(view.view) as LuaFragment
        }
    }

    /**
     * Find LuaFragment by id
     * @param id
     */
    @LuaFunction(
        manual = false,
        methodName = "findFragmentById",
        arguments = [Int::class]
    )
    fun findFragmentById(id: Int): LuaFragment {
        return fragmentManager.findFragmentById(id) as LuaFragment
    }

    /**
     * Find LuaFragment by tag
     * @param tag
     */
    @LuaFunction(
        manual = false,
        methodName = "findFragmentByTag",
        arguments = [String::class]
    )
    fun findFragmentByTag(tag: String): LuaFragment {
        return fragmentManager.findFragmentByTag(tag) as LuaFragment
    }
}