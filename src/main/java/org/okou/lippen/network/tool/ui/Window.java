package org.okou.lippen.network.tool.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.okou.lippen.network.tool.model.DataManager;
import org.okou.lippen.network.tool.model.DataManager.DataType;
import org.okou.lippen.network.tool.net.INet;
import org.okou.lippen.network.tool.ui.field.DataTextArea;
import org.okou.lippen.network.tool.ui.field.IPV4Field;
import org.okou.lippen.network.tool.ui.field.PortField;
import org.okou.lippen.network.tool.ui.menu.CharsetCheckBoxMenuItem;
import org.okou.lippen.network.tool.ui.model.ChannelListModel;
import org.okou.lippen.network.tool.ui.select.ChannelOption;
import org.okou.lippen.network.tool.ui.select.NetSelect;
import org.okou.lippen.network.tool.ui.table.ReadOnlyTable;
import org.okou.lippen.network.tool.util.NetUtil;

@SuppressWarnings("serial")
public class Window extends JFrame {
	//编码格式
	private static final String[][] charsets = new String[][] {
		{"ISO-8859-1", "ISO-8859-1"},
		{"UTF-8", "UTF-8"},
		{"GBK", "GBK"},
		{"GB2312", "GB2312"},
		{"GB18030", "GB18030"},
		{"Big5", "Big5"},
		{"Big5-HKSCS", "Big5-HKSCS"},
		{"UTF-16", "UTF-16"},
		{"UTF-16BE", "UTF-16BE"},
		{"UTF-16LE", "UTF-16LE"},
		{"UTF-32", "UTF-32"},
		{"UTF-32BE", "UTF-32BE"},
		{"UTF-32LE", "UTF-32LE"},
	};
	//本地ip
	private static final String LOCAL_IP = NetUtil.getLocalHostName();
	
	//数据中心
	private DataManager data;
	//网络类型选择框
	private NetSelect networkSelect;
	//ip和端口显示面板
	private JLabel ipLabel;
	private JLabel portLabel;
	//ip和端口输入框
	private IPV4Field ipInput;
	private PortField portInput;
	
	//接收信息列表显示格式
	private JCheckBox readHex;
	//发送信息框显示格式
	private JCheckBox writeHex;
	
	//绑定按钮
	private JButton bindButton;
	//消息发送按钮
	private JButton sendButton;
	//消息输入框
	private DataTextArea writeArea;
	//接收消息显示框
	private JTable table;
	
	//目标ip和端口输入框
	private IPV4Field targetIp;
	private PortField targetPort;

	private JList<ChannelOption> connectList;
	
	private ActionListener listener;

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	public Window() {
		this.setTitle("Lippen Network Tool");
		this.setLayout(new BorderLayout());
		this.setSize(690, 590);
		this.setMinimumSize(new Dimension(690, 590));
		initComponent();
		addListener();
		initMenu();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("设置(O)");
		menu.setMnemonic('O');
		JMenu m = new JMenu("编码设置(E)");
		ButtonGroup group = new ButtonGroup();
		for(String[] charset:charsets) {
			if(charset.length == 0) {
				m.addSeparator();
				continue;
			}
			CharsetCheckBoxMenuItem item = new CharsetCheckBoxMenuItem(charset[0], charset[1]);
			if(charset[1].equals(DEFAULT_CHARSET.name())) {
				item.setSelected(true);
			}
			item.addActionListener(listener);
			group.add(item);
			m.add(item);
		}
		menu.add(m);
		
		JMenu about = new JMenu("关于");

		menuBar.add(menu);
		menuBar.add(about);

		this.setJMenuBar(menuBar);
	}

	private void initComponent() {
		//初始化数据中心
		Object[] columnNames = new Object[]{"时间", "数据"};
		Object[][] rowData = new Object[][]{};
		data = new DataManager(rowData, columnNames); 
		data.setCharset(DEFAULT_CHARSET);
		
		//-------------初始化控件-------------
		JLabel networkTypeLabel = new JLabel("（1）协议类型");
		//网络类型选择框
		networkSelect = new NetSelect(data);
		
		ipLabel = new JLabel("（2）本地ip地址");
		//ip地址输入框
		ipInput = new IPV4Field(LOCAL_IP);
		
		portLabel = new JLabel("（3）本地端口");
		//端口输入框
		portInput = new PortField(8080);
		
		bindButton = new JButton("连接");
		
		readHex = new JCheckBox("十六进制显示");
		
		writeHex = new JCheckBox("十六进制显示");
		
		table = new ReadOnlyTable(data);
		JScrollPane tableScroll = new JScrollPane(table);
		
		JLabel targetHost = new JLabel("目标主机");
		targetIp = new IPV4Field(LOCAL_IP);
		JLabel targetPortLabel = new JLabel("目标端口");
		targetPort = new PortField(7000);
		
		//消息发送框
		writeArea = new DataTextArea(data.getWriteType(), data.getCharset());
		sendButton = new JButton("发送");
		
		connectList = new JList<>();
		ChannelListModel model = new ChannelListModel(connectList);
		connectList.setModel(model);
		data.setChannelListModel(model);
		
		JPanel targetPanel = new JPanel();
//		targetPanel.setLayout(new BoxLayout(targetPanel, BoxLayout.X_AXIS));
		targetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		targetPanel.add(targetHost);
		targetPanel.add(targetIp);
		targetPanel.add(targetPortLabel);
		targetPanel.add(targetPort);
		
		JPanel writePanel = new JPanel();
		writePanel.setBorder(BorderFactory.createTitledBorder("发送信息"));
		writePanel.setLayout(new BorderLayout());
		writePanel.add(new JScrollPane(writeArea), BorderLayout.CENTER);
		writePanel.add(sendButton, BorderLayout.EAST);
		
		JPanel readPanel = new JPanel();
		readPanel.setBorder(BorderFactory.createTitledBorder("接收信息"));
		readPanel.setLayout(new BoxLayout(readPanel, BoxLayout.Y_AXIS));
		readPanel.add(tableScroll);
		readPanel.add(targetPanel);
		
		//网络设置面板
		JPanel netSettingPanel = new JPanel();
		netSettingPanel.setBorder(BorderFactory.createTitledBorder("网络设置"));
		netSettingPanel.setLayout(new GridLayout(7, 1));
		netSettingPanel.add(networkTypeLabel);
		netSettingPanel.add(networkSelect);
		netSettingPanel.add(ipLabel);
		netSettingPanel.add(ipInput);
		netSettingPanel.add(portLabel);
		netSettingPanel.add(portInput);
		netSettingPanel.add(bindButton);
		
		JPanel readSettingPanel = new JPanel();
		readSettingPanel.setBorder(BorderFactory.createTitledBorder("接收设置"));
		readSettingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		readSettingPanel.add(readHex);
		
		
		JPanel leftUpPanel = new JPanel();
		leftUpPanel.setLayout(new BoxLayout(leftUpPanel, BoxLayout.Y_AXIS));
		leftUpPanel.add(netSettingPanel);
		leftUpPanel.add(readSettingPanel);

		JPanel writeSettingPanel = new JPanel();
		writeSettingPanel.setBorder(BorderFactory.createTitledBorder("发送设置"));
		writeSettingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		writeSettingPanel.add(writeHex);
		
		//左半边面板
		JPanel leftPanel = new JPanel();
		//设置布局为BorderLayout，这样在调节窗口大小时，顶部不变，底部不变，中间为空白变化
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(leftUpPanel, BorderLayout.NORTH);
		leftPanel.add(writeSettingPanel, BorderLayout.SOUTH);
		//右边面板
		JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, readPanel, writePanel);
		//显示分隔面板在3/4位置
		splitPanel.setDividerLocation((this.getHeight() >> 2) * 3);
		//取消边框
		splitPanel.setBorder(null);
		
		this.add(leftPanel, BorderLayout.WEST);
		this.add(splitPanel, BorderLayout.CENTER);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("连接列表"));
		panel.setLayout(new GridLayout(1, 1));
		
		JScrollPane listScroll = new JScrollPane(connectList);
		listScroll.setPreferredSize(new Dimension(100, 100));
		panel.add(listScroll);
		this.add(panel, BorderLayout.EAST);
	}
	
	public void addListener(){
		data.setAddressSource(() -> {
			String udpIP = targetIp.getText();
			if(udpIP == null) {
				JOptionPane.showMessageDialog(null, "UDP客户端信息未配置", "UDP", JOptionPane.OK_OPTION);
				return null;
			}
			int udpPort = this.targetPort.getNumber();
			return new InetSocketAddress(udpIP, udpPort);
		});
		sendButton.addActionListener((e) -> {
			String str = writeArea.getText();
			INet n = networkSelect.getSelectedItem();
			if(n == null) {
				return;
			}
			n.sendMsg(str);
		});
		networkSelect.addActionListener((e) -> {
			INet option = networkSelect.getItemAt(networkSelect.getSelectedIndex());
			if (!option.isServer()) {
				ipLabel.setText("（2）服务器IP地址");
				portLabel.setText("（3）服务器端口");
			} else {
				ipLabel.setText("（2）本地ip地址");
				portLabel.setText("（3）本地端口");
			}
		});
		bindButton.addActionListener((e) -> {
			String ipStr = ipInput.getText();
			int port = portInput.getNumber();
			INet n = networkSelect.getSelectedItem();
			if(n == null) {
				return;
			}
			if(bindButton.getText().equals("连接")) {
				if(n.start(ipStr, port)){
					bindButton.setText("断开");
					networkSelect.setEnabled(false);
					ipInput.setEnabled(false);
					portInput.setEnabled(false);
				}
			} else {
				if(n.stop()) {
					bindButton.setText("连接");
					networkSelect.setEnabled(true);
					ipInput.setEnabled(true);
					portInput.setEnabled(true);
				}
			}
		});
		readHex.addActionListener((e) -> {
			if(readHex.isSelected()) {
				data.setReadType(DataType.HEX);
			} else {
				data.setReadType(DataType.STRING);
			}
			table.repaint();
		});
		writeHex.addActionListener((e) -> {
			if(writeHex.isSelected()) {
				data.setWriteType(DataType.HEX);
			} else {
				data.setWriteType(DataType.STRING);
			}
			writeArea.setType(data.getWriteType());
		});
		listener = (e) -> {
			Object obj = e.getSource();
			if(obj instanceof CharsetCheckBoxMenuItem) {
				CharsetCheckBoxMenuItem i = (CharsetCheckBoxMenuItem) obj;
				Charset c = i.getCharset();
				data.setCharset(c);
				table.repaint();
				writeArea.setCharset(c);
			}
		};
	}
	
	
}
