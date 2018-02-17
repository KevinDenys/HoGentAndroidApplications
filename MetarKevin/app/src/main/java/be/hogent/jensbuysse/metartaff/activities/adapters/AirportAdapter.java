package be.hogent.jensbuysse.metartaff.activities.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Airport;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

/**
 * Created by eothein on 07.11.17.
 */

public class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.ViewHolder> implements DataObserver<List<Airport>>{


    /**
     * List containing the different aiports where a METAR can be calculated
     */
    private List<Airport> airports ;
    /**
     * Interface to global information about an application environment.
     * This is an abstract class whose implementation is provided by the Android system.
     * It allows access to application-specific resources and classes, as well as up-calls
     * for application-level operations such as launching activities, broadcasting and
     * receiving intents, etc.
     */
    private Context context;

    /**
     * Custom listener to listen to click events in the recyclerview
     */
    private CustomItemClickListener listener;

    private MetarApplication app;

    private DataSubscriptionList subscriptions = new DataSubscriptionList();

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView name;
        public TextView description;
        public ImageView thumbNail;




        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            thumbNail = (ImageView)itemView.findViewById(R.id.thumbnail);
        }
    }


    /**
     * Custom Item clicklistener for the elements in the recyclerview
     */
    public interface CustomItemClickListener {
        void onItemClick(View v, int position);
    }

    /**
     * Construcor
     * @param context
     * @param app
     * @param listener
     */
    public AirportAdapter(Context context, MetarApplication app, CustomItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.app = app;
        setAirports();

    }

    public void setAirports(){
        Box<Airport> airportBox = app.getBoxStore().boxFor(Airport.class);
        Query<Airport> airportQuery = airportBox.query().build();
        airportQuery.subscribe().on(AndroidScheduler.mainThread()).observer(this);

    }

    /**
     * Method which is called when de data loading from the database has finished
     * @param data De new data
     */
    @Override
    public void onData(List<Airport> data) {
        airports = data;
        Logger.i("onData called, registering the airport data");
        notifyDataSetChanged();
    }



    @Override
    public AirportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout,parent, false);

        final ViewHolder viewHolder = new ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Airport airport = airports.get(position);
        holder.name.setText(airport.getPlaatsindicator());
        holder.description.setText(airport.getBeschrijving());
        Picasso.with(context).load(R.mipmap.airport).into(holder.thumbNail);

    }


    @Override
    public int getItemCount() {
        return airports.size();
    }


    public Airport getAiport(int position){
        return airports.get(position);
    }

}
