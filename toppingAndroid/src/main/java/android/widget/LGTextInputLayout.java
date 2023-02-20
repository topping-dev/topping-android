package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputLayout;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGTextInputLayout")
public class LGTextInputLayout extends LGViewGroup implements LuaInterface {

    public LGTextInputLayout(LuaContext context) {
        super(context);
    }

    public LGTextInputLayout(LuaContext context, String luaId) {
        super(context, luaId);
    }

    public LGTextInputLayout(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context) {
        view = lc.getLayoutInflater().createView(context, "TextInputLayout");
        if (view == null)
            view = new TextInputLayout(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs) {
        view = lc.getLayoutInflater().createView(context, "TextInputLayout", attrs);
        if (view == null)
            view = new TextInputLayout(context, attrs);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs, int defStyle) {
        view = lc.getLayoutInflater().createView(context, "TextInputLayout", attrs);
        if (view == null)
            view = new TextInputLayout(context, attrs, defStyle);
    }

    @Override
    public String GetId() {
        return "LGTextInputLayout";
    }
}
