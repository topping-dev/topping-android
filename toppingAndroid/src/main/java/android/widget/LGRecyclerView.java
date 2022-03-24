package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.luagui.LuaContext;

@LuaClass(className = "LGRecyclerView")
public class LGRecyclerView extends LGView
{
    private LinearLayoutManager mLayoutManager;

    /**
     * Creates LGRecyclerView Object From Lua.
     * @param lc
     * @return LGRecyclerView
     */
    @LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGRecyclerView.class)
    public static LGRecyclerView Create(LuaContext lc)
    {
        return new LGRecyclerView(lc);
    }

    /**
     * (Ignore)
     */
    public LGRecyclerView(LuaContext context)
    {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGRecyclerView(LuaContext context, String luaId)
    {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGRecyclerView(LuaContext context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGRecyclerView(LuaContext context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = lc.GetLayoutInflater().createView(context, "RecyclerView");
        if(view == null)
            view = new RecyclerView(context);
    }

    @Override
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.GetLayoutInflater().createView(context, "RecyclerView", attrs);
        if(view == null)
            view = new RecyclerView(context, attrs);
    }

    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
        view = lc.GetLayoutInflater().createView(context, "RecyclerView", attrs);
        if(view == null)
            view = new RecyclerView(context, attrs, defStyle);
    }

    @Override
    public void AfterSetup(Context context)
    {
        super.AfterSetup(context);
        mLayoutManager = new LinearLayoutManager(context);
        ((RecyclerView)view).setLayoutManager(mLayoutManager);
    }

    /**
     * Gets the LGRecyclerViewAdapter of recyclerview
     * @return LGRecyclerViewAdapter
     */
    @LuaFunction(manual = false, methodName = "GetAdapter", arguments = { })
    public LGRecyclerViewAdapter GetAdapter()
    {
        return (LGRecyclerViewAdapter) ((RecyclerView)view).getAdapter();
    }

    /**
     * Sets the LGRecyclerViewAdapter of listview
     * @param adapter
     */
    @LuaFunction(manual = false, methodName = "SetAdapter", arguments = { LGRecyclerViewAdapter.class })
    public void SetAdapter(LGRecyclerViewAdapter adapter)
    {
        ((RecyclerView)view).setAdapter(adapter);
        adapter.parent = this;
    }
}
