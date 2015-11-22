package skj.raf.client.controller;

import skj.raf.client.view.MainFrame;

public class RenderCtrl {

	public enum Status {
		DISCONNECTED ("Disconnected"),
		CONNECTED ("Connected"),
		UPLOADING ("Uploading");
		
		private String _name;
		
		Status (String name) {
			_name = name;
		}
		
		@Override
		public String toString() {
			return _name;
		}
	}
	
	private static MainFrame _mainFrame;
	
	public static void draw() {
		if(_mainFrame == null) {
			_mainFrame = new MainFrame();
			_mainFrame.bootstrap();
		}
	}
	
	public static void end() {
		if(_mainFrame.isVisible()) {
			_mainFrame.close();
		}
	}
	
	public static void updateIP(String ip) {
		_mainFrame.updateIP(ip);
	}
	
	public static void updatePort(int port) {
		_mainFrame.updatePort(port);
	}
	
	public static void updateStatus(Status status) {
		_mainFrame.updateStatus(status.toString());
	}
	
	public static void updateSelected(String selected) {
		_mainFrame.updateSelected(selected);
	}
	
	public static void updateCurrent(String current) {
		_mainFrame.updateCurrent(current);
	}
	
	public static void updateSpeed(String speed) {
		_mainFrame.updateSpeed(speed);
	}
	
	public static void updateTotal(int totalDir, int totalFile) {
		_mainFrame.updateTotal("Created: " + totalDir + " dir, Uploaded: " + totalFile + " files");
	}
}
