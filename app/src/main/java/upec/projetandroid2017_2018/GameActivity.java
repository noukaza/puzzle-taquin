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

    public static int ROW ;
    private Game game;
    public DbGame dbGame;
    private static Chronometer chronometre ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        chronometre = findViewById(R.id.chronometre);
        chronometre.start();
        ROW =  intent.getIntExtra("level",2);
        dbGame = new DbGame(this);
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(ROW);
        gridLayout.setRowCount(ROW);
        FrameLayout l = (FrameLayout)findViewById(R.id.FrameLyout);
        game = new Game(gridLayout,this);
        if(intent.getBooleanExtra("restart",false)){
             restartGame();
            //Toast.makeText(this,""+dbGame.getData(ROW,this).size(),Toast.LENGTH_SHORT).show();
        }else {
            game.initGame();
            game.startgame();
        }
        l.addView(game.getGridLayout());
       // Toast.makeText(this,"onCreate",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
       // chronometre.stop();
        dbGame.deleteData(ROW);
        dbGame.insertData(game.lastData(),ROW);
        Toast.makeText(this,"laste"+game.lastData().size(),Toast.LENGTH_SHORT).show();

      //  Toast.makeText(this,"onPause"+dbGame.thereIsAData(ROW),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chronometre.start();


      //  Toast.makeText(this,"onResume"+ dbGame.thereIsAData(ROW),Toast.LENGTH_SHORT).show();

    }
    private void restartGame(){
        ArrayList<MyButton> myButtons = dbGame.getData(ROW,this);
        for (int i=0; i< myButtons.size();i++){
            if(!myButtons.get(i).isEempty())
                myButtons.get(i).getButton().setOnTouchListener(game.getOnTouchListener());
            else {
                myButtons.get(i).getButton().setOnDragListener(game.getDragListener());
                myButtons.get(i).getButton().setBackground(null);
                myButtons.get(i).getButton().setText("");
                game.setEmpty(myButtons.get(i));
                game.getEmpty().getButton().setId(myButtons.get(i).getButton().getId());
            }
        }
        game.setMyButtons(myButtons);
        game.rePaintLayout();

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