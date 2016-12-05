package org.okou.lippen.network.tool.ui.field;

import java.nio.charset.Charset;

import javax.swing.JTextArea;

import org.okou.lippen.network.tool.model.DataManager.DataType;
import org.okou.lippen.network.tool.util.DataFormatUtil;

@SuppressWarnings("serial")
public class DataTextArea extends JTextArea {
	private DataType type;
	private Charset charset;
	public DataTextArea(DataType type, Charset charset) {
		this.type = type;
		this.charset = charset;
		this.setLineWrap(true);
		this.setAutoscrolls(true);
	}

	private String regex = "[0-9|a-f|A-F| ]*";
	@Override
	public void replaceSelection(String content) {
		if(content == null ) {
			return;
		}
		if(type == DataType.HEX) {
			if(!content.matches(regex)) {
				return;
			}
		}
		super.replaceSelection(content);
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		if(this.type != type) {
			switch (type) {
			case HEX:
				setText(DataFormatUtil.strToHex(getText(), charset));
				break;
			case STRING:
				setText(DataFormatUtil.hexToStr(getText(), charset));
				break;
			}
			this.type = type;
		}
	}
	public Charset getCharset() {
		return charset;
	}
	public void setCharset(Charset charset) {
		if(type == DataType.HEX) {
			//如果显示的是16进制，那么设置编码的时候，需要按原编码转为字符串，然后再按新编码计算字节数组
			String str = DataFormatUtil.hexToStr(getText(), this.charset);
			setText(DataFormatUtil.strToHex(str, charset));
		}
		this.charset = charset;
	}
	
}
