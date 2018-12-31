package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.videos;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import br.com.udacity.ruyano.filmesfamosos.BR;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Video;
import br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.MovieDetailsViewModel;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private MovieVideosViewModel viewModel;
    private List<Video> videos;

    public VideoAdapter(MovieVideosViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.video_list_item;
    }

    @Override
    public int getItemCount() {
        return  videos == null ? 0 : videos.size();
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MovieVideosViewModel viewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

    }


}
