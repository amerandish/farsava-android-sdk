package com.amerandish.farsava.core;

import com.amerandish.farsava.core.Model.ASRRequestBodyData;
import com.amerandish.farsava.core.Model.ASRResponseBody;
import com.amerandish.farsava.core.Model.ErrorResponseBody;
import com.amerandish.farsava.core.Network.ApiService;
import com.amerandish.farsava.core.Network.FarsAvaCallback;
import com.amerandish.farsava.core.Network.NetworkManager;

import java.net.Socket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarsAva {
    private ApiService api;
    private Socket socket;
    private ErrorResponseBody connectionErrorResponse = new ErrorResponseBody(103, "Network connection failed.", "Connectivity", "ClientConnectionError");

    private FarsAva(ApiService apiService, Socket socket) {
        this.api = apiService;
        this.socket = socket;
    }

    // API /Speech/ASR
    public void asr(ASRRequestBodyData body, final FarsAvaCallback<ASRResponseBody> callback) {
        api.asr(body).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.body() != null) {
                    if (response.body() instanceof ASRResponseBody)
                        callback.onSuccess((ASRResponseBody) response.body());
                    else if (response.body() instanceof ErrorResponseBody)
                        callback.onFail((ErrorResponseBody) response.body());
                    else if (response.body() instanceof String)
                        callback.onFail(new ErrorResponseBody(103, (String) response.body(), "Bad Request", "HTTPBadRequestError"));
                } else
                    callback.onFail(connectionErrorResponse);
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                callback.onFail(connectionErrorResponse);
            }
        });
    }
    //endregion

    //region Functions

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
        public FarsAva build() {
            // Validation
            if (this.baseUrl != null)
                NetworkManager.setBaseUrl(this.baseUrl);
            if (this.token != null)
                NetworkManager.setToken(this.token);
            else
                throw new IllegalArgumentException("JWT Token cannot be null.");

            return new FarsAva(NetworkManager.getRetrofit().create(ApiService.class), NetworkManager.getSocket());
        }
    }
    //endregion
}
