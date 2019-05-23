package org.okou.lippen.network.tool.net;

public interface INet {
    public boolean start(String ip, int port);

    public boolean stop();

    public void sendMsg(String text);

    public boolean isServer();

    public boolean canRemoveClient();

    public boolean needTarget();
}
