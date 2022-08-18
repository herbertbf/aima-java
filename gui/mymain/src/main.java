import aima.core.environment.map2d.*;
import aima.core.environment.support.ProblemFactory;
import aima.core.search.api.Node;
import aima.core.search.api.Problem;
import aima.core.search.basic.informed.AStarSearch;

import java.util.Collections;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class main {
    public static void main(String[] args) {

        // LABORATORIO 1
//        Problem problem = ProblemFactory.getSimplifiedRoadMapOfPartOfRomaniaProblem(
//                SimplifiedRoadMapOfPartOfRomania.ARAD, SimplifiedRoadMapOfPartOfRomania.DOBRETA);
//
//        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
//        List list = breadthFirstSearch.apply(problem);
//        System.out.println(list);


        // LABORATORIO 2

        Map2D map = new SimplifiedRoadMapOfPartOfRomania();
        String initialLocation = SimplifiedRoadMapOfPartOfRomania.ARAD;
        String goal = SimplifiedRoadMapOfPartOfRomania.BUCHAREST;
        Problem<GoAction, InState> problem = ProblemFactory.getSimplifiedRoadMapOfPartOfRomaniaProblem(initialLocation,
                goal);
        MyAStarSearch aStarSearch = new MyAStarSearch(new Map2DFunctionFactory.StraightLineDistanceHeuristic(map, goal));
        Node result =  aStarSearch.search(problem);
        double coast = result.pathCost();
        System.out.println(coast);
    }
}