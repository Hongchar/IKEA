package jframe;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class JFrames {
	
	private static Map<String, JFrame> JFrameMap = new HashMap<>();

	public static JFrame getJFrame(String key) {
		return JFrames.JFrameMap.get(key);
	}

	public static void setJFrame(String key, JFrame jframe) {
		JFrames.JFrameMap.put(key, jframe);
	}	
	
}