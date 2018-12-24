package br.com.udacity.ruyano.filmesfamosos.mvvm;

import android.view.View;

import java.util.List;

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

public class ViewModelTeste extends ViewModel {

    private MvvmAdapter adapter;
    private MutableLiveData<Movie> movieSelected;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    public ObservableBoolean isLoading;

    //creating livedata for PagedList  and PagedKeyedDataSource
    LiveData<PagedList<Movie>> moviesPagedList;
    private LiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource;

    private MoviesDataSourceFactory moviesDataSourceFactory;

    void init() {
        adapter = new MvvmAdapter(this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        movieSelected = new MutableLiveData<>();
        isLoading = new ObservableBoolean(false);

    }

    //constructor
    public ViewModelTeste() {
        //getting our data source factory
        moviesDataSourceFactory = new MoviesDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = moviesDataSourceFactory.getLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build();

        //Building the paged list

        moviesPagedList = (new LivePagedListBuilder(moviesDataSourceFactory, pagedListConfig)).build();
    }

    public MvvmAdapter getAdapter() {
        return adapter;
    }

    public void setMoviesInAdapter(PagedList<Movie> movies) {
        adapter.submitList(movies);
    }

    public MutableLiveData<Movie> getMovieSelected() {
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
        moviesDataSourceFactory.invalidateDataSource();
    }

}
