package com.hwswcodesign.legoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class APnotFoundDialogFragment extends DialogFragment{
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_APnotFound)
               .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   // User presses ok: Do nothing (so far)
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
}
