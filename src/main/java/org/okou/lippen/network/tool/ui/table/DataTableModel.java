package org.okou.lippen.network.tool.ui.table;

import javax.swing.table.AbstractTableModel;

import org.okou.lippen.network.tool.model.DataManager;

@SuppressWarnings("serial")
public class DataTableModel extends AbstractTableModel{
	private DataManager data;
	private int index;
	public DataTableModel(DataManager data) {
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		int i = index;
		return data.getRowCount() - i;
	}

	@Override
	public int getColumnCount() {
		return data.getColumnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int i = index;
		return data.getValueAt(rowIndex + i, columnIndex);
	}
	@Override
	public String getColumnName(int column) {
		return data.getColumnName(column);
	}
	public void clear(){
		this.index = data.getRowCount();
	}
}
