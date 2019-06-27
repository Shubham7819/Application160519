package com.example.ideal48.application160519;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.VideoView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AnimeVideoPopupWindow extends PopupWindow {

    View view;
    Context mContext;
    VideoView videoView;
//    ProgressBar loading;
    ViewGroup parent;

//    MediaSessionCompat mediaSession;
//    PlaybackStateCompat.Builder stateBuilder;

    public AnimeVideoPopupWindow(Context context, int layout, View v, final String videoUrl, Bitmap bitmap) {
        super(((LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.video_popup_window_layout, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = context;
        this.view = getContentView();
        ImageButton closeButton = (ImageButton) this.view.findViewById(R.id.popup_close_btn);
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
        //---------Begin customising this popup--------------------

        videoView = view.findViewById(R.id.popup_video_view);
//        loading = (ProgressBar) view.findViewById(R.id.popup_progress_bar);
//        loading.setIndeterminate(true);
//        loading.setVisibility(View.VISIBLE);
        parent = (ViewGroup) videoView.getParent();
        //----------------------------
        if (videoUrl != null) {
//            loading.setVisibility(View.GONE);
//            // Create a MediaSessionCompat
//            mediaSession = new MediaSessionCompat(mContext, "Media Session");
//
//            // Enable callbacks from MediaButtons and TransportControls
//            mediaSession.setFlags(
//                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//
//            // Do not let MediaButtons restart the player when the app is not visible
//            mediaSession.setMediaButtonReceiver(null);
//
//            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
//            stateBuilder = new PlaybackStateCompat.Builder()
//                    .setActions(
//                            PlaybackStateCompat.ACTION_PLAY |
//                                    PlaybackStateCompat.ACTION_PLAY_PAUSE);
//            mediaSession.setPlaybackState(stateBuilder.build());
//
//            // MySessionCallback has methods that handle callbacks from a media controller
//            mediaSession.setCallback(new MySessionCallback());
//
//            // Create a MediaControllerCompat
//            MediaControllerCompat mediaController =
//                    new MediaControllerCompat(mContext, mediaSession);
//
//            MediaControllerCompat.setMediaController(this, mediaController);

            Uri videoUri = Uri.parse(videoUrl);
            videoView.setVideoURI(videoUri);
            videoView.start();
            MediaController ctlr = new MediaController(context);
            ctlr.setMediaPlayer(videoView);
            videoView.setMediaController(ctlr);
            videoView.requestFocus();

//            if (Build.VERSION.SDK_INT >= 16) {
//            } else {
//
//            }
        } else {

//            loading.setVisibility(View.GONE);

            showAtLocation(v, Gravity.CENTER, 0, 0);
        }

    }

}
