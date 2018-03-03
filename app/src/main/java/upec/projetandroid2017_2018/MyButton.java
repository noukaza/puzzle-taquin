package upec.projetandroid2017_2018;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;

/**
 * Created by NoureddinePc on 24/02/2018.
 */

public class MyButton {
    private Button button;
    private int ID;


    MyButton(final Button button, int ID) {
        this.button = button;
        button.setGravity(Gravity.CENTER);
        button.setText(""+ID);
        button.setTextColor(Color.WHITE);
        button.setMinimumWidth(60);
        button.setWidth(120);
        button.setId(ID);
        button.setMinHeight(60);
        button.setHeight(120);
        this.ID = ID;
    }

    Button getButton() {
        return button;
    }
    public int getID() {
        return ID;
    }


    void setID(int ID) {
        this.ID = ID;
    }
}
