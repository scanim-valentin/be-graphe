package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label{
	
	private Node currentNode = null; 
	private boolean minKnown = false;
	private double cost = 1.0/0.0;
	private Arc parent = null;
	
    public Label(Node current) {
    	this.currentNode = current;
    }
    
    public Label(Node current, double initCost) {
    	this.currentNode = current;
    	this.cost = initCost;
    }
    
    public Label(Node current, double initCost, Arc initParent) {
    	this.currentNode = current;
    	this.cost = initCost;
    	this.parent = initParent;
    }
    /*
     * Is true if this label's current node has a lower or equal cost than the other's
     */
    public boolean compareTo(Label other) {
    	return this.cost <= other.getCost();
    }
    
    /*
     * Return the node which this label is attached to 
     */
    public Node getCurrentNode() {
    	return this.currentNode;
    }
    
    /*
     * Update the minKnown flag to true which indicates the minimum cost to currentNode has been found
     */
    public void setMinKnown() {
    	this.minKnown = true;
    }
    
    /*
     * Returns the value of the minKnown flag
     */
    public boolean isMinKnown() {
    	return this.minKnown;
    }
    
    /*
     * Update the cost to newCost (should only be updated to a lower value)
     */
    public void updateCost(double newCost) {
    	this.cost = newCost;
    }
    
    /*
     * Return to cost of currentNode
     */
    public double getCost() {
    	return this.cost;

    }
    
    /*
     * Update the currentNode's parent with the arc that leads to it
     */
    public void updateParent(Arc Parent) {
    	this.parent = Parent; 
    }
    
    /*
     * Returns parent 
     */
    public Arc getParent() {
    	return this.parent;
    }
    
    

}