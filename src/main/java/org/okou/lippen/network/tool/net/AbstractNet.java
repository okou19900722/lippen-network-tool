package org.okou.lippen.network.tool.net;

import javax.swing.JOptionPane;

import org.okou.lippen.network.tool.model.DataManager;
import org.okou.lippen.network.tool.util.DataFormatUtil;

import io.netty.channel.Channel;

public abstract class AbstractNet implements INet {
    protected DataManager data;
    protected Channel channel;
    private String netName;

    public AbstractNet(DataManager data, String netName) {
        this.data = data;
        this.netName = netName;
    }

    protected byte[] msg2Bytes(String text) {
        byte[] bytes = null;
        switch (data.getWriteType()) {
            case HEX:
                bytes = DataFormatUtil.hexToBytes(text);
                break;
            case STRING:
                bytes = text.getBytes(data.getCharset());
                break;
        }
        return bytes;
    }

    @Override
    public boolean stop() {
        if (channel == null) {
            return true;
        }
        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "停止异常", "停止异常", JOptionPane.OK_OPTION);
            return false;
        }
        channel = null;
        return true;
    }

    @Override
    public String toString() {
        return netName;
    }
}
