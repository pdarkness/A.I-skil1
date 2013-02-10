import java.lang.String;
import java.lang.System;
import java.util.*;

public class StateDFS implements MoveCalculator {
    private HashSet<State> marked;
    Stack<String> successMoves;

    public StateDFS(State startingState) {
        marked = new HashSet<State>();
        Node root = new Node(startingState,"TURN_ON",null);
        successMoves = new Stack<String>();
        dfs(root);
    }

    private class Node {
        private State state;
        private String move;
        private Node parent;
        Node(State state, String move, Node parent)
        {
            this.state=state;
            this.move=move;
            this.parent=parent;
        }
    }

    private void dfs(Node current) {
        marked.add(current.state);
        if(current.state.successor()) {
            successMoves.push("TURN_OFF");
            Node it = current;
            while(it != null) {
                successMoves.push(it.move);
                it=it.parent;
            }
            return;
        }
        for (String move : current.state.legalMoves()) {
            State resultingState = current.state.ResultingState(move);
            if (!marked.contains(resultingState)) {
                Node newNode = new Node(resultingState,move,current);
                dfs(newNode);
            }
        }
    }

    public String  nextMove() {
        return successMoves.pop();
    }
}