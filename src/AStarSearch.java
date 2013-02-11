import java.util.ArrayList;
import java.util.Comparator;
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
            Node firstNode = new Node(startingState,"TURN_ON",null);
            aSearch(firstNode).state.debug();
       }

       private Node aSearch(Node current)
       {
          System.out.println(current.move +" " +  current.state.getCurrentLocation().getX() + " " + current.state
          .getCurrentLocation().getY());
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
           int min = Integer.MAX_VALUE;
           for(Node node : children)
              {
                  int nodeScore = h(node.state);
                  if( nodeScore < min)
                  {
                      min = nodeScore;
                      nodeToExpand = node;
                  }
              }
           return aSearch(nodeToExpand);
       }

       private int h(State s)
       {
           if(!s.getDirtLocations().isEmpty() )
           {
               Location dirt;
               dirt = s.getDirtLocations().get(0);
               int difX = Math.abs(s.getCurrentLocation().getX()-dirt.getX());
               int difY = Math.abs(s.getCurrentLocation().getY()-dirt.getY());
               int manhattan = difX+difY+1;
               return manhattan;
           }
           return 0;
       }

       public String nextMove()
       {
           return "TURN_OFF";
       }
}
