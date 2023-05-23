package dev.topping.android

import android.widget.LGToolbar
import android.widget.LGView
import androidx.appcompat.widget.Toolbar
import androidx.customview.widget.Openable
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import dev.topping.android.backend.LuaClass
import dev.topping.android.backend.LuaFunction
import dev.topping.android.luagui.LuaRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Navigation UI
 */
@LuaClass(className = "LuaNavigationUI", isKotlin = true)
class LuaNavigationUI {

    companion object {
        /**
         * Navigate Up
         */
        @LuaFunction(
            manual = false,
            methodName = "navigateUp",
            arguments = [LuaNavController::class, LuaAppBarConfiguration::class],
            self = LuaNavigationUI::class
        )
        fun navigateUp(
            luaNavController: LuaNavController,
            luaAppBarConfiguration: LuaAppBarConfiguration
        ) {
            NavigationUI.navigateUp(luaNavController.navController, luaAppBarConfiguration.appBarConfiguration)
        }

        /**
         * Navigate Up
         * @param luaNavController
         * @param openableLayout
         */
        @LuaFunction(
            manual = false,
            methodName = "navigateUpView",
            arguments = [LuaNavController::class, LGView::class],
            self = LuaNavigationUI::class
        )
        fun navigateUpView(
            luaNavController: LuaNavController,
            openableLayout: LGView
        ) {
            NavigationUI.navigateUp(luaNavController.navController, openableLayout.view as Openable)
        }

        /**
         * Setup with action bar
         * @param luaForm
         * @param luaNavController
         */
        @LuaFunction(
            manual = false,
            methodName = "setupActionBarWithNavController",
            arguments = [LuaForm::class, LuaNavController::class],
            self = LuaNavigationUI::class
        )
        fun setupActionBarWithNavController(
            luaForm: LuaForm,
            luaNavController: LuaNavController,
        ) {
            NavigationUI.setupActionBarWithNavController(luaForm, luaNavController.navController)
        }

        /**
         * Setup with action bar
         * @param luaForm
         * @param luaNavController
         * @param openableLayout
         */
        @LuaFunction(
            manual = false,
            methodName = "setupActionBarWithNavControllerView",
            arguments = [LuaForm::class, LuaNavController::class, LGView::class],
            self = LuaNavigationUI::class
        )
        fun setupActionBarWithNavControllerView(
            luaForm: LuaForm,
            luaNavController: LuaNavController,
            openableLayout: LGView
        ) {
            NavigationUI.setupActionBarWithNavController(luaForm, luaNavController.navController, openableLayout.view as Openable)
        }

        /**
         * Setup with action bar
         * @param luaForm
         * @param luaAppBarConfiguration
         */
        @LuaFunction(
            manual = false,
            methodName = "setupActionBarWithNavControllerConfiguration",
            arguments = [LuaForm::class, LuaNavController::class, LuaAppBarConfiguration::class],
            self = LuaNavigationUI::class
        )
        fun setupActionBarWithNavControllerConfiguration(
            luaForm: LuaForm,
            luaNavController: LuaNavController,
            luaAppBarConfiguration: LuaAppBarConfiguration
        ) {
            NavigationUI.setupActionBarWithNavController(luaForm, luaNavController.navController, luaAppBarConfiguration.appBarConfiguration)
        }

        /**
         * Setup with toolbar
         * @param lgToolbar
         * @param luaNavController
         */
        @LuaFunction(
            manual = false,
            methodName = "setupWithNavController",
            arguments = [LGToolbar::class, LuaNavController::class],
            self = LuaNavigationUI::class
        )
        fun setupWithNavController(
            lgToolbar: LGToolbar,
            luaNavController: LuaNavController,
        ) {
            NavigationUI.setupWithNavController(lgToolbar.view as Toolbar, luaNavController.navController)
        }

        /**
         * Setup with toolbar
         * @param lgToolbar
         * @param luaNavController
         * @param openableLayout
         */
        @LuaFunction(
            manual = false,
            methodName = "setupWithNavControllerView",
            arguments = [LGToolbar::class, LuaNavController::class, LGView::class],
            self = LuaNavigationUI::class
        )
        fun setupWithNavControllerView(
            lgToolbar: LGToolbar,
            luaNavController: LuaNavController,
            openableLayout: LGView
        ) {
            NavigationUI.setupWithNavController(lgToolbar.view as Toolbar, luaNavController.navController, openableLayout.view as Openable)
        }

        /**
         * Setup with toolbar
         * @param lgToolbar
         * @param luaNavController
         * @param luaAppBarConfiguration
         */
        @LuaFunction(
            manual = false,
            methodName = "setupWithNavControllerConfiguration",
            arguments = [LGToolbar::class, LuaNavController::class, LuaAppBarConfiguration::class],
            self = LuaNavigationUI::class
        )
        fun setupWithNavControllerConfiguration(
            lgToolbar: LGToolbar,
            luaNavController: LuaNavController,
            luaAppBarConfiguration: LuaAppBarConfiguration
        ) {
            NavigationUI.setupWithNavController(lgToolbar.view as Toolbar, luaNavController.navController, luaAppBarConfiguration.appBarConfiguration)
        }
    }
}