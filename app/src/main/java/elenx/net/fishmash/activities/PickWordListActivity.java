package elenx.net.fishmash.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import elenx.net.fishmash.R;
import elenx.net.fishmash.daos.WordListDAO;
import elenx.net.fishmash.models.WordList;

public class PickWordListActivity extends OptionsActivity
{
    private ScrollView wordListScrollView;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        wordListScrollView = (ScrollView) findViewById(R.id.wordListsScrollView);
    }

    private void showWordLists()
    {
        WordListDAO wordListDAO = new WordListDAO(this);
        List<WordList> wordLists = wordListDAO.selectAll();

        for(final WordList wordList : wordLists)
        {
            TextView textView = new TextView(this);
            textView.setText(wordList.getName());
            wordListScrollView.addView(textView);

            textView.setOnClickListener
            (
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), ViewWordsActivity.class);
                        intent.putExtra("id", wordList.getId());
                        startActivity(intent);
                        finish();
                    }
                }
            );
        }
    }
}
