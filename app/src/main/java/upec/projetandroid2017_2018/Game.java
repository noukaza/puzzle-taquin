package upec.projetandroid2017_2018;

import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;

/**
 * Created by NoureddinePc on 24/02/2018.
 */

public class Game {
    private GridLayout gridLayout ;
    private ArrayList <MyButton> myButtons ;

    public Game(GridLayout gridLayout) {
        this.gridLayout = gridLayout;
        myButtons = new ArrayList<>();

    }
    public void init(MyButton button){
        myButtons.add(button);
    }
    private void hashMyArry(){
        java.util.Collections.shuffle(myButtons);

    }
    public void start(){
        hashMyArry();
        for(int i =0 ; i < myButtons.size();i++)
            gridLayout.addView(myButtons.get(i).getButton());
    }

    public GridLayout getGridLayout() {
        return gridLayout;
    }

    public ArrayList<MyButton> getMyButtons() {
        return myButtons;
    }
}
