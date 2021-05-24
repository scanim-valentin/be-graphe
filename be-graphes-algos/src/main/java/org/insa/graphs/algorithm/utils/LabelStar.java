package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.Node;

public class LabelStar extends Label {
	
	private double Heuristic;
	
	public LabelStar(Node current, Node destination) {
    	super(current);
    	this.Heuristic = destination.getPoint().distanceTo(current.getPoint());
    }
	
	public LabelStar(Node current, Node destination, double Speed) {
    	super(current);
    	// t = d / v
    	this.Heuristic = destination.getPoint().distanceTo(current.getPoint()) / Speed;
    	
    }
	
    
    /**	
     * @return double The distance to the destination from current node 
     */
    public double getHeuristic() {
    	return this.Heuristic;
    }    
    
	@Override
	public double getTotalCost() {
		return this.Heuristic+this.cost;
	}
}
