package elenx.net.fishmash.updaters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import elenx.net.fishmash.Constant;
import elenx.net.fishmash.daos.WordListDAO;
import elenx.net.fishmash.models.WordList;

public class WordListUpdater extends AsyncTask<Void, Integer, Void>
{
    private Context context;
    private ProgressDialog progressDialog;
    private JSONArray jsonArray;
    private List<WordList> wordLists;

    public WordListUpdater(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
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

        /// TODO

        switch(values[0])
        {
            case 0:
                break;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();
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
        catch(IOException | JSONException e)
        {
            e.printStackTrace();
        }
    }

    private List<WordList> convertJsonToList()
    {
        wordLists = new LinkedList<>();

        JSONObject jsonObject;
        int size = jsonArray.length();

        for(int i = 0; i < size; i++)
        {
            try
            {
                jsonObject = jsonArray.getJSONObject(i);
                wordLists.add(new WordList(jsonObject));
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        return wordLists;
    }

    private void updateDatabase()
    {
        WordListDAO wordListDAO = new WordListDAO(context);
        wordListDAO.truncate();
        wordListDAO.insert(wordLists);
    }
}
