package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.constraintlayout.utils.widget.MotionButton;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintMotionEffect")
public class LGConstraintMotionEffect extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintMotionEffect(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionEffect(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionEffect(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionEffect(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "MotionEffect");
        if(view == null)
            view = new MotionEffect(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "MotionEffect", attrs);
        if(view == null)
            view = new MotionEffect(context, attrs);
    }
}

