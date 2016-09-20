package org.okou.lippen.network.tool.model;

import java.io.UnsupportedEncodingException;

import org.okou.lippen.network.tool.util.DataFormatUtil;

public class Data {
	private final byte[] data;
	private final DataManager manager;
	public Data(byte[] data, DataManager manager) {
		super();
		this.data = data;
		this.manager = manager;
	}
	@Override
	public String toString() {
		String result = null;
		switch(manager.getReadType()) {
		case HEX:
			result = toHexString();
			break;
		case STRING:
			String charset = manager.getCharset();
			try {
				result = new String(data, charset);
			} catch (UnsupportedEncodingException e) {
				result = "±àÂë[" + charset + "]Òì³£,Êý¾Ý:" + toHexString();
			}
			break;
		}
		return result;
	}
	private String toHexString(){
		return DataFormatUtil.bytesToHex(data);
	}
}
