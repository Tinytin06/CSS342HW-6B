package homeworkSixB;

import java.util.*;

public class Graph {
	public static final int INFINITY = Integer.MAX_VALUE;
    private HashMap vertexMap = new HashMap( );    // Maps vertices to internal Vertex

    public void addEdge( CellToken sourceName, CellToken destName, Cell[][] theCellArray ) {
        Cell sourceNameCell=theCellArray[sourceName.getRow()][sourceName.getColumn()];
        Cell destNameCell=theCellArray[destName.getRow()][destName.getColumn()];
        sourceNameCell.feedInto.add(destNameCell);
        destNameCell.dependsOn.add(sourceNameCell);
        destNameCell.dist=destNameCell.dependsOn.size();

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
    //top sorts cell by cell that way we know which cell we can sort out w/o any other cell
        public void topSort(Spreadsheet theSpreadsheet) {
            Cell[][] theArr = theSpreadsheet.getSpreadsheetArray();
            int counter = 0;
            Queue solveTheseFirst = new LinkedList();
            for (int i = 0; i < theSpreadsheet.getNumRows(); i++) {
                for (int j = 0; j < theSpreadsheet.getNumColumns(); j++) {
                    if(theArr[i][j].dist==0){
                    solveTheseFirst.add(theArr[i][j]);
                    }
                }
            }
            while(!solveTheseFirst.isEmpty()){
                Cell current= (Cell) solveTheseFirst.remove();
                solve(current);
                for (Cell c: current.feedInto) {
                    if (--c.dist == 0) {
                        solveTheseFirst.add(c);
                    }
                    if (counter != theSpreadsheet.getNumColumns() * theSpreadsheet.getNumRows())
                    throw new RuntimeException("CycleFound");
                }
            }
        }
        public void solve(Cell cell){

        }


    public static void main(String[] args) {
        Graph newGraph = new Graph();
        CellToken paul = new CellToken();
        Cell[][] paul2 = new Cell[5][5];
        Cell paulCell = new Cell();
        paul2[1][1]=paulCell;
        CellToken pablo = new CellToken();
        Cell pabloCell = new Cell();
        paul2[0][0]=pabloCell;
        paul.setColumn(1);
        paul.setRow(1);
        pablo.setColumn(0);
        pablo.setRow(0);
            Vertex rest= new Vertex(paul);
            newGraph.vertexMap.put(1,rest);
            newGraph.addEdge(paul,pablo,paul2);
            newGraph.addEdge(paul,pablo,paul2);

    }
}
