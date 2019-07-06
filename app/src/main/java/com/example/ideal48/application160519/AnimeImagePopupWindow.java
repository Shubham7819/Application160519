package com.example.ideal48.application160519;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AnimeImagePopupWindow extends PopupWindow {

    View view;
    Context mContext;
    ImageView photoView;
    ProgressBar loading;
    ViewGroup parent;
    private static AnimeImagePopupWindow instance = null;


    public AnimeImagePopupWindow(Context context, int layout, View v, final String imageUrl, Bitmap bitmap) {
        super(((LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.image_popup_window_layout, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = context;
        this.view = getContentView();

        photoView = view.findViewById(R.id.popup_image_view);
        loading = view.findViewById(R.id.popup_progress_bar);
        ImageButton closeButton = this.view.findViewById(R.id.popup_close_btn);
        ImageButton shareButton = this.view.findViewById(R.id.popup_share_btn);
        setOutsideTouchable(true);

        setFocusable(true);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dismiss();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Share Button Clicked...", Toast.LENGTH_SHORT).show();

                Uri imageUri = Uri.parse(imageUrl);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                intent.setType("image/*");
                mContext.startActivity(Intent.createChooser(intent, "Share image using"));

            }
        });
        //---------Begin customising this popup--------------------

        loading.setIndeterminate(true);
        loading.setVisibility(View.VISIBLE);
//        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        // ImageUtils.setZoomable(imageView);
        //----------------------------
        if (bitmap != null) {
            loading.setVisibility(View.GONE);
            photoView.setImageBitmap(bitmap);
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.ic_close).into(photoView);

            loading.setVisibility(View.GONE);

            showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        //------------------------------

    }

}
