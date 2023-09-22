package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.Carousel;
import androidx.constraintlayout.widget.Barrier;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintCarousel")
public class LGConstraintCarousel extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintCarousel(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintCarousel(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintCarousel(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintCarousel(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "Carousel");
        if(view == null)
            view = new Carousel(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "Carousel", attrs);
        if(view == null)
            view = new Carousel(context, attrs);
    }
}

