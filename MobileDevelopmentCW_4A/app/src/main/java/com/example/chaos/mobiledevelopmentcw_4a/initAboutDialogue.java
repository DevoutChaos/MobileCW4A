package com.example.chaos.mobiledevelopmentcw_4a;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by chaos on 03/12/2016.
 */

public class initAboutDialogue extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder initAboutDialog = new AlertDialog.Builder(getActivity());
        initAboutDialog.setMessage(R.string.dialog_About)
                .setPositiveButton(R.string.dialog_About_OK_btn, new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        initAboutDialog.setTitle("About");
        initAboutDialog.setIcon(R.drawable.ic_about);
        // Create the AlertDialog object and return it
        return initAboutDialog.create();
    }
}
