package com.zero.nimo.wearhrm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WearableRecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

import static android.Manifest.permission.BODY_SENSORS;

public class MainActivity extends WearableActivity {

    /**
     * make constants for switch statement for choosing
     * items to implement next activity
     * @param savedInstanceState
     */
    private static final String CurrentHeartRate = "Current Heart Rate";
    private static final String ActivityHeartRate ="Activity Heart Rate";
    private static final String Saving = "Saved";
//    private static final String BlueHeart = "Hrm Device";
    private static final String InfoAbout = "About";

    //images to be stored for menu selection
    int images[]={R.drawable.heart_icon,R.drawable.ic_launcher,R.drawable.ic_save,R.drawable.ic_about};

    //create string array for recycleViewer
    private static final String[] ListOptions= {CurrentHeartRate,ActivityHeartRate,Saving,InfoAbout};

    //create frame objects
    //private FrameLayout mMainFrameLO;
    private WearableRecyclerView mWearableRV;
    private RecyclerAdapter mRecyclerAdapter;




    final private int REQUEST_SENSOR_PERMISSION =1;
    private void startingSensor(){
        int hasSensorPermission = checkSelfPermission(Manifest.permission.BODY_SENSORS);
        if(hasSensorPermission != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {
                    Manifest.permission.BODY_SENSORS},
                    REQUEST_SENSOR_PERMISSION);
            return;
            }
       //     startingSensor();
        }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startingSensor();
        //define views
        //mMainFrameLO = (FrameLayout) findViewById(R.id.MainFrameLO);
        mWearableRV =(WearableRecyclerView) findViewById(R.id.MainWRV);

        //align firs and last elements vertically centered on screen
        mWearableRV.setCenterEdgeItems(true);

        //custom scrolling and alignment curve scroll
        CircularOffsettingHelper circularOffsettingHelper = new CircularOffsettingHelper();
        mWearableRV.setOffsettingHelper(circularOffsettingHelper);

        //fix the size no need for it to change
        mWearableRV.setHasFixedSize(true);

        //adapter sent data
        mRecyclerAdapter = new RecyclerAdapter(images,ListOptions, new Controller(this));

        //set adapter
        mWearableRV.setAdapter(mRecyclerAdapter);
    }
    //what is called when item selected
    public void itemSelected(String data){

    //what we choose on recycleview
String listSelected = data;
        Log.d("list", listSelected);
    switch(listSelected) {
        case CurrentHeartRate:
            CurrentHeartRateActivity();
            break;
        case ActivityHeartRate:
            HeartRateActivity();
            break;
        case Saving:
            HeartStorageActivity();
            break;
        case InfoAbout:
            aboutActiviyt();
            break;
    }
    }


    public void itemSelected(int imageChosen) {
        int imagelist = imageChosen;
            Log.d("imagelist", String.valueOf(imagelist));
        switch(imagelist) {
            case 2130837537:
                CurrentHeartRateActivity();
                finish();
                break;
            case 2130837554:
                HeartRateActivity();
                finish();
                break;
            case 2130837505:
                HeartStorageActivity();
                finish();
                break;
            case 2130837557:
                aboutActiviyt();
                break;
        }
    }
    public void CurrentHeartRateActivity(){
        Intent intent = new Intent(this, CurrentHeart.class);
        startActivity(intent);
    }
    public void HeartRateActivity(){
        Intent intent = new Intent(this, HeartActivity.class);
        startActivity(intent);
    }
    public void HeartStorageActivity(){
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }
    public void aboutActiviyt(){
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

}
