package upec.projetandroid2017_2018;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;

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
        for (int i=0 ;i<(GameActivity.ROW*GameActivity.ROW)-1;i++){
            Button button = new Button(context);
            MyButton myButton = new MyButton(button,i);
            getMyButtons().add(myButton);
        }
        Button button = new Button(context);
        MyButton myButton = new MyButton(button,(GameActivity.ROW*GameActivity.ROW)-1);
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
                    view.startDrag(data,dragShadowBuilder,view,0);
                    return true;
                }
            });
            gridLayout.addView(myButtons.get(i).getButton());
        }
        empty.getButton().setText("");
        myButtons.add(empty);
        empty.getButton().setOnDragListener(dragListener);
        empty.getButton().setBackground(null);
        empty.setID((GameActivity.ROW*GameActivity.ROW)-1);

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
                   gridLayout.removeView(view);
                   gridLayout.removeView(b);
                   int temp = view.getId();
                    view.setId(b.getId());
                    b.setId(temp);
                    if (view.getId()<b.getId()){
                        gridLayout.addView(view,view.getId());
                        gridLayout.addView(b,b.getId());
                    }else {
                        gridLayout.addView(b,b.getId());
                        gridLayout.addView(view,view.getId());
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    break;

            }
            return true;
        }
    };
    private void setEmpty(MyButton empty) {
        this.empty = empty;
    }



}
