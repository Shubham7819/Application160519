package com.example.ideal48.application160519.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.model.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link EditUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {link EditUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditUserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mUserId;

    UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
    UserDao userDao = userRoomDatabase.userDao();

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    User currentUser;
    ImageView imageIV;
    byte[] imageInByte;

    private int GALLERY = 1, CAMERA = 2;

//    private OnFragmentInteractionListener mListener;

    public EditUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p>
     * param param1 Parameter 1.
     * param param2 Parameter 2.
     *
     * @return A new instance of fragment EditUserFragment.
     */

    // TODO: Rename and change types and number of parameters
//    public static EditUserFragment newInstance(String param1, String param2) {
//        EditUserFragment fragment = new EditUserFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(getString(R.string.user_id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_user_layout, container, false);

        Toolbar toolbar = view.findViewById(R.id.my_toolbar);
        toolbar.setTitle("Edit User Details");
        toolbar.inflateMenu(R.menu.menu_item_save);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        imageIV = view.findViewById(R.id.edit_user_iv);
        final EditText fNameET = view.findViewById(R.id.f_name_et);
        final EditText lNameET = view.findViewById(R.id.l_name_et);
        final Button editDOBBtn = view.findViewById(R.id.edit_dob_btn);
        final TextView editDOBTV = view.findViewById(R.id.edit_dob_tv);
        final EditText userIdET = view.findViewById(R.id.user_id_et);
        final EditText passwordET = view.findViewById(R.id.password_et);

//        new AsyncTask<Void, Void, Void>(){
//            @Override
//            protected Void doInBackground(Void... voids) {
//                currentUser = userDao.getUserDetails(mUserId);
//                return null;
//            }
//        }.execute();

        currentUser = userDao.getUserDetails(mUserId);

        fNameET.setText(currentUser.getmFName());
        lNameET.setText(currentUser.getmLName());
        editDOBTV.setText(currentUser.getmDOB());
        userIdET.setText(currentUser.getmUserId());
        passwordET.setText(currentUser.getmPassword());

        byte[] imageBytes = currentUser.getmImageInByte();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageBytes);
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        imageIV.setImageBitmap(image);

        imageIV.setOnClickListener(new View.OnClickListener() {
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
                    }
                }
            }
        });

        Toast.makeText(getActivity(), "all fields are mandatory", Toast.LENGTH_SHORT).show();

        editDOBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year, month, day;
                String[] separatedDate = editDOBTV.getText().toString().split("/");
                if (separatedDate.length == 3) {
                    day = Integer.parseInt(separatedDate[0]);
                    month = Integer.parseInt(separatedDate[1]);
                    year = Integer.parseInt(separatedDate[2]);
                } else {
                    final Calendar c = Calendar.getInstance();
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH);
                    day = c.get(Calendar.DAY_OF_MONTH);
                }
                DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getActivity());
                builder.create();
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        editDOBTV.setText(String.valueOf(day) + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 568_025_136_000L);
                dialog.show();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:

                        String fName = fNameET.getText().toString();
                        String lName = lNameET.getText().toString();
                        String dob = editDOBTV.getText().toString();
                        String userId = userIdET.getText().toString();
                        String password = passwordET.getText().toString();
                        Bitmap imageBitmap = ((BitmapDrawable) imageIV.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteImage = stream.toByteArray();

                        if (fName.isEmpty() || lName.isEmpty() || userId.isEmpty() || password.isEmpty()) {
                            Toast.makeText(getActivity(), "all fields are mandatory", Toast.LENGTH_SHORT).show();
                        } else {
                            currentUser.setmFName(fName);
                            currentUser.setmLName(lName);
                            currentUser.setmDOB(dob);
                            currentUser.setmUserId(userId);
                            currentUser.setmPassword(password);
                            currentUser.setmImageInByte(byteImage);

                            userDao.updateUserDetails(currentUser);
                            Toast.makeText(getActivity(), "details updated successfully", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                        break;
                }
                return true;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//           // mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //  mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null){
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                        imageIV.setImageBitmap(bitmap);
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
                imageIV.setImageBitmap(photo);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imageInByte = stream.toByteArray();
            }
        }
    }
}
