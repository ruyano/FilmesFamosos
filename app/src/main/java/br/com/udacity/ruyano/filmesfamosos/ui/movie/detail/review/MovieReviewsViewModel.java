package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.review;

import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.Review;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.reviews.ReviewsDataSourceFactory;

public class MovieReviewsViewModel extends ViewModel {

    private ReviewAdapter reviewAdapter;

    //creating livedata for PagedList  and DataSource
    LiveData<PagedList<Review>> reviewPagedList;

    public ObservableBoolean isLoading;

    // recyclerView
    public ObservableInt recyclerViewVisibility;

    // Status image
    public ObservableInt statusImageVisibility;
    public ObservableInt statusImageResourceId;

    // Status text
    public ObservableInt statusTextVisibility;
    public ObservableInt statusTextResourceId;

    void init() {
        reviewAdapter = new ReviewAdapter(this);
        isLoading = new ObservableBoolean(false);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        statusImageVisibility = new ObservableInt(View.GONE);
        statusImageResourceId = new ObservableInt(R.drawable.loading);
        statusTextVisibility = new ObservableInt(View.GONE);
        statusTextResourceId = new ObservableInt(R.string.no_internet_message);

    }

    public void callForReviewList(Movie movie) {
        //getting our data source factory
        ReviewsDataSourceFactory reviewsDataSourceFactory = new ReviewsDataSourceFactory(movie.getId());

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build();

        //Building the paged list
        reviewPagedList = (new LivePagedListBuilder(reviewsDataSourceFactory, pagedListConfig)).build();

    }

    public ReviewAdapter getReviewAdapter() {
        return reviewAdapter;

    }

    void setReviewsInAdapter(PagedList<Review> reviews) {
        reviewAdapter.submitList(reviews);
    }

    public Review getReviewAt(Integer index) {
        if (reviewPagedList.getValue() != null
                && reviewPagedList.getValue().size() > index) {
            return reviewPagedList.getValue().get(index);
        }
        return null;

    }

    public void showNoInternetView() {
        statusImageVisibility.set(View.VISIBLE);
        statusTextVisibility.set(View.VISIBLE);
        statusTextResourceId.set(R.string.no_internet_message);
        recyclerViewVisibility.set(View.GONE);
        statusImageResourceId.set(R.drawable.no_internet);

    }

    void showEmptyView() {
        statusImageVisibility.set(View.VISIBLE);
        statusTextVisibility.set(View.VISIBLE);
        statusTextResourceId.set(R.string.review_empty_message);
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

}
