package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.videos;

import android.view.View;

import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.Video;
import br.com.udacity.ruyano.filmesfamosos.model.VideoRequestResult;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.videos.VideosDataSource;

public class MovieVideosViewModel extends ViewModel {

    private Movie movie;

    public ObservableBoolean isLoading;

    // recyclerView
    public ObservableInt recyclerViewVisibility;

    // Status image
    public ObservableInt statusImageVisibility;
    public ObservableInt statusImageResourceId;

    // Status text
    public ObservableInt statusTextVisibility;
    public ObservableInt statusTextResourceId;

    private VideoAdapter videoAdapter;

    private VideosDataSource videosDataSource;

    private MutableLiveData<Video> selectedVideo;

    void init(Movie movie) {
        this.movie = movie;
        this.videoAdapter = new VideoAdapter(this);
        this.videosDataSource = new VideosDataSource();
        this.selectedVideo = new MutableLiveData<>();
        isLoading = new ObservableBoolean(false);
        recyclerViewVisibility = new ObservableInt(View.GONE);
        statusImageVisibility = new ObservableInt(View.GONE);
        statusImageResourceId = new ObservableInt(R.drawable.loading);
        statusTextVisibility = new ObservableInt(View.GONE);
        statusTextResourceId = new ObservableInt(R.string.no_internet_message);
    }

    MutableLiveData<Video> getSelectedVideo() {
        return selectedVideo;

    }

    public VideoAdapter getVideoAdapter() {
        return videoAdapter;

    }

    void setVideosInAdapter(List<Video> videos) {
        this.videoAdapter.setVideos(videos);
        this.videoAdapter.notifyDataSetChanged();

    }

    void getVideos() {
        videosDataSource.getVideos(movie.getId());

    }

    MutableLiveData<VideoRequestResult> getVideosLiveData() {
        return videosDataSource.getVideoRequestResultMutableLiveData();

    }

    public Video getVideoAt(Integer index) {
        if (videosDataSource.getVideoRequestResultMutableLiveData().getValue() != null
                && videosDataSource.getVideoRequestResultMutableLiveData().getValue().getVideos().size() > index) {
            return videosDataSource.getVideoRequestResultMutableLiveData().getValue().getVideos().get(index);
        }
        return null;

    }

    public void onItemClick(Integer index) {
        Video selected = getVideoAt(index);
        if (selected != null)
            selectedVideo.setValue(selected);

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
        statusTextResourceId.set(R.string.video_empty_message);
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
