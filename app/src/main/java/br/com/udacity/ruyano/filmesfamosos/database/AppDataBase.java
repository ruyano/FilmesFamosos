package br.com.udacity.ruyano.filmesfamosos.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.util.DateTypeConverter;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "MOVIE_DATABASE";

    private static AppDataBase mInstance;

    public abstract MovieDAO movieDAO();

    public static AppDataBase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME).build();
        }

        return mInstance;
    }
}
