package net.elenx.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import net.elenx.fishmash.R;
import net.elenx.fishmash.daos.AuthenticateDAO;
import net.elenx.fishmash.updaters.WordListUpdater;
import net.elenx.fishmash.updaters.WordsUpdater;

public abstract class OptionsActivity extends ProgressDialogActivity
{
    final OptionsActivity me = this;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);

        if(!isAuthenticated())
        {
            logout();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void onBackPressed()
    {
        mainMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.main_menu:
                mainMenu();
                break;

            case R.id.update_wordLists:
                updateWordLists();
                break;

            case R.id.profile:
                profile();
                break;

            case R.id.logout:
                logout();
                break;

            case R.id.exit:
               finish();
                break;
        }

        return true;
    }

    void mainMenu()
    {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        finish();
    }

    void profile()
    {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateWordLists()
    {
        new WordListUpdater(this).execute();
    }

    void updateWords(long id)
    {
        new WordsUpdater(this, id).execute();
    }

    boolean isAuthenticated()
    {
        AuthenticateDAO authenticateDAO = new AuthenticateDAO(this);

        return authenticateDAO.selectAll().size() > 0;
    }

    private void logout()
    {
        new AuthenticateDAO(this).truncate();

        Intent intent = new Intent(getApplicationContext(), AuthenticateActivity.class);
        startActivity(intent);
        finish();
    }
}
