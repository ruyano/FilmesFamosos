package br.com.udacity.ruyano.filmesfamosos.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityMvvmBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.ui.MovieDetailsActivity;
import butterknife.ButterKnife;

public class MvvmActivity extends AppCompatActivity {

    private ViewModelTeste viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        setupBindings(savedInstanceState);

    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityMvvmBinding activityMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        viewModel = ViewModelProviders.of(this).get(ViewModelTeste.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityMvvmBinding.setModel(viewModel);
        setupListUpdate();

    }

    private void setupListUpdate() {
        viewModel.loading.set(View.VISIBLE);

        viewModel.moviesPagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                viewModel.loading.set(View.GONE);
                viewModel.isLoading.set(false);
                if (movies.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setMoviesInAdapter(movies);
                }
            }
        });

        setupListClick();
    }

    private void setupListClick() {
        viewModel.getMovieSelected().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Intent intent = MovieDetailsActivity.getIntent(MvvmActivity.this, movie);
                startActivity(intent);
            }
        });
    }
}
