package ch.renuo.emotionpulse;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    private Button mSendMessage;
    private GoogleApiClient mGoogleApiClient;
    private boolean mConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mSendMessage = (Button) stub.findViewById(R.id.send_message_button);
                mSendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendMessage("button clicked");
                    }
                });
            }
        });


        final String TAG = "asdf";
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                        mConnected = true;
                        sendMessage("connected");
                        // Now you can use the data layer API
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                        mConnected = false;
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

        sendMessage("auto button clicked");
    }

    private List<String> getNodes() {
        List<String> results = new ArrayList<String>();

        if (!mConnected) return results;

        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }
        return results;
    }

    private void sendMessage(String msg) {
        Log.e("sending message", msg);

        String START_ACTIVITY_PATH = "/start/MainActivity";

        List<String> nodes = getNodes();
        Log.e("node", nodes.toString());

        if (nodes.size() == 0) return;

        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                mGoogleApiClient, nodes.get(0), START_ACTIVITY_PATH, null).await();
        if (!result.getStatus().isSuccess()) {
            Log.e("error", "ERROR: failed to send Message: " + result.getStatus());
        }


    }
}
