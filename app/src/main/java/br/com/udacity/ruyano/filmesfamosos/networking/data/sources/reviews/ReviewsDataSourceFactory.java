package br.com.udacity.ruyano.filmesfamosos.networking.data.sources.reviews;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import br.com.udacity.ruyano.filmesfamosos.model.Review;

public class ReviewsDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Review>> liveDataSource = new MutableLiveData<>();

    private ReviewsDataSource dataSource;

    private Integer movieId;

    public ReviewsDataSourceFactory(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public DataSource<Integer, Review> create() {

        dataSource = new ReviewsDataSource(movieId);

        liveDataSource.postValue(dataSource);

        return dataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Review>> getLiveDataSource() {
        return liveDataSource;
    }

    public void invalidateDataSource() {
        dataSource.invalidate();
    }

}
