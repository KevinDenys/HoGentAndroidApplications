package be.kevindenys.wieishet.fragments;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import be.kevindenys.wieishet.R;
import be.kevindenys.wieishet.activity.App;
import be.kevindenys.wieishet.adapters.CharacterAdapter;
import be.kevindenys.wieishet.data.DataCharacterHandler;
import be.kevindenys.wieishet.interfaces.ClickListener;
import be.kevindenys.wieishet.interfaces.Direction;
import be.kevindenys.wieishet.models.Character;
import be.kevindenys.wieishet.util.CharacterDbHelper;
import be.kevindenys.wieishet.util.ItemSwipeHelper;
import be.kevindenys.wieishet.util.RecyclerTouchListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kevin on 30-10-2017.
 */

public class CharacterMenuFragment extends Fragment {
    /** ButterKnife Code **/
    @BindView(R.id.recyclerview)
    android.support.v7.widget.RecyclerView recyclerView;
    /** ButterKnife Code **/
    private Unbinder unbinder;

    private CharacterAdapter adapter;

    private App app;
    public CharacterMenuFragment() {}

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_character_menu, container, false);
        unbinder = ButterKnife.bind(this, v);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, this);
        transaction.commit();

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        adapter = new CharacterAdapter(app.getCharacters(), getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        //Left swipen toestaan
        ItemTouchHelper.Callback itemTouchCallBack = new ItemSwipeHelper(Direction.LEFT, adapter);
        //Left swipen binden aan swipeToDismissTouchHelper
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(itemTouchCallBack);
        //Left dismiss swipe binden aan recyclerview
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);

        //Wanneer er geklikt wordt op een item
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                CharacterDetailFragment secondFragment = new CharacterDetailFragment();
                app.setCdf(secondFragment);
                Bundle args = new Bundle();
                args.putSerializable("char", adapter.get(position));
                secondFragment.setArguments(args);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flContainer2, secondFragment) // replace flContainer
                            //.addToBackStack(null)
                            .commit();
                } else {

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flContainer, secondFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
            //Long click gebruiken om als speler 1 de character in te stellen
            @Override
            public void onLongClick(View view, int position) {
            if(app.isCharChoosen() == false){
                app.showMessage(getString(R.string.suretitel), getString(R.string.areyousure, app.getCharacters().get(position).getName()),app.getCharacters().get(position), getContext(),"Yes");
            }
            }
        }));
    }

    @Override public void onDestroyView() {
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

