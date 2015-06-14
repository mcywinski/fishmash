package net.elenx.fishmash.activities.core.keyboard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.RelativeLayout;

public class KeyboardLayout extends RelativeLayout
{
    private KeyboardListener keyboardListener;
    private Activity activity;
    private Display display;
    private final Rect rect = new Rect();
    private boolean wasKeyboardAlreadyOpenBefore = false;

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
    @SuppressWarnings("unused") // constructors of this class can be called by Android in future
    public KeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        prepareActivity(context);
    }

    private void prepareActivity(Context context)
    {
        activity = (Activity) context;
        display = activity.getWindowManager().getDefaultDisplay();
    }

    /**
     * activityHeight is height of whole physical screen
     * statusBarHeight is height of this part of screen, that is not inside this layout
     * layoutHeight is height of every View in out application, except statusBar
     *
     * if keyboard is hidden, then: activityHeight = statusBarHeight + (full) layoutHeight
     * if keyboard is shows, that: activityHeight = statusBarHeight + (shirked) layoutHeight + keyboardHeight (estimated as 128 pixels)
     *
     * @param widthMeasureSpec width of this layout
     * @param heightMeasureSpec height of this layout
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(keyboardListener == null)
        {
            return;
        }

        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        int statusBarHeight = rect.top;
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        int layoutAndStatusBarHeight = statusBarHeight + layoutHeight;

        int keyboardHeight = calculateActivityHeight() - layoutAndStatusBarHeight;

        // I am assuming, that all software keyboards have at least 128 pixels in height
        handleKeyboardState(keyboardHeight > 128);
    }

    /**
     * if isKeyboardOpenRightNow and wasKeyboardAlreadyOpenBefore then do nothing
     * if isKeyboardOpenRightNow and/but NOT wasKeyboardAlreadyOpenBefore then fire opened event
     * if NOT isKeyboardOpenRightNow and/but wasKeyboardAlreadyOpenBefore then fire closed event
     * if NOT isKeyboardOpenRightNow and NOT wasKeyboardAlreadyOpenBefore then do nothing
     *
     * @param isKeyboardOpenRightNow describes, if out screen has lost more than 128 pixels (for keyboard)
     */
    private void handleKeyboardState(boolean isKeyboardOpenRightNow)
    {
        if(isKeyboardOpenRightNow)
        {
            if(! wasKeyboardAlreadyOpenBefore)
            {
                wasKeyboardAlreadyOpenBefore = true;
                keyboardListener.onKeyboardOpenedEvent();
            }
        }
        else
        {
            if(wasKeyboardAlreadyOpenBefore)
            {
                wasKeyboardAlreadyOpenBefore = false;
                keyboardListener.onKeyboardClosedEvent();
            }
        }
    }

    private int calculateActivityHeight()
    {
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            Point size = new Point();
            display.getSize(size);

            return size.y;
        }
        else
        {
            // if API version is less than 13, then I am forced to use deprecated method
            //noinspection deprecation
            return display.getHeight();
        }
    }

    public void setKeyboardListener(KeyboardListener keyboardListener)
    {
        this.keyboardListener = keyboardListener;
    }
}
