package net.elenx.fishmash.activities.core.keyboard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class KeyboardLayout extends RelativeLayout
{
    private KeyboardListener keyboardListener;
    private Activity activity;
    private Rect rect = new Rect();
    private boolean isKeyboardOpen = false;

    public KeyboardLayout(Context context)
    {
        super(context);
        prepareActivity(context);
    }

    public KeyboardLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        prepareActivity(context);
    }

    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        prepareActivity(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        prepareActivity(context);
    }

    private void prepareActivity(Context context)
    {
        activity = (Activity) context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);

        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        int statusBarHeight = rect.top;
        int screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        int difference = (screenHeight - statusBarHeight) - height;

        if(keyboardListener == null)
        {
            return;
        }

        // assume all soft keyboards are at least 128 pixels high
        if(difference > 128)
        {
            // keyboard is open right now
            // but check, if it was already opened
            // to avoid unnecessary callbacks

            if(!isKeyboardOpen)
            {
                isKeyboardOpen = true;
                keyboardListener.onKeyboardOpenedEvent();
            }
        }
        else
        {
            // keyboard is closed right now
            // but check, if it was already closed
            // to avoid unnecessary callbacks

            if(isKeyboardOpen)
            {
                isKeyboardOpen = false;
                keyboardListener.onKeyboardClosedEvent();
            }
        }
    }

    public void setKeyboardListener(KeyboardListener keyboardListener)
    {
        this.keyboardListener = keyboardListener;
    }
}
