package dev.topping.android;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;

import java.util.HashMap;
import java.util.Map;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.Lunar;
import dev.topping.android.luagui.LuaRef;

/**
 * Nav Controller
 */
@LuaClass(className = "LuaNavController")
public class LuaNavController {

    private final NavController navController;

    /**
     * Ignore
     */
    public LuaNavController(NavController navController) {
        this.navController = navController;
    }

    /**
     * Navigate to target
     * @param target
     */
    @LuaFunction(manual = false, methodName = "navigate", arguments = {LuaRef.class})
    public void navigate(LuaRef target) {
        navController.navigate(target.getRef());
    }

    /**
     * Navigate to target with arguments
     * @param target
     * @param arguments
     */
    @LuaFunction(manual = false, methodName = "navigateArgs", arguments = {LuaRef.class, HashMap.class})
    public void navigateArgs(LuaRef target, HashMap<String, Object> arguments) {
        Bundle b = new Bundle();
        for(Map.Entry<String, Object> param : arguments.entrySet())
        {
            Lunar.putBundle(b, param.getKey(), param.getValue());
        }
        navController.navigate(target.getRef(), b);
    }

    /**
     * Navigate to target with arguments and nav options
     * @param target
     * @param arguments
     * @param navOptions
     */
    @LuaFunction(manual = false, methodName = "navigateArgsOptions", arguments = {LuaRef.class, HashMap.class, LuaNavOptions.class})
    public void navigateArgsOptions(LuaRef target, HashMap<String, Object> arguments, LuaNavOptions navOptions) {
        Bundle b = new Bundle();
        for(Map.Entry<String, Object> param : arguments.entrySet())
        {
            Lunar.putBundle(b, param.getKey(), param.getValue());
        }
        navController.navigate(target.getRef(), b, navOptions.getNavOptions());
    }

    /**
     * Navigate to previous
     */
    @LuaFunction(manual = false, methodName = "navigateUp")
    public void navigateUp() {
        navController.navigateUp();
    }

    public NavController getNavController() {
        return navController;
    }
}
