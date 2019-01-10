package br.com.udacity.ruyano.filmesfamosos.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityMainBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.movies.MoviesDataSource;
import br.com.udacity.ruyano.filmesfamosos.ui.favorites.FavoritesActivity;
import br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.MovieDetailsActivity;
import br.com.udacity.ruyano.filmesfamosos.util.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private MoviesListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBindings(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                if (viewModel.getCurrentMovieType() != MoviesDataSource.MoviesTypeEnum.POPULAR) {
                    viewModel.setMoviesType(MoviesDataSource.MoviesTypeEnum.POPULAR);
                }
                return true;
            case R.id.avaliation:
                if (viewModel.getCurrentMovieType() != MoviesDataSource.MoviesTypeEnum.TOP_RATED) {
                    viewModel.setMoviesType(MoviesDataSource.MoviesTypeEnum.TOP_RATED);
                }
                return true;
            case R.id.favorites:
                startActivity(FavoritesActivity.getIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MoviesListViewModel.class);
        viewModel.init(savedInstanceState);
        activityMainBinding.setModel(viewModel);

        setupActivityName();

        if (NetworkUtil.isConected(this)) {
            setupListUpdate();
        } else {
            viewModel.showNoInternetView();
        }

    }

    private void setupActivityName() {
        viewModel.activityTitleResourceId.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(getString(integer));
            }
        });
    }

    private void setupListUpdate() {
        viewModel.showLoadinView();

        viewModel.moviesPagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                viewModel.hideLoading();
                viewModel.isLoading.set(false);
                if (movies.size() == 0) {
                    viewModel.showErrorView();
                } else {
                    viewModel.setMoviesInAdapter(movies);
                    viewModel.restoreRecyclerViewInstanceState();
                }
            }
        });

        setupListClick();

    }

    private void setupListClick() {
        viewModel.getMovieSelected().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Intent intent = MovieDetailsActivity.getIntent(MainActivity.this, movie);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (viewModel != null) {
            viewModel.saveRecyclerViewInstanceState();
        }
    }
}
