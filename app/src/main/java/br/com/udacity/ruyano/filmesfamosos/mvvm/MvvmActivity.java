package br.com.udacity.ruyano.filmesfamosos.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityMvvmBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.ui.MovieDetailsActivity;
import br.com.udacity.ruyano.filmesfamosos.util.GridRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MvvmActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_movies)
    GridRecyclerView rvMovies;

    @BindView(R.id.srl_swipe_to_refresh_layout)
    SwipeRefreshLayout srlSwipeToRefreshLayout;

    private ViewModelTeste viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        ButterKnife.bind(this);

        srlSwipeToRefreshLayout.setOnRefreshListener(this);
        srlSwipeToRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

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

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                viewModel.loading.set(View.GONE);
                if (movies.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    srlSwipeToRefreshLayout.setRefreshing(false);
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setMoviesInAdapter(movies);
                }
            }
        });

        viewModel.fetchList();
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

    @Override
    public void onRefresh() {
        srlSwipeToRefreshLayout.setRefreshing(true);
        viewModel.fetchList();
    }
}
