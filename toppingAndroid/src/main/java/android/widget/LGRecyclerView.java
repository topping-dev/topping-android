package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;

@LuaClass(className = "LGRecyclerView")
public class LGRecyclerView extends LGView
{
    private LinearLayoutManager mLayoutManager;

    /**
     * (Ignore)
     */
    public LGRecyclerView(Context context)
    {
        super(context);
    }

    /**
     * (Ignore)
     */
    public LGRecyclerView(Context context, String luaId)
    {
        super(context, luaId);
    }

    /**
     * (Ignore)
     */
    public LGRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * (Ignore)
     */
    public LGRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * (Ignore)
     */
    public void Setup(Context context)
    {
        view = new RecyclerView(context);
    }

    @Override
    public void Setup(Context context, AttributeSet attrs)
    {
        view = new RecyclerView(context, attrs);
    }

    @Override
    public void Setup(Context context, AttributeSet attrs, int defStyle)
    {
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
