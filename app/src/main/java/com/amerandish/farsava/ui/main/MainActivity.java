package com.amerandish.farsava.ui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
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

import farsava.core.FarsAvaLiveService;
import farsava.core.FarsAvaService;
import farsava.core.Model.ASRResponseBody;
import farsava.core.Model.SpeechRecognitionResult;
import farsava.core.Model.SynthesisInput;
import farsava.core.Model.TTSAudioConfig;
import farsava.core.Model.TTSRequestBody;
import farsava.core.Model.TTSResponseBody;
import farsava.core.Model.VoiceSelectionParams;
import farsava.core.Model.WordInfo;
import farsava.core.Network.FarsAvaCallback;
import farsava.core.Network.FarsAvaLiveCallback;
import farsava.core.Util.Enums;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private static final int RECORD_AUDIO_REQUEST_CODE = 739, READ_EXTERNAL_STORAGE_REQUEST_CODE = 224;
    private Context mContext;
    private FarsAvaLiveService farsAvaLiveService;
    private FarsAvaService farsAvaService;
    private boolean isRecording = false;
    private String token = "Your JWT Token";

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
                                new TTSAudioConfig(Enums.AudioEncoding.LINEAR16, 16000)),
                        new FarsAvaCallback<TTSResponseBody>() {
                            @Override
                            public void onSuccess(TTSResponseBody responseBody) {
                                byte[] data = Base64.decode(responseBody.getAudioContent(), Base64.DEFAULT);
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
