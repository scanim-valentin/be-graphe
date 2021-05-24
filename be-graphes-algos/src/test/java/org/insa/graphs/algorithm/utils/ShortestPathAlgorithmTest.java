package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;

import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public abstract class ShortestPathAlgorithmTest {
	  	@SuppressWarnings("unused")
	    // Path to the .mapgr file used for testing
	    private static String map_carre,map_insa,map_toulouse;   
	    
	    @SuppressWarnings("unused")
	    //Readers to extract the graph from the files
	    private static GraphReader reader_carre, reader_insa, reader_toulouse;
	    
	    @SuppressWarnings("unused")
	    //Graphs extracted from the maps
	    private static Graph graph_carre, graph_insa, graph_toulouse;
	    
	    //Data to be fed to the algorithms
	    private static ShortestPathData data;
	    
	        
	    @BeforeClass
	    public static void initAll() throws IOException {

	    	 map_carre = "/home/work/Repositories/be-graphe/maps/carre.mapgr";
	    	 map_insa = "/home/work/Repositories/be-graphe/maps/insa.mapgr";
	    	 map_toulouse = "/home/work/Repositories/be-graphe/maps/toulouse.mapgr";
	    	 
	    	 //Extracting the graph from carre.mapgr
	    	 
	    	reader_carre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map_carre)))); 
	    	reader_insa = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map_insa)))); 
	    	
	    	graph_carre = reader_carre.read(); 	 
	    	graph_insa = reader_insa.read();  	 
	    	 
	    }
	    
	    private static int getRandomDoubleBetweenRange(int min, int max){
	        int x = (int) (((Math.random()*((max-min)+1))+min));
	        //System.out.print("min = "+min+" max = "+max+" random = "+x+"\n");
	        return x;
	    }
	     
	    @Test
	    public void test_no_filter_length() {
	    	System.out.print("test_no_filter_length()\n");
	    	test(graph_carre,0);
	    	test(graph_insa,0);
	    }
	    
	    @Test
	    public void test_car_time() {
	    	System.out.print("test_car_time()\n");
	    	test(graph_carre,1);
	    	test(graph_insa,1);
	    	
	    }
	    
	    @Test
	    public void test_no_filter_time() {
	    	System.out.print("test_no_filter_time()\n");
	    	test(graph_carre,2);
	    	test(graph_insa,2);
	    	
	    }
	        
	    @Test
	    public void test_car_length() {
	    	System.out.print("test_car_length()\n");
	    	test(graph_carre,3);
	    	test(graph_insa,3);
	    	
	    }
	    
	    @Test
	    public void test_pedestrian_bike_time() {
	       	System.out.print("test_pedestrian_bike_time()\n");
	        test(graph_carre,4);
	        test(graph_insa,4);
	    	
	    }
	    
	    protected abstract ShortestPathSolution generateSolution(ShortestPathData data_input);
	    
	    private void test(Graph graph, int Mode){  	
	    	
		    /*
		   	  * All the arc inspecting modes have to be tested : 
		   	  * 0 = no filter length, 1 = cars and length
		   	  * 2 = no filter time , 3 = cars and time , 4 = pedestrian and bicycle (non-private roads) time
		   	  */
		   	 int nb_nodes = graph.size()-1 ; //Max range for picking random nodes : [0 ; size-1]

			 for(int i = 0 ; i < 50 ; i++) {
			    		 
			    		 //Start and Destination nodes IDs randomly generated using Math.random 
			    		 int start_id = getRandomDoubleBetweenRange(0,nb_nodes);
			    		 int destination_id = getRandomDoubleBetweenRange(0,nb_nodes);
			    		 
			    		 //Extracting start and destination nodes from the graph
			    		 Node start_node = graph.getNodes().get(start_id);
			    		 Node destination_node = graph.getNodes().get(destination_id);
			    		 
			    		 //Selecting the arc filtering mode (test parameter)
			    		 ArcInspector filter = ArcInspectorFactory.getAllFilters().get(Mode);
			    		 
			    		 data = new ShortestPathData(graph,start_node,destination_node,filter); 


			    		//Running and saving the solution generated by the different algorithms

			    	    ShortestPathSolution solution_test = generateSolution(data);
			    	    	
			    	    BellmanFordAlgorithm algo_BF = new BellmanFordAlgorithm(data);
			    	    ShortestPathSolution solution_BF = algo_BF.run();
			    	    	
			    	    //Comparing the tested algorithm to BF (the reference)
			    	    //System.out.print("\nsolution_dijkstra = "+solution_dijkstra);
			    	    //System.out.print("\nsolution_BF = "+solution_BF+"\n");
			    	    
			    	        	    
			    	    assertEquals( solution_BF.isFeasible() , solution_test.isFeasible() );
			    	    if(solution_BF.isFeasible()) {
			    	    	Path path_BF = solution_BF.getPath();
			    	    	Path path_dijkstra = solution_test.getPath();
			    	    	//System.out.print("\npath_BF = "+path_BF);
			    	    	if(Mode <= 1)
			    	    		assertTrue( path_BF.getLength() == path_dijkstra.getLength() );
			    	    	else
			    	    		assertTrue( path_BF.getMinimumTravelTime() == path_dijkstra.getMinimumTravelTime() );
			    	    }
			    	 }
			    	 
		   	 }
	    	
	}

