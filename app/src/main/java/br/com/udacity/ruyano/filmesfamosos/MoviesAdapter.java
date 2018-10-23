package br.com.udacity.ruyano.filmesfamosos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import br.com.udacity.ruyano.filmesfamosos.model.Result;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    Context context;
    ArrayList<Result> movies;

    public MoviesAdapter(Context context, ArrayList<Result> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_grid_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(movies.get(i));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_banner)
        ImageView movieBanner;

        @BindView(R.id.tv_movie_name)
        TextView movieName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Result movie) {
            movieName.setText(movie.getTitle());
            Glide.with(context).load(movie.getPosterPath()).thumbnail(0.01f).into(movieBanner);
        }

    }
}
