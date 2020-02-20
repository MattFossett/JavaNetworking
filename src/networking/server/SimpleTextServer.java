package networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;

public class SimpleTextServer {

	public static final int port = 8000;
	
	public static void forwardMessage(Socket origin, Socket destination) throws IOException{
		while(true){
			InputStreamReader isr = new InputStreamReader(origin.getInputStream());
			BufferedReader read = new BufferedReader(isr);
			
			// Get all lines as one json object
			StringBuilder js = new StringBuilder();
			String line = read.readLine();
			
			while  (line != null && !line.isEmpty()){
				js.append(line);
				line = read.readLine();
			}
			// get the message
			JSONObject j = new JSONObject(js.toString());
			
			// send the message
			destination.getOutputStream().write((j.toString() + "\n\r").getBytes());
		}
	}
	
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		final ServerSocket server = new ServerSocket (port);
		System.out.println("Listening on port: " + port);
		
		while (true) {
			Socket client1  = server.accept();
			System.out.println("Recieved connection 1 from: " + client1.getInetAddress().getHostAddress());
			
			Socket client2  = server.accept();
			System.out.println("Recieved connection 2 from: " + client2.getInetAddress().getHostAddress());
			if (client1.isClosed() || client2.isClosed()) continue;
			
			// 1 method that has src and destination, sends appropriate message
			Thread c1 = new Thread(new Runnable(){
				public void run(){
					try {
						SimpleTextServer.forwardMessage(client1, client2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			Thread c2 = new Thread(new Runnable(){
				public void run(){
					try {
						SimpleTextServer.forwardMessage(client2, client1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			c1.start();
			c2.start();
			
			//client1.close();
			//client2.close();
			
			
		}
	}

}
