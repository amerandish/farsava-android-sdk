package farsava.core.Network;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import farsava.core.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final String TAG = "Fars Ava Core - Network";

    // JWT Token
    private static String token;
    // Accessible URLs
    private static String baseUrl = "https://api.amerandish.com/v1/";

    private static Retrofit retrofit = null;
    private static WebSocketClient socket = null;

    public static void setBaseUrl(String baseUrl) {
        NetworkManager.baseUrl = baseUrl;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        NetworkManager.token = token;
    }

    //region Retrofit Singleton
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (token != null) {
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        // Building custom request
                        Request request = original.newBuilder()
                                .addHeader("Authorization", "Bearer " + token)
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json")
                                .method(original.method(), original.body())
                                .build();

                        Response response = chain.proceed(request);

                        // Unauthorized access
                        if (response.code() == 401) {
                            Log.e(TAG, "JWT-Token is not authorized.");
                        }
                        return response;
                    }
                });
            }

            // Debug Log
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.level(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }

            httpClient
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES);

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
    //endregion
}
