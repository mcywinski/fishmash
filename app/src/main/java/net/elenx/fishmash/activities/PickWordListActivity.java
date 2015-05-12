package net.elenx.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.WordListsDAO;
import net.elenx.fishmash.models.WordList;
import net.elenx.fishmash.updaters.WordListUpdater;

import java.util.List;

public class PickWordListActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        injectActivity(getString(R.string.pick_wordlist), R.layout.activity_pick_wordlist);

        new WordListUpdater(this).execute();
        showWordLists();
    }

    private void showWordLists()
    {
        WordListsDAO wordListsDAO = new WordListsDAO(this);
        List<WordList> wordLists = wordListsDAO.selectAll();

        TableLayout tableLayoutWordList = (TableLayout) findViewById(R.id.tableLayoutLearningSection);
        TableRow tableRow;
        TextView wordListName;
        TextView wordListFirstLanguage;
        TextView wordListSecondLanguage;

        LayoutInflater layoutInflater = getLayoutInflater();

        for(final WordList wordList : wordLists)
        {
            tableRow = (TableRow) layoutInflater.inflate(R.layout.fragment_wordlist, null);

            LinearLayout linearLayout = (LinearLayout) tableRow.getChildAt(0);
            RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(0);

            wordListName = (TextView) relativeLayout.getChildAt(0);
            wordListName.setText(wordList.getName());

            wordListFirstLanguage = (TextView) relativeLayout.getChildAt(1);
            wordListFirstLanguage.setText(wordList.getMainLanguage().getLocale().getDisplayLanguage());

            wordListSecondLanguage = (TextView) relativeLayout.getChildAt(3);
            wordListSecondLanguage.setText(wordList.getForeignLanguage().getLocale().getDisplayLanguage());

            ImageView imageView = (ImageView) linearLayout.getChildAt(1);
            imageView.setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), LearningActivity.class);
                        intent.putExtra("wordListId", wordList.getId());
                        startActivity(intent);
                        finish();
                    }
                }
            );


            tableLayoutWordList.addView(tableRow);
        }
    }
}
