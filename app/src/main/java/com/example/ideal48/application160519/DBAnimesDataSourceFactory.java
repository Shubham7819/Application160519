package com.example.ideal48.application160519;

import android.arch.paging.DataSource;

class DBAnimesDataSourceFactory extends DataSource.Factory {

    private DBAnimesDataSource animesDataSource;

    public DBAnimesDataSourceFactory(AnimeDao dao) {
//        animesDataSource = new DBAnimesDataSource(dao);
    }

    @Override
    public DataSource create() {
        return animesDataSource;
    }
}
