package be.kevindenys.dotpict.fragment;

import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.leakcanary.RefWatcher;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.activity.App;
import be.kevindenys.dotpict.models.Grid;
import be.kevindenys.dotpict.views.PixelGridView;

/**
 * Created by Kevin on 2/11/2017.
 */

public class MainGridFragment extends Fragment {
    private Button btnTools;
    private App app;

    public MainGridFragment() {

    }

    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        this.app = (App) getActivity().getApplication();
        //Als de app een board bevat nemen we die over anders maken we een nieuwe met size 10
        if (app.getMyBoard() == null) {
            PixelGridView x = (PixelGridView) v.findViewById(R.id.pixelboard);
            x.setPixelGridView(new Grid(10),10, app); //basic size
            app.setMyBoard(x);
            //Test crashte hier anders
            app.setTempHolder(app.getMyBoard().getPixelGrid()); //Fixed test
        } else {
            PixelGridView x = (PixelGridView) v.findViewById(R.id.pixelboard);
            x.setPixelGridView(app.getTempHolder(),app.getTempHolder().getSize(), app);
            app.setMyBoard(x);

        }
        btnTools = (Button) v.findViewById(R.id.btnTools);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionFragment optionFragment = new OptionFragment();
                optionFragment.setApp(app);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flContainer2, optionFragment)
                            .commit();
                } else {
                    app.setTempHolder(app.getMyBoard().getPixelGrid());
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.flContainer, optionFragment)
                            .addToBackStack(null)
                            .commit();
                }

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = App.getWatcher(getActivity());
//        refWatcher.watch(this);
        app = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        app = null;
    }
}
