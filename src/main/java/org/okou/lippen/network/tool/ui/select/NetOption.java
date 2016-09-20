package org.okou.lippen.network.tool.ui.select;

public class NetOption {
	private final String value;
	private final int index;

	public NetOption(String value, int index) {
		super();
		this.value = value;
		this.index = index;
	}

	@Override
	public String toString() {
		return value;
	}

	public int getIndex() {
		return index;
	}

	public String getValue() {
		return value;
	}
}
