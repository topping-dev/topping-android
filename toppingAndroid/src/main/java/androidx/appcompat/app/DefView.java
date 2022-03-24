package androidx.appcompat.app;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DefView extends View {
    AttributeSet attrs;

    public DefView(Context context) {
        super(context);
    }

    public DefView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
    }

    public DefView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DefView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attrs = attrs;
    }
}
