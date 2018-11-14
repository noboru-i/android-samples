package hm.orz.chaos114.android.androidsamples;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.RECORD_AUDIO;

public class SpeechActivity extends AppCompatActivity {
    private static final int REQUEST_RECOGNIZE_SPEECH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        findViewById(R.id.startSpeechButton).setOnClickListener((view) -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
            startActivityForResult(intent, REQUEST_RECOGNIZE_SPEECH);
        });

        findViewById(R.id.startSpeechButton2).setOnClickListener((view) -> {
            if (ContextCompat.checkSelfPermission(this, RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, RECORD_AUDIO)) {
                    Toast.makeText(this, "Please accept.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ActivityCompat.requestPermissions(this, new String[]{RECORD_AUDIO}, 2);
            }
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
            SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new LogRecognitionListener() {
                @Override
                public void onResults(Bundle results) {
                    super.onResults(results);
                    showResult(results.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION));
                }
            });
            recognizer.startListening(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TEST", "onActivityResult");

        if (requestCode == REQUEST_RECOGNIZE_SPEECH && resultCode == RESULT_OK) {
            showResult(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS));
        }
    }

    private void showResult(List<String> resultList) {
        TextView textView = findViewById(R.id.result);
        textView.setText(TextUtils.join("\n", resultList));
        Toast.makeText(this, "result: " + resultList.get(0), Toast.LENGTH_SHORT).show();
    }

    private class LogRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d("TEST", "onReadyForSpeech: " + params);
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d("TEST", "onBeginningOfSpeech");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d("TEST", "onRmsChanged: " + rmsdB);
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d("TEST", "onBufferReceived: " + Arrays.asList(buffer));
        }

        @Override
        public void onEndOfSpeech() {
            Log.d("TEST", "onEndOfSpeech");
        }

        @Override
        public void onError(int error) {
            Log.d("TEST", "onError: " + error);
        }

        @Override
        public void onResults(Bundle results) {
            Log.d("TEST", "onResults: " + results);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d("TEST", "onPartialResults: " + partialResults);
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d("TEST", "onEvent: " + eventType + " : " + params);
        }
    }
}
