package com.example.ideal48.application160519.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.StatsResponse;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    View view;
    TextView watchingCountTV;
    TextView completedCountTV;
    TextView onHoldCountTV;
    TextView droppedCountTV;
    TextView planToWatchCountTV;
    TextView totalCountTV;
    HorizontalBarChart chart;

    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        watchingCountTV = view.findViewById(R.id.watching_count);
        completedCountTV = view.findViewById(R.id.completed_count);
        onHoldCountTV = view.findViewById(R.id.on_hold_count);
        droppedCountTV = view.findViewById(R.id.dropped_count);
        planToWatchCountTV = view.findViewById(R.id.plan_to_watch_count);
        totalCountTV = view.findViewById(R.id.total_count);
        chart = view.findViewById(R.id.chart);

        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        XAxis xl = chart.getXAxis();
//        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
//        xl.setGranularity(10f);
//
        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
//        yl.setAxisMinimum(0f);

        YAxis yr = chart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
//        yr.setAxisMinimum(0f);
//
//        chart.setFitBars(true);
//        chart.animateY(2500);
//
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setFormSize(8f);
//        l.setXEntrySpace(4f);

        Call<StatsResponse> call = AnimeDetailsActivity.service.getStatsResponse(AnimeDetailsActivity.malId);
        call.enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                if (response.body() != null) {

                    StatsResponse stats = response.body();

                    watchingCountTV.setText("Watching: " + String.valueOf(stats.getmWatchingCount()));
                    completedCountTV.setText("Completed: " + String.valueOf(stats.getmCompletedCount()));
                    onHoldCountTV.setText("On-Hold: " + String.valueOf(stats.getmOnHoldCount()));
                    droppedCountTV.setText("Dropped: " + String.valueOf(stats.getmDroppedCount()));
                    planToWatchCountTV.setText("Plan to Watch: " + String.valueOf(stats.getmPlanToWatchCount()));
                    totalCountTV.setText("Total: " + String.valueOf(stats.getmTotalCount()));

                    StatsResponse.Score score = response.body().getmScoresList();

                    ArrayList xAxis = new ArrayList();
                    xAxis.add(10);
                    xAxis.add(9);
                    xAxis.add(8);
                    xAxis.add(7);
                    xAxis.add(6);
                    xAxis.add(5);
                    xAxis.add(4);
                    xAxis.add(3);
                    xAxis.add(2);
                    xAxis.add(1);

                    ArrayList<BarEntry> rating = new ArrayList<>();
                    rating.add(new BarEntry(10, ((float) score.getmScore10().getmPercentage()), 10));
                    rating.add(new BarEntry(9, ((float) score.getmScore9().getmPercentage()), 9));
                    rating.add(new BarEntry(8, ((float) score.getmScore8().getmPercentage()), 8));
                    rating.add(new BarEntry(7, ((float) score.getmScore7().getmPercentage()), 7));
                    rating.add(new BarEntry(6, ((float) score.getmScore6().getmPercentage()), 6));
                    rating.add(new BarEntry(5, ((float) score.getmScore5().getmPercentage()), 5));
                    rating.add(new BarEntry(4, ((float) score.getmScore4().getmPercentage()), 4));
                    rating.add(new BarEntry(3, ((float) score.getmScore3().getmPercentage()), 3));
                    rating.add(new BarEntry(2, ((float) score.getmScore2().getmPercentage()), 2));
                    rating.add(new BarEntry(1, ((float) score.getmScore1().getmPercentage()), 1));

                    BarDataSet barDataSet = new BarDataSet(rating, "score");

                    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                    dataSets.add(barDataSet);

                    BarData data = new BarData(dataSets);
                    data.setBarWidth(0.5f);

                    chart.setData(data);

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        onHoldCountTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        onHoldCountTV.setText("Stats Not Found !");
                    }
                }
            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

}
