package br.com.udacity.ruyano.filmesfamosos.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;

public class MoviesDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource = new MutableLiveData<>();

    private MoviesDataSource dataSource;

    @Override
    public DataSource<Integer, Movie> create() {

        //getting our data source object
        dataSource = new MoviesDataSource();

        //posting the datasource to get the values
        liveDataSource.postValue(dataSource);

        //returning the datasource
        return dataSource;

    }

    //getter for liveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getLiveDataSource() {
        return liveDataSource;
    }


    public void invalidateDataSource() {
        dataSource.invalidate();
    }

}
