package org.okou.lippen.network.tool.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JList;

@SuppressWarnings("serial")
public class ChannelListModel extends AbstractListModel<Object>{
	private List<Object> channelList = new ArrayList<>();
	private JList<Object> list;
	public ChannelListModel(JList<Object> list){
		this.list = list;
	}
	@Override
	public int getSize() {
		return channelList.size();
	}

	@Override
	public Object getElementAt(int index) {
		return channelList.get(index);
	}
	public void addElement(Object option){
		if(channelList.contains(option)) {
			return;
		}
		channelList.add(option);
		System.out.println("连接，在线:" + channelList.size());
		list.repaint();
		fireIntervalAdded(list, channelList.size() - 1, channelList.size() - 1);
	}
//	public void add(ChannelOption option){
//		if(channelList.contains(option)) {
//			return;
//		}
//		channelList.add(option);
//		System.out.println("连接，在线:" + channelList.size());
//		list.repaint();
//		fireIntervalAdded(list, channelList.size() - 1, channelList.size() - 1);
//	}
//	public void addElement(INetSocketAddressOption option){
//		if(!channelList.contains(option)) {
//			channelList.add(option);
//		}
//		System.out.println("连接，在线:" + channelList.size());
//		System.out.println(list.getModel().getClass());
//		list.repaint();
////		fireIntervalAdded(list, channelList.size(), channelList.size());
//	}
	public void removeElement(Object option) {
		boolean b = channelList.remove(option);
		System.out.println("离开，在线:" + channelList.size());
		if(b) {
			list.repaint();
			fireIntervalAdded(list, channelList.size(), channelList.size());
		}
	}
	public void removeAllElements() {
		channelList.clear();
		list.repaint();
	}
}
