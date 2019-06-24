package com.example.ideal48.application160519.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;

import static com.example.ideal48.application160519.activity.AnimeDetailsActivity.animeDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    View view;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);

        final TextView engTitleTV = view.findViewById(R.id.eng_title_tv);
        final TextView synoTitleTV = view.findViewById(R.id.syno_title_tv);
        final TextView japTitleTV = view.findViewById(R.id.jap_title_tv);
        final TextView typeTV = view.findViewById(R.id.type_tv);
        final TextView episodesTV = view.findViewById(R.id.episodes_tv);
        final TextView statusTV = view.findViewById(R.id.status_tv);
        final TextView premieredTV = view.findViewById(R.id.premiered_tv);
        final TextView broadcastTV = view.findViewById(R.id.broadcast_tv);
        final TextView sourceTV = view.findViewById(R.id.source_tv);
        final TextView durationTV = view.findViewById(R.id.duration_tv);
        final TextView synopsysTV = view.findViewById(R.id.synopsys_tv);
        final TextView backgroundTV = view.findViewById(R.id.background_tv);


        engTitleTV.setText("English: " + String.valueOf(animeDetails.getmTitleEng()));
        synoTitleTV.setText("Synonyms: " + animeDetails.getmTitleSyno());
        japTitleTV.setText("Japanese: " + animeDetails.getmTitleJap());
        typeTV.setText("Type: " + animeDetails.getmType());
        episodesTV.setText("Episodes: " + String.valueOf(animeDetails.getmEpisodes()));
        statusTV.setText("Status: " + animeDetails.getmStatus());
        if (animeDetails.getmPremiered()== null || TextUtils.isEmpty(animeDetails.getmPremiered())){
            premieredTV.setText("Premiered: N/A");
        }else {
            premieredTV.setText("Premiered: " + animeDetails.getmPremiered());
        }

        if (animeDetails.getmBroadcast()== null || TextUtils.isEmpty(animeDetails.getmBroadcast())){
            broadcastTV.setText("Broadcast: N/A");
        }else {
            broadcastTV.setText("Broadcast: " + animeDetails.getmBroadcast());
        }
        sourceTV.setText("Source: " + animeDetails.getmSource());
        durationTV.setText("Duration: " + animeDetails.getmDuration());
        synopsysTV.setText(animeDetails.getmSynopsis());

        if (animeDetails.getmBackground()== null || TextUtils.isEmpty(animeDetails.getmBackground())){
            backgroundTV.setText("N/A");
        }else {
            backgroundTV.setText(animeDetails.getmBackground());
        }
        return view;
    }

}
