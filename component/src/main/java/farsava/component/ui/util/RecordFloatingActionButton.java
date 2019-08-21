package farsava.component.ui.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import farsava.core.FarsAvaLiveService;
import farsava.core.data.model.ASRResponseBody;
import farsava.core.data.remote.FarsAvaLiveCallback;
import farsava.core.util.ResponseHelper;

/**
 * Created by MHK on 2019-08-11.
 * www.MHKSoft.com
 */
public class RecordFloatingActionButton extends FloatingActionButton {
    private String TAG = "FarsAvaRecordFloatingActionButton";
    private FarsAvaLiveService liveService;
    private boolean initialized = false;

    public RecordFloatingActionButton(Context context) {
        super(context);
    }

    public void initialize(final TextView responseView, String JWTToken, final boolean progressive) {
        if (!initialized) {
            // Initializing LiveService
            liveService = new FarsAvaLiveService.Builder().setJWTTokent(JWTToken).build();
            // Static OnClickListener
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    liveService.start(new FarsAvaLiveCallback<ASRResponseBody>() {
                        @Override
                        public void onStart() {
                            //Start loading animation
                        }

                        @Override
                        public void onResponse(ASRResponseBody responseBody) {
                            if (progressive) {
                                responseView.setText(ResponseHelper.compileResultToString(responseBody));
                            }
                        }

                        @Override
                        public void onFinish(ASRResponseBody responseBody) {
                            responseView.setText(ResponseHelper.compileResultToString(responseBody));
                            //End loading animation
                        }
                    });
                }
            });
            initialized = true;
        } else
            Log.e(TAG, "Already initialized.");
    }

    public void recycle() {
        initialized = false;
        setOnClickListener(null);
        //Recycle animations
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (!initialized)
            super.setOnClickListener(l);
        else
            Log.e(TAG, "setOnClickListener() is not accessible for editing after initialized.");
    }
}
