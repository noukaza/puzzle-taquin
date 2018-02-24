package upec.projetandroid2017_2018;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by NoureddinePc on 24/02/2018.
 */

public class MyButton {
    private Button button;
    private int ID;


    public MyButton(final Button button, int ID) {
        this.button = button;
        button.setGravity(Gravity.CENTER);
        button.setText(""+ID);
        button.setTextColor(Color.WHITE);
       // button.setBackgroundResource(R.drawable.gamebutton);
       // button.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        //button.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setMinimumWidth(60);
        button.setWidth(120);
        button.setMinHeight(60);
        button.setHeight(120);
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Game.step(button);
            }
        });

       // button.setLayoutParams(null);
        this.ID = ID;
    }

    public Button getButton() {
        return button;
    }
    public int getID() {
        return ID;
    }


    public void setID(int ID) {
        this.ID = ID;
    }
}
