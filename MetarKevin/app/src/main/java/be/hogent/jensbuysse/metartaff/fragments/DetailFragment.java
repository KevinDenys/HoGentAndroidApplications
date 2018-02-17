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

import com.squareup.leakcanary.RefWatcher;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Metar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDetailFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DetailFragment extends Fragment {

    /** ButterKnife Code **/
    @BindView(R.id.airport)
    TextView airport;
    @BindView(R.id.dayofmonth)
    TextView dayofmonth;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.winddirection)
    TextView winddirection;
    @BindView(R.id.windspeed)
    TextView windspeed;
    @BindView(R.id.gusts)
    TextView gusts;
    @BindView(R.id.sight)
    TextView sight;
    @BindView(R.id.txtDate)
    TextView title;
    /** ButterKnife Code **/

    private Metar metar;

    private OnDetailFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateUI();
    }

    public void setMetar(Metar metar) {
        this.metar = metar;
    }

    public void updateUI(){
        if(metar != null){
            title.setText(metar.getRowName());
            sight.setText(String.format("%d",metar.getSight()));
            winddirection.setText(String.format("%d",metar.getWindDirection()));
            windspeed.setText(String.format("%d",metar.getWindSpeed()));
            time.setText(metar.getTime());
            dayofmonth.setText(String.format("%d",metar.getDayOfMonth()));
            gusts.setText(String.format("%d",metar.getUitSchieters()));
            airport.setText(metar.airport.getTarget().getPlaatsindicator());
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this,v);
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailFragmentInteractionListener) {
            mListener = (OnDetailFragmentInteractionListener) context;
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
    public interface OnDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDetailFragmentInteraction(Uri uri);
    }
}
