package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;

public class ChangePasswordActivity extends OptionsActivity
{
    private EditText oldPassowrd;
    private EditText newPassword;
    private EditText repeatNewPassword;
    private ImageView commit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.layout_change_password);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        prepareViews();
    }

    private void prepareViews()
    {
        bindViews();
        commit.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String newPasswordInput = newPassword.getText().toString();
                    String repeatNewPasswordInput = repeatNewPassword.getText().toString();

                    if(! newPasswordInput.equals(repeatNewPasswordInput))
                    {
                        Toast.makeText(me, R.string.passwordsMismatch, Toast.LENGTH_LONG).show();

                        return;
                    }


                    String oldPasswordInput = oldPassowrd.getText().toString();
                }
            }
        );
    }

    private void bindViews()
    {
        oldPassowrd = (EditText) findViewById(R.id.editTextOldPassword);
        newPassword = (EditText) findViewById(R.id.editTextNewPassword);
        repeatNewPassword = (EditText) findViewById(R.id.editTextRepeatNewPassword);
        commit = (ImageView) findViewById(R.id.imageViewCommit);
    }
}
