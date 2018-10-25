package br.com.udacity.ruyano.filmesfamosos.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Result;
import br.com.udacity.ruyano.filmesfamosos.util.GlideUtil;
import br.com.udacity.ruyano.filmesfamosos.util.ImageQuality;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRAS_MOVIE = "movie";

    @BindView(R.id.iv_movie_backdrop)
    ImageView ivMovieBackDrop;

    @BindView(R.id.iv_movie_banner)
    ImageView ivMovieBanner;

    private Result movie;

    public static Intent getIntent(Context context, Result movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRAS_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getExtras();

        if (movie != null) {
            populateView();
        } else {
            // TODO - implementar tela de erro
        }
    }

    private void getExtras() {
        if (getIntent().hasExtra(EXTRAS_MOVIE)) {
            movie = getIntent().getExtras().getParcelable(EXTRAS_MOVIE);
        }
    }

    private void populateView() {
        getSupportActionBar().setTitle(movie.getTitle());

        GlideUtil.loadImage(this,
                movie.getBackdropPath(),
                ivMovieBackDrop,
                ImageQuality.ORIGINAL);

        GlideUtil.loadImage(this,
                movie.getPosterPath(),
                ivMovieBanner,
                ImageQuality.LOW);
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
