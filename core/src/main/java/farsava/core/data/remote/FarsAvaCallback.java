package farsava.core.data.remote;

public interface FarsAvaCallback<T> {
    void onSuccess(T responseBody);

    void onFail(String errorMessage);
}
