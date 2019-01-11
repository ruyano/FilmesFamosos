package br.com.udacity.ruyano.filmesfamosos.ui.favorites;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.repositories.MovieRepository;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesAdapter adapter;
    private MutableLiveData<Movie> movieSelected;
    // recyclerView
    public ObservableInt recyclerViewVisibility;
    // Status image
    public ObservableInt statusImageVisibility;
    public ObservableInt statusImageResourceId;
    // Status text
    public ObservableInt statusTextVisibility;
    public ObservableInt statusTextResourceId;

    //creating livedata for PagedList  and DataSource
    LiveData<PagedList<Movie>> moviesPagedList;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        DataSource.Factory datasourceFactory = new MovieRepository(getApplication()).getAllMoviesPaged();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build();

        moviesPagedList = (new LivePagedListBuilder(datasourceFactory, pagedListConfig)).build();

    }


    public void init() {
        adapter = new FavoritesAdapter(this);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        movieSelected = new MutableLiveData<>();
        statusImageVisibility = new ObservableInt(View.GONE);
        statusImageResourceId = new ObservableInt(R.drawable.loading);
        statusTextVisibility = new ObservableInt(View.GONE);
        statusTextResourceId = new ObservableInt(R.string.error_favorites_message);
    }

    public FavoritesAdapter getAdapter() {
        return adapter;

    }

    public void resetMovieSelected() {
        movieSelected = new MutableLiveData<>();

    }

    void setMoviesInAdapter(PagedList<Movie> movies) {
        adapter.submitList(movies);

    }

    MutableLiveData<Movie> getMovieSelected() {
        return movieSelected;

    }

    public void onItemClick(Integer index) {
        Movie selected = getMovieAt(index);
        if (selected != null)
            movieSelected.setValue(selected);

    }

    public Movie getMovieAt(Integer index) {
        if (moviesPagedList.getValue() != null
                && moviesPagedList.getValue().size() > index) {
            return moviesPagedList.getValue().get(index);
        }
        return null;

    }

    void showEmptyView() {
        statusImageVisibility.set(View.VISIBLE);
        statusTextVisibility.set(View.VISIBLE);
        statusTextResourceId.set(R.string.error_favorites_message);
        recyclerViewVisibility.set(View.GONE);
        statusImageResourceId.set(R.drawable.cade);

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
