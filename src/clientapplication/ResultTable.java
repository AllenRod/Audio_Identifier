package clientapplication;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * The table that shows the result of matching.
 * 
 * @author Jiajie Li
 * CSE 260 PRJ 4
 * 11/29/14
 */
public class ResultTable extends JPanel{
    // table that holds the result
    private JTable resultTable;
    
    // the table model that holds the data
    private DefaultTableModel table;
    
    /**
     * create a ResultTable object
     */
    public ResultTable() {
	super();
	String[] name = new String[]{"Track ID", "Track Name", 
		"Time Difference", "Hits"};
	table = new DefaultTableModel(name, 0);
	resultTable = new JTable(table) {
	    @Override
	    public boolean isCellEditable(int rowIndex, int columIndex) {
		return false;
	    }
	};
	resultTable.setFillsViewportHeight(true);
	add(new JScrollPane(resultTable));
    }

    /**
     * get the JTable resultTable
     * @return		the JTable object
     */
    public JTable getTable() {
	return resultTable;
    }
    
    /**
     * show the result of matching
     * @param result		result of matching
     */
    public void showResult(Object[][] result) {
	removeTable();
	if (result == null) {
	    return;
	}
	for (int i = 0; i < result.length; i++) {
	    table.addRow(result[i]);
	}
    }
    
    /**
     * remove all rows in the table
     */
    private void removeTable() {
	for (int i = table.getRowCount() - 1; i >= 0; i--) {
	    table.removeRow(i);
	}
    }
}
