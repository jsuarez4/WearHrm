package com.zero.nimo.wearhrm;

import android.util.Log;

/**instruct changes on mainActivity based on item selected on recycler_row
 * Created by jer on 4/25/17.
 */

public class Controller {
    private MainActivity mView;

    Controller(MainActivity mainActivity){
        mView = mainActivity;
    }
    //choosing text
    public void itemSelected(String listSelected){
        mView.itemSelected(listSelected);
    }
    //choosing image
    public void imageSelected(int imageSelected) {
        mView.itemSelected(imageSelected);
    }
}
