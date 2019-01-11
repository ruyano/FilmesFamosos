package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.videos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityMovieVideosBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.Video;
import br.com.udacity.ruyano.filmesfamosos.model.VideoRequestResult;
import br.com.udacity.ruyano.filmesfamosos.util.NetworkUtil;

public class MovieVideosActivity extends AppCompatActivity {

    private static final String RECYCLERVIEW_SAVED_STATE = "RECYCLERVIEW_SAVED_STATE";
    public static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    private MovieVideosViewModel viewModel;
    private ActivityMovieVideosBinding activityMovieVideosBinding;
    private Parcelable recyclerViewSavedInstance;

    private Movie movie;

    public static Intent getIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieVideosActivity.class);
        intent.putExtra(EXTRAS_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_videos);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        setupBindings(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerViewSavedInstance != null) {
            Objects.requireNonNull(activityMovieVideosBinding.videosRecyclerview.getLayoutManager()).onRestoreInstanceState(recyclerViewSavedInstance);
            recyclerViewSavedInstance = null;

        }

    }

    private void getExtras() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(EXTRAS_MOVIE)) {
            this.movie = getIntent().getExtras().getParcelable(EXTRAS_MOVIE);
            Objects.requireNonNull(getSupportActionBar()).setTitle(movie.getTitle());
        }
    }

    private void setupBindings(Bundle savedInstanceState) {
        activityMovieVideosBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_videos);
        viewModel = ViewModelProviders.of(this).get(MovieVideosViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init(movie);
        }

        activityMovieVideosBinding.setModel(viewModel);

        setupRecyclerView();

        if (NetworkUtil.isConected(this)) {
            setupVideoList();

            viewModel.showLoadinView();
            viewModel.getVideos();
        } else {
            viewModel.showNoInternetView();
        }

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityMovieVideosBinding.videosRecyclerview.setLayoutManager(linearLayoutManager);
        activityMovieVideosBinding.videosRecyclerview.setAdapter(viewModel.getVideoAdapter());

    }

    private void setupVideoListClick() {
        viewModel.getSelectedVideo().observe(this, new Observer<Video>() {
            @Override
            public void onChanged(Video video) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.youtube_url, video.getKey())));
                startActivity(intent);

            }
        });

    }

    private void setupVideoList() {
        viewModel.getVideosLiveData().observe(this, new Observer<VideoRequestResult>() {
            @Override
            public void onChanged(VideoRequestResult videoRequestResult) {
                viewModel.hideLoading();
                if (videoRequestResult != null && !videoRequestResult.getVideos().isEmpty()) {
                    viewModel.setVideosInAdapter(videoRequestResult.getVideos());
                } else {
                    viewModel.showEmptyView();
                }
            }
        });
        setupVideoListClick();

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
        outState.putParcelable(RECYCLERVIEW_SAVED_STATE, Objects.requireNonNull(activityMovieVideosBinding.videosRecyclerview.getLayoutManager()).onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recyclerViewSavedInstance = savedInstanceState.getParcelable(RECYCLERVIEW_SAVED_STATE);

    }

}
