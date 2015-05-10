package net.elenx.fishmash.activities.core.drawer;

import android.os.Bundle;
import android.widget.TextView;

import net.elenx.fishmash.R;

public class OtherActivity extends DrawerActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        changeActionBarTitle("other");
        attach(R.layout.activity_other);

        TextView textView = (TextView) findViewById(R.id.otherTextView);
        textView.setText("DYNAMIC TEXT CHANGE");
    }
}
