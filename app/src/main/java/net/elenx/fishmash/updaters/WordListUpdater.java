package net.elenx.fishmash.updaters;

import net.elenx.fishmash.activities.core.OptionsActivity;
import net.elenx.fishmash.daos.WordListDAO;
import net.elenx.fishmash.models.WordList;
import net.elenx.fishmash.utilities.Fishmash;

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
    protected void download() throws Exception
    {
        String json = REST_INTERCEPTOR.getForObject(Fishmash.LISTS_TOKEN, String.class, buildParameters());
        jsonArray = new JSONArray(json);
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
        WordListDAO wordListDAO = new WordListDAO(optionsActivity);
        wordListDAO.truncate();
        wordListDAO.insert(wordLists);
    }
}
