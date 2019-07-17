package farsava.core.Network;

public interface FarsAvaCallback<T> {
    void onSuccess(T responseBody);

    void onFail(String errorMessage);
}
