package skj.raf.client.model;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClient {
	
	private static final String PRE_CONSOLE = "MainClient: ";
	
	private Socket _client;
	private UploadHandler _uploader;
	private String _serverName;
	private int _port;
	private String  _selected;
	
	public MainClient(String serverName, int port) {
		_serverName = serverName;
		_port = port;
	}
	
	public void connect() {
		try {
			_client = new Socket(_serverName, _port);
			_uploader = new UploadHandler(_client.getInputStream(), _client.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void select(String selected) {
		File file = new File(selected);
		if(file.exists()) {
			_selected = selected;
		} else {
			System.out.println(PRE_CONSOLE + "Cannot use specified file/dir");
		}
	}
	
	public void upload() {
		String fileName, workingDir;
		String[] arr = _selected.split("/");
		
		fileName = arr[arr.length - 1];
		workingDir = _selected.substring(0, _selected.length() - fileName.length());
		
		_uploader.upload(fileName, workingDir);
	}
	
}
