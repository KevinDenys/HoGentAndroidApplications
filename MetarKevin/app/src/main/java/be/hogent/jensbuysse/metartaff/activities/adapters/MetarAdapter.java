package be.hogent.jensbuysse.metartaff.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Metar;

/**
 * Created by Kevin on 6/01/2018.
 */

public class MetarAdapter extends RecyclerView.Adapter<MetarAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private List<Metar> data = new ArrayList<>();
    public MetarAdapter(Context context, List<Metar> data) {

        this.mInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public MetarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_history, parent, false);
        MetarAdapter.ViewHolder viewHolder = new MetarAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MetarAdapter.ViewHolder holder, int position) {
        final Metar metar = data.get(position);
        //Main
        holder.txtHisDate.setText(metar.getRowName());
        //INFO
        holder.sight.setText(String.format("%d",metar.getSight()));
        holder.winddirection.setText(String.format("%d",metar.getWindDirection()));
        holder.windspeed.setText(String.format("%d",metar.getWindSpeed()));
        holder.time.setText(metar.getTime());
        holder.dayofmonth.setText(String.format("%d",metar.getDayOfMonth()));
        holder.gusts.setText(String.format("%d",metar.getUitSchieters()));
        holder.airport.setText(metar.airport.getTarget().getPlaatsindicator());
        //
        holder.btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.metarinfo.getVisibility() == View.GONE){
                    holder.metarinfo.setVisibility(View.VISIBLE);
                }else{
                    holder.metarinfo.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        /** Android Views **/
        public TextView txtHisDate;
        public Button btnLoad;
        public TableLayout metarinfo;
        public TextView airport;
        public TextView dayofmonth;
        public TextView time;
        public TextView winddirection;
        public TextView windspeed;
        public TextView gusts;
        public TextView sight;
        /** Android Views **/



        public ViewHolder(View rootView) {
            super(rootView);
            txtHisDate = (TextView) rootView.findViewById(R.id.txtHisDate);
            btnLoad = (Button) rootView.findViewById(R.id.btnLoad);
            metarinfo = (TableLayout) rootView.findViewById(R.id.metarinfo);
            airport = (TextView) rootView.findViewById(R.id.airport);
            dayofmonth = (TextView) rootView.findViewById(R.id.dayofmonth);
            time = (TextView) rootView.findViewById(R.id.time);
            winddirection = (TextView) rootView.findViewById(R.id.winddirection);
            windspeed = (TextView) rootView.findViewById(R.id.windspeed);
            gusts = (TextView) rootView.findViewById(R.id.gusts);
            sight = (TextView) rootView.findViewById(R.id.sight);
        }

    }
}
