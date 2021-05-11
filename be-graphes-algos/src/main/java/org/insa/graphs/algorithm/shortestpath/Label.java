package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label{
	
	private Node currentNode = null; 
	private boolean minKnown = false;
	private double cost = 0;
	private Arc parent = null;
	
    public Label(Node current) {
    	this.currentNode = current;
    }
    
    public boolean compareTo(Label other) {
    	return this.cost > other.getCost();
    }
    
    public Node getCurrentNode() {
    	return currentNode;
    }
    
    public void setMinKnown() {
    	this.minKnown = true;
    }
    
    public boolean isMinKnown() {
    	return minKnown;
    }
    
    public void updateCost(double newCost) {
    	this.cost = newCost;
    }
    
    public double getCost() {
    	return cost;
    }
    
    private void updateParent(Arc Parent) {
    	this.parent = Parent; 
    }
    
    public Arc getParent() {
    	return parent;
    }
    
    

}