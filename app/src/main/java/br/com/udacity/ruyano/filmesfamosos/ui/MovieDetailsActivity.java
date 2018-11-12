package br.com.udacity.ruyano.filmesfamosos.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;
import br.com.udacity.ruyano.filmesfamosos.util.GlideUtil;
import br.com.udacity.ruyano.filmesfamosos.util.ImageQuality;
import br.com.udacity.ruyano.filmesfamosos.util.UsersEvaluationView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final String EXTRAS_MOVIE = "movie";

    @BindView(R.id.iv_movie_backdrop)
    ImageView ivMovieBackDrop;

    @BindView(R.id.iv_movie_banner)
    ImageView ivMovieBanner;

    @BindView(R.id.tv_movie_name)
    TextView tvMovieName;

    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @BindView(R.id.tv_overview)
    TextView tvOverview;

    @BindView(R.id.uev_users_evaluation_view)
    UsersEvaluationView uevUsersEvaluationView;

    @BindView(R.id.rl_adult_view)
    RelativeLayout rlAdultView;

    private Movie movie;

    public static Intent getIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRAS_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();

        if (movie != null) {
            populateView();
        } else {
            // TODO - implementar tela de erro
        }
    }

    private void getExtras() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(EXTRAS_MOVIE)) {
            movie = getIntent().getExtras().getParcelable(EXTRAS_MOVIE);
        }
    }

    private void populateView() {
        getSupportActionBar().setTitle(movie.getTitle());
        tvMovieName.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDateString());
        tvOverview.setText(movie.getOverview());

        uevUsersEvaluationView.setValue(movie.getVoteAverage());

        GlideUtil.loadImage(this,
                movie.getBackdropPath(),
                ivMovieBackDrop,
                ImageQuality.ORIGINAL);

        GlideUtil.loadImage(this,
                movie.getPosterPath(),
                ivMovieBanner,
                ImageQuality.MEDIUM);

        if (movie.getAdult()) {
            rlAdultView.setVisibility(View.VISIBLE);
        } else {
            rlAdultView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
