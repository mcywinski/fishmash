package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.elenx.fishmash.R;
import net.elenx.fishmash.rest.FishmashRestConsumer;
import net.elenx.fishmash.rest.RestListener;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class LoginActivity extends OptionsActivity
{
    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button buttonLogIn;
    private OptionsActivity me = this;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.actvity_login);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);

        buttonLogIn.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String login = editTextLogin.getText().toString();
                    String password = editTextPassword.getText().toString();

                    new FishmashRestConsumer<>
                    (
                        me,
                        new RestListener<JSONObject>()
                        {
                            @Override
                            public JSONObject makeRequest()
                            {
                                return new RestTemplate().getForObject("", JSONObject.class);
                            }

                            @Override
                            public void onFailure()
                            {

                            }

                            @Override
                            public void onSuccess(JSONObject result)
                            {

                            }
                        }
                    ).execute();
                }
            }
        );
    }
}
