package upec.projetandroid2017_2018;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        ROW= 6;
        myButtons = new ArrayList<>();
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(ROW);
        gridLayout.setRowCount(ROW);



        for (int i=0 ;i<(ROW*ROW)-1;i++){
            Button button = new Button(this);
            MyButton myButton = new MyButton(button,i);
            myButtons.add(myButton);


        }
        java.util.Collections.shuffle(myButtons);
        for (int i=0 ;i<(ROW*ROW)-1;i++){
            gridLayout.addView(myButtons.get(i).getButton());
        }

        FrameLayout l = (FrameLayout)findViewById(R.id.FrameLyout);
        l.addView(gridLayout);

    }




}