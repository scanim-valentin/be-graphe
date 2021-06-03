package org.insa.graphs.algorithm.utils;

import org.insa.graphs.model.Node;

public class LabelHeapMin extends Label{
	
	 public LabelHeapMin(Node current) {
	    	super(current);
	 }
	    
	 public LabelHeapMin(Node current, double initCost) {
	    super(current,initCost);
	 }

	 @Override
	 public int compareTo(Label other) {
	    	//Using Double.compare to deal with particular values such as -0.0, +0.0 or NaN
	    	return -Double.compare(getTotalCost(), other.getTotalCost());
	 }

}
