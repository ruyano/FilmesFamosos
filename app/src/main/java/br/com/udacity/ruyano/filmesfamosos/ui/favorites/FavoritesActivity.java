package br.com.udacity.ruyano.filmesfamosos.ui.favorites;

import androidx.appcompat.app.AppCompatActivity;
import br.com.udacity.ruyano.filmesfamosos.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class FavoritesActivity extends AppCompatActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
