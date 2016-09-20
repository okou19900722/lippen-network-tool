package org.okou.lippen.network.tool.ui.field;

import javax.swing.JTextArea;

import org.okou.lippen.network.tool.model.DataManager.DataType;

@SuppressWarnings("serial")
public class DataTextArea extends JTextArea {
	private DataType type;
	public DataTextArea(DataType type) {
		this.type = type;
	}
	@Override
	public void replaceSelection(String content) {
		super.replaceSelection(content);
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		if(this.type != type) {
			
		}
	}
	
}
