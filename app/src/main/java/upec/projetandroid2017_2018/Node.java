package upec.projetandroid2017_2018;

import android.util.Log;

import java.util.ArrayList;

import static upec.projetandroid2017_2018.GameActivity.ROW;

/**
 * Created by NouakazaPc on 24/03/2018.
 */

public class Node {

    public ArrayList <Node> children = new ArrayList<>();
    public Node parent ;
    public ArrayList<Integer> puzzle = new ArrayList<>();
    public int empty ;

    public  Node(ArrayList<Integer> problem){

        setPuzzle(problem);

    }
    private void setPuzzle (ArrayList<Integer> arrayList){
        for (int i=0; i<arrayList.size();i++)
            puzzle.add(arrayList.get(i));
    }
    public boolean goalTest () {
        if (puzzle.get(puzzle.size() - 1) != ((ROW * ROW) - 1)) return false;
        for (int i = 0; i < puzzle.size() - 2; i++)
            if (puzzle.get(i)>puzzle.get(i+1))
                return false;
        return true;
    }
    public void expandMove(){
        for (int i=0;i<puzzle.size();i++)
            if (puzzle.get(i)== (ROW*ROW)-1)
                empty= i;
        moveToDown(puzzle,empty);
        moveToLeft(puzzle,empty);
        moveToRight(puzzle,empty);
        moveToUp(puzzle,empty);
    }
    public void moveToUp(ArrayList<Integer> p, int i){
        int up = up(i);
        if (up>=0 && up<p.size()){

            ArrayList<Integer> pc = new ArrayList<>();
            clone(pc,p);

            int temp = pc.get(up);
            pc.set(up,pc.get(i));
            pc.set(i,temp);

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;

        }

    }
    public void moveToDown(ArrayList<Integer> p, int i){
        int down= down(i);
        if (down>=0 && down<p.size()){
            ArrayList<Integer> pc = new ArrayList<>();
            clone(pc,p);

            int temp = pc.get(down);
            pc.set(down,pc.get(i));
            pc.set(i,temp);

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }

    }
    public void moveToRight(ArrayList<Integer> p, int i){
        int right= right(i);
        if (right>=0 && right<p.size()){
            ArrayList<Integer> pc = new ArrayList<>();
            clone(pc,p);

            int temp = pc.get(right);
            pc.set(right,pc.get(i));
            pc.set(i,temp);

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }


    }
    public void moveToLeft(ArrayList<Integer> p, int i){
        int left= left(i);
        if (left>=0 && left<p.size()){
            ArrayList<Integer> pc = new ArrayList<>();
            clone(pc,p);

            int temp = pc.get(left);
            pc.set(left,pc.get(i));
            pc.set(i,temp);

            Node child = new Node(pc);
            children.add(child);
            child.parent = this;
        }

    }
    private void clone (ArrayList<Integer> a, ArrayList<Integer>b){
        for (int i=0; i<b.size();i++)
            a.add(b.get(i));
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

    public void printPuzzl(){
        System.out.println();
        int m = 0 ;
        for (int i=0;i<ROW;i++)
            for (int j=0;j<ROW;j++){
                System.out.println(puzzle.get(m));
            m++;
            }
        System.out.println();

    }

    public boolean isSamePuzzle(ArrayList<Integer> puzzle) {
        for (int i=0; i<puzzle.size();i++){
            if (this.puzzle.get(i) == puzzle.get(i))
                return true;
        }
        return false;
    }
}
