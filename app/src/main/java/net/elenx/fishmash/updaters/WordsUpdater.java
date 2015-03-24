package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.models.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class WordsUpdater extends FishmashUpdater
{
    private int wordListId;
    private JSONObject jsonWordList;
    private List<Word> words;

    public WordsUpdater(OptionsActivity optionsActivity, int wordListId)
    {
        this.optionsActivity = optionsActivity;
        this.wordListId = wordListId;
    }

    @Override
    public void download()
    {
        try
        {
            jsonWordList = new JSONObject(getStringFrom(Constant.LISTS + wordListId));
        }
        catch(JSONException e)
        {
            e.printStackTrace();

            // avoid null pointer
            jsonWordList = new JSONObject();
        }
    }

    @Override
    public void convert()
    {
        words = new LinkedList<>();

        try
        {
            JSONArray jsonWords = jsonWordList.getJSONArray("words");

            JSONObject jsonWord;
            int size = jsonWordList.length();

            for(int i = 0; i < size; i++)
            {
                jsonWord = jsonWords.getJSONObject(i);
                words.add(new Word(jsonWord));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void save()
    {
//        WordListsDAO wordListsDAO = new WordListsDAO(optionsActivity);
//        wordListsDAO.truncate();
//        wordListsDAO.insert(wordLists);
    }
}
