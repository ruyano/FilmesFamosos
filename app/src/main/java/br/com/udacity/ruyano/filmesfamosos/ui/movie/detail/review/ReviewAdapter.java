package br.com.udacity.ruyano.filmesfamosos.ui.movie.detail.review;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import br.com.udacity.ruyano.filmesfamosos.BR;
import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Review;

public class ReviewAdapter extends PagedListAdapter<Review, ReviewAdapter.ViewHolder> {

    private MovieReviewsViewModel viewModel;

    private static DiffUtil.ItemCallback<Review> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Review>() {
                @Override
                public boolean areItemsTheSame(Review oldItem, Review newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Review oldItem, @NonNull Review newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public ReviewAdapter(MovieReviewsViewModel viewModel) {
        super(DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ReviewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        getItem(position);
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.review_list_item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MovieReviewsViewModel viewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }

}
