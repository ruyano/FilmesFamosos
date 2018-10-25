package br.com.udacity.ruyano.filmesfamosos.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.udacity.ruyano.filmesfamosos.R;

public class GlideUtil {

    private static final String QUALITY = "{quality}";
    private static final String imageServerBaseUrl = "http://image.tmdb.org/t/p/" + QUALITY + "/";

    public static void loadImage(Context context, String imagePath, ImageView imageView, ImageQuality quality) {

        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.color.colorPrimaryDark)
                .error(R.color.colorPrimaryDark);

        Glide.with(context)
                .load(imageServerBaseUrl.replace(QUALITY, quality.getValue()) + imagePath)
                .apply(requestOptions)
                .thumbnail(0.01f)
                .into(imageView);
    }
}

