package dev.topping.android

import android.widget.LGView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.luagui.LuaContext

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
        val fragment = fragmentManager.findFragmentById(id)
        if(fragment is LuaFragment)
            return fragment as LuaFragment
        else if(fragment is NavHostFragment) {
            val f = LuaNavHostFragment(fragment.context, "")
            f.navHostFragment = fragment
            return f
        }
        return fragment as LuaFragment
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
        val fragment = fragmentManager.findFragmentByTag(tag)
        if(fragment is LuaFragment)
            return fragment as LuaFragment
        else if(fragment is NavHostFragment) {
            val f = LuaNavHostFragment(fragment.context, "")
            f.navHostFragment = fragment
            return f
        }
        return fragment as LuaFragment
    }
}