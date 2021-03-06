package farsava.core;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import farsava.core.data.model.ASRRequestBodyData;
import farsava.core.data.model.ASRRequestBodyURI;
import farsava.core.data.model.ASRResponseBody;
import farsava.core.data.model.LanguageModelResult;
import farsava.core.data.model.LanguageModelTrainRequestBody;
import farsava.core.data.model.RecognitionAudioData;
import farsava.core.data.model.RecognitionConfig;
import farsava.core.data.model.TTSRequestBody;
import farsava.core.data.model.TTSResponseBody;
import farsava.core.data.model.VoiceSelectionParams;
import farsava.core.data.remote.ApiService;
import farsava.core.data.remote.FarsAvaCallback;
import farsava.core.util.Enums;
import farsava.core.util.FileHelper;
import farsava.core.util.NetworkManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarsAvaService {
    private static final String TAG = "Fars Ava Core";
    private ApiService api;

    private FarsAvaService(ApiService apiService) {
        this.api = apiService;
    }

    //region Functions
    public Speech speech() {
        return new Speech();
    }

    public Language language() {
        return new Language();
    }

    public Voice voice() {
        return new Voice();
    }
    //endregion

    //region Builder
    public static class Builder {
        private String token;
        private String baseUrl;

        public Builder() {
        }

        public Builder setJWTTokent(String token) {
            this.token = token;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        // Initiate NetworkManager and Build
        public FarsAvaService build() {
            // Validation
            if (this.baseUrl != null)
                NetworkManager.setBaseUrl(this.baseUrl);
            if (this.token != null)
                NetworkManager.setToken(this.token);
            else
                throw new IllegalArgumentException("JWT Token cannot be null.");

            return new FarsAvaService(NetworkManager.getRetrofit().create(ApiService.class));
        }
    }
    //endregion

    public class Speech {
        private Speech() {
        }

        /**
         * <h3>Performs synchronous speech recognition</h3>
         * This resource receives audio data in different formats and transcribes the audio using state-of-the-art deep neural networks. It performs synchronous speech recognition and the result will be available after all audio has been sent and processed. This method is designed for transcription of short audio files up to 1 minute.
         *
         * @param requestBody      Using config object you can can specify audio configs such as audioEncoding and sampleRateHertz. We will support different languages so you can choose the languageCode. Using asrModel and languageModel in config you can use customized models.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void postASR(final ASRRequestBodyData requestBody, final FarsAvaCallback<ASRResponseBody> responseCallback) {
            api.postASR(requestBody).enqueue(new Callback<ASRResponseBody>() {
                @Override
                public void onResponse(Call<ASRResponseBody> call, Response<ASRResponseBody> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ASRResponseBody> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }

        /**
         * <h3>Performs synchronous speech recognition</h3>
         * This resource receives audio data in different formats and transcribes the audio using state-of-the-art deep neural networks. It performs synchronous speech recognition and the result will be available after all audio has been sent and processed. This method is designed for transcription of short audio files up to 1 minute.
         * For API < 24 use {@linkplain #postASR(File, Enums.AudioEncoding, FarsAvaCallback)}
         *
         * @param file             Audio file,
         *                         currently supported formats are {@code [.wav]},
         *                         currently supported encodings are {@code [.LINEAR16]},
         *                         currently supported sample rates are {@code [16000]}.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void postASR(final File file, final FarsAvaCallback<ASRResponseBody> responseCallback) {
            try {
                // To extract audio data
                MediaExtractor mediaExtractor = new MediaExtractor();
                mediaExtractor.setDataSource(file.getPath());
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(0);
                Enums.AudioEncoding audioEncoding;
                switch (mediaFormat.getInteger(MediaFormat.KEY_PCM_ENCODING)) {
                    case 2:
                        audioEncoding = Enums.AudioEncoding.LINEAR16;
                        break;
                    default:
                        throw new IllegalArgumentException("Audio encoding not supported.");
                }

                postASR(file, audioEncoding, responseCallback);
            } catch (IOException e) {
                Log.e(TAG, "postASR: ", e);
            }
        }

        /**
         * <h3>Performs synchronous speech recognition</h3>
         * This resource receives audio data in different formats and transcribes the audio using state-of-the-art deep neural networks. It performs synchronous speech recognition and the result will be available after all audio has been sent and processed. This method is designed for transcription of short audio files up to 1 minute.
         *
         * @param file             Audio file,
         *                         currently supported formats are {@code [.wav]},
         *                         currently supported sample rates are {@code [16000]}.
         * @param audioEncoding    Audio encoding, currently supported encodings are {@code [.LINEAR16]}
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void postASR(final File file, Enums.AudioEncoding audioEncoding, final FarsAvaCallback<ASRResponseBody> responseCallback) {
            try {
                // To extract audio data
                MediaExtractor mediaExtractor = new MediaExtractor();
                mediaExtractor.setDataSource(file.getPath());
                MediaFormat mediaFormat = mediaExtractor.getTrackFormat(0);

                ASRRequestBodyData requestBody = new ASRRequestBodyData(new RecognitionConfig(audioEncoding, mediaFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)), new RecognitionAudioData(FileHelper.fileToBase64(file)));
                postASR(requestBody, responseCallback);
            } catch (IOException e) {
                Log.e(TAG, "postASR: ", e);
            }
        }

        /**
         * <h3>Performs asynchronous speech recognition</h3>
         * This resource receives a uri containing the audio resource, download it and transcribes the audio using state-of-the-art deep neural networks. It performs asynchronous speech recognition and the result will be available using {@linkplain #getTranscription(UUID, FarsAvaCallback)}  getTranscription}. This method is designed for transcription of long audio files up to 240 minute.
         *
         * @param requestBody      Using config object you can can specify audio configs such as audioEncoding and sampleRateHertz. We will support different languages so you can choose the languageCode. Using asrModel and languageModel in config you can use customized models.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void postASRLongRunning(final ASRRequestBodyURI requestBody, final FarsAvaCallback<ASRResponseBody> responseCallback) {
            api.postASRLongRunning(requestBody).enqueue(new Callback<ASRResponseBody>() {
                @Override
                public void onResponse(Call<ASRResponseBody> call, Response<ASRResponseBody> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ASRResponseBody> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }

        /**
         * Retrieve a previous speech recognition result or informs you about a long running speech recognition status. To access a speech recognition result {@code transcriptionId} should be provided.
         *
         * @param transcriptionId  Id of the transcribed audio. It is a UUID string provided in {@linkplain #postASR(ASRRequestBodyData, FarsAvaCallback) ASR} & {@linkplain #postASRLongRunning(ASRRequestBodyURI, FarsAvaCallback) ASR Long Running} result.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void getTranscription(final UUID transcriptionId, final FarsAvaCallback<ASRResponseBody> responseCallback) {
            api.getTranscription(transcriptionId).enqueue(new Callback<ASRResponseBody>() {
                @Override
                public void onResponse(Call<ASRResponseBody> call, Response<ASRResponseBody> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ASRResponseBody> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }

        /**
         * Deletes a transcription for a previous file using {@code transcriptionId}.
         *
         * @param transcriptionId  Id of the transcribed audio. It is a UUID string provided in {@linkplain #postASR(ASRRequestBodyData, FarsAvaCallback) ASR} & {@linkplain #postASRLongRunning(ASRRequestBodyURI, FarsAvaCallback) ASR Long Running} result.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void deleteTranscription(final UUID transcriptionId, final FarsAvaCallback<Void> responseCallback) {
            api.deleteTranscription(transcriptionId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }
    }

    public class Language {
        private Language() {
        }

        /**
         * Returns list of user available language models. Each user can access general language models plus their own custom trained language models.
         *
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void getLanguageModels(final FarsAvaCallback<List<LanguageModelResult>> responseCallback) {
            api.getLanguageModels().enqueue(new Callback<List<LanguageModelResult>>() {
                @Override
                public void onResponse(Call<List<LanguageModelResult>> call, Response<List<LanguageModelResult>> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<LanguageModelResult>> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }

        /**
         * Train a custom language model using pharases provided by user. Returning a languageModelId for accessing the language model later and using this custom language model to transcribe audios. Using custom language models will boost accuracy for specific keywords/phrases and can be used for a domain-specific speech recognition.
         *
         * @param requestBody      Train request body.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void postLanguageModel(LanguageModelTrainRequestBody requestBody, final FarsAvaCallback<LanguageModelResult> responseCallback) {
            api.postLanguageModel(requestBody).enqueue(new Callback<LanguageModelResult>() {
                @Override
                public void onResponse(Call<LanguageModelResult> call, Response<LanguageModelResult> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LanguageModelResult> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }

        /**
         * Retrieving the status of a language model with specified languageModelId. A language model is ready to use when its status is trained.
         *
         * @param languageModelId  Id of the language model.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void getLanguageModel(Integer languageModelId, final FarsAvaCallback<LanguageModelResult> responseCallback) {
            api.getLanguageModel(languageModelId).enqueue(new Callback<LanguageModelResult>() {
                @Override
                public void onResponse(Call<LanguageModelResult> call, Response<LanguageModelResult> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LanguageModelResult> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }
    }

    public class Voice {
        private Voice() {
        }

        /**
         * <h3>Synthesizes speech synchronously</h3>
         * Receives text and data configs and synthesize speech in different voices and format using state-of-the-art deep neural networks. This service synthesizes speech synchronously and the results will be available after all text input has been processed.
         *
         * @param requestBody      Using config object you can can specify audio configs such as audioEncoding and sampleRateHertz. We will support different languages so you can choose the languageCode. using voiceSelectionParams you can choose between the supported voices with specifying voiceId. Voices differ in gender, tone and style.
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void postTTS(TTSRequestBody requestBody, final FarsAvaCallback<TTSResponseBody> responseCallback) {
            api.postTTS(requestBody).enqueue(new Callback<TTSResponseBody>() {
                @Override
                public void onResponse(Call<TTSResponseBody> call, Response<TTSResponseBody> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<TTSResponseBody> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }

        /**
         * Retrieves the list of available speakers for speech synthesization. Each speaker has a unique voiceId which can be used to synthesize speech. The result aslo includes each speaker langauge, gender and name.
         *
         * @param responseCallback {@code onSuccess} returns response body, {@code onFail} returns error message.
         */
        public void getSpeakers(final FarsAvaCallback<List<VoiceSelectionParams>> responseCallback) {
            api.getSpeakers().enqueue(new Callback<List<VoiceSelectionParams>>() {
                @Override
                public void onResponse(Call<List<VoiceSelectionParams>> call, Response<List<VoiceSelectionParams>> response) {
                    if (response.isSuccessful())
                        responseCallback.onSuccess(response.body());
                    else {
                        try {
                            responseCallback.onFail(response.errorBody().string());
                        } catch (IOException e) {
                            responseCallback.onFail(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<VoiceSelectionParams>> call, Throwable t) {
                    responseCallback.onFail(t.getMessage());
                }
            });
        }
    }

}
