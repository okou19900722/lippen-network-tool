package org.okou.lippen.network.tool.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {
	public static String getLocalHostName() {
		String ip = null;
		try {
			InetAddress address = InetAddress.getLocalHost();
			ip = address.getHostAddress();
		} catch (UnknownHostException e) {
		}
		return ip;
	}
}
