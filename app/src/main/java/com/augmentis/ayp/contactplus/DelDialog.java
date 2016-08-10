package com.augmentis.ayp.contactplus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.contactplus.Model.ContactLab;

import java.util.UUID;

/**
 * Created by Apinya on 8/10/2016.
 */
public class DelDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String CONTACT_ID = "DelDialog.ID";
    UUID contactId;

    public static DelDialog newInstance(Context context, UUID uuid){
        DelDialog dd = new DelDialog();
        Bundle args = new Bundle();
        args.putSerializable(CONTACT_ID, uuid);
        dd.setArguments(args);
        return dd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        contactId = (UUID) getArguments().getSerializable(CONTACT_ID);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you want to delete");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContactLab.getInstance(getActivity()).deleteCrime(contactId);

                Intent intent = new Intent(getActivity(), ContactListActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
