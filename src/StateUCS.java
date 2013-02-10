/**
 * Created with IntelliJ IDEA.
 * User: Beggi
 * Date: 10.2.2013
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
import javax.xml.soap.Node;
import java.util.*;
public class StateUCS {
    /**
     * Implementation Uniform Cost Search algorithm
     *
     * Uniform Cost Search pseudocode:
     *
     * <code>
     *    function UNIFORM-COST-SEARCH(problem) returns a solution, or failure
     *        node <- a node with STATE = problem.INITIAL-STATE, PATH-COST = 0
     *        frontier <- a priority queue ordered by PATH-COST, with node as the only element
     *        explored <- an empty set
     *        loop do
     *            if EMPTY?(frontier) then return failure
     *            node <- POP(frontier)
     *            if problem.GOAL-TEST(node.STATE) then return SOLUTION(node)
     *            add node.STATE to explored
     *            for each action in problem.ACTIONS(node.STATE) do
     *                child <- CHILD-NODE(problem, node, action)
     *                if child.STATE is not in explored or frontier then
     *                    frontier <- INSERT(child, frontier)
     *                else if child.STATE is in frontier with higher PATH-COST then
     *                    replace that frontier node with child
     * </code>
     *
     * @author Hoang Dat Kieu
     *
     */

    public class UniformCostSearch {

        // comparator - a path cost comparator
        Comparator<Node> comparator = new PathCostComparator();

        // frontier - queue data structure.
        private PriorityQueue<Node> frontier =
                new PriorityQueue<Node>(Global.PERMUTATIONS, comparator);

        // explored - set data structure
        Set<Object> explored = new HashSet<Object>();

        /**
         * Empty constructor
         */
        public UniformCostSearch() {}

        /**
         * Execute the uniform cost search
         * @param problem
         * @return Node
         */
        public List<Node> execute(Problem problem) {
            Node root = new Node(problem.getInitialState());
            frontier.add(root);
            do {
                Node node = frontier.remove();
                if (problem.isGoalState(node.getState())) {
                    Global.MAXIMUM_LENGTH = frontier.size();
                    return node.getPathFromRoot();
                }
                explored.add(node); Global.EXPANDED_NODES++;
                List<Node> children = ExpandNode.expand(node);
                for (int i = 0; i < children.size(); i++) {
                    Node child = children.get(i);
                    if (!alreadyExplored(child) || !frontier.contains(child)) {
                        frontier.add(child);
                    } else if (frontier.contains(child) &&
                            (comparator.compare(child, node) == 1)) {
                        node = child;
                    }
                }
            } while (!frontier.isEmpty());
            return new ArrayList<Node>();
        }

        /**
         * Check to see if a node is already explored
         * @param node
         * @return boolean
         */
        public boolean alreadyExplored(Node node) {
            return explored.contains(node);
        }

    /* -------------------------------- INNER CLASS -------------------------------- */

        /**
         * Compare the path cost between two nodes
         *
         * @author Hoang Dat Kieu
         *
         */

        private class PathCostComparator implements Comparator<Node> {

            @Override
            public int compare(Node node1, Node node2) {
                // TODO Auto-generated method stub
                if (Integer.valueOf(node1.getPathCost()) < Integer.valueOf(node2.getPathCost())) {
                    return -1;
                }
                if (Integer.valueOf(node1.getPathCost()) > Integer.valueOf(node2.getPathCost())) {
                    return 1;
                }
                return 0;
            }

        }

    /* ----------------------------------------------------------------------------- */

    }
}