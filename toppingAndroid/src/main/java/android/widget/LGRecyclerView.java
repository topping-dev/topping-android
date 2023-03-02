package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.topping.android.ILGRecyclerViewAdapter;
import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.luagui.LuaContext;

/**
 * RecyclerView
 */
@LuaClass(className = "LGRecyclerView")
public class LGRecyclerView extends LGView
{
    private LinearLayoutManager mLayoutManager;

    /**
     * Creates LGRecyclerView Object From Lua.
     * @param lc
     * @return LGRecyclerView
     */
    @LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGRecyclerView.class)
    public static LGRecyclerView create(LuaContext lc)
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
        view = lc.getLayoutInflater().createView(context, "RecyclerView");
        if(view == null)
            view = new RecyclerView(context);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs)
    {
        view = lc.getLayoutInflater().createView(context, "RecyclerView", attrs);
        if(view == null)
            view = new RecyclerView(context, attrs);
    }

    /**
     * (Ignore)
     */
    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
        view = lc.getLayoutInflater().createView(context, "RecyclerView", attrs);
        if(view == null)
            view = new RecyclerView(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
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
    @LuaFunction(manual = false, methodName = "getAdapter", arguments = { })
    public LGRecyclerViewAdapter getAdapter()
    {
        return (LGRecyclerViewAdapter) ((RecyclerView)view).getAdapter();
    }

    /**
     * Sets the LGRecyclerViewAdapter of listview
     * @param adapter
     */
    @LuaFunction(manual = false, methodName = "setAdapter", arguments = { LGRecyclerViewAdapter.class })
    public void setAdapter(LGRecyclerViewAdapter adapter)
    {
        ((RecyclerView)view).setAdapter(adapter);
        adapter.parent = this;
    }

    /**
     * (Ignore)
     */
    public void setAdapter(LuaTranslator ltInit)
    {
        LGRecyclerViewAdapter adapter = LGRecyclerViewAdapter.create(lc, "");
        adapter.kotlinInterface = (ILGRecyclerViewAdapter)ltInit.callIn(adapter);
        adapter.setOnCreateViewHolder(adapter.kotlinInterface.ltOnCreateViewHolder);
        adapter.setOnBindViewHolder(adapter.kotlinInterface.ltOnBindViewHolder);
        adapter.setGetItemViewType(adapter.kotlinInterface.ltGetItemViewType);
    }
}
