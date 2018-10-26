package br.com.udacity.ruyano.filmesfamosos.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Result;
import br.com.udacity.ruyano.filmesfamosos.util.GlideUtil;
import br.com.udacity.ruyano.filmesfamosos.util.ImageQuality;
import br.com.udacity.ruyano.filmesfamosos.util.UsersEvaluationView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Result> movies;
    private MoviesAdapterOnItemClickListener itemClickListener;

    public MoviesAdapter(Context context, ArrayList<Result> movies, MoviesAdapterOnItemClickListener itemClickListener) {
        this.context = context;
        this.movies = movies;
        this.itemClickListener = itemClickListener;
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
        return movies == null ? 0 : movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_movie_banner)
        ImageView ivMovieBanner;

        @BindView(R.id.uev_users_evaluation_view)
        UsersEvaluationView uevUsersEvaluationView;

        @BindView(R.id.rl_adult_view)
        RelativeLayout rlAdultView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Result movie) {
            GlideUtil.loadImage(context,
                    movie.getPosterPath(),
                    ivMovieBanner,
                    ImageQuality.MEDIUM);

            uevUsersEvaluationView.setValue(movie.getVoteAverage());

            if (movie.getAdult()) {
                rlAdultView.setVisibility(View.VISIBLE);
            } else {
                rlAdultView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            itemClickListener.itemCliked(getAdapterPosition(), ivMovieBanner, uevUsersEvaluationView, rlAdultView);
        }
    }

    public interface MoviesAdapterOnItemClickListener {

        void itemCliked(Integer position, ImageView ivMovieBanner, UsersEvaluationView uevUsersEvaluationView, RelativeLayout rlAdultView);

    }
}
