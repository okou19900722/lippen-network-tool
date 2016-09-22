package org.okou.lippen.network.tool.ui.select;

import java.net.SocketAddress;

public abstract class AbstractOption {
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AbstractOption) {
			SocketAddress s1 = ((AbstractOption) obj).getAddress();
			SocketAddress s2 = this.getAddress();
			return s1 != null && s1.equals(s2);
		}
		return false;
	}
	public abstract SocketAddress getAddress();
}
