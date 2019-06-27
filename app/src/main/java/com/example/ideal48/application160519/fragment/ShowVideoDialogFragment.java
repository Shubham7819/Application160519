package com.example.ideal48.application160519.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.ideal48.application160519.R;


public class ShowVideoDialogFragment extends DialogFragment {

    Dialog dialog;
    VideoView videoView;
    WebView webView;

    public ShowVideoDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_popup_window_layout);

        ImageButton closeBtn = dialog.findViewById(R.id.popup_close_btn);
        videoView = dialog.findViewById(R.id.popup_video_view);
        webView = dialog.findViewById(R.id.popup_web_view);

        String videoUrl = getArguments().getString(getString(R.string.url));
        boolean isYoutubeLink = getArguments().getBoolean(getString(R.string.is_youtube_link));

        if (isYoutubeLink) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
            webView.loadUrl(videoUrl);
            webView.setWebChromeClient(new WebChromeClient());
        } else {

            videoView.setVideoPath(videoUrl);

            MediaController mediaController = new MediaController(getActivity());
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.start();
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

}
