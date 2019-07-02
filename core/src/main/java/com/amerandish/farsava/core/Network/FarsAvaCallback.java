package com.amerandish.farsava.core.Network;

import com.amerandish.farsava.core.Model.ErrorResponseBody;

public interface FarsAvaCallback<T> {
    void onSuccess(T responseBody);

    void onFail(ErrorResponseBody errorBody);
}
