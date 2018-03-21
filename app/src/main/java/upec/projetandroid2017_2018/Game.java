package upec.projetandroid2017_2018;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;
import static upec.projetandroid2017_2018.GameActivity.*;

/**
 * Created by NoureddinePc on 24/02/2018.
 */

public class Game  {
    private GridLayout gridLayout ;
    private ArrayList <MyButton> myButtons ;
    private MyButton empty;
    private Context context;
    private DbGame dbGame ;
    private ArrayList <MyButton> bestSolution =new ArrayList<>();
    private ArrayList <MyButton> step = new ArrayList<>();
    private int MAX_DEPTH = 10;
    private int BEST_DEPTH = MAX_DEPTH;



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
                        if (((view.getId())==(ROW*ROW)-1)&( Iwin())){
                            if (dbGame.existWinLevel(ROW))
                                dbGame.deleteWinLevel(ROW);
                            dbGame.setWinLeve(ROW);
                            Intent intent = new Intent(context,WinActivity.class);
                            intent.putExtra("level",ROW);
                            startActivity(context,intent,null);

                        }
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
        dbGame = new DbGame(context);

    }

    View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }


    void setMyButtons(ArrayList<MyButton> myButtons) {
        this.myButtons = myButtons;
    }

    MyButton getEmpty() {
        return empty;
    }

    View.OnDragListener getDragListener() {
        return dragListener;
    }

    private void hashMyArry(){
        java.util.Collections.shuffle(myButtons);
        for (int i=0 ;i<myButtons.size();i++)
            myButtons.get(i).getButton().setId(i);


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
            Button b = gridLayout.findViewById(i);
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

    private boolean isCorrect(ArrayList<MyButton> goal,ArrayList<MyButton>now){
        for (int i=0;i<myButtons.size()-1;i++){
            if (!goal.get(i).getButton().getText().toString().equals(now.get(i).getButton().getText().toString()))
                return false;
        }
        return true;
    }

    public void help(ArrayList<MyButton>now, ArrayList<MyButton>goal, MyButton empty, int depth,MyButton played){
        ArrayList<MyButton> myButtonsNow =now;
        if (BEST_DEPTH<=depth) return;
        if (played!=null) {
            step.add(played);
        }

        MyButton tempEmpty = empty;

        if (isCorrect(goal,myButtonsNow)){
            BEST_DEPTH = depth;
            for (int i = 0 ;i<depth;i++)
                bestSolution.add(step.get(i));
            return;
        }
        //Log.e("isCorrect",""+isCorrect(goal,myButtonsNow));

        int up      = up(empty.getButton().getId());
        int down    = down(empty.getButton().getId());
        int left    = left(empty.getButton().getId());
        int right   = right(empty.getButton().getId());
        if (played!=null){
            if (up == played.getButton().getId()) up=-1;
            if (down == played.getButton().getId()) down=-1;
            if (left == played.getButton().getId()) left=-1;
            if (right == played.getButton().getId()) right=-1;
        }
        /*if (up>=0 && up<myButtonsNow.size()){
            //Log.e("Avant myButtonsNow",": "+myButtonsNow.get(up).getButton().getText());
            myButtonsNow.set(empty.getButton().getId(),myButtonsNow.get(up));
            myButtonsNow.get(empty.getButton().getId()).getButton().setId(empty.getButton().getId());
            myButtonsNow.set(up,tempEmpty);
            myButtonsNow.get(up).getButton().setId(up);
            myButtonsNow.get(up).getButton().setText("");
            //Log.e("myButtonsNow",": "+myButtonsNow.get(up).getButton());
            //Log.e("---------","--------");
            for (int i=0 ;i<myButtonsNow.size();i++){
                Log.e("*( "+i+" ) :"," "+myButtonsNow.get(i).getButton().getText());
            }
            Log.e("---------","--------");
            help(myButtonsNow,goal,myButtonsNow.get(up),depth+1,myButtonsNow.get(up));
        }*/
        /*Log.e("up",": "+up);
        Log.e("down",": "+down);
        Log.e("left",": "+left);
        Log.e("right",": "+right);
        Log.e("iscorrct",""+isCorrect(goal,myButtonsNow));
        Log.e("---------","--------");*/
        /*if (down>=0 && down<myButtonsNow.size()){
            myButtonsNow.set(empty.getButton().getId(),myButtonsNow.get(down));
            myButtonsNow.get(empty.getButton().getId()).getButton().setId(empty.getButton().getId());
            myButtonsNow.set(down,tempEmpty);
            myButtonsNow.get(down).getButton().setId(down);
            for (int i=0 ;i<myButtonsNow.size();i++){
                Log.e("*( "+i+" ) :"," "+myButtonsNow.get(i).getButton().getText());
            }
            Log.e("---------","--------");
            help(myButtonsNow,goal,myButtonsNow.get(down),depth+1,myButtonsNow.get(down));
        }
        if (left>=0 && left<myButtonsNow.size()){
            myButtonsNow.set(empty.getButton().getId(),myButtonsNow.get(left));
            myButtonsNow.get(empty.getButton().getId()).getButton().setId(empty.getButton().getId());
            myButtonsNow.set(left,tempEmpty);
            myButtonsNow.get(left).getButton().setId(left);
            for (int i=0 ;i<myButtonsNow.size();i++){
                Log.e("*( "+i+" ) :"," "+myButtonsNow.get(i).getButton().getText());
            }
            Log.e("---------","--------");
            help(myButtonsNow,goal,myButtonsNow.get(left),depth+1,myButtonsNow.get(left));
        }*/
        Log.e("Right",""+right);
        if (right>=0 && right<myButtonsNow.size()){
            myButtonsNow.set(empty.getButton().getId(),myButtonsNow.get(right));
            myButtonsNow.get(empty.getButton().getId()).getButton().setId(empty.getButton().getId());
            myButtonsNow.set(right,tempEmpty);
            myButtonsNow.get(right).getButton().setId(right);
            for (int i=0 ;i<myButtonsNow.size();i++){
                Log.e("*( "+i+" ) :"," "+myButtonsNow.get(i).getButton().getText());
            }
            Log.e("---------","--------");

            help(myButtonsNow,goal,myButtonsNow.get(right),depth+1,myButtonsNow.get(right));
        }
    }

    private int up(int idButoon){return idButoon-ROW;}

    private int down(int idButoon){return idButoon+ROW;}

    private int left(int idButoon){
        if ((idButoon%ROW)==0)
            return -idButoon;
        else
            return idButoon-1;
    }
    private int right(int idButoon){
        if (((idButoon+1)%ROW)==0)
            return -idButoon;
        else
            return idButoon+1;
    }

    private ArrayList<MyButton> solution(){
        ArrayList<MyButton> myButtons= new ArrayList<>();
        for (int i=0;i<this.myButtons.size()-1;i++){
            MyButton myButton = new MyButton(new Button(context),i);
            myButtons.add(myButton);
        }
        MyButton myButton = new MyButton(new Button(context),this.myButtons.size()-1);
        myButtons.add(myButton);
        return myButtons;
    }

    public void clicHelp(){

        ArrayList <MyButton> now = lastData();
        ArrayList<MyButton>goal = solution();
        for (int i=0 ;i<now.size();i++){
            Log.e("*( "+i+" ) :"," "+now.get(i).getButton().getText());
        }
        help(now, goal, empty,0,null);


        Log.e("bestSolution ",""+bestSolution.size());
        Log.e("step ",""+step.size());
        Log.e("---------","--------");




    }


}
