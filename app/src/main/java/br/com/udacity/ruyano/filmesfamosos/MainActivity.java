package br.com.udacity.ruyano.filmesfamosos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.udacity.ruyano.filmesfamosos.model.RequestResult;
import br.com.udacity.ruyano.filmesfamosos.model.Result;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    private MoviesAdapter moviesAdapter;
    private ArrayList<Result> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerView();
        request();

    }

    private void initRecyclerView() {
        moviesAdapter = new MoviesAdapter(this, movies);

        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovies.setAdapter(moviesAdapter);

    }

    private void request() {
        movies.clear();
        movies.addAll(RequestResult.getMock(10));
        moviesAdapter.notifyDataSetChanged();

    }
}
