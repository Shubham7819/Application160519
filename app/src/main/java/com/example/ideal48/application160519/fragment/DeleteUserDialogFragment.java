package com.example.ideal48.application160519.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteUserDialogFragment extends DialogFragment {

    public DeleteUserDialogFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
        final UserDao userDao = userRoomDatabase.userDao();

        final String mUserId = getArguments().getString(getString(R.string.user_id), null);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                userDao.delete(mUserId);
                                return null;
                            }
                        }.execute();
                        Toast.makeText(getActivity(), "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
