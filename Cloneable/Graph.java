package homeworkSixB;

import java.util.*;

public class Graph {
    /**
     * MAX VALUE OF INTEGER
     */
    public static final int INFINITY = Integer.MAX_VALUE;

    /**
     * Maps vertices to internal Vertex
     */
    private HashMap vertexMap = new HashMap( );

    /**
     * Constructor for the Graph class
     */
    public Graph(){}


    /**
     * Add Edge between a source token and a destination token if dependency exists
     * using a Vertices
     *
     * @param sourceName
     * @param destName
     */
    public void addEdge( Token sourceName, Token destName ) {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( w );
    }

    /**
     * Add Edge between a source token and a destination token if dependency exists
     * @param sourceName
     * @param destName
     * @param theCellArray
     */
    public void addEdge( CellToken sourceName, CellToken destName, Cell[][] theCellArray ) {
        Cell sourceNameCell = theCellArray[sourceName.getRow()-1][sourceName.getColumn()];
        Cell destNameCell = theCellArray[destName.getRow()-1][destName.getColumn()];
        sourceNameCell.feedInto.add(destNameCell);
        destNameCell.dependsOn.add(sourceNameCell);
        destNameCell.dist = destNameCell.dependsOn.size();
        //System.out.println("Source name: " + sourceNameCell.getFormula());
        //System.out.println("Destination name " + destNameCell.getFormula());
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     *
     * @param destName
     * @return
     */
    private Vertex getVertex( Token destName ) {
        Vertex v = (Vertex) vertexMap.get( destName );
        if( v == null )
        {
            v = new Vertex( destName );
            vertexMap.put( destName, v );
        }
        return v;
    }

    /**
     * Prints path of vertex
     * @param dest
     */
    private void printPath( Vertex dest ) {
        if( dest.path != null )
        {
            printPath( dest.path );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }


    /**
     * Top sorts cell by cell that way we know which cell we can sort out w/o any other cell
     * @param theSpreadsheet
     */
    public void topSort(Spreadsheet theSpreadsheet) {
        Cell[][] theArr = theSpreadsheet.getSpreadsheetArray();
        int counter = 0;
        int secondCounter = 0;
        Queue solveTheseFirst = new LinkedList();
        for (int i = 0; i < theSpreadsheet.getNumRows(); i++) {
            for (int j = 0; j < theSpreadsheet.getNumColumns(); j++) {
                if (theArr[i][j].dist == 0) {
                    solveTheseFirst.add(theArr[i][j]);
                    secondCounter++;
                }
            }
        }
        //int queueSize = 0;
        while(!solveTheseFirst.isEmpty()){
            Cell current= (Cell) solveTheseFirst.remove();
            counter++;
            solve(current,theSpreadsheet);

            for (Cell c: current.feedInto) {
                if (--c.dist == 0) {
                    solveTheseFirst.add(c);
                    secondCounter++;
                }
            }
        }
        if (counter != secondCounter) {
            throw new RuntimeException("CycleFound");
        }
    }

    /**
     * Solves the cell and gets the cell value
     *
     * @param cell
     * @param theSpreadsheet
     */
    public void solve(Cell cell,Spreadsheet theSpreadsheet) {
        cell.thisCell.getValue();
    }

}