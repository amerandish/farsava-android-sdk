package farsava.core.data.remote;

public interface FarsAvaLiveCallback<T> {
    void onStart();

    void onResponse(T responseBody);

    void onFinish(T responseBody);
}
