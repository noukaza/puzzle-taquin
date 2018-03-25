package upec.projetandroid2017_2018;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public static int ROW ;
    private Game game;
    public DbGame dbGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        dbGame = new DbGame(this);
        Intent intent = getIntent();
        ROW =  intent.getIntExtra("level",2);
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(ROW);
        gridLayout.setRowCount(ROW);
        FrameLayout l = findViewById(R.id.FrameLyout);
        TextView stepText= (TextView) findViewById(R.id.step_txt);
        game = new Game(gridLayout,this,stepText);
        if(intent.getBooleanExtra("restart",false)){
             restartGame();
             stepText.setText("Step : "+ dbGame.getDatas(ROW));
        }else {
            game.initGame();
            game.startgame();
        }
        l.addView(game.getGridLayout());
        Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.aide();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbGame.deleteData(ROW);
        dbGame.insertData(game.lastData(),ROW);
        dbGame.deletestepLevels(ROW);
        dbGame.saveStepLevels(ROW,game.getSteps());
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        game.setSteps((int) dbGame.getDatas(ROW));
    }

    public void restartBt(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("level",ROW);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this,Level.class);
        startActivity(intent);
    }

    public void exit_bt(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}