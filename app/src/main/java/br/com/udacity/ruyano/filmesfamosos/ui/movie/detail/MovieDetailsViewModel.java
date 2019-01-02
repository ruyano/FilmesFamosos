package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.repositories.MovieRepository;

public class MovieDetailsViewModel extends AndroidViewModel {

    public MutableLiveData<Movie> movie;
    public ObservableInt showVideoIcone;
    public ObservableBoolean isFavorite;
    private MovieRepository movieRepository;
    private LiveData<Movie> databaseMovie;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);

    }

    void init() {
        this.movie = new MutableLiveData<>();
        this.showVideoIcone = new ObservableInt(View.GONE);
        this.isFavorite = new ObservableBoolean(false);

    }

    public LiveData<Movie> getDatabaseMovie() {
        return databaseMovie;

    }

    void setMovie(Movie movie) {
        if (this.movie != null) {
            this.movie.setValue(movie);
        }

        databaseMovie = movieRepository.readMovieById(movie.getId());

    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite.set(isFavorite);

    }

    public void saveFavorite() {
        if (isFavorite.get()) {
            movieRepository.delete(movie.getValue());
        } else {
            movieRepository.createMovie(movie.getValue());
        }

    }

}
