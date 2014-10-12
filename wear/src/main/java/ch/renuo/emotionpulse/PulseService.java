package ch.renuo.emotionpulse;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by digi on 10/12/14.
 */
public class PulseService extends WearableListenerService {

    public static final String TAG = "PulseService";
    public static final String QUERY_PULSE_PATH = "/get/pulse";

    private PulseListener pulseListener;

//    @Override
//    public void onCreate() {
//        Log.d(TAG, "Yay!");
////        //  Is needed for communication between the wearable and the device.
////        mGoogleApiClient = new GoogleApiClient.Builder(this)
////                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
////                    @Override
////                    public void onConnectionFailed(ConnectionResult result) {
////                        Log.d(TAG, "onConnectionFailed: " + result);
////                    }
////                })
////                .addApi(Wearable.API)
////                .build();
////        mGoogleApiClient.connect();
////        pulseListener = new PulseListener(getApplicationContext());
//
//    }
//
//    @Override
//    public void onPeerConnected(Node peer) {
//        super.onPeerConnected(peer);
//        Log.d(TAG, "Started");
//        Log.d(TAG, peer.toString());
//    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "Yay!");
        if(pulseListener == null) {
            pulseListener = new PulseListener(getApplicationContext());
        }
//        for (DataEvent event : dataEvents) {
//            if (event.getType() == DataEvent.TYPE_DELETED) {
//                Log.d(TAG, "DataItem deleted: " + event.getDataItem().getUri());
//            } else if (event.getType() == DataEvent.TYPE_CHANGED) {
//                Log.d(TAG, "DataItem changed: " + event.getDataItem().getUri());
//            }
//        }
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals(QUERY_PULSE_PATH)) {
            Log.d(QUERY_PULSE_PATH, "got the request");

            Intent startIntent = new Intent(this, MainActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);
        }
    }
}
