package br.com.udacity.ruyano.filmesfamosos.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.RequestResult;
import br.com.udacity.ruyano.filmesfamosos.model.Result;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import br.com.udacity.ruyano.filmesfamosos.networking.clients.APIClient;
import br.com.udacity.ruyano.filmesfamosos.ui.adapters.MoviesAdapter;
import br.com.udacity.ruyano.filmesfamosos.util.NetworkUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    @BindView(R.id.iv_status_image)
    ImageView ivStatusImage;

    @BindView(R.id.tv_status_text)
    TextView tvStatusText;

    private MoviesAdapter moviesAdapter;
    private ArrayList<Result> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!NetworkUtil.isConected(this)) {
            showNoInternetView();
        } else {
            initRecyclerView();
            showLoading();
            request();
        }
    }

    private void showNoInternetView() {
        ivStatusImage.setVisibility(View.VISIBLE);
        tvStatusText.setVisibility(View.VISIBLE);
        tvStatusText.setText(R.string.no_internet_message);
        rvMovies.setVisibility(View.GONE);
        Glide.with(this)
                .load(R.drawable.no_internet)
                .into(ivStatusImage);
    }

    private void shoShameErrorView() {
        ivStatusImage.setVisibility(View.VISIBLE);
        tvStatusText.setVisibility(View.VISIBLE);
        tvStatusText.setText(R.string.shame_error);
        rvMovies.setVisibility(View.GONE);
        Glide.with(this)
                .load(R.drawable.shame_error)
                .into(ivStatusImage);
    }

    private void showLoading() {
        ivStatusImage.setVisibility(View.VISIBLE);
        tvStatusText.setVisibility(View.GONE);
        rvMovies.setVisibility(View.GONE);
        Glide.with(this)
                .load(R.drawable.loading)
                .into(ivStatusImage);
    }

    private void hideLoading() {
        ivStatusImage.setVisibility(View.GONE);
        tvStatusText.setVisibility(View.GONE);
        rvMovies.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        moviesAdapter = new MoviesAdapter(this, movies, new MoviesAdapter.MoviesAdapterOnItemClickListener() {
            @Override
            public void itemCliked(Integer position, ImageView ivMovieBanner) {
                Intent intent = MovieDetailsActivity.getIntent(MainActivity.this, movies.get(position));
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(MainActivity.this,
                                ivMovieBanner,
                                getString(R.string.activity_mixed_trans));
                startActivity(intent, options.toBundle());
            }
        });

        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovies.setAdapter(moviesAdapter);

    }

    private void request() {

        APIClient.getInstance().getPopularMovies(new RetrofitConfig.OnRestResponseListener<RequestResult>() {
            @Override
            public void onRestSuccess(RequestResult response) {
                movies.clear();
                movies.addAll(response.getResults());
                moviesAdapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onRestError(ResponseBody body, int code) {
                shoShameErrorView();
            }

            @Override
            public void onFailure(String str) {
                shoShameErrorView();
            }
        });

    }
}
