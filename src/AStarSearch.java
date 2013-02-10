import java.util.Stack;

public class AStarSearch implements MoveCalculator {
    Stack<String> successMoves;

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

       AStarSearch(State startingState)
       {
            successMoves = new Stack<String>();
       }

       void aSearch(Node current)
       {

       }

       private int h(State s)
       {
           return 0;
       }

       public String nextMove()
       {
           return "TURN_OFF";
       }
}
