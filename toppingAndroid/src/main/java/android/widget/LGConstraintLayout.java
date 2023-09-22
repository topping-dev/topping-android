package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Constraint Layout
 */
@LuaClass(className = "LGConstraintLayout")
public class LGConstraintLayout extends LGViewGroup implements LuaInterface
{
	/**
	 * Creates LGConstraintLayout Object From Lua.
	 * @param lc
	 * @return LGConstraintLayout
	 */
	@LuaFunction(manual = false, methodName = "create", arguments = { LuaContext.class }, self = LGConstraintLayout.class)
	public static LGConstraintLayout create(LuaContext lc)
	{
		return new LGConstraintLayout(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGConstraintLayout(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGConstraintLayout(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGConstraintLayout(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.getLayoutInflater().createView(context, "ConstraintLayout");
		if(view == null)
			view = new ConstraintLayout(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.getLayoutInflater().createView(context, "ConstraintLayout", attrs);
		if(view == null)
			view = new ConstraintLayout(context, attrs);
	}

	@Override
	public LGView generateLGViewForName(String name, LuaContext lc, AttributeSet atts) {
		if(name.equals("androidx.constraintlayout.widget.Barrier")) {
			return new LGConstraintBarrier(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.Group")) {
			return new LGConstraintGroup(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.Guideline")) {
			return new LGConstraintGuideline(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.Placeholder")) {
			return new LGConstraintPlaceholder(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.ReactiveGuide")) {
			return new LGConstraintReactiveGuide(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.motion.widget.MotionLayout")) {
			return new LGConstraintMotionLayout(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.utils.ImageFilterButton")) {
			return new LGConstraintImageFilterButton(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.utils.ImageFilterView")) {
			return new LGConstraintImageFilterView(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.widget.utils.MotionButton")) {
			return new LGConstraintMotionButton(lc, attrs);
		} /*else if(name.equals("androidx.constraintlayout.widget.utils.MotionLabel")) {
        	return new LGConstraintMotionLabel(lc, attrs);
		}*/
		else if(name.equals("androidx.constraintlayout.helper.widget.CircularFlow")) {
			return new LGConstraintCircularFlow(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.helper.widget.Flow")) {
			return new LGConstraintFlow(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.helper.widget.Grid")) {
			return new LGConstraintGrid(lc, attrs);
		} else if(name.equals("androidx.constraintlayout.helper.widget.Layer")) {
			return new LGConstraintLayer(lc, attrs);
		}
		return super.generateLGViewForName(name, lc, atts);
	}
}
