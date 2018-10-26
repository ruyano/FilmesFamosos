package br.com.udacity.ruyano.filmesfamosos.networking.services;

import java.util.List;

import br.com.udacity.ruyano.filmesfamosos.model.Language;
import br.com.udacity.ruyano.filmesfamosos.model.RequestResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAPIService {

    // configuration
    @GET("configuration/languages")
    Call<List<Language>> getLanguages();

    // movie
    @GET("movie/popular")
    Call<RequestResult> getPopularMovies(@Query("page") Integer page);


}
