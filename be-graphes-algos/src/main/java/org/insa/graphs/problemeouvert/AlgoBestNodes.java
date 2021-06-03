package org.insa.graphs.problemeouvert;


import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.Label;
import org.insa.graphs.algorithm.utils.LabelHeapMin;
import org.insa.graphs.model.Graph;

public class AlgoBestNodes {
	//Using A* here
	private static double bestCost(Graph graph,LabelHeapMin xLabel, LabelHeapMin yLabel) {
		double R = 1.0/0.0;
		
		ArcInspector filter = ArcInspectorFactory.getAllFilters().get(0); 
		ShortestPathData data = new ShortestPathData(graph,xLabel.getCurrentNode(),yLabel.getCurrentNode(),filter);
		AStarAlgorithm algoPCC = new AStarAlgorithm(data);
	    ShortestPathSolution solution = algoPCC.run();
	    if(solution.isFeasible())
	    	R = solution.getPath().getMinimumTravelTime();

	    return R; 
	}
	
	private static BinaryHeap<Label> remplissageTas(Graph graph){
		
		LabelHeapMin[] LabelList = new LabelHeapMin[graph.size()];
		
		for (int i = 0 ; i < graph.size() ; i++) {
        	//Associating each node to a Label
    		LabelList[i] = new LabelHeapMin(graph.get(i)); //Default Label Init : minKnown = false, cost = inf. , father = null		 	
    	}
		BinaryHeap<Label> tasFinal = new BinaryHeap<Label>();
		
		for(LabelHeapMin xLabel : LabelList) {
			LabelHeapMin[] auxLabelList = new LabelHeapMin[graph.size()-1];
			
			for (int i = 0 ; i < graph.size()-1 ; i++) {
	        	//Associating each node to a Label
				if(i != xLabel.getCurrentNode().getId())
					auxLabelList[i] = new LabelHeapMin(graph.get(i)); //Default Label Init : minKnown = false, cost = inf. , father = null		 	
	    	}
			
			BinaryHeap<Label> auxTas = new BinaryHeap<Label>();
			
			for(LabelHeapMin yLabel : auxLabelList) {
				if(yLabel != null) {
					double cost = bestCost(graph,xLabel,yLabel);
					//System.out.println(cost);
					if(Double.isFinite(cost)) {
						yLabel.updateCost(cost);
						auxTas.insert(yLabel);
					}
				}
			}
			//System.out.println(auxTas+"\n");
			if(!auxTas.isEmpty())
				tasFinal.insert(auxTas.deleteMin());
		}
		
		return tasFinal;
		
	}
	
	public static BinaryHeap<Label> doRun(Graph graph, int k){
		BinaryHeap<Label> tasFinal = remplissageTas(graph);
		int i = 0;
		while(!tasFinal.isEmpty() && (i < (graph.size()-k) ) ){
			tasFinal.deleteMin();
			i++;
		}
		return tasFinal;
	}
}
