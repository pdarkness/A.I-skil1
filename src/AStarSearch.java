import java.util.*;

public class AStarSearch implements MoveCalculator {
    LinkedList<String> successMoves;
    private HashSet<State> marked;

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
            public int length() {
                Node it = this;
                int count = 0;
                while(it != null)
                {
                    count++;
                    it=it.parent;
                }
                return count;
            }
       }

       public AStarSearch(State startingState)
       {
           marked = new HashSet<State>();
            successMoves = new LinkedList<String>();
            Node firstNode = new Node(startingState,"TURN_ON",null);
            aSearch(firstNode);
            successMoves.add("TURN_OFF");

       }

       private Node aSearch(Node current)
       {
          System.out.println(current.move + " " + current.state.getCurrentLocation().getX() + " " + current.state.getCurrentLocation().getY());
          successMoves.add(current.move);
          int score = h(current);
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
                  int nodeScore = h(node);
                  if( nodeScore < min)
                  {
                      min = nodeScore;
                      nodeToExpand = node;
                  }
              }
           return aSearch(nodeToExpand);
       }

       private int h(Node s)
       {
           if(!s.state.getDirtLocations().isEmpty() )
           {
               Location dirt;
               dirt = s.state.getDirtLocations().get(0);
               Node target = bfs(s,dirt);
               //int difX = Math.abs(s.getCurrentLocation().getX()-dirt.getX());
               //int difY = Math.abs(s.getCurrentLocation().getY()-dirt.getY());
               //int manhattan = difX+difY+1;
               int manhattan = 2+target.length();
               marked = new HashSet<State>();
               return manhattan;
           }
           else if(!s.state.getCurrentLocation().equals(s.state.getStartingPoint()))
           {
                Location home = s.state.getStartingPoint();
                Node target = bfs(s,home);
                int manhattan = 1+target.length();
               marked = new HashSet<State>();
                return manhattan;
           }
           return 0;
       }
    private Node bfs(Node current,Location location)
    {
        Queue<Node> q = new LinkedList<Node>();
        q.add(current);
        marked.add(current.state);
        while(!q.isEmpty())
        {
            Node t = q.poll();
            if( t.state.getCurrentLocation().equals(location))
                return t;
            List<Node> adjacentNodes = new ArrayList<Node>();
            for(String move : t.state.legalMoves())
            {
                Node newNode = new Node(t.state.ResultingState(move),move,t);
                adjacentNodes.add(newNode);
            }
            for(Node e : adjacentNodes)
            {
                Node u = e;
                if( !marked.contains(u.state) )
                {
                    marked.add(u.state);
                    q.add(u);
                }
            }
        }
        current.state.debug();
        System.out.println("punktur:" + location.getX() + " " + location.getY() );
        return null;
    }

       public String nextMove()
       {
           return successMoves.poll();
       }
}
