package br.com.udacity.ruyano.filmesfamosos.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

public class CustomViewBindings {

    @BindingAdapter("imageUrl")
    public static void bindImageViewUrl(ImageView imageView, String imageUrl) {
        GlideUtil.loadImage(imageView, imageUrl);

    }


    @BindingAdapter("imageUrlMedium")
    public static void bindImageViewUrlMedium(ImageView imageView, String imageUrl) {
        GlideUtil.loadImage(imageView, imageUrl, ImageQuality.MEDIUM);

    }

    @BindingAdapter("imageUrlOriginal")
    public static void bindImageViewUrlOriginal(ImageView imageView, String imageUrl) {
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
