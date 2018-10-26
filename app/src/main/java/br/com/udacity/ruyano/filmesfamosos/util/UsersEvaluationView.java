package br.com.udacity.ruyano.filmesfamosos.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import br.com.udacity.ruyano.filmesfamosos.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersEvaluationView extends RelativeLayout {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_percentage)
    TextView tvPercentage;

    public UsersEvaluationView(Context context) {
        super(context);
    }

    public UsersEvaluationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }


    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.users_evaluation_view, this);
        ButterKnife.bind(this);

    }

    public void setValue(Double value) {
        DecimalFormat df = new DecimalFormat("#.#");
        String valueSTR = df.format(value);
        tvPercentage.setText(valueSTR);

        progressBar.setVisibility(View.VISIBLE);
    }

}
