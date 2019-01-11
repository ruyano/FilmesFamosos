package br.com.udacity.ruyano.filmesfamosos.ui.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityFavoritesBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.MovieDetailsActivity;

public class FavoritesActivity extends AppCompatActivity {

    private static final String RECYCLERVIEW_SAVED_STATE = "RECYCLERVIEW_SAVED_STATE";

    private FavoritesViewModel favoritesViewModel;
    private ActivityFavoritesBinding activityFavoritesBinding;
    private Parcelable recyclerViewSavedInstance;

    public static Intent getIntent(Context context) {
        return new Intent(context, FavoritesActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.menu_favorites));
        setupBindings(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerViewSavedInstance != null) {
            Objects.requireNonNull(activityFavoritesBinding.favoritesRecyclerview.getLayoutManager()).onRestoreInstanceState(recyclerViewSavedInstance);
            recyclerViewSavedInstance = null;

        }

    }

    private void setupBindings(Bundle savedInstanceState) {
        activityFavoritesBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorites);
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        if (savedInstanceState == null) {
            favoritesViewModel.init();
        }
        activityFavoritesBinding.setModel(favoritesViewModel);
        setupRecyclerView();
        setupListUpdate();

    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        activityFavoritesBinding.favoritesRecyclerview.setLayoutManager(gridLayoutManager);
        activityFavoritesBinding.favoritesRecyclerview.setAdapter(favoritesViewModel.getAdapter());

    }

    private void setupListUpdate() {
        favoritesViewModel.showLoadinView();
        favoritesViewModel.moviesPagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                favoritesViewModel.hideLoading();
                if (movies.size() == 0) {
                    favoritesViewModel.showEmptyView();
                } else {
                    favoritesViewModel.setMoviesInAdapter(movies);
                }

            }
        });
        setupListClick();

    }

    private void setupListClick() {
        favoritesViewModel.getMovieSelected().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Intent intent = MovieDetailsActivity.getIntent(FavoritesActivity.this, movie);
                startActivity(intent);
                favoritesViewModel.resetMovieSelected();
                setupListClick();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECYCLERVIEW_SAVED_STATE, Objects.requireNonNull(activityFavoritesBinding.favoritesRecyclerview.getLayoutManager()).onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recyclerViewSavedInstance = savedInstanceState.getParcelable(RECYCLERVIEW_SAVED_STATE);

    }

}
