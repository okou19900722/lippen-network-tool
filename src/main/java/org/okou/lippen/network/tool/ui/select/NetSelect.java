package org.okou.lippen.network.tool.ui.select;

import javax.swing.JComboBox;

import org.okou.lippen.network.tool.listener.MessageReceivedListener;
import org.okou.lippen.network.tool.model.DataManager;
import org.okou.lippen.network.tool.net.INet;
import org.okou.lippen.network.tool.net.NetTCPClient;
import org.okou.lippen.network.tool.net.NetTCPServer;
import org.okou.lippen.network.tool.net.NetUDPServer;

@SuppressWarnings("serial")
public class NetSelect extends JComboBox<INet> {
	public NetSelect(DataManager data) {
		//数据监听器，在收到数据之后，添加到数据中心
		MessageReceivedListener messageReceived = (bytes) -> {
			data.addMessage(bytes);;
		};
		// 网络类型集合
		addItem(new NetTCPServer(data, messageReceived));
		addItem(new NetTCPClient(data, messageReceived));
		addItem(new NetUDPServer(data, messageReceived));
	}
	
	@Override
	public INet getSelectedItem() {
		return (INet) super.getSelectedItem();
	}
}
