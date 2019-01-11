package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.review;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.databinding.ActivityMovieReviewBinding;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.Review;
import br.com.udacity.ruyano.filmesfamosos.util.NetworkUtil;

public class MovieReviewsActivity extends AppCompatActivity {

    private static final String RECYCLERVIEW_SAVED_STATE = "RECYCLERVIEW_SAVED_STATE";
    private static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    private MovieReviewsViewModel viewModel;
    private ActivityMovieReviewBinding activityMovieReviewBinding;
    private Parcelable recyclerViewSavedInstance;

    private Movie movie;

    public static Intent getIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieReviewsActivity.class);
        intent.putExtra(EXTRAS_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        setupBindings(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerViewSavedInstance != null) {
            Objects.requireNonNull(activityMovieReviewBinding.reviewRecyclerview.getLayoutManager()).onRestoreInstanceState(recyclerViewSavedInstance);
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
        activityMovieReviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_review);
        viewModel = ViewModelProviders.of(this).get(MovieReviewsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        if (movie != null) {
            viewModel.callForReviewList(movie);
        }

        activityMovieReviewBinding.setModel(viewModel);

        setupRecyclerView();

        if (NetworkUtil.isConected(this)) {
            setupListUpdate();
        } else {
            viewModel.showNoInternetView();
        }

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityMovieReviewBinding.reviewRecyclerview.setLayoutManager(linearLayoutManager);
        activityMovieReviewBinding.reviewRecyclerview.setAdapter(viewModel.getReviewAdapter());

    }

    private void setupListUpdate() {
        viewModel.showLoadinView();
        viewModel.reviewPagedList.observe(this, new Observer<PagedList<Review>>() {
            @Override
            public void onChanged(PagedList<Review> reviews) {
                viewModel.hideLoading();
                if (reviews.size() > 0) {
                    viewModel.setReviewsInAdapter(reviews);
                } else {
                    viewModel.showEmptyView();
                }
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
        outState.putParcelable(RECYCLERVIEW_SAVED_STATE, Objects.requireNonNull(activityMovieReviewBinding.reviewRecyclerview.getLayoutManager()).onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recyclerViewSavedInstance = savedInstanceState.getParcelable(RECYCLERVIEW_SAVED_STATE);

    }
}
