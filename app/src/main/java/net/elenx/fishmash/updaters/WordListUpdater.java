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
    protected void download() throws JSONException
    {
        jsonArray = new JSONArray(getStringFrom(Constant.LISTS));
    }

    @Override
    protected void convert() throws JSONException
    {
        wordLists = new LinkedList<>();

        JSONObject jsonObject;
        int size = jsonArray.length();

        for(int i = 0; i < size; i++)
        {
            jsonObject = jsonArray.getJSONObject(i).getJSONObject("details");

            WordList wordList = new WordList(jsonObject);
            wordLists.add(wordList);
        }
    }

    @Override
    protected void save() throws Exception
    {
        if(wordLists.size() == 0)
        {
            throw new Exception("wordList is empty");
        }

        WordListsDAO wordListsDAO = new WordListsDAO(optionsActivity);
        wordListsDAO.truncate();
        wordListsDAO.insert(wordLists);
    }
}
