package br.com.udacity.ruyano.filmesfamosos.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;

@Dao
public interface MovieDAO {

    @Insert
    void createMovie(Movie movie);

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    LiveData<Movie> readMovieById(Integer movieId);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * from Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM Movie")
    DataSource.Factory<Integer, Movie> getAllMoviesPaged();

}
