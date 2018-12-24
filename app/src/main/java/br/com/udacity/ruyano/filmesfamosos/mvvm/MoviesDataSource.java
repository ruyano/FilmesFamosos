package br.com.udacity.ruyano.filmesfamosos.mvvm;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.RequestResult;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import retrofit2.Response;

public class MoviesDataSource extends PageKeyedDataSource<Integer, Movie> {

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        try {
            Response<RequestResult> response = RetrofitConfig.getInstance().getApi().getPopularMovies(FIRST_PAGE).execute();
            callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        try {
            Response<RequestResult> response = RetrofitConfig.getInstance().getApi().getPopularMovies(params.key).execute();
            //if the current page is greater than one
            //we are decrementing the page number
            //else there is no previous page
            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
            if (response.body() != null) {

                //passing the loaded data
                //and the previous page key
                callback.onResult(response.body().getResults(), adjacentKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        try {
            Response<RequestResult> response = RetrofitConfig.getInstance().getApi().getPopularMovies(params.key).execute();
            if (response.body() != null) {
                //if the response has next page
                //incrementing the next page number
                Boolean hasMore = !response.body().getPage().equals(response.body().getTotalPages());
                Integer key = hasMore ? params.key + 1 : null;

                //passing the loaded data and next page value
                callback.onResult(response.body().getResults(), key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
