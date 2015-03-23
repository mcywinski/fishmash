package net.elenx.fishmash.updaters;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import net.elenx.fishmash.Constant;
import net.elenx.fishmash.activities.OptionsActivity;
import net.elenx.fishmash.daos.WordListDAO;
import net.elenx.fishmash.models.WordList;

public class WordListUpdater extends AsyncTask<Void, Integer, Void>
{
    private OptionsActivity optionsActivity;
    private JSONArray jsonArray;
    private List<WordList> wordLists;

    public WordListUpdater(OptionsActivity optionsActivity)
    {
        this.optionsActivity = optionsActivity;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        publishProgress(0);

        fetchWordListsAsJson();
        publishProgress(1);

        convertJsonToList();
        publishProgress(2);

        updateDatabase();
        publishProgress(3);

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);

        switch(values[0])
        {
            case 0:
                optionsActivity.signal("Łączenie...");
                break;

            case 1:
                optionsActivity.signal("Pobieranie...");
                break;

            case 2:
                optionsActivity.signal("Konwertowanie...");
                break;

            case 3:
                optionsActivity.signal("Zapisywanie...");
                break;
        }
    }

    private void fetchWordListsAsJson()
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

    private List<WordList> convertJsonToList()
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

        return wordLists;
    }

    private void updateDatabase()
    {
        WordListDAO wordListDAO = new WordListDAO(optionsActivity);
        wordListDAO.truncate();
        wordListDAO.insert(wordLists);
    }
}
