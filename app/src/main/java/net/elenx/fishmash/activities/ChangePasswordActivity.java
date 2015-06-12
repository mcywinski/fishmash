package net.elenx.fishmash.activities;

import android.os.Bundle;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;

public class ChangePasswordActivity extends OptionsActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.layout_change_password);
    }
}
