package com.example.ideal48.application160519.fragment;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ideal48.application160519.R;

import static com.example.ideal48.application160519.activity.HomeActivity.toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {

    View rootView;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("AboutFragment.java", "OnCreate called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_layout, container, false);

        Log.v("AboutFragment.java", "OnCreateView called");

        TextView rateUs = rootView.findViewById(R.id.rate_us_view);
        TextView moreApps = rootView.findViewById(R.id.more_apps_view);
        TextView shareApp = rootView.findViewById(R.id.share_app_view);
        TextView privacyPolicy = rootView.findViewById(R.id.privacy_policy_view);
        TextView appVersion = rootView.findViewById(R.id.app_version_view);

        rateUs.setOnClickListener(this);
        moreApps.setOnClickListener(this);
        shareApp.setOnClickListener(this);
        privacyPolicy.setOnClickListener(this);

        try {
            PackageInfo pInfo =   getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;
            appVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.rate_us_view:
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.it.betalpachisihindi&hl=en")));
                break;

            case R.id.more_apps_view:
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Ideal+IT+Techno+Pvt+Ltd&hl=en")));
                break;

            case R.id.share_app_view:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.it.betalpachisihindi&hl=en");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share Using."));
                break;

            case R.id.privacy_policy_view:
                startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy?hl=en")));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar.setTitle(R.string.about);
    }
}
