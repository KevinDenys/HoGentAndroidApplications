package be.hogent.jensbuysse.metartaff.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import be.hogent.jensbuysse.metartaff.MetarApplication;
import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.models.Airport;
import io.objectbox.Box;

/**
 * Created by eothein on 08.11.17.
 */

public class AirportDialog extends DialogFragment {


    AiportDialogListener listener;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AiportDialogListener {
        public void onDialogPositiveClick(String ICAO, String description);

    }



    private EditText icaName;

    private EditText description;



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View layout = inflater.inflate(R.layout.airport_dialog, null);


        icaName = layout.findViewById(R.id.ICAO);
        description = layout.findViewById(R.id.luchhavennaam);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(layout)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String icao = icaName.getText().toString();
                        String beschrijving = description.getText().toString();
                        listener.onDialogPositiveClick(icao,beschrijving);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AirportDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

// Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AiportDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
