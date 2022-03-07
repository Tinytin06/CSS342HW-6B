package homeworkSixB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

class JTableRowHeaders extends JFrame{
	/**
	 * Main to test the Spreadsheet GUI
	 *
	 * @param args
	 */
	    public static void main(String[] args){
	        //test of the GUI
			Object[][] data = {
	                { 0,0,0,0,0,0,0,0 },
	                { 0,0,0,0,0,0,0,0 },
	                { 0,0,0,0,0,0,0,0 },
	                { 0,0,0,0,0,0,0,0 },
	                { 0,0,0,0,0,0,0,0 },
	                { 0,0,0,0,0,0,0,0 },
	                { 0,0,0,0,0,0,0,0 },
	                { 0,0,0,0,0,0,0,0 }
	        };

	        new JTableRowHeaders(data,10);
	    }

	    public JTableRowHeaders(Object[][] data, int column) {
	        super("Spreadsheet");
	        setSize(800, 300);

	        //Set close
	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        //puts frame in middle of screen
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

	        //MODEL FOR OUR ROW HEADER
	        ListModel lm = new AbstractListModel() {
	            //Header text we want displayed
	            String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8", "9","10",
									"11","12", "13", "14", "15", "16", "17", "18", "19","20",
									"21", "22", "23", "24", "25", "26", "27", "28", "29","30",
									"31", "32", "33", "34", "35", "36", "37", "38", "39","40",
									"41", "42", "43", "44", "45", "46", "47", "48", "49","50"
				};

	            @Override
	            public int getSize() {
	                return headers.length;
	            }

	            @Override
	            public Object getElementAt(int index) {
	                return headers[index];
	            }
	        };

	        DefaultTableModel dm = new DefaultTableModel(lm.getSize(), column);
	        JTable table = new JTable(dm);
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			//sets data from given array into GUI
	        for (int i = 0; i < data.length; i++) {
	            for (int j = 0; j < data[0].length; j++) {
	                table.getModel().setValueAt(data[i][j], i, j);
	            }
	        }

	        // Create Row Header
	        JList rowHeader = new JList(lm);
	        rowHeader.setFixedCellWidth(50);
	        rowHeader.setFixedCellHeight(table.getRowHeight());

	        //Set Renderer
	        rowHeader.setCellRenderer(new RowRenderer(table));

	        // JScrollPane
	        JScrollPane pane = new JScrollPane(table);
	        pane.setRowHeaderView(rowHeader);
	        getContentPane().add(pane, BorderLayout.CENTER);


	        //Set Visible
	        this.setVisible(true);

	    }
	}

	//RendererClass
	class RowRenderer extends JLabel implements ListCellRenderer{

	    //Constructor
	    public RowRenderer(JTable table){
	        JTableHeader header = table.getTableHeader();
	        setOpaque(true);
	        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	        setHorizontalAlignment(CENTER);
	        setForeground(header.getForeground());
	        setBackground(header.getBackground());
	        setFont(header.getFont());
	    }

	    @Override
	    public Component getListCellRendererComponent(JList list, Object obj, int index, boolean selected,
	                                                  boolean focused) {
	        setText((obj==null) ? "" : obj.toString());
	        return this;
	   }

}
