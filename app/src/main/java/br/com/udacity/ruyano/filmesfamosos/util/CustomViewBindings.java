package br.com.udacity.ruyano.filmesfamosos.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.udacity.ruyano.filmesfamosos.R;

public class CustomViewBindings {

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, PagedListAdapter<?,?> adapter) {
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                if (positionStart == 0) {
                    gridLayoutManager.scrollToPosition(0);
                }
            }
        });

    }

    @BindingAdapter("imageUrlMedium")
    public static void bindRecyclerViewAdapterMedium(ImageView imageView, String imageUrl) {
        GlideUtil.loadImage(imageView, imageUrl, ImageQuality.MEDIUM);

    }

    @BindingAdapter("imageUrlOriginal")
    public static void bindRecyclerViewAdapterOriginal(ImageView imageView, String imageUrl) {
        GlideUtil.loadImage(imageView, imageUrl, ImageQuality.ORIGINAL);

    }

    @BindingAdapter("booleanVisibility")
    public static void bindLinearLayoutBolleanVisibility(View view, Boolean booleanVisibility) {
        view.setVisibility(booleanVisibility == null ? View.GONE : booleanVisibility ? View.VISIBLE : View.GONE);

    }

    @BindingAdapter("userEvaluation")
    public static void usersEvaluationViewSetValue(UsersEvaluationView userEvaluationView, Double userEvaluation) {
        userEvaluationView.setValue(userEvaluation);

    }

    @BindingAdapter("loadGif")
    public static void bindGif(ImageView imageView, Integer gifResourceId) {
        Glide.with(imageView.getContext())
                .load(gifResourceId)
                .into(imageView);

    }

    @BindingAdapter("textResourceId")
    public static void bindGif(TextView textView, Integer textResourceId) {
        textView.setText(textResourceId);

    }

}
