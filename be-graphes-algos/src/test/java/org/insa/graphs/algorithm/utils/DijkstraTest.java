package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;

public final class DijkstraTest extends ShortestPathAlgorithmTest{
	
	protected ShortestPathSolution generateSolution(ShortestPathData data_input) {
		
		DijkstraAlgorithm algo = new DijkstraAlgorithm(data_input);
		return algo.run();
	}
    
}