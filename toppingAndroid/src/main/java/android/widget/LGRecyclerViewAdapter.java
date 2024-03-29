package android.widget;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.topping.android.ILGRecyclerViewAdapter;
import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * RecyclerView adapter
 */
@LuaClass(className = "LGRecyclerViewAdapter")
public class LGRecyclerViewAdapter extends RecyclerView.Adapter implements LuaInterface
{
    public ArrayList<Object> values = new ArrayList<>();

    private LuaContext mLc;
    private String id;
    public LGView parent;
    private LuaTranslator ltItemSelected;
    private LuaTranslator ltCreateViewHolder;
    private LuaTranslator ltBindViewHolder;
    private LuaTranslator ltGetItemViewType;
    public ILGRecyclerViewAdapter kotlinInterface;

    /**
     * (Ignore)
     */
    class RViewHolder extends RecyclerView.ViewHolder
    {
        public LGView lgView;

        public RViewHolder(LGView view)
        {
            super(view.view);
            lgView = view;
        }
    }

    /**
     * Creates LGRecyclerViewAdapter Object From Lua.
     * @return LGRecyclerViewAdapter
     */
    @LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class, String.class }, self = LGRecyclerViewAdapter.class)
    public static LGRecyclerViewAdapter create(LuaContext lc, String id)
    {
        LGRecyclerViewAdapter lrva = new LGRecyclerViewAdapter(lc, id);
        return lrva;
    }

    /**
     * (Ignore)
     */
    public LGRecyclerViewAdapter(LuaContext lc, String id)
    {
        mLc = lc;
        this.id = id;
    }

    /**
     * (Ignore)
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (ltCreateViewHolder == null)
            return null;

        LGView vh = (LGView) ltCreateViewHolder.callIn(this.parent, viewType, mLc);

        RViewHolder rViewHolder = new RViewHolder(vh);

        return rViewHolder;
    }

    /**
     * (Ignore)
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (ltBindViewHolder == null)
            return;

        RViewHolder vh = (RViewHolder)holder;

        ltBindViewHolder.callIn(vh.lgView, position, values.get(position));

        vh.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(ltItemSelected != null)
                {
                    ltItemSelected.callIn(parent, vh.lgView, position, values.get(position));
                }
            }
        });
    }

    /**
     * (Ignore)
     */
    @Override
    public int getItemViewType(int position)
    {
        if(ltGetItemViewType != null)
        {
            Object viewType = ltGetItemViewType.callIn(position);
            if(viewType instanceof Double)
                return ((Double)viewType).intValue();
            else
                return ((Integer)viewType);
        }

        return 0;
    }

    /**
     * (Ignore)
     */
    @Override
    public int getItemCount() {
        if (kotlinInterface != null && kotlinInterface.ltGetItemCount != null)
            return (int) kotlinInterface.ltGetItemCount.callIn();
        return values.size();
    }

    /**
     * Add Value to adapter
     * @param value
     */
    @LuaFunction(manual = false, methodName = "addValue", arguments = { Object.class })
    public void addValue(Object value)
    {
        values.add(value);
    }

    /**
     * Remove Value from adapter
     * @param value
     */
    @LuaFunction(manual = false, methodName = "removeValue", arguments = { Object.class })
    public void removeValue(Object value)
    {
        values.remove(value);
    }

    /**
     * Remove all values from adapter
     */
    @LuaFunction(manual = false, methodName = "clear")
    public void clear()
    {
        values.clear();
    }

    /**
     * Notify data change
     */
    @LuaFunction(manual = false, methodName = "notify")
    public void notifyData()
    {
        notifyDataSetChanged();
    }

    /**
     * Set OnItemSelected
     * Used to set item selected
     * @param lt +fun(adapter: LGRecyclerViewAdapter, parent: LGView, view: LGView, position: number, object: userdata):void
     */
    @LuaFunction(manual = false, methodName = "setOnItemSelected", arguments = { LuaTranslator.class })
    public void setOnItemSelected(LuaTranslator lt)
    {
        ltItemSelected = lt;
    }

    /**
     * Set OnCreateViewHolder
     * Used to create view holder
     * @param lt +fun(adapter: LGRecyclerViewAdapter, parent: LGView, viewType: number, context: LuaContext):LGView
     */
    @LuaFunction(manual = false, methodName = "setOnCreateViewHolder", arguments = { LuaTranslator.class })
    public void setOnCreateViewHolder(LuaTranslator lt)
    {
        ltCreateViewHolder = lt;
    }

    /**
     * Set OnBindViewHolder
     * Used to bind view holder
     * @param lt +fun(adapter: LGRecyclerViewAdapter, view: LGView, position: number, object: userdata):void
     */
    @LuaFunction(manual = false, methodName = "setOnBindViewHolder", arguments = { LuaTranslator.class })
    public void setOnBindViewHolder(LuaTranslator lt)
    {
        ltBindViewHolder = lt;
    }

    /**
     * Set GetItemViewType
     * Used to get type of view holder
     * @param lt +fun(adapter: LGRecyclerViewAdapter, position: number):number
     */
    @LuaFunction(manual = false, methodName = "setGetItemViewType", arguments = { LuaTranslator.class })
    public void setGetItemViewType(LuaTranslator lt)
    {
        ltGetItemViewType = lt;
    }

    /**
     * (Ignore)
     */
    @Override
    public String GetId()
    {
        if(id == null)
            return "LGRecyclerAdapterView";
        return id;
    }
}
