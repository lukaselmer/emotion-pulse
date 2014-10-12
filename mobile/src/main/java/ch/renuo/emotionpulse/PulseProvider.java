package ch.renuo.emotionpulse;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.util.Random;

/**
 * Created by digi on 10/11/14.
 */
public class PulseProvider implements MessageApi.MessageListener {

    public static final String QUERY_PULSE_PATH = "/get/pulse";
    public static final String REPLY_PULSE_PATH = "/post/pulse";
    public static final String HEART_RATE_KEY = "HEART_RATE";

    private static final String TAG = "PulseSource";
    private GoogleApiClient mGoogleApiClient;
    private Random pulseSensor;
    private Context context;

    private static final String HEART_RATE_PATH = "/update/heartrate";

    private int rate = 89;


    public PulseProvider(Context context) {
        this.context = context;
        initClient();
        Log.d(QUERY_PULSE_PATH, "Created PulseSource");
        Wearable.MessageApi.addListener(mGoogleApiClient, this);
        this.pulseSensor = new Random();
    }

    protected Context getContext() {
        return context;
    }


    protected void initClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(new ConnectionCallbacks() {

                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();

        mGoogleApiClient.connect();
    }

    public int getPulse() {
//        PutDataMapRequest dataMap = PutDataMapRequest.create(QUERY_PULSE_PATH);
//        dataMap.getDataMap().putInt(HEART_RATE_KEY, 0);
//        PutDataRequest request = dataMap.asPutDataRequest();
//        PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, request);
//        pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
//            @Override
//            public void onResult(DataApi.DataItemResult dataItemResult) {
//                Log.d(HEART_RATE_KEY, dataItemResult.getDataItem().toString());
//            }
//        });
        try {
            rate = Math.max(Math.min(rate + (pulseSensor.nextInt(5)-2), 196), 73);
        } catch (Exception ex) {
            rate = 90;
        }

        return rate;
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(HEART_RATE_PATH)) {
            Log.d(TAG, messageEvent.toString());
        }
    }

}
