package skj.raf.client.model;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import skj.raf.client.controller.RenderCtrl;
import skj.raf.client.controller.RenderCtrl.Status;

public class UploadHandler {

	private static final String PRE_CONSOLE = "UploadHandler: ";
	private static final int PACKAGE_SIZE = 4096;
	
	private int _totalFile;
	private int _totalDir;
	private BufferedReader _reader;
	private OutputStream _writer;
	private PrintWriter _printer;
	private boolean _running;
	private Thread _worker;
	private SpeedCounter _speed;
	
	public UploadHandler(InputStream reader, OutputStream writer) {
		_reader = new BufferedReader(new InputStreamReader(reader));
		_writer = writer;
		_printer = new PrintWriter(_writer, true);
		_speed = new SpeedCounter();
		_totalFile = _totalDir = 0;
		_running = false;
		RenderCtrl.updateTotal(_totalDir, _totalFile);
	}
	
	private void uploadFile(String filePath, String workingDir) {
		File file = new File(workingDir + filePath);
		try {
			System.out.println(PRE_CONSOLE + "Working with: " + file.getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	if(file.exists()){
    		try {
    			_printer.println("file");
    			long fileSize = (long)file.length(); // reads file size
        		_printer.println(fileSize);
        		_printer.println(filePath);
        		
        		String state = _reader.readLine(); 
        		if(state.equals("OK")) {
        			
        			long transfered = 0;
        			int block = 0;
        			byte[] buffer = new byte[PACKAGE_SIZE];
        			Checksum checksum = new CRC32();
        			BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
        			
        			do {
        				block = fileInput.read(buffer, 0, PACKAGE_SIZE);
        				_writer.write(buffer, 0, block);
        				checksum.update(buffer, 0, block);
        				
        				transfered += block;
        				_speed.updateSpeed(block);
            			
        				_writer.flush();
        			} while (transfered < fileSize);
        			
        			_reader.readLine();
        			
        			_printer.println(checksum.getValue());
        			_printer.flush();
        			
        			String status = _reader.readLine();
        			
        			if(status.equals("CHECKSUM_OK")) {
        				System.out.println(PRE_CONSOLE + "Checksum Ok");
        			} else {
        				System.out.println(PRE_CONSOLE + "Checksum error");
        			}
        			
        			System.out.println(PRE_CONSOLE + "Done with: " + file.getCanonicalPath());
        			fileInput.close();
        			_totalFile++;
        		} else {
        			System.out.println(PRE_CONSOLE + "Error: " + state);
        		}
			} catch (Exception e) {
				// To-Do: handle file errors
			}
    		
    	}
	}
	
	private void createDir(String dirPath) {
		_printer.println("dir");
		_printer.println(dirPath);
		_totalDir++;
	}
	
	private void traverseDir(String current, String workingDir) {
		if(_running) {
			File file = new File(workingDir + current);
			RenderCtrl.updateCurrent(current);
			if(file.exists()){
				if(file.isDirectory()) {
					createDir(current);
					
					for(File sub : file.listFiles()) {
						traverseDir(current + "/" + sub.getName(), workingDir);
					}
				} else {
					uploadFile(current, workingDir);
				}
			}
			RenderCtrl.updateTotal(_totalDir, _totalFile);
		} else {
			_printer.println("close");
		}
	}
	
	public void upload(final String selected, final String workingDir) {
		_running = true;
		_worker = new Thread(new Runnable() {
			public void run() {
				_speed.start();
				RenderCtrl.updateStatus(Status.UPLOADING);
				traverseDir(selected, workingDir);
				RenderCtrl.updateStatus(Status.CONNECTED);
				_speed.end();
			}
		});
		
		_worker.start();
	}
	
	public void disconnect() {
		if(_running) stop();
		else {
			_printer.println("close");
			_printer.flush();
		}
	}
	
	public void stop() {
		_running = false;
	}
}
