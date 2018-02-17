package be.kevindenys.dotpict.adapters;

/**
 * Created by Kevin on 3/12/2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import be.kevindenys.dotpict.R;
import be.kevindenys.dotpict.activity.App;
import be.kevindenys.dotpict.models.Save;


/**
 * Created by Kevin on 26/11/2017.
 */

public class SaveViewAdapter extends RecyclerView.Adapter<SaveViewAdapter.ViewHolder> {
    private App app;
    private List<Save> savedata = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private SaveViewAdapter.ItemClickListener mClickListener;
    public SaveViewAdapter(Context context, List<Save> data, App app) {
        this.app = app;
        this.mInflater = LayoutInflater.from(context);
        this.savedata = data;
        this.context = context;
    }
    public void setClickListener(SaveViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public Save getItem(int id) {
        return savedata.get(id);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.save_row, parent, false);
        SaveViewAdapter.ViewHolder viewHolder = new SaveViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Save save = savedata.get(position);
        holder.SaveNaam.setText(save.getName());
        holder.deleteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage(R.string.sureDelete);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        R.string.Yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //SQLTAG
                                if(app.getDatahelper().removeFromDatabase(save, app.getDatahelper().getWritableDatabase())){
                                    savedata.remove(save);
                                }else{
                                    Toast.makeText(app, R.string.somethingwentwrongdelete, Toast.LENGTH_SHORT).show();
                                }
                                //ObjectBox
                                //app.getSaveBox().remove(save.getId());
                                //savedata = app.getSaveBox().getAll();

                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });

                builder1.setNegativeButton(
                        R.string.No,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    //om de lijst up te daten, verwijderen we alle items en vullen dan de savedate terug op met de doorgegeven items lijst
    public void updateList (List<Save> items) {
        if (items != null && items.size() > 1) {
            savedata.clear();
            savedata.addAll(items);

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return savedata.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView SaveNaam;
        public Button deleteSave;

        public ViewHolder(View itemView) {
            super(itemView);
            SaveNaam = (TextView) itemView.findViewById(R.id.saveNaam);
            deleteSave = (Button) itemView.findViewById(R.id.btnDelete);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}