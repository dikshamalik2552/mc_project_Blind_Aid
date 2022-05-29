package org.tensorflow.lite.examples.Blind_aid;

import static android.content.ContentValues.TAG;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;

public class FindObjects extends AppCompatActivity implements TextToSpeech.OnInitListener {
    Button speakButton;
    boolean check = false;
    private final int REQ_CODE = 100;
    private TextToSpeech tts;
    private TextToSpeech giveInst;
    private String objectName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findobject);

        speakButton = findViewById(R.id.button);

        giveInst = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                giveInst.speak("Which object do you want to find? TAP ANYWHERE TO SPEAK",
                        TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        speakButton.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakButton.setEnabled(true);
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

                    if (check) {
                        launch(result.get(0));
                    }
                }
                break;
            }
        }
    }

    //Launching the activity detectObjectByName
    private void launch(String commandTolaunch) {
        Log.e(TAG, "launch: " + commandTolaunch);
        switch (commandTolaunch) {

            case Commands.BOTTLE:
                objectName = "bottle";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.BED:
                objectName = "bed";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.CHAIR:
                objectName = "chair";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.DOOR:
                objectName = "door";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.MOUSE:
                objectName = "mouse";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;
            case Commands.REFRIGERATOR:
                objectName = "refrigerator";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.REMOTE:
                objectName = "remote";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.SCISSORS:
                objectName = "scissors";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.CLOCK:
                objectName = "clock";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.PERSON:
                objectName = "person";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.APPLE:
                objectName = "apple";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.BOOK:
                objectName = "book";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.BOWL:
                objectName = "bowl";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.BAGPACK:
                objectName = "bagpack";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.CAR:
                objectName = "car";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.CUP:
                objectName = "cup";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.KNIFE:
                objectName = "knife";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.LAPTOP:
                objectName = "laptop";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.FORK:
                objectName = "fork";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.SOFA:
                objectName = "sofa";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.HANDBAG:
                objectName = "handbag";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.VASE:
                objectName = "vase";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.TEDDY_BEAR:
                objectName = "teddy bear";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.TVMONITOR:
                objectName = "TV monitor";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.ORANGE:
                objectName = "orange";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.TENNIS_RACKET:
                objectName = "tennis racket";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.SPOON:
                objectName = "spoon";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.SUITCASE:
                objectName = "suitcase";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.UMBRELLA:
                objectName = "umbrella";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            case Commands.BANANA:
                objectName = "banana";
                giveInst.speak("Looking for" + objectName + " Please scan around",
                        TextToSpeech.QUEUE_FLUSH, null);
                detectObjectGiven(objectName);
                break;

            default:
                try {
                    giveInst.speak("Sorry, I cannot find" + commandTolaunch + "Please try again",
                            TextToSpeech.QUEUE_FLUSH, null);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;
        }
    }

    private void detectObjectGiven(String objectName) {
        Intent intent = new Intent(FindObjects.this, DetectObjectByName.class);
        intent.putExtra("ObjectName", objectName);
        startActivity(intent);
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

