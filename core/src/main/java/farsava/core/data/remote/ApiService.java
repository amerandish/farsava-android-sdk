package farsava.core.data.remote;

import java.util.List;
import java.util.UUID;

import farsava.core.data.model.ASRRequestBodyData;
import farsava.core.data.model.ASRRequestBodyURI;
import farsava.core.data.model.ASRResponseBody;
import farsava.core.data.model.LanguageModelResult;
import farsava.core.data.model.LanguageModelTrainRequestBody;
import farsava.core.data.model.TTSRequestBody;
import farsava.core.data.model.TTSResponseBody;
import farsava.core.data.model.VoiceSelectionParams;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    //region Speech
    @POST("speech/asr")
    Call<ASRResponseBody> postASR(@Body ASRRequestBodyData asrRequestBody);

    @POST("speech/asrlongrunning")
    Call<ASRResponseBody> postASRLongRunning(@Body ASRRequestBodyURI asrRequestBodyURI);

    @GET("speech/transcriptions/{transcriptionId}")
    Call<ASRResponseBody> getTranscription(@Path("transcriptionId") UUID transcriptionId);

    @HTTP(method = "DELETE", path = "speech/transcriptions/{transcriptionId}", hasBody = true)
    Call<Void> deleteTranscription(@Path("transcriptionId") UUID transcriptionId);
    //endregion

    //region LanguageModel
    @GET("speech/languagemodels")
    Call<List<LanguageModelResult>> getLanguageModels();

    @POST("speech/languagemodels")
    Call<LanguageModelResult> postLanguageModel(LanguageModelTrainRequestBody languageModelTrainRequestBody);

    @GET("speech/languagemodels/{languageModelId}")
    Call<LanguageModelResult> getLanguageModel(@Path("languageModelId") Integer languageModelId);
    //endregion

    //region Voice
    @POST("voice/tts")
    Call<TTSResponseBody> postTTS(@Body TTSRequestBody ttsRequestBody);

    @GET("voice/speakers")
    Call<List<VoiceSelectionParams>> getSpeakers();
    //endregion
}
