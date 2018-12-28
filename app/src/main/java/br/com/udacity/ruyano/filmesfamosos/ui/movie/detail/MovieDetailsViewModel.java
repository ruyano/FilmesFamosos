package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail;

import android.view.View;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.Review;
import br.com.udacity.ruyano.filmesfamosos.model.Video;
import br.com.udacity.ruyano.filmesfamosos.model.VideoRequestResult;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.reviews.ReviewsDataSourceFactory;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.videos.VideosDataSource;

public class MovieDetailsViewModel extends ViewModel {

    public MutableLiveData<Movie> movie;
    private MutableLiveData<Video> selectedVideo;
    public ObservableInt showVideoIcone;
    private VideoAdapter videoAdapter;
    private ReviewAdapter reviewAdapter;
    private VideosDataSource videosDataSource;

    //creating livedata for PagedList  and DataSource
    LiveData<PagedList<Review>> reviewPagedList;
    private ReviewsDataSourceFactory reviewsDataSourceFactory;

    private void callForReviewList(Movie movie) {
        //getting our data source factory
        reviewsDataSourceFactory = new ReviewsDataSourceFactory(movie.getId());

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(20).build();

        //Building the paged list
        reviewPagedList = (new LivePagedListBuilder(reviewsDataSourceFactory, pagedListConfig)).build();

    }

    void init() {
        this.movie = new MutableLiveData<>();
        this.selectedVideo = new MutableLiveData<>();
        this.videoAdapter = new VideoAdapter(this);
        this.reviewAdapter = new ReviewAdapter(this);
        this.showVideoIcone = new ObservableInt(View.GONE);
        this.videosDataSource = new VideosDataSource();

    }

    void setMovie(Movie movie) {
        if (this.movie != null) {
            this.movie.setValue(movie);
            callForReviewList(movie);
        }
    }

    void getVideos() {
        videosDataSource.getVideos(movie.getValue().getId());

    }

    MutableLiveData<VideoRequestResult> getVideosLiveData() {
        return videosDataSource.getVideoRequestResultMutableLiveData();

    }

    public VideoAdapter getVideoAdapter() {
        return videoAdapter;

    }

    public ReviewAdapter getReviewAdapter() {
        return reviewAdapter;

    }

    MutableLiveData<Video> getSelectedVideo() {
        return selectedVideo;

    }

    void setVideosInAdapter(List<Video> videos) {
        this.videoAdapter.setVideos(videos);
        this.videoAdapter.notifyDataSetChanged();

    }

    void setReviewsInAdapter(PagedList<Review> reviews) {
        reviewAdapter.submitList(reviews);
    }

    public Video getVideoAt(Integer index) {
        if (videosDataSource.getVideoRequestResultMutableLiveData().getValue() != null
                && videosDataSource.getVideoRequestResultMutableLiveData().getValue().getVideos().size() > index) {
            return videosDataSource.getVideoRequestResultMutableLiveData().getValue().getVideos().get(index);
        }
        return null;

    }

    public Review getReviewAt(Integer index) {
        if (reviewPagedList.getValue() != null
                && reviewPagedList.getValue().size() > index) {
            return reviewPagedList.getValue().get(index);
        }
        return null;
    }

    public void onItemClick(Integer index) {
        Video selected = getVideoAt(index);
        if (selected != null)
            selectedVideo.setValue(selected);

    }

}
