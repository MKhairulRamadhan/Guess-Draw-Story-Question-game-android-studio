package com.khairul.gudrasto.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.khairul.gudrasto.R;

public class FinalScoreDialog {

    private Context mContext;
    private Dialog finalScoreDialog;

    private TextView textViewFinalScore;
    public FinalScoreDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void finalScoreDialog(int point){
        boolean data = false;

        finalScoreDialog = new Dialog(mContext);
        finalScoreDialog.setContentView(R.layout.final_score_dialog);

        final LinearLayout btnFinalScore = finalScoreDialog.findViewById(R.id.layoutnext);

        finalScoreCal(point);

        btnFinalScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalScoreDialog.dismiss();
            }
        });

        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);
        finalScoreDialog.setCanceledOnTouchOutside(false);

    }

    public void finalScoreCal(int point) {
        textViewFinalScore = (TextView) finalScoreDialog.findViewById(R.id.text_final_score);
        textViewFinalScore.setText("Selamat\n kamu dapat: " + String.valueOf(point)+" Point");

    }

}
