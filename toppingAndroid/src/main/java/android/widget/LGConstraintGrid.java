package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.Barrier;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintGrid")
public class LGConstraintGrid extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintGrid(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGrid(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGrid(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintGrid(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    /*public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "Barrier");
        if(view == null)
            view = new Grid(context);
    }*/

    /**
     * (Ignore)
     */
    /*public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "Barrier", attrs);
        if(view == null)
            view = new Barrier(context, attrs);
    }*/
}

