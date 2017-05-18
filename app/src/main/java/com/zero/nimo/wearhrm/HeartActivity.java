package com.zero.nimo.wearhrm;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.os.SystemClock;
import android.support.annotation.BoolRes;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;


import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.OptionalDouble;


public class HeartActivity extends WearableActivity implements SensorEventListener {

    //ui elements
    private BoxInsetLayout mContainerView;
    private TextView mBpm;
    private TextView mAcc;
    private ProgressBar mProgressBar;
    private ImageButton mImageButton;
    private Chronometer mChronometer;
    private TextView mSave;
    //sum of heart
    int sum = 0;
    //string bool
    String stage;

    //date
    Calendar c = Calendar.getInstance();
    SimpleDateFormat date = new SimpleDateFormat("MMM-dd");
    String formatted = date.format(c.getTime());


    //Long value for timer
    long stopTime = 0;
    String timerLog;

    //sensor and sensorManager
    Sensor mHeartRateSensor;
    SensorManager mSensorManager;
    int sensorId;

    //database
    SQLiteDatabase SQLITEDATABASE;

    //arraylist of Heart rates
    ArrayList<Integer> bpmlist = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart);

        //screen always on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Sensor and Manager
        mSensorManager=((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        //TODO start sensor or external sensor

        //initiate Views
        mBpm = (TextView) findViewById(R.id.textBpmActivity);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBarHeartActivity);
        mImageButton=(ImageButton)findViewById(R.id.imageStartActivity);
        mChronometer=(Chronometer)findViewById(R.id.timerTrack);
        mSave =(TextView) findViewById(R.id.textClickActivity);

        //listen for imageButton
        addListenerOnImage();


    }
        public void addListenerOnImage(){
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String flag =(String) mImageButton.getTag();
                    stage = "0";
                    //change flag on clicks of image button
                    if(flag=="0"){
                        //change image on click
                        mImageButton.setImageResource(R.drawable.ic_play);
                        mImageButton.setTag("1");

                        //set stopwatch to stop
                        stopTime = mChronometer.getBase()-SystemClock.elapsedRealtime();
                        mChronometer.stop();
                        Log.d("Timer", mChronometer.getText().toString());

                        //start listener for click on save nad make visiable
                        addListenerOnTime();
                        mSave.setVisibility(View.VISIBLE);

                    }else{
                        //change image on clicks
                        mImageButton.setImageResource(R.drawable.ic_pause);
                        mImageButton.setTag("0");
                        stage = "1";
                        //set stopwatch to start
                        mChronometer.setBase(SystemClock.elapsedRealtime()+stopTime);
                        mChronometer.start();
                        //set invisiable save button for only when paused
                        mSave.setVisibility(View.GONE);
                    }
                }
            });
        }
        public void addListenerOnTime(){
            mSave.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    timerLog = mChronometer.getText().toString();
                    Log.d("LogTimer", timerLog);
                    System.out.println("Time of Activity "+mChronometer.getText());
                    StoreActivity();
                }
            });
                                        }
    public void StoreActivity() {
            averageOfList();
                //create db
            DBHandler db = new DBHandler(this);


                // Inserting Shop/Rows
            Log.d("Insert:" , "Inserting..");
            db.addData(new HeartLogs(1, formatted , String.valueOf(sum), mChronometer.getText().toString()));

            DBCreate();
             storeValues();
             Log.d("HeartList", bpmlist.toString());

//            // Reading all shops
//            Log.d("Reading: ", "Reading all shops..");
//            List<HeartLogs> datas = db.getAllShops();
//
//            for (HeartLogs dbHeart : datas) {
//                String log = "Id: "+dbHeart.getId() + " ,Date: "+dbHeart.getDate() + " ,Average Heart Rate: "
//                +dbHeart.getAverage() +" ,Duration " + dbHeart.getTime();
//                // Writing shops to log
//                Log.d("Shop: : ",log);
//            }

                                 }
    public void storeValues(){
                Intent intent = new Intent(this,ListViewActivity.class);

                startActivity(intent);
                //DBCreate();
                finish();

                            }

    public void DBCreate(){

        SQLITEDATABASE = openOrCreateDatabase("heart", Context.MODE_PRIVATE, null);

        SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS heart(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, phone_number VARCHAR, subject VARCHAR);");
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

    public void averageOfList(){

        try {
            for (int total : bpmlist) {
                sum += total;
            }
            sum = sum / bpmlist.size();
            Log.d("sum", String.valueOf(sum));
        }
        catch (ArithmeticException ae){
            System.out.println("no heart rate red");
            finish();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        //update values
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE){
            if((int)event.values[0]>0){
                mBpm.setText(" "+(int)event.values[0]);
                mProgressBar.setVisibility(View.GONE);

                //log of values
//                Log.d("text value", String.valueOf((int)event.values[0]));
//                Log.d("arryHeart", mBpm.getText().toString());
                //adding values into list
                if (stage == "1"){
                    bpmlist.add((int)event.values[0]);
                }

//                //show Accuracy
//                if (event.accuracy == 1)
//                {mAcc.setText("Accuracy: Low");}
//                if(event.accuracy == 2)
//                {mAcc.setText("Accuracy: Mid");}
//                if(event.accuracy == 3)
//                {mAcc.setText("Accuracy: High");}
            }
        }
    }

    public void  onAccuracyChanged(Sensor sensor,int accuracy){

    }
}

