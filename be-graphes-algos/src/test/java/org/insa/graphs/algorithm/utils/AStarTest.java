package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;

public final class AStarTest extends ShortestPathAlgorithmTest {
	
	protected ShortestPathSolution generateSolution(ShortestPathData data_input) {
			
			AStarAlgorithm algo = new AStarAlgorithm(data_input);
			return algo.run();
		}
}
