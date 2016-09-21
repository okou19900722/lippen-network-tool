package org.okou.lippen.network.tool.ui.select;

import java.net.SocketAddress;

import io.netty.channel.Channel;

public class AbstractOption {
	@Override
	public boolean equals(Object obj) {
		SocketAddress s1 = getAddress(this);
		SocketAddress s2 = getAddress(obj);
		return s1 != null && s1.equals(s2);
	}
	
	private SocketAddress getAddress(Object obj){
		SocketAddress address = null;
		if(obj instanceof ChannelOption) {
			Channel channel = ((ChannelOption) obj).getChannel();
			address = channel.remoteAddress();
		} else if(obj instanceof INetSocketAddressOption) {
			address = ((INetSocketAddressOption) obj).getAddress();
		}
		return address;
	}
}
