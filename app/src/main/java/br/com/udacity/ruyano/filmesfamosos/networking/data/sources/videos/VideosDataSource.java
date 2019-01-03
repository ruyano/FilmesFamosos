package br.com.udacity.ruyano.filmesfamosos.networking.data.sources.videos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import br.com.udacity.ruyano.filmesfamosos.model.VideoRequestResult;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideosDataSource {

    private MutableLiveData<VideoRequestResult> videoRequestResultMutableLiveData;

    public VideosDataSource() {
        videoRequestResultMutableLiveData = new MutableLiveData<>();
    }

    public void getVideos(Integer id) {
        RetrofitConfig.getInstance().getApi().getVideos(id).enqueue(new Callback<VideoRequestResult>() {
            @Override
            public void onResponse(Call<VideoRequestResult> call, Response<VideoRequestResult> response) {
                videoRequestResultMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<VideoRequestResult> call, Throwable t) {
                Log.e("VideosDataSource", t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<VideoRequestResult> getVideoRequestResultMutableLiveData() {
        return videoRequestResultMutableLiveData;
    }

}
