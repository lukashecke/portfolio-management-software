package Models;

import javax.swing.table.AbstractTableModel;

/**
 * Controller für ein JTable.
 */
public class DataTableModel extends AbstractTableModel {

    private Object[][] data;

    /**
     * Erzeugt DataTableModel mit den übergebenen Daten.
     * @param data Übergebene Daten
     */
    public DataTableModel(Object[][] data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
