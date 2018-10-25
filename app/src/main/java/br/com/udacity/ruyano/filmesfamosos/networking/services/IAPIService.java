package br.com.udacity.ruyano.filmesfamosos.networking.services;

import br.com.udacity.ruyano.filmesfamosos.model.RequestResult;
import br.com.udacity.ruyano.filmesfamosos.util.Constants;
import retrofit2.Call;
import retrofit2.http.POST;

public interface IAPIService {

    @POST("popular?api_key=" + Constants.API_KEY)
    Call<RequestResult> getPopularMovies();

}
