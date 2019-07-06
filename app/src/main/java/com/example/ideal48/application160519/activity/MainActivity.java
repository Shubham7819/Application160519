package com.example.ideal48.application160519.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ideal48.application160519.AnimesListViewModel;
import com.example.ideal48.application160519.R;

public class MainActivity extends AppCompatActivity {

   // AnimesListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.BrandedLaunch);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewModel = ViewModelProviders.of(this).get(AnimesListViewModel.class);
        MyRunnable myRunnable = new MyRunnable();

        Thread thread = new Thread(myRunnable);

        thread.start();

        try {
            Thread.sleep(3L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myRunnable.doStop();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        this.finish();
    }

    public class MyRunnable implements Runnable {

        private boolean doStop = false;

        public synchronized void doStop() {
            this.doStop = true;
        }

        private synchronized boolean keepRunning() {
            return this.doStop == false;
        }

        @Override
        public void run() {

//            viewModel.repository.liveDataMerger.postValue(viewModel.getAnimes());

            while (keepRunning()) {
                // keep doing what this thread should do.

                try {
                    Thread.sleep(3L * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
