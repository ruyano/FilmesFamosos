package br.com.udacity.ruyano.filmesfamosos.ui.favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityFavoritesBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.MovieDetailsActivity;
import br.com.udacity.ruyano.filmesfamosos.util.NetworkUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class FavoritesActivity extends AppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }

    private FavoritesViewModel favoritesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.menu_favorites));

        setupBindings(savedInstanceState);

    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityFavoritesBinding activityFavoritesBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorites);
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        if (savedInstanceState == null) {
            favoritesViewModel.init();
        }
        activityFavoritesBinding.setModel(favoritesViewModel);

        setupListUpdate();

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
}
