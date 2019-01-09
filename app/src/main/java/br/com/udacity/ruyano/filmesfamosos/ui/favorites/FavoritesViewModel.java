package br.com.udacity.ruyano.filmesfamosos.ui.favorites;

import android.app.Application;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.repositories.MovieRepository;

public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private Parcelable gridLayoutManagerSavedStatus;
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
    private DataSource.Factory datasourceFactory;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);

        datasourceFactory = new MovieRepository(getApplication()).getAllMoviesPaged();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build();

        moviesPagedList = (new LivePagedListBuilder(datasourceFactory, pagedListConfig)).build();

    }


    public void init(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            adapter = new FavoritesAdapter(this);
            recyclerViewVisibility = new ObservableInt(View.GONE);
            movieSelected = new MutableLiveData<>();
            statusImageVisibility = new ObservableInt(View.GONE);
            statusImageResourceId = new ObservableInt(R.drawable.loading);
            statusTextVisibility = new ObservableInt(View.GONE);
            statusTextResourceId = new ObservableInt(R.string.error_favorites_message);
        }

        gridLayoutManager = new GridLayoutManager(getApplication(), 2);

    }

    public FavoritesAdapter getAdapter() {
        return adapter;

    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;

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


    void saveRecyclerViewInstanceState() {
        gridLayoutManagerSavedStatus = gridLayoutManager.onSaveInstanceState();
    }

    void restoreRecyclerViewInstanceState() {
        if (gridLayoutManagerSavedStatus != null) {
            gridLayoutManager.onRestoreInstanceState(gridLayoutManagerSavedStatus);
            gridLayoutManagerSavedStatus = null;
        }
    }
}
