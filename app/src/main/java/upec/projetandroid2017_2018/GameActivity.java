package upec.projetandroid2017_2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private int ROW ;
    private Game game;
    Chronometer chronometre ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        chronometre = findViewById(R.id.chronometre);
        //chronometre.start();


        ROW =  intent.getIntExtra("level",2);

        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(ROW);
        gridLayout.setRowCount(ROW);
        FrameLayout l = (FrameLayout)findViewById(R.id.FrameLyout);
        game = new Game(gridLayout,this);
        initGame();
        game.start();
        l.addView(game.getGridLayout());

    }

    @Override
    protected void onPause() {
        super.onPause();
        chronometre.stop();
        Toast.makeText(this,"onPause"+chronometre.isActivated(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chronometre.stop();
        Toast.makeText(this,"onStop"+chronometre.isActivated(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        chronometre.stop();
        Toast.makeText(this,"onStart"+chronometre.isActivated(),Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        chronometre.start();

        Toast.makeText(this,"onResume"+chronometre.isActivated(),Toast.LENGTH_SHORT).show();
    }

    private void initGame() {
        for (int i=0 ;i<(ROW*ROW)-1;i++){
            Button button = new Button(this);
            MyButton myButton = new MyButton(button,i);
            game.getMyButtons().add(myButton);
        }
        Button button = new Button(this);
        MyButton myButton = new MyButton(button,(ROW*ROW));
        game.setEmpty(myButton);
    }

    private void Vibration (){
        if(MainActivity.VIBRATION){
            Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(50);
        }
    }
    private void clicSound(){
        if (MainActivity.SOND){
            final MediaPlayer clicSound = MediaPlayer.create(this,R.raw.clic);
            clicSound.start();
        }
    }

    public void VibrationAndClicSound(){
        Vibration ();
        clicSound();
    }



}