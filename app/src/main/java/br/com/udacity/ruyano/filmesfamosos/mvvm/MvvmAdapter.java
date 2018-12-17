package br.com.udacity.ruyano.filmesfamosos.mvvm;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import br.com.udacity.ruyano.filmesfamosos.BR;
import br.com.udacity.ruyano.filmesfamosos.model.Movie;

public class MvvmAdapter extends RecyclerView.Adapter<MvvmAdapter.ViewHolder> {

    private int layoutId;
    private List<Movie> movies;
    private ViewModelTeste viewModel;

    public MvvmAdapter(int layoutId, ViewModelTeste viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @NonNull
    @Override
    public MvvmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MvvmAdapter.ViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ViewModelTeste viewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

    }
}
