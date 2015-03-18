package elenx.net.fishmash.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import elenx.net.fishmash.R;
import elenx.net.fishmash.models.Word;
import elenx.net.fishmash.models.WordList;

public class ViewWordsActivity extends Activity
{
    private TextView nameTextView;
    private TextView descriptionTextView;
    private Button nextWordButton;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwords);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        nextWordButton = (Button) findViewById(R.id.nextWordButton);

        final WordList wordList = new WordList(this);

        nextWordButton.setOnClickListener
        (
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Word word = wordList.next();

                    if(word == null)
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                        return;
                    }

                    nameTextView.setText(word.getName());
                    descriptionTextView.setText(word.getDescription());
                }
            }
        );
    }
}
