package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintImageFilterView")
public class LGConstraintImageFilterView extends LGImageView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterView(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterView(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterView(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintImageFilterView(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "ImageFilterView");
        if(view == null)
            view = new ImageFilterView(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "ImageFilterView", attrs);
        if(view == null)
            view = new ImageFilterView(context, attrs);
    }
}

