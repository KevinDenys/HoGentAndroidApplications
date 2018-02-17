package be.hogent.jensbuysse.metartaff.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.RefWatcher;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Metar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRawFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RawFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RawFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Metar metar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnRawFragmentInteractionListener mListener;

    public RawFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RawFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RawFragment newInstance(String param1, String param2) {
        RawFragment fragment = new RawFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    private void updateUI(){
        if(metar != null){
            TextView v = this.getActivity().findViewById(R.id.rawmetar);
            v.setText(metar.getRawMetar());
        }
    }

    public void setMetar(Metar metar) {
        this.metar = metar;
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_raw, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRawFragmentInteractionListener) {
            mListener = (OnRawFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOldMetarInteractionListener");
        }
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
    public interface OnRawFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRawFragmentInteraction(Uri uri);
    }
}
