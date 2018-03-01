package upec.projetandroid2017_2018;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by NoureddinePc on 24/02/2018.
 */

public class Game {
    static private GridLayout gridLayout ;
    static private ArrayList <MyButton> myButtons ;
    static private MyButton empty;
    static private Context context;

    public Game(GridLayout gridLayout,Context context) {
        this.gridLayout = gridLayout;
        myButtons = new ArrayList<>();
        this.context = context;

    }
    public void init(MyButton button){
        myButtons.add(button);
    }
    private void hashMyArry(){
        java.util.Collections.shuffle(myButtons);

    }
    void start(){
        hashMyArry();
        for(int i =0 ; i < myButtons.size();i++) {
            gridLayout.addView(myButtons.get(i).getButton());

        }
        empty.getButton().setText("");
        empty.getButton().setVisibility(View.INVISIBLE);
        myButtons.add(empty);
        gridLayout.addView(empty.getButton());
    }

    GridLayout getGridLayout() {
        return gridLayout;
    }

    ArrayList<MyButton> getMyButtons() {
        return myButtons;
    }

    static void step(View view){
        for (int i=0 ; i<myButtons.size();i++){
            if(myButtons.get(i).getButton().equals(view) && !myButtons.get(i).getButton().equals(empty.getButton())  ){
                if (i!=0){
                    MyButton tempmyButton1 = myButtons.get(i+1);
                    MyButton tempmyButton = myButtons.get(i);
                    myButtons.remove(tempmyButton);
                    myButtons.remove(tempmyButton1);

                    myButtons.add(i,tempmyButton1);
                    myButtons.add(i+1,tempmyButton);
                 //   myButtons.add(i+1,tempmyButton);
                    rePaintLayout();
                    System.out.println();
                    System.err.println("i : "+i+" vall :"+tempmyButton.getButton().getId());
                    System.out.println();
                    return;
                }

            }
        }
    }
    static private void rePaintLayout(){
        gridLayout.removeAllViews();
        final MediaPlayer clicSound = MediaPlayer.create(context,R.raw.clic);

        for (MyButton button:myButtons) {
            gridLayout.addView(button.getButton());
        }

    }

    public void setEmpty(MyButton empty) {
        this.empty = empty;
    }


}
