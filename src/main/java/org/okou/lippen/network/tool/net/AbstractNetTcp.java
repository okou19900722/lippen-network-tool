package org.okou.lippen.network.tool.net;

import javax.swing.JOptionPane;

import org.okou.lippen.network.tool.listener.MessageReceivedListener;
import org.okou.lippen.network.tool.model.DataManager;
import org.okou.lippen.network.tool.net.handler.ServerHandler;
import org.okou.lippen.network.tool.util.DataFormatUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;

public abstract class AbstractNetTcp implements INet{
	protected DataManager data;
	protected ChannelHandler initializer;
	protected Channel channel;
	protected AbstractNetTcp(DataManager data, MessageReceivedListener listener){
		initializer = new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast("encoder", new MessageToByteEncoder<byte[]>() {
					@Override
					protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
						out.writeBytes(msg);
					}
				});
				ch.pipeline().addLast("handler", new ServerHandler(data, listener));
			}
		};
		this.data = data;
	}
	@Override
	public boolean stop() {
		if(channel == null) {
			return true;
		}
		try {
			channel.close().sync();
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "ֹͣ�쳣", "ֹͣ�쳣", JOptionPane.OK_OPTION);
			return false;
		}
		channel = null;
		return true;
	}
	protected byte[] msg2Bytes(String text) {
		byte[] bytes = null;
		switch(data.getWriteType()) {
		case HEX:
			bytes = DataFormatUtil.hexToBytes(text);
			break;
		case STRING:
			bytes = text.getBytes(data.getCharset());
			break;
		}
		return bytes;
	}
}
