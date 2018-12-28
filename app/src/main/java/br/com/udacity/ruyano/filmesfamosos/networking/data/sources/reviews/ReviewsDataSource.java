package br.com.udacity.ruyano.filmesfamosos.networking.data.sources.reviews;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import br.com.udacity.ruyano.filmesfamosos.ReviewRequestResult;
import br.com.udacity.ruyano.filmesfamosos.model.Review;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import retrofit2.Response;

public class ReviewsDataSource extends PageKeyedDataSource<Integer, Review> {

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    private Integer movieId;

    public ReviewsDataSource(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Review> callback) {
        try {
            Response<ReviewRequestResult> resultResponse = RetrofitConfig.getInstance().getApi().getReviews(movieId, FIRST_PAGE).execute();
            callback.onResult(resultResponse.body().getReviews(), null, FIRST_PAGE + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Review> callback) {
        try {
            Response<ReviewRequestResult> resultResponse = RetrofitConfig.getInstance().getApi().getReviews(movieId, params.key).execute();
            //if the current page is greater than one
            //we are decrementing the page number
            //else there is no previous page
            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
            if (resultResponse.body() != null) {

                //passing the loaded data
                //and the previous page key
                callback.onResult(resultResponse.body().getReviews(), adjacentKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Review> callback) {
        try {
            Response<ReviewRequestResult> resultResponse = RetrofitConfig.getInstance().getApi().getReviews(movieId, params.key).execute();
            if (resultResponse.body() != null) {
                //if the response has next page
                //incrementing the next page number
                Boolean hasMore = !resultResponse.body().getPage().equals(resultResponse.body().getTotalPages());
                Integer key = hasMore ? params.key + 1 : null;

                //passing the loaded data and next page value
                callback.onResult(resultResponse.body().getReviews(), key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
