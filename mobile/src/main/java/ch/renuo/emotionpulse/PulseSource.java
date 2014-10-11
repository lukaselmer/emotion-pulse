package ch.renuo.emotionpulse;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by digi on 10/11/14.
 */
public class PulseSource {

    private static final String TAG = "PulseSource";
    private GoogleApiClient mGoogleApiClient;
    private boolean mGoogleApiClientReady = false;
    private Node node;
    private Context context;

    public PulseSource(Context context) {
        this.context = context;
initClient();

    }

    protected Context getContext() {
        return context;
    }

    protected void initNode() {

        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
       node = nodes.getNodes().get(0);
    }

    protected void initClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(new ConnectionCallbacks() {

                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                        initNode();
                        mGoogleApiClientReady = true;

                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                        mGoogleApiClientReady = false;
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                        mGoogleApiClientReady = false;
                    }
                })
                .addApi(Wearable.API)
                .build();
    }

    public int getPulse() {
        if (!mGoogleApiClientReady) {
            return 0;
        }

        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                mGoogleApiClient, "test", "test2", null).await();

        if (!result.getStatus().isSuccess()) {
            Log.e(TAG, "ERROR: failed to send Message: " + result.getStatus());
            return 0;
        }

        return 100;
    }

}
