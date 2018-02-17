package be.kevindenys.wieishet.interfaces;

import android.content.Context;
import android.view.View;

/**
 * Created by Kevin on 30-10-2017.
 */

public interface ItemTouchHelperAdapter {
    //position in recylerview
    public void swipeToDelete(int position);
}