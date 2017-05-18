package com.zero.nimo.wearhrm;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CurrentHeart extends WearableActivity implements SensorEventListener {

    //ui elements
    private BoxInsetLayout mContainerView;
    private TextView mBpm;
    private TextView mAcc;
    private ProgressBar mProgressBar;

    //sensor and sensorManager
    Sensor mHeartRateSensor;
    SensorManager mSensorManager;
    int sensorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_heart);
        //screen always on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Sensor and Manager
        mSensorManager=((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        //TODO start sensor or external sensor

        mContainerView = (BoxInsetLayout) findViewById(R.id.HrDisplay);
        mBpm = (TextView) findViewById(R.id.textBpm);
        mAcc = (TextView)findViewById(R.id.textAcc);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBarHeart);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(mSensorManager !=null){
            mSensorManager.registerListener(this,mHeartRateSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(mSensorManager !=null){
            mSensorManager.unregisterListener(this);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        //update values
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE){
            if((int)event.values[0]>0){
                mBpm.setText(" "+(int)event.values[0]);
                mProgressBar.setVisibility(View.GONE);

                //show Accuracy
                if (event.accuracy == 1)
                {mAcc.setText("Accuracy: Low");}
                if(event.accuracy == 2)
                {mAcc.setText("Accuracy: Mid");}
                if(event.accuracy == 3)
                {mAcc.setText("Accuracy: High");}
            }
        }
    }

    public void  onAccuracyChanged(Sensor sensor,int accuracy){

    }
}

