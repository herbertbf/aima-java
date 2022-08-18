import aima.core.search.api.Node;
import aima.core.search.api.NodeFactory;
import aima.core.search.api.Problem;
import aima.core.search.api.SearchForActionsFunction;
import aima.core.search.basic.SearchUtils;
import aima.core.search.basic.support.BasicNodeFactory;
import aima.core.search.basic.uninformedsearch.GenericSearchInterface;

import java.util.*;
import java.util.function.ToDoubleFunction;

public class MyAStarSearch <A, S> implements GenericSearchInterface<A, S>, SearchForActionsFunction<A, S> {

    // The heuristic function
    protected ToDoubleFunction<Node<A, S>> h;
    // A helper class to generate new nodes.
    protected NodeFactory<A, S> nodeFactory = new BasicNodeFactory<>();

    // frontier ← a priority queue ordered by f(n) = h(n)+g(n), with a node for the initial state
    PriorityQueue<Node<A, S>> frontier = new PriorityQueue<>(new Comparator<Node<A, S>>() {
        @Override
        public int compare(Node<A, S> o1, Node<A, S> o2) {
            return (int) (getCostValue(o1) - getCostValue(o2));
        }
    });


    /**
     * The constructor that takes in the heuristics function.
     *
     * @param h
     */
    public MyAStarSearch(ToDoubleFunction<Node<A, S>> h) {
        this.h = h;
    }

    @Override
    public Node<A,S> search(Problem<A, S> problem) {
        if (problem.isGoalState(problem.initialState())) {
            return nodeFactory.newRootNode(problem.initialState());
        }
        frontier.clear();
        frontier.add(nodeFactory.newRootNode(problem.initialState()));
        // reached ← a table of {state: the best path that reached state}; initially empty
        HashMap<S, Node<A, S>> reached = new HashMap<>();
        Node<A, S> solution = null;
        int count = 0;
        // while frontier is not empty and top(frontier) is cheaper than solution do
        while (!frontier.isEmpty() &&
                (solution == null || getCostValue(frontier.peek()) < getCostValue(solution))) {
            count += 1;
            Node<A, S> parent = frontier.poll();
            for (Node<A, S> child :
                    SearchUtils.successors(problem, parent)) {
                S s = child.state();
                // if s is not in reached or child is a cheaper path than reached[s] then
                if (!reached.containsKey(s) ||
                        getCostValue(child) < getCostValue(reached.get(s))) {
                    reached.put(s, child);
                    frontier.add(child);
                    // if child is a goal and is cheaper than solution
                    if (problem.isGoalState(s) &&
                            (solution == null || getCostValue(child) < getCostValue(solution))) {
                        solution = child;
                    }
                }
            }
        }
        System.out.println(count);
        return solution;
    }

    /**
     * Returns the list of actions that need to be taken in order to achieve the goal.
     *
     * @param problem The search problem
     * @return the list of actions
     */
    @Override
    public List<A> apply(Problem<A, S> problem) {
        Node<A, S> solution = this.search(problem);
        if (solution == null)
            return new ArrayList<>();
        else
            return SearchUtils.generateActions(solution);
    }

    /**
     * Finds the value of f(n) = g(n)+h(n) for a node n.
     *
     * @param node The node n
     * @return f(n)
     */
    private double getCostValue(Node<A, S> node) {
        return node.pathCost() + h.applyAsDouble(node);
    }
}
