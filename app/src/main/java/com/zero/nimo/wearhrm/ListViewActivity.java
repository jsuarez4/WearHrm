package com.zero.nimo.wearhrm;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

public class ListViewActivity extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);




        DBHandler handler = new DBHandler(this);
// Get access to the underlying writeable database
        SQLiteDatabase db = handler.getWritableDatabase();
        SQLiteDatabase dbaa = handler.getReadableDatabase();
       // Cursor cur =  dbaa.rawQuery("SELECT id as _id * FROM heart", null);

// Query for items from the database and get a cursor back
        Cursor todoCursor = db.rawQuery("SELECT  * FROM heart", null);

        // Find ListView to populate
        ListView lvItems = (ListView) findViewById(R.id.listView1);
// Setup cursor adapter using cursor from last step
        ListAdapter todoAdapter = new ListAdapter(this, todoCursor);
// Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);


        }


    @Override
    protected void onResume() {

        super.onResume();
    }

}