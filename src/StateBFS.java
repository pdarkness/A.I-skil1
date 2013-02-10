import java.util.*;

public class StateBFS {
    private HashSet<State> marked;    // marked[v] = is there an s-v path?
    Stack<String> successMoves;
    List<Node> branches = new ArrayList<Node>();

    private class Node
    {
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

    public StateBFS(State startingState)
    {
        marked = new HashSet<State>();
        Node root = new Node(startingState,"TURN_ON",null);
        successMoves = new Stack<String>();
        Node successNode = bfs(root);
        Node it = successNode;
        successMoves.push("TURN_OFF");
        while(it != null)
            successMoves.push(it.move);
    }

    private Node bfs(Node current)
    {
        Queue<Node> q = new LinkedList<Node>();
        q.add(current);
        marked.add(current.state);
        while(!q.isEmpty())
        {
            Node t = q.poll();
            if( t.state.successor() )
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
        return null;
    }

    public String  nextMove()
    {
        return successMoves.pop();
    }

}
