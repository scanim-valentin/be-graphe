package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

import com.sun.tools.javac.util.List;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        
        //Retrieving the graph
        Graph graph = data.getGraph();
        //Initialization
        ArrayList<Label> LabelList = new ArrayList<Label>();
    	for (int i = 0 ; i < graph.size() ; i++) {
        	//Associating each node to a Label
    		LabelList.add(new Label(graph.get(i)));//Default Label Init : minKnown = false, cost = inf. , father = null		
    	}
    	//Initiating start node
    	Node start = data.getOrigin();
    	LabelList.get(start.getId()).updateCost(0);
    	//Inserting the start node into the tree
    	BinaryHeap<Node> heap = new BinaryHeap<Node>();
    	heap.insert(start);
    	
    	//Iterations
    	boolean allMarked = false; 
    	while(!allMarked) {
    		
    		Node min = heap.findMin();
    		
    	
        }
        
        
        
        return solution;
    }

}
