package org.tensorflow.lite.examples.Blind_aid;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextView speechText;
    ImageButton speak;
    String command = "blabla";
    boolean check = false;
    private final int REQ_CODE = 100;
    private TextToSpeech tts;
    String welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcome = "Welcome to BLIND-AID!";

        speechText = (TextView) findViewById(R.id.textViewShow);
        speak = (ImageButton) findViewById(R.id.imageButtonSpeak);

        //Welcome
        speechText.setText(welcome);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.speak("Welcome to Blind Aid! What can I do for you today? " +
                                " Find object? Avoid obstacle? or Detect object? " +
                                "TAP ON THE UPPER HALF OF THE SCREEN TO SPEAK",
                        TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
                check = true;
            }
        });
    }

    // Showing google speech input dialog
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speak.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra
                            (RecognizerIntent.EXTRA_RESULTS);

                    speechText.setText(result.get(0));

                    //Speak the text to be executed
                    speakOut();
                }
                break;
            }
        }
    }

    //Update the text and speak the text while calling lauch for new activity
    private void speakOut() {
        String text = speechText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        command = text;

        Log.e(TAG, "speakOut: " + command);

        //Launch task
        if (check) {
            launch(command);
        }
    }

    //Launching the activity according to the command
    private void launch(@NonNull String commandTolaunch) {
        Log.e(TAG, "launch: " + commandTolaunch);
        switch (commandTolaunch) {

            case Commands.FINDOBJECT:
                Intent intentf = new Intent(MainActivity.this, FindObjects.class);
                startActivity(intentf);
                break;

            case Commands.DETECTOBJECT:
                Toast.makeText(getBaseContext(), "Detecting Object", Toast.LENGTH_SHORT).show();
                Intent intentd = new Intent(MainActivity.this, DetectorActivity.class);
                startActivity(intentd);
                break;

            case Commands.AVOIDOBSTACLE:
                Toast.makeText(getBaseContext(), "Avoiding Obstacle", Toast.LENGTH_SHORT).show();
                Intent intenta = new Intent(MainActivity.this, ObstacleAvoidance.class);
                startActivity(intenta);
                break;

            default:
                try {
                    tts.speak("Sorry, didn't get you. Please try again",
                            TextToSpeech.QUEUE_FLUSH, null);
                    speechText.setText(welcome);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        // Shuts Down TTS
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}
