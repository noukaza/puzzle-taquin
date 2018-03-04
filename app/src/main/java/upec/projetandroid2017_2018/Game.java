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
    private ArrayList <MyButton> myButtons ;
    private MyButton empty;
    private Context context;

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
                        b.setId(temp);
                        if (view.getId()<b.getId()){
                            gridLayout.addView(view,view.getId());
                            gridLayout.addView(b,b.getId());
                        }else {
                            gridLayout.addView(b,b.getId());
                            gridLayout.addView(view,view.getId());
                        }
                        if (((view.getId())==(ROW*ROW)-1)&( Iwin()))
                                Toast.makeText(context," you WIN !!! ",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return true;
        }
    };
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null,dragShadowBuilder,view,0);
            return true;

        }
    };

    public Game(GridLayout gridLayout,Context context) {
        this.gridLayout = gridLayout;
        myButtons = new ArrayList<>();
        this.context = context;

    }

    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public void setGridLayout(GridLayout gridLayout) {
        this.gridLayout = gridLayout;
    }

    public void setMyButtons(ArrayList<MyButton> myButtons) {
        this.myButtons = myButtons;
    }

    public MyButton getEmpty() {
        return empty;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public View.OnDragListener getDragListener() {
        return dragListener;
    }

    public void setDragListener(View.OnDragListener dragListener) {
        this.dragListener = dragListener;
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

            myButtons.get(i).getButton().setOnTouchListener(onTouchListener);
            gridLayout.addView(myButtons.get(i).getButton());
        }
        empty.getButton().setText("");
        myButtons.add(empty);
        empty.getButton().setOnDragListener(dragListener);
        empty.getButton().setBackground(null);
        empty.setEempty(true);
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
    public void rePaintLayout(){
        gridLayout.removeAllViews();

        for (MyButton button:myButtons) {
            gridLayout.addView(button.getButton());
        }

    }

    public void setEmpty(MyButton empty) {
        this.empty = empty;
    }

    private boolean canChanged(int id){
        int nbrCase = GameActivity.ROW;
        if((((empty.getButton().getId())+1) % nbrCase)==0){
            if (((empty.getButton().getId()+nbrCase)==id)|(((empty.getButton().getId())-1)==id)|((empty.getButton().getId()-nbrCase)==id)){
                return true;
            }
        }else
        if((empty.getButton().getId()==0)|(((empty.getButton().getId()) % nbrCase)==0)){
            if (((empty.getButton().getId()+nbrCase)==id)|(((empty.getButton().getId())+1)==id)|((empty.getButton().getId()-nbrCase)==id)){
                return true;
            }
        }else
        if((empty.getButton().getId()-nbrCase)==id|(empty.getButton().getId()+nbrCase)==id|((empty.getButton().getId())-1)==id|((empty.getButton().getId())+1)==id){
            return true;
        }
        return false;
    }
    public ArrayList<MyButton>lastData(){
        ArrayList<MyButton> myButtons = new ArrayList<>();
        for (int i=0;i<this.myButtons.size();i++){
            Button b = (Button) gridLayout.findViewById(i);
            MyButton myButton =new MyButton(new Button(context),gridLayout.getChildAt(i).getId());
            myButton.getButton().setText(""+b.getText());
            if (empty.getButton().getId()== b.getId())
                myButton.setEempty(true);
            myButtons.add(myButton);
        }
        return myButtons;
    }
    public boolean Iwin(){
        ArrayList<MyButton> myButtons = lastData();
        for (int i=0;i<myButtons.size()-1;i++){

            if (!myButtons.get(i).getButton().getText().toString().equals(Integer.toString(i)) )
                return false;
        }

        return true;
    }

}
