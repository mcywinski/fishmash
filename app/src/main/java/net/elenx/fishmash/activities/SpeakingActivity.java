package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

abstract class SpeakingActivity extends OptionsActivity implements TextToSpeech.OnInitListener
{
    private TextToSpeech textToSpeech;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    protected void onDestroy()
    {
        if(textToSpeech != null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }

    synchronized void speak(String text, Locale language)
    {
        if(textToSpeech == null || text == null || language == null)
        {
            return;
        }

        textToSpeech.setLanguage(language);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
