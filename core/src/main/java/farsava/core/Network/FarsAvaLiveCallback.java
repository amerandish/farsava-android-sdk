package farsava.core.Network;

public interface FarsAvaLiveCallback<T> {
    void onStart();

    void onResponse(T responseBody);

    void onFinish(T responseBody);
}
