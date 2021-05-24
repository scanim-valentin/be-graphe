package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.utils.LabelStar;
import org.insa.graphs.algorithm.utils.Label;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected Label[] initLabels(ShortestPathData data) {
    	LabelStar[] LabelList = new LabelStar[data.getGraph().size()];
    	for (int i = 0 ; i < data.getGraph().size() ; i++) {
        	//Associating each node to a Label
    		if(data.getMode().equals(AbstractInputData.Mode.LENGTH))
    			LabelList[i] = new LabelStar(data.getGraph().get(i), data.getDestination()); //Default Label Init : minKnown = false, cost = inf. , father = null		
    		else {
    			LabelList[i] = new LabelStar(data.getGraph().get(i), data.getDestination(), data.getGraph().getGraphInformation().getMaximumSpeed());
    			//System.out.print("speed = "+graph.getGraphInformation().getMaximumSpeed()+"\n");
    		}
    	}
    	return LabelList;
    }
            
   
}
