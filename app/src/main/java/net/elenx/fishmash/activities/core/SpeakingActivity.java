package net.elenx.fishmash.activities.core;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

abstract public class SpeakingActivity extends OptionsActivity implements TextToSpeech.OnInitListener
{
    private static final Random random = new Random();
    private static final HashMap<String, String> parameters = new HashMap<>();

    private TextToSpeech textToSpeech;
    private boolean isReadyToSpeak = false;

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

    @Override
    public void onInit(int i)
    {
        if(i == TextToSpeech.SUCCESS)
        {
            isReadyToSpeak = true;
        }
    }

    protected synchronized void say(String text, Locale language)
    {
        if(!isReadyToSpeak || textToSpeech == null || text == null || language == null)
        {
            return;
        }

        textToSpeech.setLanguage(language);

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
        {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, String.valueOf(random.nextLong()));
        }
        else
        {
            // if running api version is below 21 (KitKat) then I am forced to use deprecated api
            // or else, application won't speak at all

            //noinspection deprecation
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, parameters);
        }
    }
}
