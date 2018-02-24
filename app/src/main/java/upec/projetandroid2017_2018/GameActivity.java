package upec.projetandroid2017_2018;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private int ROW ;
    private ArrayList <MyButton> myButtons ;
    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        int r = intent.getIntExtra("level",2);
        ROW= r;
        myButtons = new ArrayList<>();
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(ROW);
        gridLayout.setRowCount(ROW);
        FrameLayout l = (FrameLayout)findViewById(R.id.FrameLyout);
        game = new Game(gridLayout);
        initGame();
        game.start();
        l.addView(game.getGridLayout());

    }

    private void initGame() {
        for (int i=0 ;i<(ROW*ROW)-1;i++){
            Button button = new Button(this);
            MyButton myButton = new MyButton(button,i);
            game.getMyButtons().add(myButton);
        }
    }



}