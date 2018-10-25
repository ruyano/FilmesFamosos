package br.com.udacity.ruyano.filmesfamosos.networking.clients;

import br.com.udacity.ruyano.filmesfamosos.model.RequestResult;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import br.com.udacity.ruyano.filmesfamosos.networking.services.IAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIClient {

    private static APIClient mInstance;
    private IAPIService service = ((IAPIService) RetrofitConfig.getInstance().getRetrofit().create(IAPIService.class));

    private APIClient() {
        mInstance = this;
    }

    public static APIClient getInstance() {
        if (mInstance == null) {
            APIClient aPIClient = new APIClient();
        }
        return mInstance;
    }

    public void getPopularMovies(final RetrofitConfig.OnRestResponseListener<RequestResult> listener) {
        this.service.getPopularMovies().enqueue(new Callback<RequestResult>() {
            @Override
            public void onResponse(Call<RequestResult> call, Response<RequestResult> response) {
                if (response.isSuccessful()) {
                    listener.onRestSuccess(response.body());
                } else {
                    listener.onRestError(response.errorBody(), response.code());
                }
            }

            @Override
            public void onFailure(Call<RequestResult> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
