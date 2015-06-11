package net.elenx.fishmash.activities.core.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.widget.RelativeLayout;

public class KeyboardLayout extends RelativeLayout
{
    private KeyboardListener keyboardListener;
    private Activity activity;
    private Rect rect = new Rect();

    public KeyboardLayout(Context context)
    {
        super(context);
        activity = (Activity) context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int height = MeasureSpec.getSize(heightMeasureSpec);

        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        int statusBarHeight = rect.top;
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        int difference = (screenHeight - statusBarHeight) - height;

        if (keyboardListener != null)
        {
            if(difference > 128) // assume all soft keyboards are at least 128 pixels high
            {
                keyboardListener.onKeyboardOpened();
            }
            else
            {
                keyboardListener.onKeyboardCloseed();
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setKeyboardListener(KeyboardListener keyboardListener)
    {
        this.keyboardListener = keyboardListener;
    }
}
