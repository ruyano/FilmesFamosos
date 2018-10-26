package br.com.udacity.ruyano.filmesfamosos.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import br.com.udacity.ruyano.filmesfamosos.util.EndlessRecyclerViewScrollListener;
import br.com.udacity.ruyano.filmesfamosos.util.NetworkUtil;
import br.com.udacity.ruyano.filmesfamosos.util.UsersEvaluationView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    @BindView(R.id.srl_swipe_to_refresh_layout)
    SwipeRefreshLayout srlSwipeToRefreshLayout;

    @BindView(R.id.iv_status_image)
    ImageView ivStatusImage;

    @BindView(R.id.tv_status_text)
    TextView tvStatusText;

    @BindView(R.id.ll_status_view)
    LinearLayout llStatusView;

    private EndlessRecyclerViewScrollListener scrollListener;
    private MoviesAdapter moviesAdapter;
    private ArrayList<Result> movies = new ArrayList<>();

    private Boolean isRequestingPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!NetworkUtil.isConected(this)) {
            showNoInternetView();
        } else {
            init();
            requestFirstPage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                isRequestingPopular = true;
                requestFirstPage();
                return true;
            case R.id.avaliation:
                isRequestingPopular = false;
                requestFirstPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    private void init() {
        srlSwipeToRefreshLayout.setOnRefreshListener(this);
        srlSwipeToRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        llStatusView.setOnClickListener(this);

        moviesAdapter = new MoviesAdapter(this, movies, new MoviesAdapter.MoviesAdapterOnItemClickListener() {
            @Override
            public void itemCliked(Integer position, ImageView ivMovieBanner, UsersEvaluationView uevUsersEvaluationView, RelativeLayout rlAdultView) {
                Intent intent = MovieDetailsActivity.getIntent(MainActivity.this, movies.get(position));

                Pair bannerPair = Pair.create(ivMovieBanner, getString(R.string.activity_mixed_trans_banner));
                Pair evaluationPair = Pair.create(uevUsersEvaluationView, getString(R.string.activity_mixed_trans_evaluation));
                Pair adultPair = Pair.create(rlAdultView, getString(R.string.activity_mixed_trans_adult));

                ActivityOptions options;
                if (rlAdultView.getVisibility() == View.VISIBLE) {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, evaluationPair, adultPair, bannerPair);
                } else {
                    options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, evaluationPair, bannerPair);
                }
                startActivity(intent, options.toBundle());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMovies.setLayoutManager(gridLayoutManager);
        rvMovies.setAdapter(moviesAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                request(page);
            }
        };
        rvMovies.addOnScrollListener(scrollListener);
    }

    private void requestFirstPage() {
        showLoading();
        movies.clear();
        moviesAdapter.notifyDataSetChanged();
        scrollListener.resetState();
        request(1);
    }

    private void request(Integer page) {

        RetrofitConfig.OnRestResponseListener serviceListener = new RetrofitConfig.OnRestResponseListener<RequestResult>() {
            @Override
            public void onRestSuccess(RequestResult response) {
                srlSwipeToRefreshLayout.setRefreshing(false);
                for(Result movie : response.getResults()) {
                    movies.add(movie);
                    moviesAdapter.notifyItemChanged(movies.size());
                }
                hideLoading();
            }

            @Override
            public void onRestError(ResponseBody body, int code) {
                srlSwipeToRefreshLayout.setRefreshing(false);
                shoShameErrorView();
            }

            @Override
            public void onFailure(String str) {
                srlSwipeToRefreshLayout.setRefreshing(false);
                shoShameErrorView();
            }
        };


        if (isRequestingPopular) {
            APIClient.getInstance().getPopularMovies(page, serviceListener);
        } else {
            APIClient.getInstance().getTopRatedMovies(page, serviceListener);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_status_view:
                requestFirstPage();
                break;
        }
    }

    @Override
    public void onRefresh() {
        srlSwipeToRefreshLayout.setRefreshing(true);
        requestFirstPage();
    }
}
