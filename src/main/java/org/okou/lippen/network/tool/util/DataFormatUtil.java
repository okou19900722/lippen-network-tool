package org.okou.lippen.network.tool.util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class DataFormatUtil {
	private static final String[] BYTE_HEX = new String[256];
	private static final Map<String, Integer> map = new HashMap<>();
	private static final Map<String, String> map2 = new HashMap<>();
	static{
		for(int i = 0; i < 16; i++) {
			String str = "0" + Integer.toHexString(i);
			BYTE_HEX[i] = str;
			map.put(str, i);
			map2.put(str.toUpperCase(), str);
		}
		for(int i = 16; i < 256; i++) {
			String str = Integer.toHexString(i);
			BYTE_HEX[i] = str;
			map.put(str, i);
			map2.put(str.toUpperCase(), str);
		}
	}
	public static String strToHex(String str, Charset charset){
		return bytesToHex(str.getBytes(charset));
	}
	public static String bytesToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (byte b : data) {
			int i = b & 0xff;
			if(buf.length() > 0) {
				buf.append(" ");
			}
			buf.append(BYTE_HEX[i]);
		}
		return buf.toString();
	}
	public static String hexToStr(String hex, Charset charset) {
		return new String(hexToBytes(hex), charset);
	}
	public static byte[] hexToBytes(String hex){
		String str = hex.replaceAll("[ \r\n]", "");
		if((str.length() & 1) != 0) {
			str = str.substring(0, str.length() - 1);
		}
		byte[] result = new byte[str.length() >> 1];
		for(int i = 0; i < str.length(); i += 2) {
			String s = str.substring(i, i + 2);
			String lowerCase = map2.get(s);
			s = lowerCase == null ? s : lowerCase;
			Integer val = map.get(s);
			if(val == null) {
				throw new RuntimeException("字符串[" + s + "]不是字节的16进制字符串");
			}
			result[i>>1] = val.byteValue();
		}
		return result;
	}
	
	public static void main(String[] args) {
//		byte[] bytes = DataFormatUtil.hexToBytes("0 A 0 b \r \n");
//		System.out.println(bytesToHex(bytes));
		String regex = "[0-9|a-f|A-F ]*";
		String str = "0123456789abcdef ";
		System.out.println(str.matches(regex));
	}
}
