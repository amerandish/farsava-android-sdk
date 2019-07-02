package com.amerandish.farsava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.amerandish.farsava.core.FarsAva;
import com.amerandish.farsava.core.Model.ASRResponseBody;
import com.amerandish.farsava.core.Model.ErrorResponseBody;
import com.amerandish.farsava.core.Network.FarsAvaCallback;

public class MainActivity extends AppCompatActivity {
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE1NjQ0NzA0OTIsIm5iZiI6MTU2MTg3ODQ5MiwiaWF0IjoxNTYxODc4NDkyLCJpc3MiOiJpYW0tbXMtaWFtIiwiZXh0Ijp7InNydiI6ImU3M2FlNjAzLTUwM2QtNDkyYS1iNjJiLTliYzk3OTU4YjIzZiJ9LCJzdWIiOiJrYWxhbnRhcmlhbiIsInNjcCI6WyJhc3IvcG9zdCIsImFzci9saXZlIl19.ZOG4l9gvueps41psf7MKAxf8aaBC5MlMiIzprI0Hka0UfekFnGi4wCqHehBYcre6yPtX-i6KCT1M2_I6yft0HIK1zo_kQ8BRd-rL5CDHqhyaW8VT5RWZVvlDRhCuqkZtQMfUdUf1ME23Gl11XI9bPxjcAI0t4OXYaWJFqDJSZVRsKUJX93YjBnYez6N3GZxMj0coQb1pGVGVbSFj9ZN_i4dHA1M_RDJRiXV73ahg2_h-S15OI7ZEwqsKrIuTeOfvNvJVLAocYb6vsU5xbSX2Puo8XOXs4hOwWdpz7Ie5VnjFxhFQfX4jQIotlkwugmMjFdJwDGfgLRZLcLTRQbEhEUGLYAZG_G16NxmFr4BG0pu30x1hHAUnckpTc8OWHVUKjC-XwytWsv3BFbuH_Fh24C6uhpepej7_pSXVzvRWkGhQlte9okzZJqefEz7p7c9_r7ydRr-sBrimEuK5Up01xRKkCrttRqE97NaNfHHTLcM0f7ZdNQM-17PdHtiyyJFOVBudgB3LXfgsFI6kqIIvOQQa2hUZeP9Cecl3ISC2l4E8Xo77k012PEDXPFuEVEEWx1P0Ngc7Hesl71AJ7DptmOfKG_jv6qaOOzudkka_HvFJHqi-xbf2DQz3Wdvx3FGGQzTiYyiJlU6hSAf7dSgMz5eier5m2DG1REN9rzawuyI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FarsAva farsAva = new FarsAva.Builder()
                .setJWTTokent(token)
                .build();

        farsAva.asr(null, new FarsAvaCallback<ASRResponseBody>() {
            @Override
            public void onSuccess(ASRResponseBody responseBody) {

            }

            @Override
            public void onFail(ErrorResponseBody errorBody) {

            }
        });
    }
}
