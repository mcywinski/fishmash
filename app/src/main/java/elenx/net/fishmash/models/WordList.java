package elenx.net.fishmash.models;

import android.content.Context;

import java.util.Iterator;
import java.util.List;

import elenx.net.fishmash.daos.WordDAO;

public class WordList
{
    private List<Word> wordList;
    private Iterator<Word> wordListIterator;

    public WordList(Context context)
    {
        wordList = new WordDAO(context).selectAll();
        wordListIterator = wordList.iterator();
    }

    public Word next()
    {
        if(wordListIterator.hasNext())
        {
            return wordListIterator.next();
        }
        else
        {
            return null;
        }
    }
}
