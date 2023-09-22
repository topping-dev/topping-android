package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.motion.widget.MotionLayout;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintMotionLayout")
public class LGConstraintMotionLayout extends LGConstraintLayout implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintMotionLayout(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionLayout(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintMotionLayout(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "MotionLayout");
        if(view == null)
            view = new MotionLayout(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "MotionLayout", attrs);
        if(view == null)
            view = new MotionLayout(context, attrs);
    }

    @Override
    public LGView generateLGViewForName(String name, LuaContext lc, AttributeSet atts) {
        if(name.equals("androidx.constraintlayout.helper.widget.Carousel")) {
            return new LGConstraintCarousel(lc, attrs);
        } else if(name.equals("androidx.constraintlayout.helper.widget.MotionEffect")) {
            return new LGConstraintMotionEffect(lc, attrs);
        } else if(name.equals("androidx.constraintlayout.helper.widget.MotionPlaceholder")) {
            return new LGConstraintMotionPlaceholder(lc, attrs);
        }
        return super.generateLGViewForName(name, lc, atts);
    }
}
