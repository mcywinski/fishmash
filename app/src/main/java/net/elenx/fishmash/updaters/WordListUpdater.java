package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.WordListsDAO;
import net.elenx.fishmash.models.WordList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class WordListUpdater extends FishmashUpdater
{
    private JSONArray jsonArray;
    private List<WordList> wordLists;

    public WordListUpdater(OptionsActivity optionsActivity)
    {
        super(optionsActivity);
    }

    @Override
    protected void download()
    {
        try
        {
            jsonArray = new JSONArray(getStringFrom(Constant.LISTS));
        }
        catch(JSONException e)
        {
            // avoid null pointer
            jsonArray = new JSONArray();
        }
    }

    @Override
    protected void convert()
    {
        wordLists = new LinkedList<>();

        JSONObject jsonObject;
        int size = jsonArray.length();

        try
        {
            for(int i = 0; i < size; i++)
            {
                jsonObject = jsonArray.getJSONObject(i).getJSONObject("details");

                WordList wordList = new WordList(jsonObject);
                wordLists.add(wordList);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void save()
    {
        if(wordLists.size() == 0)
        {
            return;
        }

        WordListsDAO wordListsDAO = new WordListsDAO(optionsActivity);
        wordListsDAO.truncate();
        wordListsDAO.insert(wordLists);
    }
}
