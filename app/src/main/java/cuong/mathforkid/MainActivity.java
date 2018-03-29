package cuong.mathforkid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton play;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.startButton);
        play.setOnClickListener(this);

        title = findViewById(R.id.title);
        Typeface t = Typeface.createFromAsset(getAssets(),"comicbd.ttf");
        title.setTypeface(t);
        title.setText(R.string.app_name);
        title.setTextColor(getResources().getColor(R.color.textColor));
    }
    @Override
    public void onClick(View view) {
        Intent t = new Intent(this,GameActivity.class);
        t.putExtra("mute",false);
        startActivity(t);
    }
}
