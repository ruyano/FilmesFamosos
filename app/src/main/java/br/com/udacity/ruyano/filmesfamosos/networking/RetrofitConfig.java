package br.com.udacity.ruyano.filmesfamosos.networking;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static RetrofitConfig ourInstance;
    private Retrofit retrofit;

    private RetrofitConfig() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static RetrofitConfig getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitConfig();
        }
        return ourInstance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public interface OnRestResponseListener<T> {

        void onRestSuccess(T response);

        void onRestError(ResponseBody body, int code);

        void onFailure(String str);

    }

}
