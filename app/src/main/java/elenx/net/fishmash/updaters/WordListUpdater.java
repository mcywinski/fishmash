package elenx.net.fishmash.updaters;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import elenx.net.fishmash.activities.OptionsActivity;
import elenx.net.fishmash.daos.WordListDAO;
import elenx.net.fishmash.models.WordList;

public class WordListUpdater extends AsyncTask<Void, Integer, Void>
{
    private static final String MOCK = "" +
        "["+
        "  {"+
        "    \"id\":1,"+
        "    \"name\":\"General English revision\","+
        "    \"description\":\"Powtórka testowa\","+
        "    \"main_language_id\":1,"+
        "    \"foreign_language_id\":2,"+
        "    \"created_at\":\"2015-03-14T20:42:32.674Z\","+
        "    \"updated_at\":\"2015-03-14T20:42:32.674Z\""+
        "  },"+
        "  {"+
        "    \"id\":2,"+
        "    \"name\":\"Animals\","+
        "    \"description\":\"Basic animal-related vocabulary\","+
        "    \"main_language_id\":1,"+
        "    \"foreign_language_id\":2,"+
        "    \"created_at\":\"2015-03-14T20:42:32.679Z\","+
        "    \"updated_at\":\"2015-03-14T20:42:32.679Z\""+
        "  },"+
        "  {"+
        "    \"id\":3,"+
        "    \"name\":\"Familie\","+
        "    \"description\":\"Podstawowy zakres słownictwa z zakresu rodziny z języka niemieckiego.\","+
        "    \"main_language_id\":1,"+
        "    \"foreign_language_id\":3,"+
        "    \"created_at\":\"2015-03-14T20:42:32.683Z\","+
        "    \"updated_at\":\"2015-03-14T20:42:32.683Z\""+
        "  }"+
        "]";

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
//
        updateDatabase();
        publishProgress(3);

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);

        /// TODO

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
//            URL url = new URL(Constant.API + Constant.LISTS);
//            Scanner scanner = new Scanner(url.openStream());
//
//            StringBuilder stringBuilder = new StringBuilder();
//
//            scanner.useDelimiter("\\Z");
//
//            while(scanner.hasNext())
//            {
//                stringBuilder.append(scanner.nextLine());
//            }

            jsonArray = new JSONArray(MOCK);
//            jsonArray = new JSONArray(stringBuilder.toString());
        }
        catch(JSONException e)
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
                jsonObject = jsonArray.getJSONObject(i);

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
