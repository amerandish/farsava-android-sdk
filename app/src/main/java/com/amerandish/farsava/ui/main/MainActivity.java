package com.amerandish.farsava.ui.main;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.amerandish.farsava.R;
import com.amerandish.farsava.ui.util.FileChooser;

import java.io.File;

import farsava.component.ui.util.RecordFloatingActionButton;
import farsava.core.FarsAvaLiveService;
import farsava.core.FarsAvaService;
import farsava.core.data.model.ASRResponseBody;
import farsava.core.data.model.SpeechRecognitionResult;
import farsava.core.data.model.SynthesisInput;
import farsava.core.data.model.TTSAudioConfig;
import farsava.core.data.model.TTSRequestBody;
import farsava.core.data.model.TTSResponseBody;
import farsava.core.data.model.VoiceSelectionParams;
import farsava.core.data.model.WordInfo;
import farsava.core.data.remote.FarsAvaCallback;
import farsava.core.data.remote.FarsAvaLiveCallback;
import farsava.core.util.Enums;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private static final int RECORD_AUDIO_REQUEST_CODE = 739, READ_EXTERNAL_STORAGE_REQUEST_CODE = 224;
    private Context mContext;
    private FarsAvaLiveService farsAvaLiveService;
    private FarsAvaService farsAvaService;
    private boolean isRecording = false;
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1NjUyNzE3MjcsIm5iZiI6MTU2MjY3OTcyNywiaWF0IjoxNTYyNjc5NzI3LCJpc3MiOiJodHRwczovL2FjY291bnRzLmFtZXJhbmRpc2guY29tIiwiZXh0Ijp7InNydiI6ImNkZTU1MTk4LTk4YTQtNGU4ZS1hNzg5LTk3OTUzNWNkNTNmMCJ9LCJzdWIiOiJsYXZhc2FuaSIsInNjcCI6WyJhc3IiLCJhc3IvcG9zdCIsImFzci9sb25ncnVubmluZyIsImFzci9saXZlIiwibG10IiwidHRzIl19.2WqU0Ro7xF2wC1dpHXiT2vWNSVweLQkIJlZJeAqHZM0j5Fdp5Iap9QhfZdVbpoAa8pTjiz7umGgnnrLP2y4vuoBzOcLWD0F60lXFXmpVec1kMQNRS92FHImP6mYZ2ik2A72v2vo-A4QBju3Go-zEU_B2up9QhP1deN7LWfxPMR01XTrR7sYMWqggGU-8qCff5k83OXv3PdyCdc4m_p6XTriC792zUDPIwxFQsa_Eih2ZZnZEYAs-5C_w3N-EOfd_uund1WTgGl_wnmenCOVzS2ZomAZa2fjOTBIolb3edEFteMkDzTkuLsf6HOHOIKpYYy9ZQ2iazmE0j-Ao3HnYxSZYbdndqhU9C2-BCkGtH4mNMwP3MKlqekCfkdXrQLfuKmWrY4zk4vkmg7j5pLfkvLlaqoD5312iQUtOuUGegmFzcH0SpksGC_q3z-kCqw-pOvGH5LL-1H4zQrqqAn0KAKVMX7-Xixhykc-nt9yT9wYYBfcTUjJQ-lLScKDBIKdieya55k3h3lEhDo7Q7p-ahR0AC0vrJj00TF8l2sNGNAI7WiP5B0JiR77gdGINLHrWlIBdAd7zrQQ1xm8tj32hB-0TBwV-HxhNILyWBTAzbwHQABdRxgAQcpIcVB6al-nE6tsVv_8yLL6XJzkWFuHb_3tK0E3r_JEEtUz4lQm0N-4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


        farsAvaLiveService = new FarsAvaLiveService.Builder()
                .setJWTTokent(token)
                .build();

        farsAvaService = new FarsAvaService.Builder()
                .setJWTTokent(token)
                .build();

        initButtons();
        RecordFloatingActionButton rfab;
    }

    private void initButtons() {
        // Live ASR
        findViewById(R.id.live_asr_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (ActivityCompat.checkSelfPermission(mContext, RECORD_AUDIO) != PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
                else {
                    if (isRecording) {
                        farsAvaLiveService.stop();
                        isRecording = false;
                        ((Button) v).setText("Record");
                    } else {
                        ((Button) v).setText("Connecting...");
                        v.setEnabled(false);
                        farsAvaLiveService.start(new FarsAvaLiveCallback<ASRResponseBody>() {
                            @Override
                            public void onStart() {
                                isRecording = true;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((Button) v).setText("Stop");
                                        v.setEnabled(true);
                                    }
                                });
                            }

                            @Override
                            public void onResponse(ASRResponseBody responseBody) {

                            }

                            @Override
                            public void onFinish(ASRResponseBody responseBody) {
                                for (SpeechRecognitionResult result : responseBody.getResults()) {
                                    for (WordInfo word : result.getWords()) {
                                        ((TextView) findViewById(R.id.asr_live_response_tv)).append(word.getWord() + " ");
                                    }
                                }

                                isRecording = false;
                                runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      ((Button) v).setText("Record");
                                                  }
                                              }
                                );
                            }
                        });
                    }
                }
            }
        });
        // Post ASR
        findViewById(R.id.asr_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
                else {
                    new FileChooser(mContext, "wav")
                            .setFileListener(new FileChooser.FileSelectedListener() {
                                @Override
                                public void fileSelected(File file) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        farsAvaService.speech().postASR(file, new FarsAvaCallback<ASRResponseBody>() {
                                            @Override
                                            public void onSuccess(ASRResponseBody responseBody) {
                                                for (SpeechRecognitionResult result : responseBody.getResults()) {
                                                    for (WordInfo word : result.getWords()) {
                                                        ((TextView) findViewById(R.id.asr_response_tv)).append(word.getWord() + " ");
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFail(String errorMessage) {
                                                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        farsAvaService.speech().postASR(file, Enums.AudioEncoding.LINEAR16, new FarsAvaCallback<ASRResponseBody>() {
                                            @Override
                                            public void onSuccess(ASRResponseBody responseBody) {
                                                for (SpeechRecognitionResult result : responseBody.getResults()) {
                                                    for (WordInfo word : result.getWords()) {
                                                        ((TextView) findViewById(R.id.asr_response_tv)).append(word.getWord() + " ");
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFail(String errorMessage) {
                                                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            })
                            .showDialog();
                }
            }
        });
        // Post TTS
        findViewById(R.id.tts_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ttsText = ((EditText) findViewById(R.id.tts_request_et)).getText().toString();
                farsAvaService.voice().postTTS(new TTSRequestBody(
                                new SynthesisInput(ttsText),
                                new VoiceSelectionParams(),
                                new TTSAudioConfig(Enums.AudioEncoding.LINEAR16, 22400)),
                        new FarsAvaCallback<TTSResponseBody>() {
                            @Override
                            public void onSuccess(TTSResponseBody responseBody) {
                                try {
                                    String url = "data:audio/wav;base64," + responseBody.getAudioContent();
                                    MediaPlayer mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setDataSource(url);
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
                                } catch (Exception ex) {
                                    Log.e("TTS", "Error: ", ex);
                                }
                            }

                            @Override
                            public void onFail(String errorMessage) {
                                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
