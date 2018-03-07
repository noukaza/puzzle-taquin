package upec.projetandroid2017_2018;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static int ROW ;
    private Game game;
    public DbGame dbGame;
    private Chronometer chronometre ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        //connection a la base de donnée
        dbGame = new DbGame(this);
        // recuperer le intent avec la valeur du level
        Intent intent = getIntent();
        ROW =  intent.getIntExtra("level",2);
        chronometre = findViewById(R.id.chronometre);
        chronometre.start();
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(ROW);
        gridLayout.setRowCount(ROW);
        FrameLayout l = findViewById(R.id.FrameLyout);
        game = new Game(gridLayout,this);
        if(intent.getBooleanExtra("restart",false)){
             restartGame();
        }else {
            game.initGame();
            game.startgame();
        }
        l.addView(game.getGridLayout());

    }

    @Override
    protected void onPause() {
        super.onPause();
        chronometre.stop();
        dbGame.deleteData(ROW);
        dbGame.insertData(game.lastData(),ROW);
        if (dbGame.thereistime(ROW))
            dbGame.deleteTime(ROW);
        dbGame.saveTime(ROW,chronometre.getBase());
    }

    @Override
    protected void onResume() {
        super.onResume();
        chronometre.start();
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
        if(dbGame.thereistime(ROW))
            chronometre.setBase(dbGame.getDatas(ROW));

    }

    private void Vibration (){
        if(MainActivity.VIBRATION){
            Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            assert vib != null;
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