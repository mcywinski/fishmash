package net.elenx.fishmash.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import net.elenx.fishmash.R;
import net.elenx.fishmash.updaters.WordListUpdater;
import net.elenx.fishmash.updaters.WordsUpdater;

public class OptionsActivity extends ProgressDialogActivity
{
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
            case R.id.update_wordlists:
                updateWordLists();
                break;
            case R.id.exit:
               finish();
                break;
        }

        return true;
    }

    protected void mainMenu()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateWordLists()
    {
        WordListUpdater wordListUpdater = new WordListUpdater(this);
        wordListUpdater.execute();
    }

    protected void updateWords(long id)
    {
        if(id <= 0)
        {
            return;
        }

        WordsUpdater wordsUpdater = new WordsUpdater(this, id);
        wordsUpdater.execute();
    }
}
