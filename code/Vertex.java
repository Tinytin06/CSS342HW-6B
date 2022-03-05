package homeworkSixB;

import java.util.*;
import java.io.*;

public class Vertex {
	 Token      name;   // Vertex name
	 List<Vertex> adj;    // Adjacent vertices
	 int        dist;   // Cost
	 Vertex     path;   // Previous vertex on shortest path
	 List<Edge>	edgeAdj; //all edges for adjacent verticies

	 public Vertex( Token destName ) { 
		 name = destName;
		 edgeAdj = new LinkedList<Edge>();
		 adj = new LinkedList<Vertex>( );
		 reset( );
	 }

	public Token getName() {
		return name;
	}

	public void reset( ) {
		 dist = Graph.INFINITY;
		 path = null;
	 }  
}
