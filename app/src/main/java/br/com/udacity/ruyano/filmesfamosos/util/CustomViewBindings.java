package br.com.udacity.ruyano.filmesfamosos.util;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewBindings {

    private static EndlessRecyclerViewScrollListener scrollListener;

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, PagedListAdapter<?,?> adapter) {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @BindingAdapter("imageUrlMedium")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        GlideUtil.loadImage(imageView, imageUrl, ImageQuality.MEDIUM);
    }

    @BindingAdapter("booleanVisibility")
    public static void bindLinearLayoutBolleanVisibility(View view, Boolean booleanVisibility) {
        view.setVisibility(booleanVisibility ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("userEvaluation")
    public static void usersEvaluationViewSetValue(UsersEvaluationView userEvaluationView, Double userEvaluation) {
        userEvaluationView.setValue(userEvaluation);
    }

}
