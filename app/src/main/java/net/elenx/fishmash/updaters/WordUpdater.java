package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.WordDAO;
import net.elenx.fishmash.models.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class WordUpdater extends FishmashUpdater
{
    private final long wordListId;
    private JSONObject jsonWordList;
    private List<Word> words;

    public WordUpdater(OptionsActivity optionsActivity, long wordListId)
    {
        super(optionsActivity);
        this.wordListId = wordListId;
    }

    @Override
    protected void download() throws JSONException
    {
        jsonWordList = new JSONObject(getStringFrom(Constant.LISTS + wordListId));
    }

    @Override
    protected void convert() throws JSONException
    {
        words = new LinkedList<>();

        JSONArray jsonWords = jsonWordList.getJSONArray("words");

        JSONObject jsonWord;
        int size = jsonWordList.length();

        for(int i = 0; i < size; i++)
        {
            jsonWord = jsonWords.getJSONObject(i);
            words.add(new Word(jsonWord));
        }
    }

    @Override
    protected void save() throws Exception
    {
        if(words.size() == 0)
        {
            throw new Exception("there are no words");
        }

        WordDAO wordDAO = new WordDAO(optionsActivity);
        wordDAO.truncate();
        wordDAO.insert(words);
    }
}
