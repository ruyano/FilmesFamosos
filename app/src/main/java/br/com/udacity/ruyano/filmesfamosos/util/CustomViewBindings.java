package br.com.udacity.ruyano.filmesfamosos.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewBindings {

    private static final String QUALITY = "{quality}";
    private static final String imageServerBaseUrl = "http://image.tmdb.org/t/p/" + QUALITY + "/";

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
}
