package homeworkSixB;

import java.util.*;
import java.io.*;

/**
 * 
 *
 * Code used from:
 * https://users.cs.fiu.edu/~weiss/dsaajava/Code/Miscellaneous/Graph.java
 *
 */
public class Graph {
	private Stack myStack;
	
	public Graph() {
		
	}
	
	public Graph(Stack theStack) {
		this.myStack = theStack;
	}
	
	public void addEdge( CellToken sourceName, CellToken destName, Cell[][] theCellArray ) {
        Cell sourceNameCell = theCellArray[sourceName.getRow()][sourceName.getColumn()];
        Cell destNameCell = theCellArray[destName.getRow()][destName.getColumn()];
        sourceNameCell.feedInto.add(destNameCell);
        destNameCell.dependsOn.add(sourceNameCell);
        destNameCell.dist = destNameCell.dependsOn.size();
        System.out.println("Source name: " + sourceNameCell);
        System.out.println("Destination name " + destNameCell);
    }
	
	//top sorts cell by cell that way we know which cell we can sort out w/o any other cell
	public void topSort(Spreadsheet theSpreadsheet) {
        Cell[][] theArr = theSpreadsheet.getSpreadsheetArray();
        int counter = 0;
        Queue solveTheseFirst = new LinkedList();
        for (int i = 0; i < theSpreadsheet.getNumRows(); i++) {
            for (int j = 0; j < theSpreadsheet.getNumColumns(); j++) {
                if (theArr[i][j].dist == 0) {
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
                if (counter != theSpreadsheet.getNumColumns() * theSpreadsheet.getNumRows()) {
                	throw new RuntimeException("CycleFound");
                }
            }
        }
    }

	public void solve(Cell cell) {
		cell.getValue();
    }
	
	public static void main(String[] args) {
		Spreadsheet spreadsheet = new Spreadsheet(10);
		Object[][] arr = spreadsheet.getSpreadsheetArray();
        Graph newGraph = new Graph();
        CellToken paul = new CellToken();
        Cell[][] paul2 = new Cell[5][5];
        Cell paulCell = new Cell();
        paul2[1][1]=paulCell;
        CellToken pablo = new CellToken();
        Cell pabloCell = new Cell();
        paul2[0][0]=pabloCell;
        
        arr[1][1] = paulCell;
        arr[5][5] = paul2;
        arr[0][0] = pabloCell;
//        paul.setColumn(1);
//        paul.setRow(1);
//        pablo.setColumn(0);
//        pablo.setRow(0);
        //Vertex rest= new Vertex(paul);
        //newGraph.vertexMap.put(1,rest);
        newGraph.addEdge(paul,pablo,paul2);
        newGraph.addEdge(paul,pablo,paul2);
        newGraph.topSort(spreadsheet);
    }
}
