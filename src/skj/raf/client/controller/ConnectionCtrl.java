package skj.raf.client.controller;

import skj.raf.client.model.MainClient;

public class ConnectionCtrl {
	
	private static MainClient _client;
	
	public static void createClient() {
		_client = new MainClient("localhost", 9999);
	}
	
	public static void changeIP(String ip) {
		_client.changeServer(ip);
	}
	
	public static void changePort(int port) {
		_client.changePort(port);
	}
	
	public static void connect() {
		_client.connect();
	}
	
	public static void disconnect() {
		_client.close();
	}
	
	public static void select(String selected) {
		_client.select(selected);
	}
	
	public static void upload() {
		_client.upload();
	}
}
