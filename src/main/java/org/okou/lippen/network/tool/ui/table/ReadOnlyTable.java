package org.okou.lippen.network.tool.ui.table;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import org.okou.lippen.network.tool.model.DataManager;

@SuppressWarnings("serial")
public class ReadOnlyTable extends JTable {
	public ReadOnlyTable(DataManager data) {
		super(createTableModel(data));
		data.setComponent(this);
		
		JTableHeader header = this.getTableHeader();
		setFillsViewportHeight(true);
		header.getColumnModel().getColumn(0).setMaxWidth(120);
		header.getColumnModel().getColumn(0).setPreferredWidth(120);
		
	}

	private static TableModel createTableModel(DataManager data) {
		return new DataTableModel(data);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
