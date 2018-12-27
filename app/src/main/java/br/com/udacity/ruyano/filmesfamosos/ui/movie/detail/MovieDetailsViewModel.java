package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail;

import android.view.View;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.Video;
import br.com.udacity.ruyano.filmesfamosos.model.VideoRequestResult;
import br.com.udacity.ruyano.filmesfamosos.networking.data.sources.videos.VideosDataSource;

public class MovieDetailsViewModel extends ViewModel {

    public MutableLiveData<Movie> movie;
    public MutableLiveData<Video> selectedVideo;
    public ObservableInt showVideoIcone;
    public VideoAdapter videoAdapter;
    private VideosDataSource videosDataSource;

    void init() {
        this.movie = new MutableLiveData<>();
        this.selectedVideo = new MutableLiveData<>();
        this.videoAdapter = new VideoAdapter(this);
        this.showVideoIcone = new ObservableInt(View.GONE);
        this.videosDataSource = new VideosDataSource();

    }

    void setMovie(Movie movie) {
        if (this.movie != null)
            this.movie.setValue(movie);

    }

    void getVideos() {
        videosDataSource.getVideos(movie.getValue().getId());

    }

    MutableLiveData<VideoRequestResult> getVideosLiveData() {
        return videosDataSource.getVideoRequestResultMutableLiveData();

    }

    public VideoAdapter getAdapter() {
        return videoAdapter;

    }

    public MutableLiveData<Video> getSelectedVideo() {
        return selectedVideo;

    }

    void setVideosInAdapter(List<Video> videos) {
        this.videoAdapter.setVideos(videos);
        this.videoAdapter.notifyDataSetChanged();

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

}
