package skj.raf.client.controller;

import skj.raf.client.view.MainFrame;

public class RenderCtrl {

	private static MainFrame _mainFrame;
	
	public static void draw() {
		_mainFrame = new MainFrame();
		_mainFrame.bootstrap();
	}
	
}
