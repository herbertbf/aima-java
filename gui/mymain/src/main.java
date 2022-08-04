

import aima.core.environment.map2d.*;
import aima.core.environment.support.ProblemFactory;
import aima.core.search.api.Node;
import aima.core.search.api.Problem;
import aima.core.search.basic.informed.AStarSearch;
import aima.core.search.basic.uninformed.BreadthFirstSearch;

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
        AStarSearch aStarSearch = new AStarSearch(new Map2DFunctionFactory.StraightLineDistanceHeuristic(map, goal));
        List list =  aStarSearch.apply(problem);
        double coast = aStarSearch.search(problem).pathCost();
        System.out.println(list);
        System.out.println(coast);
    }
}
