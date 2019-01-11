package br.com.udacity.ruyano.filmesfamosos.ui.main;

import android.app.Application;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.movies.MoviesDataSource;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.movies.MoviesDataSourceFactory;

public class MoviesListViewModel extends AndroidViewModel {

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

    public MutableLiveData<Integer> activityTitleResourceId;

    //creating livedata for PagedList  and DataSource
    LiveData<PagedList<Movie>> moviesPagedList;

    private MoviesDataSourceFactory moviesDataSourceFactory;


    void init() {
        adapter = new MoviesListAdapter(this);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        movieSelected = new MutableLiveData<>();
        isLoading = new ObservableBoolean(false);

        statusImageVisibility = new ObservableInt(View.GONE);
        statusImageResourceId = new ObservableInt(R.drawable.loading);

        statusTextVisibility = new ObservableInt(View.GONE);
        statusTextResourceId = new ObservableInt(R.string.no_internet_message);

        activityTitleResourceId = new MutableLiveData<>();
        activityTitleResourceId.postValue(R.string.menu_popularity);

    }

    //constructor
    public MoviesListViewModel(Application application) {
        super(application);
        //getting our data source factory
        moviesDataSourceFactory = new MoviesDataSourceFactory();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build();

        //Building the paged list
        moviesPagedList = (new LivePagedListBuilder(moviesDataSourceFactory, pagedListConfig)).build();

    }

    void setMoviesType(MoviesDataSource.MoviesTypeEnum moviesType) {
        moviesDataSourceFactory.setMoviesType(moviesType);
        moviesDataSourceFactory.invalidateDataSource();

        if (moviesType == MoviesDataSource.MoviesTypeEnum.POPULAR) {
            activityTitleResourceId.postValue(R.string.menu_popularity);
        } else {
            activityTitleResourceId.postValue(R.string.menu_avaliation);
        }

    }

    MoviesDataSource.MoviesTypeEnum getCurrentMovieType() {
        return moviesDataSourceFactory.getMoviesType();

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

    public void resetMovieSelected() {
        this.movieSelected = new MutableLiveData<>();
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
        moviesDataSourceFactory.invalidateDataSource();
    }

    void showNoInternetView() {
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
