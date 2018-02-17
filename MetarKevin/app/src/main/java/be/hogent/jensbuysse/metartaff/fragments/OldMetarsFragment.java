package be.hogent.jensbuysse.metartaff.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.activities.adapters.MetarAdapter;
import be.hogent.jensbuysse.metartaff.models.Metar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OldMetarsFragment extends Fragment {
    /** ButterKnife Code **/
    @BindView(R.id.historyView)
    android.support.v7.widget.RecyclerView recHistory;
    /** ButterKnife Code **/
    private Metar metar;
    private MetarApplication app;
    private MetarAdapter adapter;
    public MetarApplication getApp() {
        return app;
    }

    public void setApp(MetarApplication app) {
        this.app = app;
        updateUI();
    }

    private OnOldMetarInteractionListener mListener;

    public OldMetarsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_old_metars, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOldMetarFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOldMetarInteractionListener) {
            mListener = (OnOldMetarInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOldMetarInteractionListener");
        }
    }

    public void updateUI(){
        if(metar != null){
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setStackFromEnd(true);
            recHistory.setLayoutManager(linearLayoutManager);
            adapter = new MetarAdapter(getContext(),metar.getHistory(app));
            recHistory.setAdapter(adapter);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //canary leak here
        //mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener = null; //canary fix
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        RefWatcher refWatcher = MetarApplication.getWatcher(getActivity());
//        refWatcher.watch(this);
    }
    public void setMetar(Metar metar) {
        this.metar = metar;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnOldMetarInteractionListener {
        // TODO: Update argument type and name
        void onOldMetarFragmentInteraction(Uri uri);
    }
}
