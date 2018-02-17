package be.kevindenys.wieishet.adapters.viewModels;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import be.kevindenys.wieishet.R;
import be.kevindenys.wieishet.models.Character;

/**
 * Created by Kevin on 30-10-2017.
 */

public class CharacterViewHolder extends RecyclerView.ViewHolder {

//Geprobeerd met butterknife maar dan was er kans op random crashes..

    private CardView cv;
    private TextView name;
    private ImageView characterImage;

    public CharacterViewHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        name = (TextView) itemView.findViewById(R.id.name);
        characterImage = (ImageView) itemView.findViewById(R.id.characterImage);
    }

    public void setData( Character character){

        name.setText(character.getName());
        characterImage.setImageResource(character.getCharacterImage());
        Context context = characterImage.getContext();
        //Picasso om images te gebruiken op aanraden van Mr Buysse
        //http://square.github.io/picasso/
        Picasso.with(context).load(character.getCharacterImage()).resize(200, 200)
                .centerCrop().into(characterImage);

    }
}
