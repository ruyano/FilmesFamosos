package br.com.udacity.ruyano.filmesfamosos.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.udacity.ruyano.filmesfamosos.R;
import br.com.udacity.ruyano.filmesfamosos.model.Language;
import br.com.udacity.ruyano.filmesfamosos.networking.RetrofitConfig;
import br.com.udacity.ruyano.filmesfamosos.networking.clients.APIClient;
import br.com.udacity.ruyano.filmesfamosos.util.Constants;
import okhttp3.ResponseBody;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLanguageList();
    }

    private void getLanguageList() {
        APIClient.getInstance().getLanguages(new RetrofitConfig.OnRestResponseListener<List<Language>>() {
            @Override
            public void onRestSuccess(List<Language> response) {
                String deviceCountry = Locale.getDefault().getCountry();
                String deviceLanguage = Locale.getDefault().getLanguage();

                if(deviceCountry != null && deviceLanguage != null) {
                    for (Language language : response) {
                        if (language.getIso_639_1().equals(deviceLanguage)) {
                            saveLanguageAndGoToMain(deviceLanguage + "-" + deviceCountry);
                            return;
                        }
                    }
                }

                saveLanguageAndGoToMain(null);
            }

            @Override
            public void onRestError(ResponseBody body, int code) {
                saveLanguageAndGoToMain(null);
            }

            @Override
            public void onFailure(String str) {
                saveLanguageAndGoToMain(null);
            }
        });
    }

    private void saveLanguageAndGoToMain(String language) {
        if(language != null) {
            Constants.DEVICE_LANGUAGE = language;
            RetrofitConfig.getInstance().updateRetrofit();
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
