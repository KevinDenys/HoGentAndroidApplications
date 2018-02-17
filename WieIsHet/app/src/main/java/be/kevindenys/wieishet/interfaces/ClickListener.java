package be.kevindenys.wieishet.interfaces;

import android.view.View;

/**
 * Created by Kevin on 30-10-2017.
 */

public interface ClickListener{
    //position in recylerview
    public void onClick(View view,int position);
    public void onLongClick(View view, int position);
}