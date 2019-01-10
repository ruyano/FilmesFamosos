package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityMovieDetailsBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.review.MovieReviewsActivity;
import br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.videos.MovieVideosActivity;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String EXTRAS_MOVIE = "movie";

    private Movie movie;

    private MovieDetailsViewModel viewModel;

    public static Intent getIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRAS_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        setActionBarTitle();
        setupBindings(savedInstanceState);
    }

    private void getExtras() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(EXTRAS_MOVIE)) {
            this.movie = getIntent().getExtras().getParcelable(EXTRAS_MOVIE);
        }
    }

    private void setActionBarTitle() {
        if (movie != null && !movie.getTitle().isEmpty())
            Objects.requireNonNull(getSupportActionBar()).setTitle(movie.getTitle());
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityMovieDetailsBinding activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

        if (savedInstanceState == null)
            viewModel.init();

        if (movie != null)
            viewModel.setMovie(movie);

        activityMovieDetailsBinding.setModel(viewModel);

        setupFavoriteVerification();

    }

    private void setupFavoriteVerification() {
        viewModel.getDatabaseMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                viewModel.setIsFavorite(movie != null);
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

    public void showReviews(View view) {
        startActivity(MovieReviewsActivity.getIntent(this, movie));
    }

    public void showVideos(View view) {
        startActivity(MovieVideosActivity.getIntent(this, movie));
    }

}
