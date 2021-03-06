package org.insa.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     */
    public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        // TODO:
        if(nodes.size() == 0) {
            return new Path(graph); 
        }
        if(nodes.size() == 1) {
            return new Path(graph, nodes.get(0)); 
        }
        List<Arc> arcs = new ArrayList<Arc>();
        // TODO:
        List<Arc> successors_ok = new ArrayList<Arc>(); //List of successors leading to the next node
        Arc fastest_arc;
        for(int i = 0; i < nodes.size()-1; i++) { //We do not care about the last node for it has no successor.
        	successors_ok.clear();
        	//Listing successors leading to the next node
        	for(int j = 0; j<nodes.get(i).getNumberOfSuccessors();j++) {
        		if( nodes.get(i).getSuccessors().get(j).getDestination() == nodes.get(i+1) ) {
        			successors_ok.add(nodes.get(i).getSuccessors().get(j));
        		}
        	}
        	//Failure to retrieve successors leading to the next node
        	if(successors_ok.isEmpty()) {
        		throw new IllegalArgumentException("Node without successor");
        	}
        	//Finding the fastest arc leading to the next node
        	fastest_arc = successors_ok.get(0);
        	for(int j = 0; j<successors_ok.size(); j++) {
        		if(successors_ok.get(j).getMinimumTravelTime() < fastest_arc.getMinimumTravelTime()) {
        			fastest_arc = successors_ok.get(j);
        		}
        	}
        	//Adding fastest arc to the arcs list
        	arcs.add(fastest_arc);
        }
        return new Path(graph, arcs);
    }

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
    	if(nodes.size() == 0) {
            return new Path(graph); 
        }
        if(nodes.size() == 1) {
            return new Path(graph, nodes.get(0)); 
        }
        List<Arc> arcs = new ArrayList<Arc>();
        // TODO:
        List<Arc> successors_ok = new ArrayList<Arc>(); //List of successors leading to the next node
        Arc shortest_arc;
        for(int i = 0; i < nodes.size()-1; i++) { //We do not care about the last node for it has no successor.
        	successors_ok.clear();
        	//Listing successors leading to the next node
        	for(int j = 0; j<nodes.get(i).getNumberOfSuccessors();j++) {
        		if( nodes.get(i).getSuccessors().get(j).getDestination() == nodes.get(i+1) ) {
        			successors_ok.add(nodes.get(i).getSuccessors().get(j));
        		}
        	}
        	//Failure to retrieve successors leading to the next node
        	if(successors_ok.isEmpty()) {
        		throw new IllegalArgumentException("Node without successor");
        	}
        	//Finding the shortest arc leading to the next node
        	shortest_arc = successors_ok.get(0);
        	for(int j = 0; j<successors_ok.size(); j++) {
        		if(successors_ok.get(j).getLength() < shortest_arc.getLength()) {
        			shortest_arc = successors_ok.get(j);
        		}
        	}
        	//Adding shortest arc to the arcs list
        	arcs.add(shortest_arc);
        }
        return new Path(graph, arcs);
    }
    
    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * 
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid.
     * 
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     */
    public boolean isValid() {
        // TODO:
    	boolean R = this.isEmpty() || this.size() == 1 || ( ( this.origin == this.arcs.get(0).getOrigin() ) && ( this.arcs.get(0).getDestination() == this.arcs.get(1).getOrigin() ) && (this.arcs.get(1).getDestination() == this.arcs.get(2).getOrigin()  ) );
    	 
        return R;
    }

    /**
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     */
    public float getLength() {
        // TODO:
    	float R = 0;
    	for (Arc arc: this.arcs) {
    	       //System.out.println(arc);
    	       R +=arc.getLength();
    	}
    	//System.out.println(R);
        return R;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * 
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).
     */
    public double getTravelTime(double speed) {
        // TODO:
    	double R = 0;
    	for (Arc arc: this.arcs) {
    	       //System.out.println(arc);
    	       R +=arc.getTravelTime(speed);
    	}
    	//System.out.println(R);
        return R;
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     */
    public double getMinimumTravelTime() {
        // TODO:
    	double R = 0;
    	for (Arc arc: this.arcs) {
    	       //System.out.println(arc);
    	       R +=arc.getMinimumTravelTime();
    	}
        return R;
    }
    
    /**
     * Checks if this path and another path extracted from the same graph are the same
     * NOTE : it is required to directly compare origin and destination Nodes for each Arc
     * because they directly reference to the Graph unlike the Arc lists built during the execution
     * of the algorithms 
     * @param other Another path
     * @return True if they are equal or False if they are not
     */
    /*
    @Override
    public boolean equals(Object other) {
    	boolean R = false;
    	Path other_path = (Path) other;
    	if(other_path != null && (other_path.size() == this.size()) ) {
    		
    		R = true;
    		int i = 0;
    		List<Arc> other_arcs = other_path.getArcs();
    		
    		while( R == true && (i < this.getArcs().size()) ) {
    			System.out.print("size = "+this.size()+" i = "+i+"\n");
    			R = ( other_arcs.get(i).getOrigin().getId() == this.arcs.get(i).getOrigin().getId() ) && ( other_arcs.get(i).getDestination().getId() == this.arcs.get(i).getDestination().getId() );
    			i++;

    		}
    		System.out.print("R = "+R+"\n");
    	}
    	return R;
    }*/
    

}
