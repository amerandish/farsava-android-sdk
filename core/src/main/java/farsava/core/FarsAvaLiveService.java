package farsava.core;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import farsava.core.Model.ASRLiveResponseBody;
import farsava.core.Network.FarsAvaLiveCallback;
import farsava.core.Network.NetworkManager;
import farsava.core.Util.NotificationHelper;

public class FarsAvaLiveService {
    private static final String TAG = "Fars Ava Core - Live";
    private WebSocketClient socket;
    private NotificationHelper notificationHelper;

    private AudioRecord recorder;
    private int minBufferSize;
    private byte[] buffer;
    private boolean isLive = false;

    private FarsAvaLiveService(Context context) {
        notificationHelper = new NotificationHelper(context);

        minBufferSize = AudioRecord.getMinBufferSize(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        buffer = new byte[minBufferSize];
    }

    /**
     * <h3>Performs asynchronous live speech recognition using socket</h3>
     * This resource establish a socket with client and receives audio data using socket. It will start transcribing the audio using state-of-the-art deep neural networks and returns the partial results.This endpoint is designed for transcription of stream audio data up to 15 minute.
     *
     * @param responseCallback {@code onStart} called when you are connected.
     *                         {@code onResponse} returns async server response.
     *                         {@code onFinish} returns final server response which is the completed one.
     */
    public void start(final FarsAvaLiveCallback<ASRLiveResponseBody> responseCallback) {
        try {
            if (socket != null && socket.isOpen())
                throw new IllegalStateException("Service is already live. Please stop service before starting another one.");

            socket = new WebSocketClient(new URI("wss://api.amerandish.com/v1/speech/asrlive?jwt=" + NetworkManager.getToken())) {
                @Override
                public void onOpen(ServerHandshake handshakeData) {
                    Log.d(TAG, "connected.");
                    responseCallback.onStart();

                    recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
                    recorder.startRecording();
                    isLive = true;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isLive) {
                                recorder.read(buffer, 0, buffer.length);
                                socket.send(Base64.encode(buffer, Base64.NO_WRAP));
                            }
                        }
                    }).start();

                    // Notify user that the microphone is being recorded
                    notificationHelper.createNotification("فارس آوا", "در حال ضبط و پردازش صدای شما");
                }

                @Override
                public void onMessage(String message) {
                    Log.d(TAG, "success. Response: " + message);

                    ASRLiveResponseBody responseBody = new Gson().fromJson(message, ASRLiveResponseBody.class);

                    if (responseBody.getFinal()) {
                        notificationHelper.updateNotification("فارس آوا", "در حال پردازش نهایی صدای شما");
                        responseCallback.onFinish(responseBody);
                        socket.close();
                    } else
                        responseCallback.onResponse(responseBody);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d(TAG, "closed. Reason: " + reason + code);
                    notificationHelper.closeNotification();
                    stopRecorder();
                }

                @Override
                public void onError(Exception ex) {
                    Log.e(TAG, "faced an error. Reason: " + ex.getMessage());
                    notificationHelper.closeNotification();
                    stop();
                }
            };

            socket.connect();
        } catch (URISyntaxException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    //endregion

    /**
     * Stop the ongoing live
     */
    public void stop() {
        if (isLive) {
            isLive = false;
            recorder.stop();
            recorder.release();
            socket.send("close");
        } else
            throw new IllegalStateException("No live service detected. Please start a service before stopping it.");
    }

    private void stopRecorder() {
        if (isLive) {
            isLive = false;
            recorder.stop();
            recorder.release();
        }
    }

    //region Builder
    public static class Builder {
        private String token;
        private Context context;

        public Builder() {
        }

        public Builder setJWTTokent(String token) {
            this.token = token;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        // Initiate NetworkManager and Build
        public FarsAvaLiveService build() {
            // Validation
            if (this.token != null)
                NetworkManager.setToken(this.token);
            else
                throw new NullPointerException("JWT Token cannot be null.");

            if (this.context == null)
                throw new NullPointerException("Given context cannot be null.");

            return new FarsAvaLiveService(context);
        }
    }
}
