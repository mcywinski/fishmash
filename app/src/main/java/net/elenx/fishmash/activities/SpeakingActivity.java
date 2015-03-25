package net.elenx.fishmash.activities;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;

abstract class SpeakingActivity extends OptionsActivity implements TextToSpeech.OnInitListener
{
    private TextToSpeech textToSpeech;
    protected boolean isReadyToSpeak = false;

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        textToSpeech = new TextToSpeech(this,this);
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

    @Override
    public void onInit(int i)
    {
        if(i == TextToSpeech.SUCCESS)
        {
            isReadyToSpeak = true;
        }
    }

    synchronized void say(String text)
    {
        say(text, Locale.US);
    }

    synchronized void say(String text, Locale language)
    {
        if(!isReadyToSpeak || textToSpeech == null || text == null || language == null)
        {
            return;
        }

        textToSpeech.setLanguage(language);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, new HashMap<String, String>());
    }
}
