package homeworkSixB;

import java.util.*;
import java.io.*;

/**
 * Topological sorting is done in this class. 
 *
 * @version Winter 2022
 */
public class Graph {
	/**
	 * Adds the edges to the celltokens.
	 * @param sourceName the new celltoken
	 * @param destName the formula of the celltoken to be changed. 
	 * @param theCellArray to get the cell of the celltokens.
	 */
	public void addEdge( CellToken sourceName, CellToken destName, Cell[][] theCellArray ) {
        Cell sourceNameCell = theCellArray[sourceName.getRow()][sourceName.getColumn()];
        Cell destNameCell = theCellArray[destName.getRow()][destName.getColumn()];
        if (sourceNameCell.getFormula() != destNameCell.getFormula()) {
        	sourceNameCell.feedInto.add(destNameCell);
        	destNameCell.dependsOn.add(sourceNameCell);
        	destNameCell.dist = destNameCell.dependsOn.size();
        }
        
        if (sourceNameCell.getFormula() == destNameCell.getFormula()) {
        	throw new RuntimeException("CycleFound");
        }
    }
	
	/**
	 * Topological sorts cell by cell so that way we know which 
	 * cell we can sort out.
	 * 
	 * @param theSpreadsheet the spreadsheet of the cells.
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
        
        while (!solveTheseFirst.isEmpty()) {
            Cell current = (Cell) solveTheseFirst.remove();
            counter++;
            getValue(current);
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
	 * Gets the value of the cell.
	 * 
	 * @param cell to get the value.
	 */
	public void getValue(Cell cell) {
		cell.thisCell.getValue();
    }
}
