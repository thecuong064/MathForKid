package cuong.mathforkid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    static final String[] backGrColor= {"#8E24AA", "#3949AB", "#512DA8", "#1565C0", "#FFB300", "#9E9D24",
                                        "#64DD17", "#00C853", "#FFA000", "#5D4037", "#455A64"};
    RelativeLayout layout;
    TextView formulaText, resText, curScoreText, cScore;
    int a, b, res, maxx, minn, keyRes, currentScore, equation;
    ImageButton trueChoice, falseChoice,soundButton;
    Timer timer;
    TimerTask timerTask;
    Random random;
    ProgressBar proBar;
    CountDownTimer countDownTimer;
    MediaPlayer rightSound,wrongSound;
    boolean startGame,mute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        random = new Random();
        //find View
        layout = findViewById(R.id.layout_rela);
        formulaText = findViewById(R.id.formulaView);
        resText = findViewById(R.id.resView);
        trueChoice = findViewById(R.id.trueBtn);
        falseChoice = findViewById(R.id.falseBtn);
        curScoreText = findViewById(R.id.cScoreView);
        cScore = findViewById(R.id.cScore);
        proBar = findViewById(R.id.progress);
        soundButton = findViewById(R.id.sound);

        //set random background color
        int randomColor = random.nextInt(12);
        layout.setBackgroundColor(Color.parseColor(backGrColor[randomColor]));

        //set custom font
        Typeface font = Typeface.createFromAsset(getAssets(),"VNVOGU.TTF");
        formulaText.setTypeface(font);
        resText.setTypeface(font);
        Typeface t = Typeface.createFromAsset(getAssets(),"NewAthleticM54.ttf");
        curScoreText.setTypeface(t);
        cScore.setTypeface(t);       //SCORE

        cScore.setText(R.string.score);    cScore.setTextColor(getResources().getColor(R.color.textColor));
        curScoreText.setText("0");  curScoreText.setTextColor(getResources().getColor(R.color.textColor));


        currentScore = 0;
        startGame = false; //value to keep the timer stop
        //user input
        trueChoice.setOnClickListener(this);
        falseChoice.setOnClickListener(this);
        soundButton.setOnClickListener(this);
        trueChoice.setOnTouchListener(this);
        falseChoice.setOnTouchListener(this);

        //create sound for button click
        rightSound = MediaPlayer.create(this, R.raw.rightbell);
        wrongSound = MediaPlayer.create(this, R.raw.wrongbell);

        //get the volume status from previous game
        mute = getIntent().getBooleanExtra("mute",false);
        if (!mute) unmuteSound(); else muteSound();

        gameplay();
    }

    private void gameplay() {
        //for range
            minn = 1;
            maxx = 9;

        a = random.nextInt((maxx - minn) + 1) + minn;
        b = random.nextInt((maxx - minn) + 1) + minn;
        
       //random number for equation: 0-->multiply ; 1-->add 
       equation = random.nextInt(2);
       if (equation == 0) {
           res = a * b;
           //display equation
           formulaText.setText(a + " x " + b);
       }
       else
       {
           res = a + b;
           //display question
           formulaText.setText(a + " + " + b);
       }
       
       //set color for the equation
        formulaText.setTextColor(getResources().getColor(R.color.textColor));

        keyRes = random.nextInt(2);       //random for wrong or right result
        switch (keyRes) {
            case 0:     //display wrong result
                int comp = random.nextInt(2);      //random for bigger or smaller result
                if (comp == 0) res -= random.nextInt( (2 - 1) + 1) + 1;    //if 0->smaller
                else res += random.nextInt( (2 - 1) + 1) + 1;              //else bigger
                break;
            case 1:
                break;
            default:    //right result
                break;
        }
        if (res<0) res = 0;         //cannot have zero result
        resText.setText("= "+res); //display the result
        resText.setTextColor(getResources().getColor(R.color.textColor));

  /*      //create timer task
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //show game over
                        gameOverDisplay();
                    }
                });
            }
        };
        startProgressbar();       //create progress bar for timer task
        timer.schedule(timerTask,1000);        //time each turn
         */
        startProgressbar();
    }

    private void startProgressbar() {
    proBar.setMax(1000);
    proBar.setProgress(1000);
        //call onTick every 10ms
        countDownTimer = new CountDownTimer(1000,10) {
            @Override
            public void onTick(long time) {
               proBar.setProgress(((int)time));
            }

            @Override
            public void onFinish() {
               proBar.setProgress(0);
               gameOverDisplay();
            }
        };
        if (startGame) countDownTimer.start();
    }

    private void updateCurrentScore() {
        curScoreText.setText(""+currentScore);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trueBtn:
                if (keyRes == 1) {
                    continueGame();
                } else {
                    gameOverDisplay();  }
                break;
            case R.id.falseBtn:
                if (keyRes == 0) {
                    continueGame();
                } else {
                    gameOverDisplay();  }
                 break;
            case R.id.sound:
                if (mute)
                {
                    mute = false;
                    unmuteSound();
                } else {
                    mute = true;
                    muteSound();
                }
                break;
            default:
                break;
        }
    }

    private void muteSound() {
        soundButton.setImageResource(R.drawable.mute);
        rightSound.setVolume(0,0);
        wrongSound.setVolume(0,0);
    }

    private void unmuteSound() {
        soundButton.setImageResource(R.drawable.unmute);
        rightSound.setVolume(1,1);
        wrongSound.setVolume(1,1);
    }

    private void continueGame() {
        if (!mute) playRightSound();   //play sound
        currentScore+=1;
        updateCurrentScore();
        cancelTimer();
        if (!startGame) startGame = true;
        gameplay();
    }

    private void playRightSound() {
        if (rightSound.isPlaying()) {
            rightSound.stop();
            rightSound.release();
            rightSound = MediaPlayer.create(this, R.raw.rightbell);
        }       //passed the last sound
        rightSound.start();       //start new sound
    }


    private void gameOverDisplay() {
        wrongSound.start();
        cancelTimer();
        Intent t = new Intent(this,GameOverActivity.class);
        t.putExtra("score",currentScore);
        t.putExtra("mute",mute);
        startActivity(t);
        finish();
    }

    private void cancelTimer() {
        //  timerTask.cancel();
        //  timer.cancel();
        countDownTimer.cancel();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //pressed
                switch (v.getId()) {
                    case R.id.trueBtn:
                        falseChoice.setClickable(false);
                      //  Toast.makeText(GameActivity.this,"Hold true",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.falseBtn:
                        trueChoice.setClickable(false);
                     //   Toast.makeText(GameActivity.this,"Hold false",Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                //released
              //  Toast.makeText(GameActivity.this,"Release",Toast.LENGTH_SHORT).show();
                trueChoice.setClickable(true);
                falseChoice.setClickable(true);
                break;
        }
        return false;
    }

}
