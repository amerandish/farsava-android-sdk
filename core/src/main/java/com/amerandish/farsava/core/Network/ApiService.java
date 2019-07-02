package com.amerandish.farsava.core.Network;

import com.amerandish.farsava.core.Model.ASRRequestBodyData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface ApiService {
    @GET("speech/asr")
    Call<Object> asr(@Body ASRRequestBodyData asrRequestBody);

}
