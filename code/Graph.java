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
        v.path=w;
        w.edgeAdj.add(new Edge(v,w.scratch+1));
        System.out.println(w.edgeAdj.get(0).cost);
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
    public Queue topRunner(){
        Queue queue = new LinkedList();
        Collection<Vertex> vertexSet = vertexMap.values();
        return topologicalSort(queue,vertexSet);
    }
    public Queue topologicalSort(Queue queue, Collection<Vertex> vertexSet) {
            for (Vertex v : vertexSet) {
                for (Edge e : v.edgeAdj) {
                    e.dest.scratch++;
                }
            }
                for( Vertex v : vertexSet ) {
                    if( v.scratch == 0 )
                    queue.add( v );
                }
        int iterations;
                for( iterations = 0; !queue.isEmpty( ); iterations++ )
             {
             Vertex v = (Vertex) queue.remove( );

             for( Edge e : v.edgeAdj )
                 {
                     Vertex w = e.dest;
                      double cvw = e.cost;

                      if( --w.scratch == 0 )
                      queue.add( w );

                      if( v.dist == INFINITY )
                      continue;

                      if( w.dist > v.dist + cvw )
                      {
                      w.dist = (int) (v.dist + cvw);
                      w.path = v;
                      }
                      }
                  }
        System.out.println(iterations);
        System.out.println(vertexMap.size());
         if( iterations != vertexMap.size( ) ){
             throw new RuntimeException( "Graph has a cycle!" );

                 }


        return queue;
    }

    public static void main(String[] args) {
        Graph newGraph = new Graph();
        CellToken paul = new CellToken();
        CellToken paul2 = new CellToken();
        CellToken pablo = new CellToken();
        Vertex circle = new Vertex(pablo);
        newGraph.vertexMap.put(0,circle);


            Vertex rest= new Vertex(paul);
            newGraph.vertexMap.put(1,rest);
            newGraph.addEdge(paul,pablo);


        System.out.println(newGraph.topRunner());

    }
}
