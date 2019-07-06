package com.example.ideal48.application160519;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.example.ideal48.application160519.model.Anime;

public class AnimesListViewModel extends AndroidViewModel {

    public AnimesRepository repository;

    public AnimesListViewModel(@NonNull Application application) {
        super(application);
        repository = AnimesRepository.getInstance(application);
    }

    public LiveData<PagedList<Anime>> getAnimes() {
        return repository.getAnimes();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}
