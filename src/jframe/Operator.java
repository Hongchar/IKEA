package jframe;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class Operator {
	
	private static Map<String, JFrame> JFrameMap = new HashMap<>();

	public static JFrame getJFrameMap(String key) {
		return Operator.JFrameMap.get(key);
	}

	public static void setJFrameMap(String key, JFrame jframe) {
		Operator.JFrameMap.put(key, jframe);
	}
	
}