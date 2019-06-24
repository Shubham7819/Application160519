package com.example.ideal48.application160519;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
        ImageButton closeButton = (ImageButton) this.view.findViewById(R.id.popup_close_btn);
        ImageButton shareButton = (ImageButton) this.view.findViewById(R.id.popup_share_btn);
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

        photoView = (ImageView) view.findViewById(R.id.popup_image_view);
        loading = (ProgressBar) view.findViewById(R.id.popup_progress_bar);
        loading.setIndeterminate(true);
        loading.setVisibility(View.VISIBLE);
//        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        // ImageUtils.setZoomable(imageView);
        //----------------------------
        if (bitmap != null) {
            loading.setVisibility(View.GONE);
//            if (Build.VERSION.SDK_INT >= 16) {
//                parent.setBackground(new BitmapDrawable(mContext.getResources(),
//                        Constants.fastblur(Bitmap.createScaledBitmap(bitmap,
//                                50, 50, true))));// ));
//            } else {
//                onPalette(Palette.from(bitmap).generate());
//
//            }
            photoView.setImageBitmap(bitmap);
        } else {
            Picasso.with(context)
                    .load(imageUrl)

                    .error(R.drawable.ic_close).into(photoView);
            loading.setVisibility(View.GONE);
//                    .listener(new RequestListener<Bitmap>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                            loading.setIndeterminate(false);
//                            loading.setBackgroundColor(Color.LTGRAY);
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                            if (Build.VERSION.SDK_INT >= 16) {
//                                parent.setBackground(new BitmapDrawable(mContext.getResources(), Constants.fastblur(Bitmap.createScaledBitmap(resource, 50, 50, true))));// ));
//                            } else {
//                                onPalette(Palette.from(resource).generate());
//
//                            }
//                            photoView.setImageBitmap(resource);
//
//                            loading.setVisibility(View.GONE);
//                            return false;
//                        }
//                    })
//
//
//
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)


            showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        //------------------------------

    }
//
//    public void onPalette(Palette palette) {
//        if (null != palette) {
//            ViewGroup parent = (ViewGroup) photoView.getParent().getParent();
//            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
//        }
//    }
    
}
