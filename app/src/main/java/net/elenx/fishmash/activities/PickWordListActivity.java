package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import net.elenx.fishmash.daos.WordListDAO;
import net.elenx.fishmash.models.WordList;

import java.util.List;

import elenx.net.fishmash.R;

public class PickWordListActivity extends OptionsActivity
{
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_pickwordlist);

        showWordLists();
    }

    private void showWordLists()
    {
        TableLayout wordListsTable = (TableLayout) findViewById(R.id.wordListsTable);

        WordListDAO wordListDAO = new WordListDAO(this);
        List<WordList> wordLists = wordListDAO.selectAll();

        Log.e("count", String.valueOf(wordLists.size()));

        for(final WordList wordList : wordLists)
        {
            TextView textView = new TextView(this);
            textView.setText(wordList.getName());
            textView.setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Toast.makeText(getApplicationContext(), wordList.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            );

            TableRow tableRow = new TableRow(this);
            tableRow.addView(textView);

            wordListsTable.addView(tableRow);
        }
    }
}
