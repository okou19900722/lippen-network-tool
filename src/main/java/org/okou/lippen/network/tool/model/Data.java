package org.okou.lippen.network.tool.model;

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
			result = new String(data, manager.getCharset());
			break;
		}
		return result;
	}
	private String toHexString(){
		return DataFormatUtil.bytesToHex(data);
	}
}
