package skj.raf.client.controller;

public class MainCtrl {

    public static void main(String[] args){
    	String serverName;
    	int port = 9999;
    	String selected = "";
    	
    	RenderCtrl.draw();
    	
    	if(args.length > 0) {
    		serverName = args[0];
    		
    		if(args.length > 1) {
        		port = Integer.parseInt(args[1]);
        	}
        	
        	if(args.length > 2) {
        		selected = args[2];
        	}
	    	ConnectionCtrl.createClient(serverName, port, selected);
    	} else {
	    	ConnectionCtrl.createClient();
    	}
    }
	
}
