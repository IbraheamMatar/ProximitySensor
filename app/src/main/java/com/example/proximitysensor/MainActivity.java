package com.example.proximitysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor =null;
    Sensor sensor2;
     ImageView imgView;
     TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tv= findViewById(R.id.tv);
        imgView = findViewById(R.id.imgView);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) !=null &&sensorManager.getDefaultSensor(sensor.TYPE_ACCELEROMETER)!=null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }else {
            Toast.makeText(this, "GYROSCOPE is not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            tv.setText("TYPE_ACCELEROMETER");
        }

            if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
           int Val = (int)sensorEvent.values[0];
           if(sensorEvent.values[0]>0){
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imgView,"scaleX",3f);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imgView,"scaleY",3f);
              objectAnimatorX.setDuration(1000);
              objectAnimatorY.setDuration(1000);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();
            tv.setText("TYPE_PROXIMITY");
        }else {

               ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imgView,"scaleX",0.2f);
               ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imgView,"scaleY",0.2f);
               objectAnimatorX.setDuration(1000);
               objectAnimatorY.setDuration(1000);
               AnimatorSet animatorSet = new AnimatorSet();
               animatorSet.play(objectAnimatorX).with(objectAnimatorY);
               animatorSet.start();
           }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensor2,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

}