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

public class UploadHandler {

	private static final String PRE_CONSOLE = "UploadHandler: ";
		
	private BufferedReader _reader;
	private OutputStream _writer;
	private PrintWriter _printer;
	private boolean _running = true;
	private Thread _worker;
	
	public UploadHandler(InputStream reader, OutputStream writer) {
		_reader = new BufferedReader(new InputStreamReader(reader));
		_writer = writer;
		_printer = new PrintWriter(_writer, true);
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
    			int fileSize = (int)file.length(); // reads file size
        		_printer.println(fileSize);
        		_printer.println(filePath);
        		byte[] buffer = new byte[fileSize];
        		
        		BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
        		fileInput.read(buffer, 0, buffer.length);
        		
        		Checksum checksum = new CRC32();
        		checksum.update(buffer, 0, buffer.length);
        		_printer.println(checksum.getValue());
        		
        		String state = _reader.readLine(); 
        		if(state.equals("OK")) {
        			_writer.write(buffer, 0, buffer.length);
        			_writer.flush();
        		} else {
        			System.out.println(PRE_CONSOLE + "Error: " + state);
        		}
        		
        		fileInput.close();
			} catch (Exception e) {
				// To-Do: handle file errors
			}
    		
    	}
	}
	
	private void createDir(String dirPath) {
		_printer.println("dir");
		_printer.println(dirPath);
	}
	
	private void traverseDir(String current, String workingDir) {
		if(_running) {
			File file = new File(workingDir + current);
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
		}		
	}
	
	public void upload(final String selected, final String workingDir) {
		_worker = new Thread(new Runnable() {
			public void run() {
				traverseDir(selected, workingDir);
			}
		});
		
		_worker.start();
	}
	
	public void stop() {
		_running = false;
	}
}
