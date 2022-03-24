package androidx.appcompat.app;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Constructor;

import dev.topping.android.R;
import dev.topping.android.osspecific.ClassCache;

public class CustomLayoutInflater
{
    private boolean tried = false;
    private AppCompatViewInflater inflater;
    private AttributeSet defaultSet;

    public View createView(Context ctx, String name)
    {
        if(defaultSet == null) {
            DefView v = (DefView) LayoutInflater.from(ctx).inflate(R.layout.def, null);
            defaultSet = v.attrs;
        }
        if(defaultSet == null)
            return null;
        else
            return createView(ctx, name, defaultSet);
    }

    public View createView(Context ctx, String name, AttributeSet attrs)
    {
        Resources.Theme theme = ctx.getTheme();
        TypedValue value = new TypedValue();
        if(!tried && inflater == null) {
            int viewInflaterClassId = ctx.getResources().getIdentifier("viewInflaterClass", "attr", ctx.getPackageName());
            if (theme.resolveAttribute(viewInflaterClassId, value, true)) {
                try {
                    Class myClass = ClassCache.forName(String.valueOf(value.string));
                    Class[] types = {};
                    Constructor constructor = myClass.getConstructor(types);

                    Object[] parameters = {};
                    inflater = (AppCompatViewInflater) constructor.newInstance(parameters);
                } catch (Exception e) {
                    tried = true;
                }
            }
        }
        if(inflater != null)
        {
            return inflater.createView(null, name, ctx, attrs, false, true, true, true);
        }
        else
        {
            try {
                View view = null;
                if(name.indexOf('.') == -1){
                    if ("View".equals(name)) {
                        view = LayoutInflater.from(ctx).createView(name, "android.view.", attrs);
                    }
                    if (view == null) {
                        view = LayoutInflater.from(ctx).createView(name, "android.widget.", attrs);
                    }
                    if (view == null) {
                        view = LayoutInflater.from(ctx).createView(name, "android.webkit.", attrs);
                    }

                }else{
                    if (view == null){
                        view = LayoutInflater.from(ctx).createView(name, null, attrs);
                    }
                }
                return view;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }
}
