import java.lang.String;
import java.lang.System;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: haukur
 * Date: 8.2.2013
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */

public class StateDFS {
    private HashSet<State> marked;    // marked[v] = is there an s-v path?
    List<String> queue = new ArrayList<String>();
    Stack<String> successMoves;
    List<Node> branches = new ArrayList<Node>();

    public StateDFS(State startingState) {
        marked = new HashSet<State>();
        Node root = new Node(startingState,"TURN_ON",null);
        successMoves = new Stack<String>();
        dfs(root);
    }

    private class Node
    {
        private State state;
        private String move;
        private Node parent;
        private int cost;
        Node(State state, String move, Node parent)
        {
            if(move == "TURN_OFF")
                 this.cost= 1 + (15*state.getDirtLocations().size());
            else if(move=="SUCK" && !state.atDirt())
                 this.cost=5;
            else
                this.cost=1;
            this.state=state;
            this.move=move;
            this.parent=parent;
        }
    }

    private void dfs(Node current) {
        marked.add(current.state);
        if(current.state.successor())
        {
            successMoves.push("TURN_OFF");
            Node it = current;
            while(it != null)
            {
                successMoves.push(it.move);
                it=it.parent;
            }
            return;
        }
        for (String move : current.state.legalMoves()) {
            State resultingState = current.state.ResultingState(move);
            if (!marked.contains(resultingState)) {
                Node newNode = new Node(resultingState,move,current);
                branches.add(newNode);
                dfs(newNode);
            }
        }
    }

    public void print() {
        int i =0;
        int length = 0;
        for(Node n: branches)
        {
            if(n.state.getDirtLocations().isEmpty()) {
            System.out.println("--------BRANCH " + i + "--------");
            Node current = n;
            int len = 0;
            while(current != null)
            {
                System.out.print(current.move + " -" + current.state.hashCode() + "- (" + current.state.getCurrentLocation().getX() + " " + current.state.getCurrentLocation().getY() + ")" + " OR:" + current.state.getOrientation());
                System.out.print(" LEGAL MOVES:");
                if(current.parent != null) {
                    for(String move : current.state.legalMoves())
                        System.out.print(move + " ");
                }
                System.out.println();
                current = current.parent;
                len++;
            }
            System.out.println("LEN:" + len );
            System.out.println("CLEANED:" + (5-n.state.getDirtLocations().size()) );
            i++;
            }
        }
    }

    public String  nextMove() {
      return successMoves.pop();
    }
}


