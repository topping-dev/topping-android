package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.helper.widget.Layer;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGConstraintGrid")
public class LGConstraintLayer extends LGView implements LuaInterface {

    /**
     * (Ignore)
     */
    public LGConstraintLayer(LuaContext context) {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGConstraintLayer(LuaContext context, String luaId) {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGConstraintLayer(LuaContext context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGConstraintLayer(LuaContext context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.getLayoutInflater().createView(context, "Layer");
        if(view == null)
            view = new Layer(context);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "Layer", attrs);
        if(view == null)
            view = new Layer(context, attrs);
    }
}

