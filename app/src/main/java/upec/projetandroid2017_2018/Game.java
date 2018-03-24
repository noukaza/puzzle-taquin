package upec.projetandroid2017_2018;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

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
    private ArrayList <Integer> bestSolution =new ArrayList<>();
    private ArrayList <Integer> stepsArrry = new ArrayList<>();
    private int MAX_DEPTH = 10;
    private int steps = 0;
    private TextView step_txt ;



    private View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int dragEv = dragEvent.getAction();
            Button b = (Button) dragEvent.getLocalState();
            switch (dragEv){

                case DragEvent.ACTION_DRAG_ENDED:
                    VibrationAndClicSound();
                    break;
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
                        steps++;
                        step_txt.setText("Step : "+steps);
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

    public Game(GridLayout gridLayout,Context context , TextView step_txt) {
        this.gridLayout = gridLayout;
        myButtons = new ArrayList<>();
        this.context = context;
        dbGame = new DbGame(context);
        this.step_txt = step_txt;

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

     void initGame(){
        for (int i = 0; i<(ROW* ROW)-1; i++){
            Button button = new Button(context);
            MyButton myButton = new MyButton(button,i);
            getMyButtons().add(myButton);
        }
        Button button = new Button(context);
        MyButton myButton = new MyButton(button,(ROW* ROW)-1);
        setEmpty(myButton);
    }

     void startgame(){
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

     void rePaintLayout(){
        gridLayout.removeAllViews();

        for (MyButton button:myButtons) {
            gridLayout.addView(button.getButton());
        }

    }

     void setEmpty(MyButton empty) {
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
     ArrayList<MyButton>lastData(){
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
     boolean Iwin(){
        ArrayList<MyButton> myButtons = lastData();
        for (int i=0;i<myButtons.size()-1;i++){
            if (!myButtons.get(i).getButton().getText().toString().equals(Integer.toString(i)) )
                return false;
        }
        return true;
    }


    private boolean fin (ArrayList<Integer> goal , ArrayList<Integer> now){
        if (now.get(now.size()-1)!=((ROW*ROW)-1)) return false;
        for(int i=0 ;i<now.size()-1;i++)
            if (goal.get(i)!= now.get(i))
                return false;
        return true;
    }
    private ArrayList<Integer > clone (ArrayList<Integer> arrayList){
        ArrayList<Integer> clone = new ArrayList<>();
        for (int i=0; i<arrayList.size();i++)
            clone.add(arrayList.get(i));
        return clone;
    }
     void clicadie(ArrayList<Integer> now, ArrayList<Integer> goal, int empty, int depth, int played){
         Log.e("is",fin(goal,now)+"");
        if (MAX_DEPTH <=depth) return;
        if (played>-1) stepsArrry.add(played);
         int tempEmpty = empty;
        if (fin(goal,now)){
            MAX_DEPTH= depth;
            for (int i = 0 ;i<depth;i++)
                bestSolution.add(stepsArrry.get(i));
            Log.e("step",""+depth);
            return;
        }


        int up      = up(tempEmpty);
        int down    = down(tempEmpty);
        int left    = left(tempEmpty);
        int right   = right(tempEmpty);
        if (played >= 0){
            if (up == played) up=-1;
            if (down == played) down=-1;
            if (left ==played) left=-1;
            if (right == played) right=-1;
        }
        if (down>=0 && down<now.size()){
            ArrayList<Integer> buttonNow = clone(now);
            int temp = buttonNow.get(empty);
            buttonNow.set(tempEmpty, buttonNow.get(down));
            buttonNow.set(down,temp);;
            clicadie(buttonNow,goal,down,depth+1,up(down));
        }
        if (up>=0 && up<now.size()){
            ArrayList<Integer> buttonNow = clone(now);
            int temp = buttonNow.get(empty);
            buttonNow.set(tempEmpty, buttonNow.get(up));
            buttonNow.set(up,temp);
            clicadie(buttonNow,goal,up,depth+1,down(up));
        }

        if (right>=0 && right<now.size()){
            ArrayList<Integer> buttonNow = clone(now);
            int temp = buttonNow.get(empty);
            buttonNow.set(tempEmpty, buttonNow.get(right));
            buttonNow.set(right,temp);
            clicadie(buttonNow,goal,right,depth+1,left(right));
        }
        if (left>=0 && left<now.size()){
            ArrayList<Integer> buttonNow = clone(now);
            int temp = buttonNow.get(empty);
            buttonNow.set(tempEmpty, buttonNow.get(left));
            buttonNow.set(left,temp);
            clicadie(buttonNow,goal,left,depth+1,right(left));
        }


    }



    private int up(int idButoon){return idButoon-ROW;}

    private int down(int idButoon){return idButoon+ROW;}

    private int left(int idButoon){
        if (idButoon == 0)
            return -1;
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

    private void Vibration (){
        if(MainActivity.VIBRATION){
            Vibrator vib = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
            assert vib != null;
            vib.vibrate(50);
        }
    }
    private void clicSound(){
        if (MainActivity.SOND){
            final MediaPlayer clicSound = MediaPlayer.create(context,R.raw.clic);
            clicSound.start();
        }
    }

    public void VibrationAndClicSound(){
        Vibration ();
        clicSound();
    }

    private ArrayList<Integer> orderNow(){
        ArrayList<Integer> now = new ArrayList<>();
        for (int i = 0 ; i<this.myButtons.size();i++){
            Button b = (Button)gridLayout.findViewById(i);
            if (empty.getButton().getId()== b.getId())
                now.add((ROW*ROW)-1);
            else
                now.add(Integer.parseInt(b.getText().toString()));
        }
        return now;
    }
    private ArrayList<Integer> solutions(){
        ArrayList<Integer> solution = new ArrayList<>();
        for (int i = 0 ; i<this.myButtons.size();i++)
            solution.add(i);
        return solution;
    }

    public void aide (){

       /* ArrayList <Integer> now = orderNow();
        ArrayList <Integer> solution = solutions();
        clicadie(now,solution,empty.getButton().getId(),0,-1);
*/

        ArrayList <Integer> now = orderNow();
        Node root = new Node(now);
        UninFormedSearch ui = new UninFormedSearch();
        ArrayList <Node> soluto = ui.BreadthFirstSearch(root);
        Log.e("nnnn ",""+soluto.size());
        String nowB = " : ";
        for (int i=0 ;i<soluto.size();i++){
           for (int j=0;j<soluto.get(i).puzzle.size();j++)
               nowB = nowB +"["+soluto.get(i).puzzle.get(j)+"] ";
        }
        Log.e("",nowB);
    }


}
