package cuong.mathforkid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {
    Button resumeBtn, exitBtn;
    TextView score, title, over, cScore, tScore, best;
    int newScore, bestScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        resumeBtn = findViewById(R.id.resume);
        exitBtn = findViewById(R.id.exit);
        score = findViewById(R.id.newScore);
        over = findViewById(R.id.over);
        cScore = findViewById(R.id.currentScore);
        title = findViewById(R.id.title);
        tScore = findViewById(R.id.topScore);
        best = findViewById(R.id.bestScore);

        //custom font
        Typeface t = Typeface.createFromAsset(getAssets(),"comicbd.ttf");

        //set font, color and text for objects
        title.setTypeface(t);
            title.setTextColor(getResources().getColor(R.color.textColor));
        score.setTypeface(t);
            score.setTextColor(getResources().getColor(R.color.textColor));
        over.setTypeface(t);
             over.setTextColor(getResources().getColor(R.color.textColor));
        cScore.setTypeface(t);
            cScore.setTextColor(getResources().getColor(R.color.textColor));
        resumeBtn.setTypeface(t);
            resumeBtn.setTextColor(getResources().getColor(R.color.white_text));
        exitBtn.setTypeface(t);
            exitBtn.setTextColor(getResources().getColor(R.color.white_text));
        tScore.setTypeface(t);
            tScore.setTextColor(getResources().getColor(R.color.textColor));
        best.setTypeface(t);
            best.setTextColor(getResources().getColor(R.color.textColor));

        //set Text
        title.setText(R.string.app_name);
        over.setText(R.string.gameover);
        cScore.setText(R.string.score);
        resumeBtn.setText(R.string.play);
        exitBtn.setText(R.string.exit);
        tScore.setText(R.string.best);

        newScore = getIntent().getIntExtra("score",0);
        updateTopScore();
        score.setText(""+newScore);


        resumeBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
    }

    private void updateTopScore() {
        SharedPreferences pref = this.getSharedPreferences("myPrefKey", Context.MODE_PRIVATE);
        bestScore = pref.getInt("score",0);
        if (bestScore < newScore) {
            bestScore = newScore;
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("score", bestScore);
            edit.apply();
        }
        best.setText(""+bestScore);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resume:
                Intent returnIntent = new Intent(this,GameActivity.class);
                startActivity(returnIntent);
                finish();
                break;
            case R.id.exit:
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();
                break;
            default:
                break;
        }
    }

   /* @Override
    public void onBackPressed() {
        Intent t = new Intent(this,MainActivity.class);
        startActivity(t);
    }*/

}
