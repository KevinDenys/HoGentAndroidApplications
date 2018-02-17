package be.kevindenys.wieishet.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import be.kevindenys.wieishet.R;
import be.kevindenys.wieishet.activity.App;
import be.kevindenys.wieishet.adapters.viewModels.CharacterViewHolder;
import be.kevindenys.wieishet.data.DataCharacterHandler;
import be.kevindenys.wieishet.interfaces.ItemTouchHelperAdapter;
import be.kevindenys.wieishet.models.Character;

/**
 * Created by Kevin on 30-10-2017.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder> implements ItemTouchHelperAdapter {

    //De data lijst aanmaken, leeg
    List<Character> list = Collections.emptyList();

    public CharacterAdapter(List<Character> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private Context context;

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Layout inladen
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_element_view, parent, false);
        CharacterViewHolder holder = new CharacterViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        //Grote van de lijst terug geven
        return list.size();
    }


    //verwijder item op positie
    public void remove(int position) {
        App app = (App) context.getApplicationContext();
        if(app.isCharChoosen()){ //Kijken of er al een character gekozen is, zo niet da nmoeten we eerst 1 selecteren
            if(list.get(position).getName().equals(app.getChoosenChar().getName())){//als het de character is die gezocht moet worden dan zijn we verloren
                list.remove(position);
                notifyItemRemoved(position);
                app.lost(app.getDialogContext());
            }else{
                list.remove(position);
                notifyItemRemoved(position);
                if(app.getGameDone() == 0 && list.size() == 1){ //Kijken of we als enige overblijven
                    app.won(app.getDialogContext());
                }
            }


        }else{
            Toast.makeText(app, R.string.choosepersonfirst, Toast.LENGTH_LONG).show();
            notifyItemChanged(position);
        }


    }
    public Character get(int position) {
       return list.get(position);
    }

    //Swipe van de ItemTouchHelperAdapter
    @Override
    public void swipeToDelete(int position) {
        remove(position);
    }

}