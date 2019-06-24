package com.example.ideal48.application160519.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ideal48.application160519.R;

import java.io.ByteArrayInputStream;


public class UserDetailsFragment extends Fragment {

    private int mId;
    private String mUserId;
    private String mPassword;
    private String mFName;
    private String mLName;
    private String mDOB;
    private Bitmap mImageInByte;


    public UserDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("UserDetailsFragment", "OnCreate called");

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mId = getArguments().getInt(getString(R.string.id));
            mUserId = getArguments().getString(getString(R.string.user_id));
            mPassword = getArguments().getString(getString(R.string.password));
            mFName = getArguments().getString(getString(R.string.first_name));
            mLName = getArguments().getString(getString(R.string.last_name));
            mDOB = getArguments().getString(getString(R.string.dob));

            byte[] byteImage = getArguments().getByteArray(getString(R.string.image_bytes));
            ByteArrayInputStream imageStream = new ByteArrayInputStream(byteImage);
            mImageInByte = BitmapFactory.decodeStream(imageStream);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_details_layout, container, false);

        Log.d("UserDetailsFragment", "OnCreateView called");

        TextView idTV = view.findViewById(R.id.id_tv_user_details);
        TextView fNameTV = view.findViewById(R.id.f_name_tv_user_details);
        TextView lNameTV = view.findViewById(R.id.l_name_tv_user_details);
        TextView userIdTV = view.findViewById(R.id.user_id_tv_user_details);
        TextView passwordTV = view.findViewById(R.id.password_tv_user_details);
        ImageView userIV = view.findViewById(R.id.user_iv_user_details);
        TextView dobTV = view.findViewById(R.id.dob_tv_user_details);

        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("User Details");
        toolbar.inflateMenu(R.menu.alter_items);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString(getString(R.string.user_id), mUserId);
                        EditUserFragment editUserFragment = new EditUserFragment();
                        editUserFragment.setArguments(bundle);
                        ft.replace(R.id.user_frame_layout, editUserFragment).addToBackStack("EditUserFragment");
                        ft.commit();
                        break;

                    case R.id.action_delete:
                        Bundle b = new Bundle();
                        b.putString(getString(R.string.user_id), mUserId);
                        DeleteUserDialogFragment deleteUserDialogFragment = new DeleteUserDialogFragment();
                        deleteUserDialogFragment.setArguments(b);
                        deleteUserDialogFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "DeleteUserDialogFragment");
                        break;
                }
                return true;
            }
        });

        idTV.setText(String.valueOf(mId));
        fNameTV.setText(mFName);
        lNameTV.setText(mLName);
        userIdTV.setText(mUserId);
        passwordTV.setText(mPassword);
        userIV.setImageBitmap(mImageInByte);
        dobTV.setText(mDOB);
        // Inflate the layout for this fragment
        return view;
    }
}
