package net.elenx.fishmash.activities.core;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.keyboard.KeyboardLayout;
import net.elenx.fishmash.activities.core.keyboard.KeyboardListener;

abstract class ActionsActivity extends ProgressDialogActivity implements KeyboardListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        KeyboardLayout keyboardLayout = (KeyboardLayout) findViewById(R.id.keyboardLayout);
        keyboardLayout.setKeyboardListener(this);
    }

    protected void switchIntentTo(Class<?> clazz)
    {
        switchIntentTo(clazz, null, -1);
    }

    protected void switchIntentTo(Class<?> clazz, String extraKey, long extraValue)
    {
        finish();

        Class myClass = getClass();

        if(myClass == clazz)
        {
            return;
        }

        Log.e(myClass.getSimpleName() + " is switching to", clazz.getSimpleName());

        Intent intent = new Intent(getApplicationContext(), clazz);

        boolean hasNoExtraValues = (extraKey == null && extraValue == -1);
        boolean hasExtraValues = ! hasNoExtraValues;

        if(hasExtraValues)
        {
            intent.putExtra(extraKey, extraValue);
            Log.e(extraKey, String.valueOf(extraValue));
        }

        startActivity(intent);
    }

    @Override
    public void onKeyboardOpenedEvent()
    {
        Log.e("keyboard", "opened");
    }

    @Override
    public void onKeyboardClosedEvent()
    {
        Log.e("keyboard", "closed");
    }
}
