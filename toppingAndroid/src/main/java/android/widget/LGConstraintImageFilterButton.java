package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.widget.Guideline;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintImageFilterButton")
public class LGConstraintImageFilterButton extends LGImageView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterButton(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterButton(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterButton(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterButton(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "ImageFilterButton");
        if(view == null)
            view = new ImageFilterButton(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "ImageFilterButton", attrs);
        if(view == null)
            view = new ImageFilterButton(context, attrs);
    }
}

