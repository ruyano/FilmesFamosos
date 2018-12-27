package br.com.udacity.ruyano.filmesfamosos.ui.main;

import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.movies.MoviesDataSource;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.movies.MoviesDataSourceFactory;

public class MoviesListViewModel extends ViewModel {

    private MoviesListAdapter adapter;
    private MutableLiveData<Movie> movieSelected;
    public ObservableBoolean isLoading;

    // recyclerView
    public ObservableInt recyclerViewVisibility;

    // Status image
    public ObservableInt statusImageVisibility;
    public ObservableInt statusImageResourceId;

    // Status text
    public ObservableInt statusTextVisibility;
    public ObservableInt statusTextResourceId;


    //creating livedata for PagedList  and PagedKeyedDataSource
    LiveData<PagedList<Movie>> moviesPagedList;

    private LiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource;

    private MoviesDataSourceFactory popularMoviesDataSourceFactory;

    void init() {
        adapter = new MoviesListAdapter(this);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        movieSelected = new MutableLiveData<>();
        isLoading = new ObservableBoolean(false);

        statusImageVisibility = new ObservableInt(View.GONE);
        statusImageResourceId = new ObservableInt(R.drawable.loading);

        statusTextVisibility = new ObservableInt(View.GONE);
        statusTextResourceId = new ObservableInt(R.string.no_internet_message);

    }

    //constructor
    public MoviesListViewModel() {
        callForPopularMovies("popular");
    }

    public void callForPopularMovies(String subList) {

        if (popularMoviesDataSourceFactory != null)
            popularMoviesDataSourceFactory.invalidateDataSource();
        //getting our data source factory
        popularMoviesDataSourceFactory = new MoviesDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = popularMoviesDataSourceFactory.getLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build();

        //Building the paged list

        moviesPagedList = (new LivePagedListBuilder(popularMoviesDataSourceFactory, pagedListConfig)).build();

    }

    void setMoviesType(MoviesDataSource.MoviesTypeEnum moviesType) {
        popularMoviesDataSourceFactory.setMoviesType(moviesType);
        popularMoviesDataSourceFactory.invalidateDataSource();

    }

    MoviesDataSource.MoviesTypeEnum getCurrentMovieType() {
        return popularMoviesDataSourceFactory.getMoviesType();
    }

    public MoviesListAdapter getAdapter() {
        return adapter;
    }

    void setMoviesInAdapter(PagedList<Movie> movies) {
        adapter.submitList(movies);
    }

    MutableLiveData<Movie> getMovieSelected() {
        return movieSelected;
    }

    public Movie getMovieAt(Integer index) {
        if (moviesPagedList.getValue() != null
                && moviesPagedList.getValue().size() > index) {
            return moviesPagedList.getValue().get(index);
        }
        return null;
    }

    public void onItemClick(Integer index) {
        Movie selected = getMovieAt(index);
        if (selected != null)
            movieSelected.setValue(selected);
    }

    public void onRefresh() {
        isLoading.set(true);
        recyclerViewVisibility.set(View.GONE);
        popularMoviesDataSourceFactory.invalidateDataSource();
    }

    public void showNoInternetView() {
        statusImageVisibility.set(View.VISIBLE);
        statusTextVisibility.set(View.VISIBLE);
        statusTextResourceId.set(R.string.no_internet_message);
        recyclerViewVisibility.set(View.GONE);
        statusImageResourceId.set(R.drawable.no_internet);

    }

    void showErrorView() {
        statusImageVisibility.set(View.VISIBLE);
        statusTextVisibility.set(View.VISIBLE);
        statusTextResourceId.set(R.string.error_message);
        recyclerViewVisibility.set(View.GONE);
        statusImageResourceId.set(R.drawable.error);

    }

    void showLoadinView() {
        statusImageVisibility.set(View.VISIBLE);
        statusTextVisibility.set(View.GONE);
        recyclerViewVisibility.set(View.GONE);
        statusImageResourceId.set(R.drawable.loading);

    }

    void hideLoading() {
        statusImageVisibility.set(View.GONE);
        statusTextVisibility.set(View.GONE);
        recyclerViewVisibility.set(View.VISIBLE);

    }

}
