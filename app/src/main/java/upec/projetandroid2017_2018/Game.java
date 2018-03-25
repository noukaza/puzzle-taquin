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
import android.widget.TextView;
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
    private int steps = 0;
    private TextView step_txt ;
    private ArrayList <Node> solution;

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

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
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
      //  hashMyArry();
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

    private ArrayList<MyButton> getMyButtons() {
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
     private boolean Iwin(){
        ArrayList<MyButton> myButtons = lastData();
        for (int i=0;i<myButtons.size()-1;i++){
            if (!myButtons.get(i).getButton().getText().toString().equals(Integer.toString(i)) )
                return false;
        }
        return true;
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

    void aide(){
        ArrayList <Integer> now = orderNow();
        Node root = new Node(now);
        UninFormedSearch ui = new UninFormedSearch();
        solution = ui.BreadthFirstSearch(root);
        Log.e("nnnn ",""+solution.size());
        String nowB = "";


        if (solution.size() < 0)
            Toast.makeText(context,"can't help you",Toast.LENGTH_SHORT).show();
        else {
            solution.remove(solution.size()-1);
            updateWithSolution(solution.get(solution.size()-1).puzzle);
            for (int i=0;i<solution.size();i++){
                for (int j=0;j<solution.get(i).puzzle.size();j++)
                    nowB = nowB + "[" + solution.get(i).puzzle.get(j) + "] ";
                nowB = nowB+"\n";
            }
            Log.e("",nowB);
            //solution.remove(solution.size()-1);
        }

    }

    private ArrayList<MyButton>  trasnlati(ArrayList<Integer> array){
        ArrayList<MyButton> myButtons = new ArrayList<>();
        for (int i=0; i<array.size();i++){
            if (array.get(i)!=(ROW*ROW)-1){
                MyButton myButton = new MyButton(new Button(context),array.get(i));
                myButtons.add(myButton);
            }else {
                MyButton myButton = new MyButton(new Button(context),array.get(i));
                myButton.setEempty(true);
                myButtons.add(myButton);
            }
        }
        return myButtons;
    }
    private void updateWithSolution(ArrayList<Integer> solution){

        ArrayList<MyButton> myButtons = trasnlati(solution);
        for (int i=0; i< myButtons.size();i++){
            if(!myButtons.get(i).isEempty())
                myButtons.get(i).getButton().setOnTouchListener(getOnTouchListener());
            else {
                myButtons.get(i).getButton().setOnDragListener(getDragListener());
                myButtons.get(i).getButton().setBackground(null);
                myButtons.get(i).getButton().setText("");
                setEmpty(myButtons.get(i));
                getEmpty().getButton().setId(myButtons.get(i).getButton().getId());
            }
        }
        setMyButtons(myButtons);
        rePaintLayout();
    }


}
