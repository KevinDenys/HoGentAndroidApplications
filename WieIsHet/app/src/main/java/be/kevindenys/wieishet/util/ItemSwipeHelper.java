package be.kevindenys.wieishet.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import be.kevindenys.wieishet.interfaces.Direction;
import be.kevindenys.wieishet.interfaces.ItemTouchHelperAdapter;

/**
 * Created by Kevin on 30-10-2017.
 */
//Deze tutorial gevolgd: http://www.tothenew.com/blog/swipe-to-dismiss-recyclerview/

//Nieuwe klasse zodat we de ItemTouchHelper Callback kunnen gebruiken
public class ItemSwipeHelper extends ItemTouchHelper.Callback {

    private int direction;
    private ItemTouchHelperAdapter itemTouchHelperAdapter;

    //we maken een swipe helper aan, in deze applicatie wordt er enkel een linker swipe helper gemaakt, maar we implementeren alles zodat er later bijvoorbeeld naar rechts geswiped kan worden
    public ItemSwipeHelper(int direction,ItemTouchHelperAdapter itemTouchHelperAdapter) {
        this.direction = direction;
        this.itemTouchHelperAdapter= itemTouchHelperAdapter;
    }


//Hier gebruiken we de directie waarop er geswiped is om te weten naar waar we het item moeten bewegen
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeDirection=0;
        switch(direction)
        {
            case Direction.LEFT:
                swipeDirection=ItemTouchHelper.LEFT;
                break;
            case Direction.RIGHT:
                swipeDirection=ItemTouchHelper.RIGHT;
                break;
            case Direction.UP:
                swipeDirection=ItemTouchHelper.UP;
                break;

            case Direction.DOWN:
                swipeDirection=ItemTouchHelper.DOWN;
                break;
        }

        return makeMovementFlags(0,swipeDirection);
    }
//Wat er zou moeten gebeuren als we het item verplaatsen in de recyclerview, gebruiken wij niet dus false
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    //wat moet er gebeurt worden als er geswiped wordt. wij gaan enkel verwijderen
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchHelperAdapter.swipeToDelete(viewHolder.getAdapterPosition());
    }
}