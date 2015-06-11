package net.elenx.fishmash.activities.core;

import android.content.Intent;
import android.util.Log;

abstract class ActionsActivity extends ProgressDialogActivity
{
    @Override
    public void onBackPressed()
    {
        finish();
    }

    protected void switchIntentTo(Class<?> clazz)
    {
        switchIntentTo(clazz, null, -1);
    }

    protected void switchIntentTo(Class<?> clazz, String extraKey, long extraValue)
    {
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
        finish();
    }
}
