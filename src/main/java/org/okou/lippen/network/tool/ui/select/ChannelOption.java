package org.okou.lippen.network.tool.ui.select;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import io.netty.channel.Channel;

public class ChannelOption extends AbstractOption{
	private Channel channel;
	public ChannelOption(Channel channel) {
		super();
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	@Override
	public String toString() {
		SocketAddress address = channel.remoteAddress();
		address = address == null ? channel.localAddress() : address; 
		if(address instanceof InetSocketAddress) {
			InetSocketAddress add = (InetSocketAddress) address;
			return add.getHostString() + ":" + add.getPort();
		}
		return address.toString();
	}
	
	@Override
	public SocketAddress getAddress() {
		return channel.remoteAddress();
	}
}
