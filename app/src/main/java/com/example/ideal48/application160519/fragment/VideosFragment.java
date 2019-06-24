package com.example.ideal48.application160519.fragment;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ideal48.application160519.AnimeVideoPopupWindow;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.EpisodesVideos;
import com.example.ideal48.application160519.model.PromoVideos;
import com.example.ideal48.application160519.model.VideosResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideosFragment extends Fragment {

    View view;

    List<PromoVideos> promoVideosList;
    List<EpisodesVideos> episodesVideosList;

    RecyclerView promoVideosRV;
    RecyclerView episodesVideosRV;

    TextView promoEmptyTV;
    TextView episodesEmptyTV;

    View promoLoadingIndicator;
    View episodesLoadingIndicator;

    public VideosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_videos, container, false);

        promoVideosRV = view.findViewById(R.id.promo_video_rv);
        episodesVideosRV = view.findViewById(R.id.episodes_video_rv);

        promoEmptyTV = view.findViewById(R.id.promo_empty_view);
        episodesEmptyTV = view.findViewById(R.id.episodes_empty_view);

        promoLoadingIndicator = view.findViewById(R.id.promo_loading_indicator);
        episodesLoadingIndicator = view.findViewById(R.id.episodes_loading_indicator);

        Call<VideosResponse> call = AnimeDetailsActivity.service.getVideosResponse(AnimeDetailsActivity.malId);
        call.enqueue(new Callback<VideosResponse>() {
            @Override
            public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
                if (response.body() != null) {

                    promoVideosList = response.body().getmPromoList();
                    episodesVideosList = response.body().getmEpisodesList();

                    if (promoVideosList != null && promoVideosList.size() != 0) {

                        PromoVideosListAdapter promoVideosListAdapter = new PromoVideosListAdapter(getActivity());
                        promoVideosRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        promoLoadingIndicator.setVisibility(View.GONE);
                        promoVideosRV.setAdapter(promoVideosListAdapter);

                    } else {
                        promoLoadingIndicator.setVisibility(View.GONE);
                        promoEmptyTV.setText("Promos Not Available..");
                    }

                    if (episodesVideosList != null && episodesVideosList.size() != 0) {

                        EpisodesVideosListAdapter episodesVideosListAdapter = new EpisodesVideosListAdapter(getActivity());
                        episodesVideosRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        episodesLoadingIndicator.setVisibility(View.GONE);
                        episodesVideosRV.setAdapter(episodesVideosListAdapter);

                    } else {
                        episodesLoadingIndicator.setVisibility(View.GONE);
                        episodesEmptyTV.setText("Episodes Not Available..");
                    }

                }
            }

            @Override
            public void onFailure(Call<VideosResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "No Data !", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    class PromoVideosListAdapter extends RecyclerView.Adapter<PromoVideosListAdapter.PromoVideosViewHolder> {

        private LayoutInflater mInflater;
        private Context context;

        PromoVideosListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public PromoVideosListAdapter.PromoVideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.videos_list_item, parent, false);
            return new PromoVideosViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(PromoVideosListAdapter.PromoVideosViewHolder holder, int position) {

            final PromoVideos currentPromoVideo = promoVideosList.get(position);

            Picasso picasso = Picasso.with(context);
            picasso.load(currentPromoVideo.getmImageUrl()).into(holder.videoSnapIV);

            holder.titleTV.setText(currentPromoVideo.getmTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String[] splittedUrl = currentPromoVideo.getmVideoUrl().split("embed/");
                    int stringLength = splittedUrl.length;
                    Intent appIntent = new Intent();
                    if (stringLength > 1){
                        appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + splittedUrl[1]));
                    }
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(currentPromoVideo.getmVideoUrl()));
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(webIntent);
                    }

//                    Bundle b = new Bundle();
//                    b.putString(getString(R.string.url), currentPromoVideo.getmVideoUrl());
//                    b.putBoolean(getString(R.string.is_youtube_link), true);
//
//                    ShowVideoDialogFragment videoDialogFragment = new ShowVideoDialogFragment();
//                    videoDialogFragment.setArguments(b);
//                    videoDialogFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "ShowVideoDialogFragment");

//                    Dialog mVideoDialog;
//                    VideoView mVideoFullScreen;
//                    MediaController controller;
//
//                    mVideoDialog = new Dialog(context);
//                    mVideoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    mVideoDialog.setContentView(R.layout.video_popup_window_layout);
////                    mVideoDialog.setOnKeyListener(this);
//                    mVideoFullScreen = (VideoView) mVideoDialog.findViewById(R.id.popup_video_view);
//
//                    MediaSession mediaSession;
//                    PlaybackState.Builder stateBuilder;
//
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                        mediaSession = new MediaSession(context, "Media Session");
//                        mediaSession.setFlags(
//                                MediaSession.FLAG_HANDLES_MEDIA_BUTTONS |
//                                        MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
//                        mediaSession.setMediaButtonReceiver(null);
//                        stateBuilder = new PlaybackState().Builder()
//                                .setActions(
//                                        PlaybackStateCompat.ACTION_PLAY |
//                                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
//                        mediaSession.setPlaybackState(stateBuilder.build());
//                        mediaSession.setCallback(new MySessionCallback());
//                        controller = new MediaController(context, mediaSession);
//                    }
//                    controller = new MediaController(context, mediaSession);
//                    HelloWebViewClient webViewClient = new HelloWebViewClient();
//                    webView.setWebViewClient(webViewClient);
//
//
//                    private class HelloWebViewClient extends WebViewClient {
//                        @Override
//                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                            if (url.contentEquals("file:///android_asset/01")) {
//                                showVideo(01); //check user click url from webview and pass int
//                            } else if (url.contentEquals("file:///android_asset/02")) {
//                                showVideo(02);//pass int to determine which video to play
//                            } else {
//                                System.out.println("DDD URL: " + url.toString());
//                                view.loadUrl(url);
//                            }
//                            return true;
//                        }
//
//                        public void showVideo(int i) {
//                            if (i == 01) {
//                                mVideoDialog.show();
//                                mVideoFullScreen.setVideoPath("file:///sdcard/video file name.m4v");
//                                controller.setMediaPlayer(mVideoFullScreen);
//                                mVideoFullScreen.setMediaController(controller);
//                                mVideoFullScreen.requestFocus();
//                                mVideoFullScreen.start();
//                            } else {
//                            }
//                        }
//
////                    new AnimeVideoPopupWindow(context, R.layout.image_popup_window_layout, view, currentPromoVideo.getmVideoUrl(), null);
//                    }
//                    mVideoDialog.show();
                    //Toast.makeText(context,"item clicked...",Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return promoVideosList.size();
        }

        public class PromoVideosViewHolder extends RecyclerView.ViewHolder {

            ImageView videoSnapIV;
            TextView titleTV;

            public PromoVideosViewHolder(View itemView) {
                super(itemView);
                videoSnapIV = itemView.findViewById(R.id.video_snap_iv);
                titleTV = itemView.findViewById(R.id.video_title_tv);
            }
        }
    }

    class EpisodesVideosListAdapter extends RecyclerView.Adapter<EpisodesVideosListAdapter.EpisodesVideosViewHolder> {

        private LayoutInflater mInflater;
        private Context context;

        public EpisodesVideosListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public EpisodesVideosListAdapter.EpisodesVideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.videos_list_item, parent, false);
            return new EpisodesVideosViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(EpisodesVideosListAdapter.EpisodesVideosViewHolder holder, int position) {

            final EpisodesVideos currentEpisodesVideos = episodesVideosList.get(position);

            Picasso picasso = Picasso.with(context);
            picasso.load(currentEpisodesVideos.getmImageUrl()).into(holder.videoSnapIV);

            holder.titleTV.setText(currentEpisodesVideos.getmTitle());
            holder.episodeIndexIV.setText(currentEpisodesVideos.getmEpisode());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new AnimeVideoPopupWindow(context, R.layout.image_popup_window_layout, view, currentEpisodesVideos.getmUrl(), null);
                    String[] splittedUrl = currentEpisodesVideos.getmUrl().split("embed/");
                    int stringLength = splittedUrl.length;
                    Intent appIntent = new Intent();
                    if (stringLength > 1){
                        appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + splittedUrl[1]));
                    }
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(currentEpisodesVideos.getmUrl()));
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(webIntent);
                    }
//
//                    Bundle b = new Bundle();
//                    b.putString(getString(R.string.url), currentEpisodesVideos.getmUrl());
//                    b.putBoolean(getString(R.string.is_youtube_link), true);
//
//                    ShowVideoDialogFragment videoDialogFragment = new ShowVideoDialogFragment();
//                    videoDialogFragment.setArguments(b);
//                    videoDialogFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "ShowVideoDialogFragment");
                    //Toast.makeText(context, "item clicked...", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return episodesVideosList.size();
        }

        public class EpisodesVideosViewHolder extends RecyclerView.ViewHolder {

            ImageView videoSnapIV;
            TextView titleTV;
            TextView episodeIndexIV;

            public EpisodesVideosViewHolder(View itemView) {
                super(itemView);
                videoSnapIV = itemView.findViewById(R.id.video_snap_iv);
                titleTV = itemView.findViewById(R.id.video_title_tv);
                episodeIndexIV = itemView.findViewById(R.id.video_episode_index_tv);
            }
        }
    }
}
