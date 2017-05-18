package com.zero.nimo.wearhrm;

import android.support.wearable.view.WearableRecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jer on 4/25/17.
 */

public class RecyclerAdapter extends WearableRecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    //set of menu/images
    private String[] mOptionSet;
    int[] imageList;

    //controller for selection
    private Controller mController;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder,final int position) {
        //listens to clicks on text
        viewholder.mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mController.itemSelected(mOptionSet[position]);
            }
        });
        //listen to clicks on images in list
        viewholder.mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                mController.imageSelected(imageList[position]);
                //Log.d("image", String.valueOf(viewholder.mImageView.setImageResource(imageList[position])));

            }
        });
        //fills in correct info for each row
        viewholder.mTextView.setText(mOptionSet[position]);
        viewholder.mImageView.setImageResource(imageList[position]);


    }

    @Override
    public int getItemCount() {
        return mOptionSet.length;
    }

    //data reference
    public static class ViewHolder extends WearableRecyclerView.ViewHolder{
    private final TextView mTextView;
    private final ImageView mImageView;

    public ViewHolder(View view){
        super(view);
        mTextView=(TextView) view.findViewById(R.id.textView);
        mImageView=(ImageView) view.findViewById(R.id.imageView);
    }

    @Override
    public String toString(){
        return (String) mTextView.getText();
    }
}

public RecyclerAdapter(int[] images,String[] optionSet, Controller controller){
    mOptionSet= optionSet;
    imageList=images;
    mController=controller;

}

}
