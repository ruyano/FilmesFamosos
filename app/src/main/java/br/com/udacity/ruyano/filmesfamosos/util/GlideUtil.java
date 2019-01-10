package br.com.udacity.ruyano.filmesfamosos.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.com.udacity.ruyano.filmesfamosos.R;

public class GlideUtil {

    private static final String QUALITY = "{quality}";
    private static final String imageServerBaseUrl = "http://image.tmdb.org/t/p/" + QUALITY + "/";

    public static void loadImage(ImageView imageView, String imageUrl, ImageQuality quality) {
        if (imageUrl != null) {

            imageUrl = imageServerBaseUrl.replace(QUALITY, quality.getValue()) + imageUrl;

            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView).load(imageUrl).into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
            imageView.setImageBitmap(null);
        }
    }

    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView).load(imageUrl).into(imageView);
            }
        } else {
            imageView.setTag(R.id.image_url, null);
            imageView.setImageBitmap(null);
        }
    }

}

