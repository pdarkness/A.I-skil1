import java.util.ArrayList;
import java.util.List;
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

       public AStarSearch(State startingState)
       {
            successMoves = new Stack<String>();
       }

       private Node aSearch(Node current)
       {
          int score = h(current.state);
          if(score == 0)
          {
             return current;
          }
          List<Node> children = new ArrayList<Node>();
          for(String move: current.state.legalMoves())
          {
              Node newNode = new Node(current.state.ResultingState(move),move,current);
              children.add(newNode);
          }
           Node nodeToExpand = null;
           int min = Integer.MIN_VALUE;
           for(Node node : children)
              {
                  int nodeScore = h(node.state);
                  if( score < min)
                  {
                      min = score;
                      nodeToExpand = node;
                  }
              }
           return aSearch(nodeToExpand);
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
