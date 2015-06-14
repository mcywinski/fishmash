package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.models.adapters.PasswordsHolder;
import net.elenx.fishmash.updaters.PasswordUpdater;
import net.elenx.fishmash.updaters.listeners.PasswordListener;

public class ChangePasswordActivity extends OptionsActivity
{
    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatNewPassword;
    private ImageView commit;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        attach(R.layout.layout_change_password);
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
                        Toast.makeText(me, R.string.passwords_mismatch, Toast.LENGTH_LONG).show();

                        return;
                    }


                    String oldPasswordInput = oldPassword.getText().toString();

                    changePassword(new PasswordsHolder(oldPasswordInput, newPasswordInput, repeatNewPasswordInput));
                }
            }
        );
    }

    private void bindViews()
    {
        oldPassword = (EditText) findViewById(R.id.editTextOldPassword);
        newPassword = (EditText) findViewById(R.id.editTextNewPassword);
        repeatNewPassword = (EditText) findViewById(R.id.editTextRepeatNewPassword);
        commit = (ImageView) findViewById(R.id.imageViewCommit);
    }

    private void changePassword(PasswordsHolder passwordsHolder)
    {
        PasswordUpdater passwordUpdater = new PasswordUpdater(this);
        passwordUpdater.setPasswordsHolder(passwordsHolder);
        passwordUpdater.setPasswordListener
        (
            new PasswordListener()
            {
                @Override
                public void passwordChanged()
                {
                    clear(oldPassword);
                    clear(newPassword);
                    clear(repeatNewPassword);
                    Toast.makeText(me, R.string.password_has_benn_changed, Toast.LENGTH_LONG).show();
                }

                @Override
                public void internalError()
                {
                    Toast.makeText(me, R.string.internal_error, Toast.LENGTH_LONG).show();
                }

                @Override
                public void passwordsAreDifferent()
                {
                    clear(newPassword);
                    clear(repeatNewPassword);
                    Toast.makeText(me, R.string.passwords_are_different, Toast.LENGTH_LONG).show();
                }

                @Override
                public void oldPasswordIsWrong()
                {
                    clear(oldPassword);
                    Toast.makeText(me, R.string.old_password_is_incorrect, Toast.LENGTH_LONG).show();
                }
            }
        );

        passwordUpdater.execute();
    }

    private void clear(EditText editText)
    {
        editText.setText(EMPTY_STRING);
    }
}
