package upec.projetandroid2017_2018;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by NouakazaPc on 24/03/2018.
 */

public class UninFormedSearch {
    public static final int MAX_SOLUTION = 1300;

    public UninFormedSearch(){}

    public ArrayList<Node> BreadthFirstSearch(Node root){
        ArrayList<Node>pathToSolution = new ArrayList<>();
        ArrayList<Node>openList = new ArrayList<>();
        ArrayList<Node>closedList = new ArrayList<>();

        openList.add(root);
        boolean goalFount= false;
        int depth = 0;
        while (openList.size()>0 && !goalFount && MAX_SOLUTION>depth){
            Node currentNode = openList.get(0);
            closedList.add(currentNode);
            openList.remove(0);

            currentNode.expandMove();
            Log.e("sie",""+currentNode.children.size());

            for (int i=0; i< currentNode.children.size();i++){
                Node currentChild = currentNode.children.get(i);
                if (currentChild.goalTest()){
                    goalFount= true;
                    pathTrace(pathToSolution,currentChild);
                }
                if (contains(openList,currentChild)&&(contains(closedList,currentChild)))
                    openList.add(currentChild);
            }
            depth++;
        }
        return pathToSolution;
    }
    public void pathTrace (ArrayList<Node>path , Node n){
        Node current = n;
        path.add(current);
        while (current.parent !=null){
            current = current.parent ;
            path.add(current);
        }

    }
    public boolean contains (ArrayList<Node> list,Node c){
        for (int i=0; i<list.size();i++){
            if (!list.get(i).isSamePuzzle(c.puzzle)){
                return false;
            }

        }
    return true;
    }
}

