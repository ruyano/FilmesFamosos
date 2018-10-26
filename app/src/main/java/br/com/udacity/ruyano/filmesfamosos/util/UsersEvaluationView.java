package br.com.udacity.ruyano.filmesfamosos.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    private Context context;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.tv_percentage)
    TextView tvPercentage;

    public UsersEvaluationView(Context context) {
        super(context);
        this.context = context;
    }

    public UsersEvaluationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init(context);
    }


    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.users_evaluation_view, this);
        ButterKnife.bind(this);

    }

    public void setValue(Double value) {
        if (value != null) {
            DecimalFormat df = new DecimalFormat("#.#");
            String valueSTR = df.format(value);
            tvPercentage.setText(valueSTR);

            progressBar.setMax(10);
            int progress = (int) Math.round(value);
            progressBar.setProgress(progress);
            setProgressColorByScore(value);
        } else {
            tvPercentage.setText("NR");
            progressBar.setMax(10);
            progressBar.setProgress(0);
            setProgressColorByScore(null);
        }
    }

    private void changeProgressBarColors(int progressColor, int backgroundColor) {
        tvPercentage.setTextColor(progressColor);
        progressBar.getProgressDrawable().setColorFilter(progressColor, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.getBackground().setColorFilter(backgroundColor, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void setProgressColorByScore(Double score) {
        if (score >= 7.5) {
            changeProgressBarColors(context.getResources().getColor(R.color.progress_high_score),
                    context.getResources().getColor(R.color.progress_high_score_background));
        } else if (score >= 5) {
            changeProgressBarColors(context.getResources().getColor(R.color.progress_good_score),
                    context.getResources().getColor(R.color.progress_good_score_background));
        } else if (score >= 2.5) {
            changeProgressBarColors(context.getResources().getColor(R.color.progress_low_score),
                    context.getResources().getColor(R.color.progress_low_score_background));
        } else if (score != null) {
            changeProgressBarColors(context.getResources().getColor(R.color.progress_lowest_score),
                    context.getResources().getColor(R.color.progress_lowest_score_background));
        } else {
            changeProgressBarColors(context.getResources().getColor(R.color.progress_none_score),
                    context.getResources().getColor(R.color.progress_none_score_background));
        }
    }

}
