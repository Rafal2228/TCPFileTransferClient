package skj.raf.client.model;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import skj.raf.client.controller.RenderCtrl;
import skj.raf.client.controller.RenderCtrl.Status;

public class MainClient {
	
	private static final String PRE_CONSOLE = "MainClient: ";
	private static final int MAX_TIMEOUT = 30000;
	private static final int CHECK_FOR_DISCONNECTION = 10000;
	
	private Socket _client;
	private UploadHandler _uploader;
	private String _serverName;
	private int _port;
	private String  _selected;
	private boolean _connected = false;
	
	public MainClient(){
		_serverName = null;
		_port = 0;
		RenderCtrl.updateIP(_serverName);
		RenderCtrl.updatePort(_port);
		RenderCtrl.updateStatus(Status.DISCONNECTED);
	}
	
	public MainClient(String serverName, int port) {
		_serverName = serverName;
		_port = port;
		RenderCtrl.updateIP(serverName);
		RenderCtrl.updatePort(port);
		RenderCtrl.updateStatus(Status.DISCONNECTED);
	}
	
	public void connect() {
		if(isConnectable()) {
			try {
				_client = new Socket(_serverName, _port);
				_uploader = new UploadHandler(_client.getInputStream(), _client.getOutputStream());
				_connected = true;
				RenderCtrl.updateStatus(Status.CONNECTED);
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						while(!_client.isInputShutdown() || !_client.isOutputShutdown()) {
							try {
								Thread.sleep(CHECK_FOR_DISCONNECTION);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						lost();
					}
				}).start();
				
			} catch (UnknownHostException e) {
				System.out.println(PRE_CONSOLE + "Unreachable host");
			} catch (IOException e) {
				if(!_connected) System.out.println(PRE_CONSOLE + "Cannot establish connection"); 
				else System.out.println(PRE_CONSOLE + "IO exception");
			}
		}
	}
	
	public void lost() {
		_connected = false;
		_uploader.stop();
		RenderCtrl.updateStatus(Status.DISCONNECTED);
	}
	
	public void disconnect() {
		if(_connected) {
			_connected = false;
			_uploader.disconnect();
			RenderCtrl.updateStatus(Status.DISCONNECTED);
		} else {
			System.out.println(PRE_CONSOLE + "Not connected");
		}
		
	}
	
	public void select(String selected) {
		File file = new File(selected);
		if(file.exists()) {
			_selected = selected;
			RenderCtrl.updateSelected(selected);
		} else {
			System.out.println(PRE_CONSOLE + "Cannot use specified file/dir");
		}
	}
	
	public void upload() {
		if(_connected) {
			String fileName, workingDir;
			File file = new File(_selected);
			fileName = file.getName();
			
			workingDir = _selected.substring(0, _selected.length() - fileName.length());
			
			_uploader.upload(fileName, workingDir);
		} else {
			System.out.println(PRE_CONSOLE + "Please connect to a server	");
		}
	}
	
	public void changeServer(String serverName) {
		try {
			InetAddress ip = InetAddress.getByName(serverName);
			try {
				if(ip.isReachable(MAX_TIMEOUT)){
					_serverName = serverName;
					RenderCtrl.updateIP(serverName);
				} else {
					System.out.println(PRE_CONSOLE + "Unreachable within timeout");
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void changePort(int port) {
		_port = port;
		RenderCtrl.updatePort(port);
	}
	
	public boolean isConnectable() {
		return _port > 0 && _serverName != null && !_connected;
	}
}
