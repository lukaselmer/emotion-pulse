package ch.renuo.emotionpulse;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessScopes;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.DataTypes;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourceListener;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.concurrent.TimeUnit;

import android.util.Log;

/**
 * Created by digi on 10/11/14.
 */
public class PulseSource {

    public PulseSource() {

    }

    public int getPulse() {
        return 86;
    }

//    public static final String TAG = "PulseSourceReader";
//    // [START auth_variable_references]
//    private static final int REQUEST_OAUTH = 1;
//
//    private static final String AUTH_PENDING = "auth_state_pending";
//    private boolean authInProgress = false;
//
//    private GoogleApiClient mClient = null;
//    // [END auth_variable_references]
//
//    // [START mListener_variable_reference]
//    // Need to hold a reference to this listener, as it's passed into the "unregister"
//    // method in order to stop all sensors from sending data to this listener.
//    private DataSourceListener mListener;
//    // [END mListener_variable_reference]
//
//    public static int getPulse() {
//        new PulseSource();
//    }
//
//    public PulseSource() {
//
//        buildFitnessClient();
//        mClient.connect();
//    }
//
//    private void buildFitnessClient() {
//        // Create the Google API Client
//        mClient = new GoogleApiClient.Builder(this)
//                .addApi(Fitness.API)
//                .addScope(FitnessScopes.SCOPE_LOCATION_READ)
//                .addConnectionCallbacks(
//                        new GoogleApiClient.ConnectionCallbacks() {
//
//                            @Override
//                            public void onConnected(Bundle bundle) {
//                                Log.i(TAG, "Connected!!!");
//                                // Now you can make calls to the Fitness APIs.
//                                // Put application specific code here.
//                                // [END auth_build_googleapiclient_beginning]
//                                //  What to do? Find some data sources!
//                                findFitnessDataSources();
//
//                                // [START auth_build_googleapiclient_ending]
//                            }
//
//                            @Override
//                            public void onConnectionSuspended(int i) {
//                                // If your connection to the sensor gets lost at some point,
//                                // you'll be able to determine the reason and react to it here.
//                                if (i == ConnectionCallbacks.CAUSE_NETWORK_LOST) {
//                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
//                                } else if (i == ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
//                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
//                                }
//                            }
//                        }
//                )
//                .addOnConnectionFailedListener(
//                        new GoogleApiClient.OnConnectionFailedListener() {
//                            // Called whenever the API client fails to connect.
//                            @Override
//                            public void onConnectionFailed(ConnectionResult result) {
//                                Log.i(TAG, "Connection failed. Cause: " + result.toString());
//
//                                // The failure has a resolution. Resolve it.
//                                // Called typically when the app is not yet authorized, and an
//                                // authorization dialog is displayed to the user.
//                                if (!authInProgress) {
//                                    try {
//                                        Log.i(TAG, "Attempting to resolve failed connection");
//                                        authInProgress = true;
//                                        result.startResolutionForResult(MainActivity.this,
//                                                REQUEST_OAUTH);
//                                    } catch (IntentSender.SendIntentException e) {
//                                        Log.e(TAG,
//                                                "Exception while starting resolution activity", e);
//                                    }
//                                }
//                            }
//                        }
//                )
//                .build();
//    }
//    // [END auth_build_googleapiclient_ending]
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_OAUTH) {
//            authInProgress = false;
//            if (resultCode == RESULT_OK) {
//                // Make sure the app is not already connected or attempting to connect
//                if (!mClient.isConnecting() && !mClient.isConnected()) {
//                    mClient.connect();
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean(AUTH_PENDING, authInProgress);
//    }
//    // [END auth_connection_flow_in_activity_lifecycle_methods]
//
//    /**
//     * Find available data sources and attempt to register on a specific {@link DataType}.
//     * If the application cares about a data type but doesn't care about the source of the data,
//     * this can be skipped entirely, instead calling
//     * {@link com.google.android.gms.fitness.SensorsApi
//     * #register(GoogleApiClient, SensorRequest, DataSourceListener)},
//     * where the {@link SensorRequest} contains the desired data type.
//     */
//    private void findFitnessDataSources() {
//        // [START find_data_sources]
//        Fitness.SensorsApi.findDataSources(mClient, new DataSourcesRequest.Builder()
//                // At least one datatype must be specified.
//                .setDataTypes(DataTypes.LOCATION_SAMPLE)
//                        // Can specify whether data type is raw or derived.
//                .setDataSourceTypes(DataSource.TYPE_RAW)
//                .build())
//                .setResultCallback(new ResultCallback<DataSourcesResult>() {
//                    @Override
//                    public void onResult(DataSourcesResult dataSourcesResult) {
//                        Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
//                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
//                            Log.i(TAG, "Data source found: " + dataSource.toString());
//                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());
//
//                            //Let's register a listener to receive Activity data!
//                            if (dataSource.getDataType().equals(DataTypes.LOCATION_SAMPLE)
//                                    && mListener == null) {
//                                Log.i(TAG, "Data source for LOCATION_SAMPLE found!  Registering.");
//                                registerFitnessDataListener(dataSource, DataTypes.LOCATION_SAMPLE);
//                            }
//                        }
//                    }
//                });
//        // [END find_data_sources]
//    }
//
//    /**
//     * Register a listener with the Sensors API for the provided {@link DataSource} and
//     * {@link DataType} combo.
//     */
//    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
//        // [START register_data_listener]
//        mListener = new DataSourceListener() {
//            @Override
//            public void onEvent(DataPoint dataPoint) {
//                for (Field field : dataPoint.getDataType().getFields()) {
//                    Value val = dataPoint.getValue(field);
//                    Log.i(TAG, "Detected DataPoint field: " + field.getName());
//                    Log.i(TAG, "Detected DataPoint value: " + val);
//                }
//            }
//        };
//
//        Fitness.SensorsApi.register(
//                mClient,
//                new SensorRequest.Builder()
//                        .setDataSource(dataSource) // Optional but recommended for custom data sets.
//                        .setDataType(dataType) // Can't be omitted.
//                        .setSamplingRate(10, TimeUnit.SECONDS)
//                        .build(),
//                mListener)
//                .setResultCallback(new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        if (status.isSuccess()) {
//                            Log.i(TAG, "Listener registered!");
//                        } else {
//                            Log.i(TAG, "Listener not registered.");
//                        }
//                    }
//                });
//        // [END register_data_listener]
//    }
//
//    /**
//     * Unregister the listener with the Sensors API.
//     */
//    private void unregisterFitnessDataListener() {
//        if (mListener == null) {
//            // This code only activates one listener at a time.  If there's no listener, there's
//            // nothing to unregister.
//            return;
//        }
//
//        // [START unregister_data_listener]
//        // Waiting isn't actually necessary as the unregister call will complete regardless,
//        // even if called from within onStop, but a callback can still be added in order to
//        // inspect the results.
//        Fitness.SensorsApi.unregister(
//                mClient,
//                mListener)
//                .setResultCallback(new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//                        if (status.isSuccess()) {
//                            Log.i(TAG, "Listener was removed!");
//                        } else {
//                            Log.i(TAG, "Listener was not removed.");
//                        }
//                    }
//                });
//        // [END unregister_data_listener]
//    }

}
