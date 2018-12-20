package br.com.udacity.ruyano.filmesfamosos.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewBindings {

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
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

    @BindingAdapter("booleanVisibility")
    public static void usersEvaluationViewSetValue(UsersEvaluationView view, Boolean booleanVisibility) {
        view.setVisibility(booleanVisibility ? View.VISIBLE : View.GONE);
    }


}
