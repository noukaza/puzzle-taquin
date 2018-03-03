package upec.projetandroid2017_2018;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static upec.projetandroid2017_2018.GameActivity.*;

/**
 * Created by NoureddinePc on 24/02/2018.
 */

public class Game  {
    private GridLayout gridLayout ;
    static private ArrayList <MyButton> myButtons ;
    private MyButton empty;
    private Context context;

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
        for (int i=0 ;i<myButtons.size();i++){
        myButtons.get(i).getButton().setId(i);
        }

    }

    public void initGame(){
        for (int i = 0; i<(ROW* ROW)-1; i++){
            Button button = new Button(context);
            MyButton myButton = new MyButton(button,i);
            getMyButtons().add(myButton);
        }
        Button button = new Button(context);
        MyButton myButton = new MyButton(button,(ROW* ROW)-1);
        setEmpty(myButton);
    }

    public void startgame(){
        hashMyArry();
        for(int i =0 ; i < myButtons.size();i++) {

            myButtons.get(i).getButton().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ClipData data = ClipData.newPlainText("","");
                    View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);

                    view.startDrag(null,dragShadowBuilder,view,0);
                    return true;
                }
            });
            gridLayout.addView(myButtons.get(i).getButton());
        }
        empty.getButton().setText("");
        myButtons.add(empty);
        empty.getButton().setOnDragListener(dragListener);
        empty.getButton().setBackground(null);
        empty.setID((ROW* ROW)-1);

        gridLayout.addView(empty.getButton());
    }

    GridLayout getGridLayout() {
        return gridLayout;
    }

    ArrayList<MyButton> getMyButtons() {
        return myButtons;
    }

    static void step(View view){

    }
    public void restartGame(ArrayList<MyButton> myButtons){

    }
    private void rePaintLayout(){
        gridLayout.removeAllViews();

        for (MyButton button:myButtons) {
            gridLayout.addView(button.getButton());
        }

    }


    private View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int dragEv = dragEvent.getAction();
            Button b = (Button) dragEvent.getLocalState();

            switch (dragEv){

                case DragEvent.ACTION_DRAG_ENTERED:
                   if (canChanged(b.getId())){

                       gridLayout.removeView(view);
                       gridLayout.removeView(b);
                       int temp = view.getId();
                       view.setId(b.getId());
                       empty.setID(b.getId());
                       b.setId(temp);
                       if (view.getId()<b.getId()){
                           gridLayout.addView(view,view.getId());
                           gridLayout.addView(b,b.getId());
                       }else {
                           gridLayout.addView(b,b.getId());
                           gridLayout.addView(view,view.getId());
                       }
                   }
                    break;
            }
            return true;
        }
    };

    public void setEmpty(MyButton empty) {
        this.empty = empty;
    }

    private boolean canChanged(int id){
        int nbrCase = GameActivity.ROW;
        Toast.makeText(context,"b : "+id+" v : "+empty.getID(),Toast.LENGTH_SHORT).show();

        if(((empty.getID()+1) % nbrCase)==0){
            if (((empty.getID()+nbrCase)==id)|((empty.getID()-1)==id)|((empty.getID()-nbrCase)==id)){
                return true;
            }
       }else
       if((empty.getID()==0)|(((empty.getID()) % nbrCase)==0)){
           if (((empty.getID()+nbrCase)==id)|((empty.getID()+1)==id)|((empty.getID()-nbrCase)==id)){
               return true;
           }
       }else
       if((empty.getID()-nbrCase)==id|(empty.getID()+nbrCase)==id|(empty.getID()-1)==id|(empty.getID()+1)==id){
           return true;
       }
        return false;
    }

}
