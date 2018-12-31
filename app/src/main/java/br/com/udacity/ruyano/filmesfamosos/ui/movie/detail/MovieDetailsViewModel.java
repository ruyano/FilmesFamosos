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
import br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.videos.VideoAdapter;

public class MovieDetailsViewModel extends ViewModel {

    public MutableLiveData<Movie> movie;
    public ObservableInt showVideoIcone;

    void init() {
        this.movie = new MutableLiveData<>();
        this.showVideoIcone = new ObservableInt(View.GONE);

    }

    void setMovie(Movie movie) {
        if (this.movie != null) {
            this.movie.setValue(movie);
        }

    }

}
