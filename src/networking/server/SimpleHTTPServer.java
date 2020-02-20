package networking.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;


public class SimpleHTTPServer {
	
	public static final int port = 8000; 
	
	public static void main(String[] args) throws IOException {
		final ServerSocket server = new ServerSocket (port );
		System.out.println("Listening on port: " + port);
		String html = "<!DOCTYPE html> <html><head></head><body><h1>Did it work?</h1></body></html>";
		
		
		while (true) {
			
			Socket client  = server.accept();
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			BufferedReader read = new BufferedReader(isr);
			
			File temp = File.createTempFile("index", "html");
			FileWriter fr = new FileWriter(temp);
			fr.append(html);
			fr.close();
			
			
			
			String line = read.readLine();
			client.getOutputStream().write(getHTTP200(html.length()+"").getBytes());
			Files.copy(temp.toPath(), client.getOutputStream());
			//client.getOutputStream().write();
			
			temp.delete();
			
			
			
			//client.getOutputStream()
			//client.getOutputStream().write(write.toString().getBytes("UTF-8"));
			client.close();
			
			
		}
		
		// Make temp file
		// write incoming messages then send back the log
	}
	
	public static String getHTTP200(String contentLength){
		StringBuilder str = new StringBuilder();
		str.append("HTTP:/1.1 200 OK\r\n");
		str.append("Date: " + new Date().toGMTString() + "\r\n");
		str.append("Server: Java HTTP Host\r\n");
		str.append("Content-Length: " + contentLength + "\r\n");
		str.append("Content-Type: text/html\r\n");
		str.append("Connection: Closed\r\n");
		
		str.append("\n");
		
		return str.toString();
	}
}

	/**
	 * 
A Status-line

Zero or more header (General|Response|Entity) fields followed by CRLF

An empty line (i.e., a line with nothing preceding the CRLF) 
indicating the end of the header fields

Optionally a message-body
	 */
	

