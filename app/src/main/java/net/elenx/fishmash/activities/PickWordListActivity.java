package net.elenx.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.elenx.fishmash.R;
import net.elenx.fishmash.daos.WordListsDAO;
import net.elenx.fishmash.models.WordList;

import java.util.List;

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

        WordListsDAO wordListsDAO = new WordListsDAO(this);
        List<WordList> wordLists = wordListsDAO.selectAll();

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
                        Intent intent = new Intent(getApplicationContext(), LearningActivity.class);
                        intent.putExtra("wordListId", wordList.getId());
                        startActivity(intent);
                        finish();
                    }
                }
            );

            TableRow tableRow = new TableRow(this);
            tableRow.addView(textView);

            wordListsTable.addView(tableRow);
        }
    }
}
