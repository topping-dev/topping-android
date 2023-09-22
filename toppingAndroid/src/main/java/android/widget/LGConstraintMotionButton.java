package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.Layer;
import androidx.constraintlayout.utils.widget.MotionButton;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintMotionButton")
public class LGConstraintMotionButton extends LGButton implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintMotionButton(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionButton(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionButton(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionButton(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "MotionButton");
        if(view == null)
            view = new MotionButton(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "MotionButton", attrs);
        if(view == null)
            view = new MotionButton(context, attrs);
    }
}

