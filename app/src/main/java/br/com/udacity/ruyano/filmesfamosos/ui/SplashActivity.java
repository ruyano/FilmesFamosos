package br.com.udacity.ruyano.filmesfamosos.ui;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Language;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import br.com.udacity.ruyano.filmesfamosos.networking.clients.APIClient;
import br.com.udacity.ruyano.filmesfamosos.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Glide.with(this)
                .load(R.drawable.loading)
                .into(ivLogo);

        getLanguageList();
    }

    private void getLanguageList() {
        APIClient.getInstance().getLanguages(this, new RetrofitConfig.OnRestResponseListener<List<Language>>() {
            @Override
            public void onRestSuccess(List<Language> response) {
                validateLocalLanguage(response);
            }

            @Override
            public void onRestError(ResponseBody body, int code) {
                saveLanguageAndGoToMain(null);
            }

            @Override
            public void onFailure(String str) {
                saveLanguageAndGoToMain(null);
            }

            @Override
            public void noInternet() {
                saveLanguageAndGoToMain(null);
            }
        });

    }

    private void validateLocalLanguage(List<Language> languages) {
        String deviceCountry = Locale.getDefault().getCountry();
        String deviceLanguage = Locale.getDefault().getLanguage();

        if (deviceCountry != null && deviceLanguage != null) {
            for (Language language : languages) {
                if (language.getIso_639_1().equals(deviceLanguage)) {
                    saveLanguageAndGoToMain(deviceLanguage + "-" + deviceCountry);
                    return;
                }
            }
        }

        saveLanguageAndGoToMain(null);
    }

    private void saveLanguageAndGoToMain(String language) {
        if (language != null) {
            Constants.DEVICE_LANGUAGE = language;
            RetrofitConfig.getInstance().updateRetrofit();
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
