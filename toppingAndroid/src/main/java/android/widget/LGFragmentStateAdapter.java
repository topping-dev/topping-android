package android.widget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import dev.topping.android.LuaForm;
import dev.topping.android.LuaFragment;
import dev.topping.android.LuaFragmentManager;
import dev.topping.android.LuaLifecycle;
import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.luagui.LuaRef;

@LuaClass(className = "LGFragmentStateAdapter")
public class LGFragmentStateAdapter extends FragmentStateAdapter implements LuaInterface
{
    private LuaContext mLc;
    public LGView parent;

    private LuaTranslator ltCreateFragment;
    private LuaTranslator ltGetItemCount;

    /**
     * Creates LGFragmentStateAdapter Object From Lua.
     * @return LGFragmentStateAdapter
     */
    @LuaFunction(manual = false, methodName = "createFromForm", arguments = { LuaForm.class }, self = LGFragmentStateAdapter.class)
    public static LGFragmentStateAdapter createFromForm(LuaForm form)
    {
        return create(form.getContext(), form.getFragmentManagerInner(), form.getLuaLifecycle());
    }

    /**
     * Creates LGFragmentStateAdapter Object From Lua.
     * @return LGFragmentStateAdapter
     */
    @LuaFunction(manual = false, methodName = "createFromFragment", arguments = { LuaFragment.class }, self = LGFragmentStateAdapter.class)
    public static LGFragmentStateAdapter createFromFragment(LuaFragment fragment)
    {
        return create(fragment.getLuaContextJava(), new LuaFragmentManager(fragment.getChildFragmentManager()), LuaLifecycle.Companion.create(fragment));
    }

    /**
     * Creates LGFragmentStateAdapter Object From Lua.
     * @return LGFragmentStateAdapter
     */
    @LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class, LuaFragmentManager.class, LuaLifecycle.class }, self = LGFragmentStateAdapter.class)
    public static LGFragmentStateAdapter create(LuaContext context, LuaFragmentManager fragmentManager, LuaLifecycle lifecycle)
    {
        return new LGFragmentStateAdapter(context, fragmentManager, lifecycle);
    }

    /**
     * (Ignore)
     */
    public LGFragmentStateAdapter(LuaContext context, LuaFragmentManager fragmentManager, LuaLifecycle lifecycle)
    {
        super(fragmentManager.getFragmentManager(),
                lifecycle.getLifecycle());
        mLc = context;
    }

    /**
     * (Ignore)
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(ltCreateFragment != null)
            return (LuaFragment)ltCreateFragment.callIn(position);
        return LuaFragment.Companion.create(mLc, LuaRef.withValue(0));
    }

    /**
     * (Ignore)
     */
    @Override
    public int getItemCount()
    {
        if(ltGetItemCount != null)
            return (int) ltGetItemCount.callIn();
        return 0;
    }

    /**
     * Set CreateFragment
     * Used to create fragment
     * @param lt +fun(adapter: LGFragmentStateAdapter, position: number):LuaFragment
     */
    @LuaFunction(manual = false, methodName = "setCreateFragment", arguments = { LuaTranslator.class })
    public void setCreateFragment(LuaTranslator lt)
    {
        ltCreateFragment = lt;
    }

    /**
     * Set GetItemCount
     * Used to return item count
     * @param lt +fun(adapter: LGFragmentStateAdapter):number
     */
    @LuaFunction(manual = false, methodName = "setGetItemCount", arguments = { LuaTranslator.class })
    public void setGetItemCount(LuaTranslator lt)
    {
        ltGetItemCount = lt;
    }

    /**
     * (Ignore)
     */
    @Override
    public String GetId()
    {
        return "LGFragmentStateAdapter";
    }
}
