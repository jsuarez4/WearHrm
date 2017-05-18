package com.zero.nimo.wearhrm;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by jer on 4/26/17.
 */

public class ListAdapter extends CursorAdapter {

    public ListAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);

    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listviewdatalayout, parent, false);

    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        // Find fields to populate in inflated template

        TextView tvAverage= (TextView) view.findViewById(R.id.textViewAverage);
        TextView tvDate = (TextView) view.findViewById(R.id.textViewDate);
        TextView tvTime = (TextView) view.findViewById(R.id.textViewTime);
        // Extract properties from cursor
        //int id = cursor.getInt(cursor.getColumnIndexOrThrow("_ID"));

        String date = cursor.getString(cursor.getColumnIndexOrThrow("Date"));
        String heartTime = cursor.getString(cursor.getColumnIndexOrThrow("Heart_Timer"));
        String heartAverage=cursor.getString(cursor.getColumnIndexOrThrow("Heart_Average"));

        // Populate fields with extracted properties
        tvDate.setText(date);
        tvTime.setText(heartTime);
        tvAverage.setText(heartAverage);
    }
}