package org.okou.lippen.network.tool.net;

import java.net.BindException;
import java.util.List;

import javax.swing.JOptionPane;

import org.okou.lippen.network.tool.listener.MessageReceivedListener;
import org.okou.lippen.network.tool.model.DataManager;
import org.okou.lippen.network.tool.ui.select.ChannelOption;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetTCPServer extends AbstractNetTcp {
	private ServerBootstrap server;
	public NetTCPServer(DataManager data, MessageReceivedListener listener){
		super(data, listener, "TCP Server");
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(bossGroup, workGroup);
		server.channel(NioServerSocketChannel.class);
		server.childHandler(initializer);
	}
	public boolean start(String ip, int port){
		try {
			ChannelFuture f = server.bind(ip, port).sync();
			channel = f.channel();
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "服务器异常", "服务器异常", JOptionPane.OK_OPTION);
			return false;
		} catch (Exception e) {
			if(e instanceof BindException) {
				JOptionPane.showMessageDialog(null, "端口[" + port + "]已经被占用", "参数异常", JOptionPane.OK_OPTION);
			}
			return false;
		} 
		return true;
	}
	@Override
	public void sendMsg(String text) {
		List<Object> channels = data.getConnections();
		byte[] bytes = msg2Bytes(text);
		if(bytes != null) {
			for (Object obj : channels) {
				if(obj instanceof ChannelOption) {
					ChannelOption c = (ChannelOption) obj;
					c.getChannel().writeAndFlush(bytes);
				} else {
					System.err.println("TCP 连接列表中有非TCP连接" + obj);
				}
			}
		}
		
	}
	@Override
	public boolean isServer() {
		return true;
	}
	@Override
	public boolean canRemoveClient() {
		return false;
	}
}
