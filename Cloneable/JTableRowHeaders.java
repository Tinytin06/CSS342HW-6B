package homeworkSixB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

class JTableRowHeaders extends JFrame{
	    public static void main(String[] args){
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

	        new JTableRowHeaders(data);
	    }


	    public JTableRowHeaders(Object[][] data) {
	        super("Row Header Example");
	        setSize(800, 300);

	        //Set close
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //puts frame in middle of screen
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

	        //MODEL FOR OUR ROW HEADER
	        ListModel lm = new AbstractListModel() {
	            //Header text we want displayed
	            String[] headers = {"1", "2", "3", "4", "5", "6", "7", "8"};

	            @Override
	            public int getSize() {
	                return headers.length;
	            }

	            @Override
	            public Object getElementAt(int index) {
	                return headers[index];
	            }
	        };

	        DefaultTableModel dm = new DefaultTableModel(lm.getSize(), 10);
	        JTable table = new JTable(dm);
	        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
