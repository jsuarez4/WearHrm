package com.zero.nimo.wearhrm;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class About extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.textAbout);

                mTextView.setText("This is a standalone application" +
                        " Accuracy tells how accurate the reading is 1 being lowest 3 highest" +
                        " Tightening or loosening watch can improve accuracy" +
                        " may not always be 100 accurate");
            }
        });
    }
}
