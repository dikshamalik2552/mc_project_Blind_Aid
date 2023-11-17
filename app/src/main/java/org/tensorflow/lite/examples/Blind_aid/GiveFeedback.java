package org.tensorflow.lite.examples.Blind_aid;

import static android.content.ContentValues.TAG;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class GiveFeedback extends AppCompatActivity implements TextToSpeech.OnInitListener {
    boolean check = false;
    private final int REQ_CODE = 100;
    private TextToSpeech tts;
    private TextToSpeech giveInst;
    private FeedbackHelper dbHelper;
    Button speakButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        dbHelper = new FeedbackHelper(this);

        tts = new TextToSpeech(this, this);

        // Enable voice query when the screen is tapped
        speakButton = findViewById(R.id.button);
        giveInst = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                giveInst.speak("TAP ANYWHERE TO GIVE us YOUR VALUABLE FEEDBACK",
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

    // Showing Google speech input dialog
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

    private void launch(String fb) {
        Log.e(TAG, "launch: " + fb);
        saveFeedback(fb);
    }


    private void saveFeedback(String fb) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("feedback", fb);

        long newRowId = db.insert("FeedbackEntry", null, values);
        db.close();

        if (newRowId != -1) {
            Log.e(TAG, "Feedback saved with ID: " + newRowId);
            Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
            giveInst.speak("Thank you for your feedback!!",
                    TextToSpeech.QUEUE_FLUSH, null);
        } else {
            Log.e(TAG, "Error saving feedback");
            Toast.makeText(this, "Error saving feedback. Please try again.", Toast.LENGTH_SHORT).show();
            giveInst.speak("Error saving feedback. Please try again.",
                    TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

}