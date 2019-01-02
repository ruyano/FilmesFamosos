package br.com.udacity.ruyano.filmesfamosos.repositories;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import br.com.udacity.ruyano.filmesfamosos.database.AppDataBase;
import br.com.udacity.ruyano.filmesfamosos.database.MovieDAO;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;

public class MovieRepository {

    private MovieDAO movieDAO;

    public MovieRepository(Application application) {
        AppDataBase movieDataBase = AppDataBase.getInstance(application);
        movieDAO = movieDataBase.movieDAO();

    }

    public void createMovie(final Movie movie) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieDAO.createMovie(movie);
            }
        }).start();

    }

    public LiveData<Movie> readMovieById(Integer movieId) {
        return movieDAO.readMovieById(movieId);

    }

    public void delete(final Movie movie) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieDAO.deleteMovie(movie);
            }
        }).start();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return movieDAO.getAllMovies();

    }

    public DataSource.Factory<Integer, Movie> getAllMoviesPaged() {
        return movieDAO.getAllMoviesPaged();
    }


}


