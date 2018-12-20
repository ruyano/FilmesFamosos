package br.com.udacity.ruyano.filmesfamosos.mvvm;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.model.RequestResult;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import br.com.udacity.ruyano.filmesfamosos.networking.clients.APIClient;
import okhttp3.ResponseBody;

public class MovieRepository extends BaseObservable {

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    public MutableLiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void fetchList(final Integer page) {
        APIClient.getInstance().getPopularMovies(page, new RetrofitConfig.OnRestResponseListener<RequestResult>() {
            @Override
            public void onRestSuccess(RequestResult response) {
                if (page > 1) {
                    List<Movie> currrent = new ArrayList<>();
                    if (movies != null && movies.getValue() != null) {
                        currrent.addAll(movies.getValue());
                    }
                    currrent.addAll(response.getResults());
                    movies.setValue(currrent);
                } else {
                    movies.setValue(response.getResults());
                }
            }

            @Override
            public void onRestError(ResponseBody body, int code) {

            }

            @Override
            public void onFailure(String str) {

            }

            @Override
            public void noInternet() {

            }
        });
    }

}