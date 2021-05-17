package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

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
    	while( destinationLabel.isMinKnown() ) {
    		Label xLabel = LabelHeap.findMin();
    		xLabel.setMinKnown();
    		notifyNodeMarked(xLabel.getCurrentNode());
    		for(Arc yArc : xLabel.getCurrentNode().getSuccessors()) {
    			Node y = yArc.getDestination();
    			Label yLabel = LabelList[y.getId()];
    			yLabel.updateParent(yArc);
    			double W = data.getCost(yArc);
    			if( (!yLabel.isMinKnown()) && (yLabel.getCost() > xLabel.getCost()+W) ) {
    				
    				
    				if( Double.isFinite(yLabel.getCost() ) {
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
        //Assembling the solution
    	ArrayList<Arc> ArcList = new ArrayList<>();
        Arc auxArc = LabelList[data.getDestination().getId()].getParent();
        ArcList.add(auxArc);
        while(auxArc != null) {
        	auxArc = auxArc.
        }
        return solution;
    }

}
