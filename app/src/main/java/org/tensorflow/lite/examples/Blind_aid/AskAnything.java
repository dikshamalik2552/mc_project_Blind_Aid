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

public class AskAnything extends AppCompatActivity implements TextToSpeech.OnInitListener {
    boolean check = false;
    private final int REQ_CODE = 100;
    private TextToSpeech tts;
    private TextToSpeech giveInst;
    Button speakButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.askanything);

        tts = new TextToSpeech(this, this);

        // Enable voice query when the screen is tapped
        speakButton = findViewById(R.id.button);
        giveInst = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                giveInst.speak("What do you want to search? TAP ANYWHERE TO SPEAK",
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

    private void launch(String query) {
        Log.e(TAG, "launch: " + query);

        giveInst.speak("Searching " + query + " Please wait",
                TextToSpeech.QUEUE_FLUSH, null);
        searchOnGoogle(query);
    }


    private void searchOnGoogle(String query) {
        Log.e(TAG, "launch: " + query);
        String searchUrl = "https://www.google.com/search?q=" + query;
        Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(searchUrl));
        startActivity(intent);
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
