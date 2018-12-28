package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityMovieDetailsBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.Review;
import br.com.udacity.ruyano.filmesfamosos.model.Video;
import br.com.udacity.ruyano.filmesfamosos.model.VideoRequestResult;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String EXTRAS_MOVIE = "movie";

    private Movie movie;

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
        setupBindings(savedInstanceState);
    }

    private void getExtras() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(EXTRAS_MOVIE)) {
            this.movie = getIntent().getExtras().getParcelable(EXTRAS_MOVIE);
        }
    }

    private void setupBindings(Bundle savedInstanceState) {
        ActivityMovieDetailsBinding activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        final MovieDetailsViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);

        if (savedInstanceState == null)
            viewModel.init();

        if (movie != null)
            viewModel.setMovie(movie);

        activityMovieDetailsBinding.setModel(viewModel);

        setupVideoList(viewModel);

        setupReviewList(viewModel);

        viewModel.getVideos();
    }

    private void setupReviewList(final MovieDetailsViewModel viewModel) {
        viewModel.reviewPagedList.observe(this, new Observer<PagedList<Review>>() {
            @Override
            public void onChanged(PagedList<Review> reviews) {
                if (reviews != null && !reviews.isEmpty()) {
                    viewModel.setReviewsInAdapter(reviews);
                }
            }
        });
    }

    private void setupVideoListClick(MovieDetailsViewModel viewModel) {
        viewModel.getSelectedVideo().observe(this, new Observer<Video>() {
            @Override
            public void onChanged(Video video) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.youtube_url, video.getKey())));
                startActivity(intent);
            }
        });
    }

    private void setupVideoList(final MovieDetailsViewModel viewModel) {
        viewModel.getVideosLiveData().observe(this, new Observer<VideoRequestResult>() {
            @Override
            public void onChanged(VideoRequestResult videoRequestResult) {
                if (videoRequestResult != null && !videoRequestResult.getVideos().isEmpty()) {
                    viewModel.setVideosInAdapter(videoRequestResult.getVideos());
                }
                // TODO - tratar caso de lista vazia
            }
        });

        setupVideoListClick(viewModel);
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
