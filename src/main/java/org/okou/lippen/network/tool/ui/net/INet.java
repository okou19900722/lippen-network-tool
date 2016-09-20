package org.okou.lippen.network.tool.ui.net;

public interface INet {
	public boolean start(String ip, int port);
	public boolean stop();
	public void sendMsg(String text);
}
