package org.okou.lippen.network.tool.ui.model;

import java.util.List;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import org.okou.lippen.network.tool.ui.select.ChannelOption;

import io.netty.channel.Channel;

@SuppressWarnings("serial")
public class ChannelListModel extends AbstractListModel<ChannelOption>{
	private List<ChannelOption> channelList = new ArrayList<>();
	private JList<ChannelOption> list;
	public ChannelListModel(JList<ChannelOption> list){
		this.list = list;
	}
	@Override
	public int getSize() {
		return channelList.size();
	}

	@Override
	public ChannelOption getElementAt(int index) {
		return channelList.get(index);
	}
	public void add(ChannelOption option){
		if(channelList.contains(option)) {
			return;
		}
		channelList.add(option);
		list.repaint();
		fireIntervalAdded(list, channelList.size() - 1, channelList.size() - 1);
	}
	public void remove(ChannelOption option) {
		remove(option.getChannel());
	}
	public void remove(Channel channel) {
		channelList.remove(channel);
		list.repaint();
		fireIntervalAdded(list, channelList.size() - 1, channelList.size() - 1);
	}
}
