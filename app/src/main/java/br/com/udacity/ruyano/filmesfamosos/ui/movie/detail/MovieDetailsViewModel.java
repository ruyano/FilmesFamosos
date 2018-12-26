package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;

public class MovieDetailsViewModel extends ViewModel {

    public MutableLiveData<Movie> movie;

    void init() {
        this.movie = new MutableLiveData<>();
    }

    void setMovie(Movie movie) {
        if (this.movie != null)
            this.movie.setValue(movie);
    }

}
