package br.com.udacity.ruyano.filmesfamosos.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import br.com.udacity.ruyano.filmesfamosos.util.Constants;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static RetrofitConfig ourInstance;
    private Retrofit retrofit;

    private RetrofitConfig() {
        updateRetrofit();
    }

    public void updateRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(Constants.API_KEY_QUERY, Constants.API_KEY)
                        .addQueryParameter(Constants.DEVICE_LANGUAGE_QUERY, Constants.DEVICE_LANGUAGE)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.THE_MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
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
