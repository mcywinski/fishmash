package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

abstract class SpeakingActivity extends OptionsActivity implements TextToSpeech.OnInitListener
{
    private TextToSpeech textToSpeech;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
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
        if(language == null)
        {
            return;
        }

        textToSpeech.setLanguage(language);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int i)
    {

    }
}
