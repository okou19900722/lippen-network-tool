package org.okou.lippen.network.tool.ui.field;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NumberField extends JTextField{
	public NumberField(){
		super();
	}
	public NumberField(int number){
		super(String.valueOf(number));
	}
	@Override
	public void replaceSelection(String content) {
		//重写本方法，控制只允许输入数字
		if(content == null) {
			return;
		}
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if(c <= '9' && c >= '0') {
				buffer.append(c);
			}
		}
		super.replaceSelection(buffer.toString());
	}
	public int getNumber(){
		return Integer.parseInt(getText());
	}
}
