package br.com.udacity.ruyano.filmesfamosos.networking.data.sources.movies;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;

public class MoviesDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource = new MutableLiveData<>();

    private MoviesDataSource dataSource;

    private MoviesDataSource.MoviesTypeEnum moviesType = MoviesDataSource.MoviesTypeEnum.POPULAR;

    @Override
    public DataSource<Integer, Movie> create() {

        //getting our data source object
        dataSource = new MoviesDataSource(moviesType);

        //posting the datasource to get the values
        liveDataSource.postValue(dataSource);

        //returning the datasource
        return dataSource;

    }

    public void setMoviesType(MoviesDataSource.MoviesTypeEnum moviesType) {
        this.moviesType = moviesType;
        liveDataSource.postValue(dataSource);
    }

    public MoviesDataSource.MoviesTypeEnum getMoviesType() {
        return moviesType;
    }

    //getter for liveDataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getLiveDataSource() {
        return liveDataSource;
    }


    public void invalidateDataSource() {
        dataSource.invalidate();
    }

}