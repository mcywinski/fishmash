package net.elenx.fishmash.updaters;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.daos.WordListDAO;
import net.elenx.fishmash.models.WordList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WordListUpdater extends FishmashUpdater
{
    private JSONArray jsonArray;
    private List<WordList> wordLists;

    public WordListUpdater(OptionsActivity optionsActivity)
    {
        this.optionsActivity = optionsActivity;
    }

    @Override
    public void download()
    {
        try
        {
            URL url = new URL(Constant.API + Constant.LISTS);
            Scanner scanner = new Scanner(url.openStream());

            StringBuilder stringBuilder = new StringBuilder();
            scanner.useDelimiter("\\Z");

            while(scanner.hasNext())
            {
                stringBuilder.append(scanner.nextLine());
            }

            jsonArray = new JSONArray(stringBuilder.toString());
        }
        catch(JSONException | IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void convert()
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
    public void save()
    {
        WordListDAO wordListDAO = new WordListDAO(optionsActivity);
        wordListDAO.truncate();
        wordListDAO.insert(wordLists);
    }
}
