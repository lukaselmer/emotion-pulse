package ch.renuo.emotionpulse;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by digi on 10/12/14.
 */
public class PulseListener implements SensorEventListener {
    private Context context;
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private static final int SENSOR_TYPE_HEARTRATE = 65562;
    private static final String TAG = "PulseListener";
    private float currentRate = 0;
    private CountDownLatch latch;


    public PulseListener(Context context) {
        this.context = context;
        mSensorManager = ((SensorManager) context.getSystemService(Context.SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(SENSOR_TYPE_HEARTRATE); // using Sensor Lib2 (Samsung Gear Live)
        //mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE); // using Sensor Lib (Samsung Gear Live)

        latch = new CountDownLatch(1);
        latch.countDown();

        mSensorManager.registerListener(this, this.mHeartRateSensor, 3);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        try {
            latch.await();
            if(sensorEvent.values[0] > 0){
                Log.d(TAG, "sensor event: " + sensorEvent.accuracy + " = " + sensorEvent.values[0]);
                currentRate = Float.parseFloat(String.valueOf(sensorEvent.values[0]));
            }

        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d(TAG, "accuracy changed: " + i);
    }

    public float getCurrentRate() {
        return currentRate;
    }


}
