package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.ArrayList;
import org.insa.graphs.algorithm.AbstractSolution.Status;

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
        Label[] LabelList = new Label[graph.size()];
    	for (int i = 0 ; i < graph.size() ; i++) {
        	//Associating each node to a Label
    		LabelList[i] = new Label(graph.get(i)); //Default Label Init : minKnown = false, cost = inf. , father = null		
    	}
    	//Initiating start node
    	Node start = data.getOrigin();
    	LabelList[start.getId()].updateCost(0);
    	//Inserting the start node into the tree
    	BinaryHeap<Label> LabelHeap = new BinaryHeap<Label>();
    	LabelHeap.insert(LabelList[start.getId()]);
    	notifyOriginProcessed(data.getOrigin());
    	//Iterations 
    	Label destinationLabel = LabelList[data.getDestination().getId()];
    	while( !LabelHeap.isEmpty() && !destinationLabel.isMinKnown() ) {
    		//ATTENTION : LabelHeap.isEmpty required to avoid isolated unreachable destination
    		Label xLabel = LabelHeap.findMin();
    		xLabel.setMinKnown();
    		notifyNodeMarked(xLabel.getCurrentNode());
    		for(Arc yArc : xLabel.getCurrentNode().getSuccessors()) {
    			Node y = yArc.getDestination();
    			Label yLabel = LabelList[y.getId()];
    			yLabel.updateParent(yArc);
    			double W = data.getCost(yArc);
    			if( (!yLabel.isMinKnown()) && (yLabel.getCost() > xLabel.getCost()+W) ) {
    				
    				
    				if( Double.isFinite(yLabel.getCost()) ) {
    					//If y cost finite then y has already been inserted in the tree before
    					LabelHeap.remove(yLabel);
    					notifyNodeReached(yLabel.getCurrentNode());
    				}
    				//else : y is infinite and has been reached for the first time
    				yLabel.updateCost(xLabel.getCost()+W);
    				LabelHeap.insert(yLabel);
    			}
    		}
    			
    		
        }
    	System.out.print("LabelList = "+LabelList);
        //Assembling the solution
        Arc auxArc = LabelList[data.getDestination().getId()].getParent();
        ArrayList<Arc> ArcList = null;
        if(auxArc != null) {
	        ArcList = new ArrayList<>();
	        
	        while(auxArc != null) {
	        	ArcList.add(auxArc);
	        	auxArc = LabelList[auxArc.getDestination().getId()].getParent();
	        }
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, ArcList));
        }
        else {
        	 solution = new ShortestPathSolution(data, Status.OPTIMAL);
        }
	    return solution;
    }

}
