package homeworkSixB;

import java.util.*;
import java.io.*;

public class Graph {
	public static final int INFINITY = Integer.MAX_VALUE;
    private HashMap vertexMap = new HashMap( );    // Maps vertices to internal Vertex

    public void addEdge( Token sourceName, Token destName ) {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( w );
    }

    public void printPath( String destName ) throws NoSuchElementException {
        Vertex w = (Vertex) vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            printPath( w );
            System.out.println( );
        }
    }

      // If vertexName is not present, add it to vertexMap.
      // In either case, return the Vertex.
    private Vertex getVertex( Token destName ) {
        Vertex v = (Vertex) vertexMap.get( destName );
        if( v == null )
        {
            v = new Vertex( destName );
            vertexMap.put( destName, v );
        }
        return v;
    }

    private void printPath( Vertex dest ) {
        if( dest.path != null )
        {
            printPath( dest.path );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }

    private void clearAll( ) {
        for( Iterator itr = vertexMap.values( ).iterator( ); itr.hasNext( ); )
            ( (Vertex)itr.next( ) ).reset( );
    }

    public void unweighted( String startName ) throws NoSuchElementException {
        clearAll( ); 

        Vertex start = (Vertex) vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        LinkedList q = new LinkedList( );
        q.addLast( start ); start.dist = 0;

        while( !q.isEmpty( ) ) {
            Vertex v = (Vertex) q.removeFirst( );

            for( Iterator itr = v.adj.iterator( ); itr.hasNext( ); ) {
                Vertex w = (Vertex) itr.next( );
                if( w.dist == INFINITY ) {
                    w.dist = v.dist + 1;
                    w.path = v;
                    q.addLast( w );
                }
            }
        }
    }
    
    public void topologicalSort() {
    	Stack stack = new Stack();
    	int counter = 0;
    	
    	Collection<Vertex> vertexSet = vertexMap.values();
    	for (Vertex v: vertexSet) {
    		for (Edge e: v.adj) {
    			
    		}
    	}
    }
}
