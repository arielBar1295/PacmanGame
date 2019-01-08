package Algo;

import java.io.*; 
import java.util.*; 
  


 
/**
 *  This class represents a directed graph using adjacency list 
 * @author moshe and ariel
 *
 */
public class graph {


	    private int V;   // No. of vertices 
	    private LinkedList<Integer> adj[]; //Adjacency Lists 
	  
	    // Constructor 
	    graph(int v) 
	    { 
	        V = v; 
	        adj = new LinkedList[v]; 
	        for (int i=0; i<v; ++i) 
	            adj[i] = new LinkedList(); 
	    } 
	  
	    public LinkedList<Integer>[] getAdj() {
			return adj;
		}

		public void setAdj(LinkedList<Integer>[] adj) {
			this.adj = adj;
		}

		
		/**
		 * Function to add an edge into the graph 
		 * @param v The current vertex.
		 * @param w the adjacency to add.
		 */
	    void addEdge(int v,int w) 
	    { 
	    	if(!adj[v].contains(w)) {
	        adj[v].add(w); 
	    	}
	    } 
	  
}
