package org.okou.lippen.network.tool.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import org.okou.lippen.network.tool.listener.MessageReceivedListener;
import org.okou.lippen.network.tool.model.DataManager;
import org.okou.lippen.network.tool.ui.field.DataTextArea;
import org.okou.lippen.network.tool.ui.field.JMIPV4AddressField;
import org.okou.lippen.network.tool.ui.field.NumberField;
import org.okou.lippen.network.tool.ui.net.INet;
import org.okou.lippen.network.tool.ui.net.NetTcpClient;
import org.okou.lippen.network.tool.ui.net.NetTcpServer;
import org.okou.lippen.network.tool.ui.table.ReadOnlyTable;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private JMenuItem configMenu;
	private JLabel ipLabel;
	private JLabel portLabel;
	private JButton bindButton;
	private NumberField portInput;
	
	private DataTextArea inputArea;

	public Window() {
		this.setTitle("Lippen Network Tool");
		this.setLayout(new BorderLayout());
		this.setSize(590, 590);
		this.setMinimumSize(new Dimension(590, 590));
		initMenu();
		initPanel();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("设置(O)");
		menu.setMnemonic('O');
		configMenu = new JMenuItem("编码设置(E)", KeyEvent.VK_E);
		menu.add(configMenu);

		JMenu about = new JMenu("关于");

		menuBar.add(menu);
		menuBar.add(about);

		this.setJMenuBar(menuBar);
	}

	class NetOption {
		private final String value;
		private final int index;

		public NetOption(String value, int index) {
			super();
			this.value = value;
			this.index = index;
		}

		@Override
		public String toString() {
			return value;
		}

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}
	}
	private void initPanel() {
		Object[] columnNames = new Object[]{"时间", "数据"};
		Object[][] rowData = new Object[][]{};
		DataManager data = new DataManager(rowData, columnNames); 
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel optionPanel = new JPanel();
		optionPanel.setBorder(BorderFactory.createTitledBorder("网络设置"));
		optionPanel.setLayout(new GridLayout(7, 1));
		JLabel networkTypeLabel = new JLabel("（1）协议类型");
		JComboBox<NetOption> networkSelect = new JComboBox<>();
		networkSelect.addItem(new NetOption("TCP server", 0));
		networkSelect.addItem(new NetOption("TCP client", 1));
		networkSelect.addItem(new NetOption("UDP", 2));
		networkSelect.addActionListener((e) -> {
			NetOption option = networkSelect.getItemAt(networkSelect.getSelectedIndex());
			if (option.getIndex() == 1) {
				ipLabel.setText("（2）服务器IP地址");
				portLabel.setText("（3）服务器端口");
			} else {
				ipLabel.setText("（2）本地ip地址");
				portLabel.setText("（3）本地端口");
			}
		});
		ipLabel = new JLabel("（2）本地ip地址");
		String ip = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			ip = address.getHostAddress();
		} catch (UnknownHostException e) {
		}
		JMIPV4AddressField ipInput = new JMIPV4AddressField(ip);
		portLabel = new JLabel("（3）本地端口");
		portInput = new NumberField(8080);
		bindButton = new JButton("连接");
		MessageReceivedListener messageReceived = (bytes) -> {
			data.addMessage(bytes);;
		};
		List<INet> netList = new ArrayList<>();
		netList.add(new NetTcpServer(data, messageReceived));
		netList.add(new NetTcpClient(data, messageReceived));

		Supplier<INet> fun = () -> {
			NetOption option = networkSelect.getItemAt(networkSelect.getSelectedIndex());
			int i = option.getIndex();
			if(i >= netList.size()) {
				JOptionPane.showMessageDialog(null, option.getValue() + "功能未实现");
				return null;
			}
			INet n = netList.get(i);
			return n;
		};
		bindButton.addActionListener((e) -> {
			String ipStr = ipInput.getText();
			int port = portInput.getNumber();
			INet n = fun.get();
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
		optionPanel.add(networkTypeLabel);
		optionPanel.add(networkSelect);
		optionPanel.add(ipLabel);
		optionPanel.add(ipInput);
		optionPanel.add(portLabel);
		optionPanel.add(portInput);
		optionPanel.add(bindButton);

		JPanel outOptionPanel = new JPanel();
		outOptionPanel.setBorder(BorderFactory.createTitledBorder("接收设置"));
		JPanel inOptionPanel = new JPanel();
		inOptionPanel.setBorder(BorderFactory.createTitledBorder("发送设置"));
		

		
		JPanel leftUpPanel = new JPanel();
		leftUpPanel.setLayout(new BoxLayout(leftUpPanel, BoxLayout.Y_AXIS));
		leftUpPanel.add(optionPanel);
		leftUpPanel.add(outOptionPanel);
		panel.add(leftUpPanel, BorderLayout.NORTH);
		panel.add(inOptionPanel, BorderLayout.SOUTH);

		JPanel outPanel = new JPanel();
		outPanel.setBorder(BorderFactory.createTitledBorder("接收信息"));
		outPanel.setLayout(new GridLayout(1, 1));
		JTable table = new ReadOnlyTable(data);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		outPanel.add(scrollPane);
		JPanel inPanel = new JPanel();
		inPanel.setBorder(BorderFactory.createTitledBorder("发送信息"));
		inPanel.setLayout(new BorderLayout());
		inputArea = new DataTextArea(data.getWriteType(), data.getCharset());
		JButton sendButton = new JButton("发送");
		sendButton.addActionListener((e) -> {
//			int w = table.getTableHeader().getColumnModel().getColumn(0).getPreferredWidth();
//			System.out.println(w);
			String str = inputArea.getText();
			INet n = fun.get();
			if(n == null) {
				return;
			}
			n.sendMsg(str);
		});
		inPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);
		inPanel.add(sendButton, BorderLayout.EAST);
		JSplitPane splitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outPanel, inPanel);
		splitPanel.setDividerLocation((this.getHeight() >> 2) * 3);
		splitPanel.setBorder(null);

		this.add(panel, BorderLayout.WEST);
		this.add(splitPanel, BorderLayout.CENTER);
	}
}
