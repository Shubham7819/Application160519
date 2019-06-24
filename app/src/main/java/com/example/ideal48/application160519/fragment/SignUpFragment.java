package com.example.ideal48.application160519.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.AppSession;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.activity.HomeActivity;
import com.example.ideal48.application160519.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private Context activity;
    static String dateMessage = null;
    static TextView dobTV;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private int GALLERY = 1, CAMERA = 2;

    byte[] imageInByte;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//// This callback will only be called when MyFragment is at least Started.
//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button event
//            }
//        });
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();

        View view = inflater.inflate(R.layout.signup_layout, container, false);

        // initialize XML ImageView, get default image in byteArray
        imageView = view.findViewById(R.id.signup_user_iv);
        Bitmap photo = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        imageInByte = stream.toByteArray();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setTitle("Select Action");
                        String[] dialogItems = {"Select photo from gallery", "Capture photo from camera"};
                        dialog.setItems(dialogItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                        startActivityForResult(galleryIntent, GALLERY);
                                        break;
                                    case 1:
                                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, CAMERA);
                                        break;
                                }
                            }
                        });
                        dialog.show();

//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        final EditText fNameET = view.findViewById(R.id.fname_et);

        final EditText lNameET = view.findViewById(R.id.lname_et);

        Button dobBtn = view.findViewById(R.id.dob_btn);

        dobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DOBPickerFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "DOBPicker");
            }
        });

        dobTV = view.findViewById(R.id.dob_tv);

        final EditText userIdET = view.findViewById(R.id.user_id_ET);

        final EditText passwordET = view.findViewById(R.id.password_ET);

        Button signUpButton = view.findViewById(R.id.sign_up_btn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fName = fNameET.getText().toString();
                final String lName = lNameET.getText().toString();
                final String userId = userIdET.getText().toString();
                final String password = passwordET.getText().toString();

                if (fName.isEmpty() || lName.isEmpty() || userId.isEmpty() || password.isEmpty() || dateMessage == null) {

                    // Prompt for Empty Fields
                    if (fName.isEmpty()) {
                        Toast.makeText(getActivity(), "First Name Should not be Blank", Toast.LENGTH_SHORT).show();
                    } else if (lName.isEmpty()) {
                        Toast.makeText(getActivity(), "Last Name Should not be Blank", Toast.LENGTH_SHORT).show();
                    } else if (userId.isEmpty()) {
                        Toast.makeText(getActivity(), "UserId Should not be Blank", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()) {
                        Toast.makeText(getActivity(), "Password Should not be Blank", Toast.LENGTH_SHORT).show();
                    } else if (dateMessage == null) {
                        Toast.makeText(getActivity(), "Please Select a Date Of Birth", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
                    final UserDao userDao = userRoomDatabase.userDao();
                    final User user = new User(userId, password, fName, lName, dateMessage, imageInByte);

                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            userDao.insert(user);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);

                            Toast.makeText(getActivity(), "User Added", Toast.LENGTH_SHORT).show();
/*
                            SharedPreferences sharedPrefe = getActivity().getSharedPreferences(getString(R.string.stored_user_id), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPrefe.edit();
                            editor.putString(getString(R.string.user_name), userId);
                            editor.commit();
  */
                            new AppSession(getActivity()).setLoginId(userId);

                            Intent i = new Intent(getActivity(), HomeActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }.execute();
                }
            }
        });

        TextView registeredTV = view.findViewById(R.id.already_registered_text);
        registeredTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Toast.makeText(getActivity(), "All fields Mandatory", Toast.LENGTH_SHORT).show();

        return view;
    }

    public static void processDOBPickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        dateMessage = (day_string +
                "/" + month_string + "/" + year_string);
        //Toast.makeText(activity, "Date: " + dateMessage, Toast.LENGTH_SHORT).show();
        dobTV.setText(dateMessage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(activity, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
//
//        protected void onActivityResult(int requestCode, int resultCode, Intent data)
//        {
//            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
//            {
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                imageView.setImageBitmap(photo);
//            }
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                        imageView.setImageBitmap(bitmap);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        imageInByte = stream.toByteArray();

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imageInByte = stream.toByteArray();
            }
        }
//        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            imageInByte = stream.toByteArray();
//        }
    }
}
