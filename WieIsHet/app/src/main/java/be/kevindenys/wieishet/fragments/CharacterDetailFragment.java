package be.kevindenys.wieishet.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;

import be.kevindenys.wieishet.R;
import be.kevindenys.wieishet.activity.App;
import be.kevindenys.wieishet.models.Character;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CharacterDetailFragment extends Fragment {
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.btnRemoveCharacter)
    Button btnRemoveCharacter;
    @BindView(R.id.characterImage)
    ImageView characterImage;
    @BindView(R.id.eName)
    EditText eName;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.eDescription)
    EditText eDescription;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.btnUpdateCharacter)
    Button btnUpdateCharacter;
    @BindView(R.id.btnRestart)
    Button btnRestart;
    /**
     * ButterKnife Code
     **/
    private Unbinder unbinder;
    private Character character;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Helpt bij de overlaping, wanneer er gedraait wordt
        //Zodat de tekst niet 3 keer verschijnt..
        if (savedInstanceState == null) {
            if (getArguments() != null) {
                character = (Character) getArguments().get("char");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Layout inladen
        View v = inflater.inflate(R.layout.fragment_character_detail, parent, false);
        unbinder = ButterKnife.bind(this, v);
        App x = (App) getContext().getApplicationContext();
        //Kijken of het spel al bezig, als het bezig is kan de databank niet aangepast worden
        if (x.getGameInProgress()) {
            setPlayMode();
        } else {
            setEditMode();
        }
        x = null; //canary fix
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (null != character) {
            // update view
            name.setText(character.getName());
            description.setText(character.getDescription());
            //edit
            eName.setText(character.getName());
            eDescription.setText(character.getDescription());
            Context context = characterImage.getContext();
            Picasso.with(context).load(character.getCharacterImage()).resize(500, 500)
                    .centerCrop().into(characterImage);
        }

    }

    private void setPlayMode() {
        btnRemoveCharacter.setVisibility(View.GONE);
        eName.setVisibility(View.GONE);
        eDescription.setVisibility(View.GONE);
        name.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        btnUpdateCharacter.setVisibility(View.GONE);
        characterImage.setVisibility(View.VISIBLE);
    }

    private void setEditMode() {
        characterImage.setVisibility(View.VISIBLE);
        btnUpdateCharacter.setVisibility(View.VISIBLE);
        btnRemoveCharacter.setVisibility(View.VISIBLE);
        eName.setVisibility(View.VISIBLE);
        eDescription.setVisibility(View.VISIBLE);
        name.setVisibility(View.GONE);
        description.setVisibility(View.GONE);
    }

    //empty the view
    public void makeInvis(){
        characterImage.setVisibility(View.GONE);
        btnUpdateCharacter.setVisibility(View.GONE);
        btnRemoveCharacter.setVisibility(View.GONE);
        eName.setVisibility(View.GONE);
        eDescription.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        description.setVisibility(View.GONE);
    }
    //enkel de restart button tonen
    private void forceRestartPress() {
        btnRestart.setVisibility(View.VISIBLE);
        makeInvis();
    }

    @OnClick(R.id.btnRestart)
    public void restartTheApp(){
        App x = (App) getContext().getApplicationContext();
        x.restartAppProgram();
        x = null;
    }

    @OnClick(R.id.btnRemoveCharacter)
    public void removeCharacter() {
        App x = (App) getContext().getApplicationContext();
        SQLiteDatabase db = x.getDatahelper().getWritableDatabase();
        if (x.getDatahelper().removeFromDatabase(character, db)) {
            Toast.makeText(x, R.string.removedfromdb, Toast.LENGTH_LONG).show();
            forceRestartPress();
        }else{
            Toast.makeText(x, R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
        }
        x = null; //canary fix
    }

    @OnClick(R.id.btnUpdateCharacter)
    public void updateCharacter() {
        character.setDescription(eDescription.getText().toString());
        character.setName(eName.getText().toString());
        App x = (App) getContext().getApplicationContext();
        if (x.getDatahelper().updateInDatabase(character, x.getDatahelper().getWritableDatabase())) {
            Toast.makeText(x, R.string.updatedindb, Toast.LENGTH_LONG).show();
            forceRestartPress();
        }else{
            Toast.makeText(x, R.string.somethingwentwrong, Toast.LENGTH_SHORT).show();
        }
        x = null; //canary fix
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = App.getWatcher(getActivity());
//        refWatcher.watch(this);
    }
}
