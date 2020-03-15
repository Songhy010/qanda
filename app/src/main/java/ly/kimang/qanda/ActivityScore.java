package ly.kimang.qanda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import score.scores;

public class ActivityScore extends AppCompatActivity {

    private TextView tv_math,tv_phy,tv_bio,tv_eng,tv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tv_math = findViewById(R.id.tv_math);
        tv_phy = findViewById(R.id.tv_phy);
        tv_bio = findViewById(R.id.tv_bio);
        tv_eng = findViewById(R.id.tv_eng);
        tv_total = findViewById(R.id.tv_total);
        tv_math.setText(scores.getInstance().getMath()+"");
        tv_phy.setText(scores.getInstance().getPhy()+"");
        tv_bio.setText(scores.getInstance().getBio()+"");
        tv_eng.setText(scores.getInstance().getEng()+"");
        final int total = (scores.getInstance().getEng()+scores.getInstance().getMath()+scores.getInstance().getBio()+scores.getInstance().getPhy());
        tv_total.setText(""+total);
    }
}
