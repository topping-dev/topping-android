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
    @LuaFunction(manual = false, methodName = "CreateFromForm", arguments = { LuaForm.class }, self = LGFragmentStateAdapter.class)
    public static LGFragmentStateAdapter CreateFromForm(LuaForm form)
    {
        return Create(form.GetContext(), form.getFragmentManagerInner(), form.GetLifecycle());
    }

    /**
     * Creates LGFragmentStateAdapter Object From Lua.
     * @return LGFragmentStateAdapter
     */
    @LuaFunction(manual = false, methodName = "CreateFromFragment", arguments = { LuaFragment.class }, self = LGFragmentStateAdapter.class)
    public static LGFragmentStateAdapter CreateFromFragment(LuaFragment fragment)
    {
        return Create(fragment.GetContext(), new LuaFragmentManager(fragment.getChildFragmentManager()), LuaLifecycle.Companion.Create(fragment));
    }

    /**
     * Creates LGFragmentStateAdapter Object From Lua.
     * @return LGFragmentStateAdapter
     */
    @LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class, LuaFragmentManager.class, LuaLifecycle.class }, self = LGFragmentStateAdapter.class)
    public static LGFragmentStateAdapter Create(LuaContext context, LuaFragmentManager fragmentManager, LuaLifecycle lifecycle)
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
            return (LuaFragment)ltCreateFragment.CallIn(position);
        return LuaFragment.Companion.Create(mLc, LuaRef.WithValue(0));
    }

    /**
     * (Ignore)
     */
    @Override
    public int getItemCount()
    {
        if(ltGetItemCount != null)
            return (int) ltGetItemCount.CallIn();
        return 0;
    }

    /**
     * Set CreateFragment
     * Used to create fragment
     * @param lt +fun(adapter: LGFragmentStateAdapter, position: number):LuaFragment
     */
    @LuaFunction(manual = false, methodName = "SetCreateFragment", arguments = { LuaTranslator.class })
    public void SetCreateFragment(LuaTranslator lt)
    {
        ltCreateFragment = lt;
    }

    /**
     * Set GetItemCount
     * Used to return item count
     * @param lt +fun(adapter: LGFragmentStateAdapter):number
     */
    @LuaFunction(manual = false, methodName = "SetGetItemCount", arguments = { LuaTranslator.class })
    public void SetGetItemCount(LuaTranslator lt)
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
