package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.Group;
import androidx.constraintlayout.widget.Guideline;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintGuideline")
public class LGConstraintGuideline extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintGuideline(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGuideline(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGuideline(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGuideline(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "Guideline");
        if(view == null)
            view = new Guideline(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "Guideline", attrs);
        if(view == null)
            view = new Guideline(context, attrs);
    }
}

