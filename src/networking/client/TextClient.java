package networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONObject;

public class TextClient {
	
	public static String IP_ADDRESS = "127.0.0.1";
	public static int SERVER_SOCKET = 8000;
	public static String USERNAME = "";
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		if (args.length > 1){
			IP_ADDRESS = args[1];
			SERVER_SOCKET = Integer.parseInt(args[2]);
		}
		
		try {
			Socket server = new Socket(IP_ADDRESS, SERVER_SOCKET);
			
			
			//while (true){
				// 1 thread to print incoming text from server
				// 1 thread to get input from user
				Thread responses = new Thread(new Runnable() {
					public void run(){
						try {
							TextClient.getResponse(server);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				responses.start();
				
				Thread userInput = new Thread(new Runnable(){
					public void run(){
						try {
							TextClient.userInput(server, in);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				userInput.start();
				try {
					userInput.join();
					responses.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			//}
			
		} catch (UnknownHostException e) {
			System.err.print("Could not get connection to " + IP_ADDRESS + ":" + SERVER_SOCKET);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.print("Could not get connection to " + IP_ADDRESS + ":" + SERVER_SOCKET);
			e.printStackTrace();
		}
		
		

	}
	
	public static void getResponse(Socket server) throws IOException{
		while (true){
			InputStreamReader isr = new InputStreamReader(server.getInputStream());
			BufferedReader read = new BufferedReader(isr);
			// Get all lines as one json object
			StringBuilder js = new StringBuilder();
			String line = read.readLine();
			
			while  (line != null && !line.isEmpty()){
				js.append(line);
				line = read.readLine();
			}
			JSONObject j = new JSONObject(js.toString());
			System.out.println( "\n\t" + j.get("message") + "\n>");
		}
	}
	
	public static void userInput(Socket server, Scanner in) throws IOException{
		try{
		while(true){
			System.out.print(">");
			String message = in.nextLine();
			if (message.isEmpty()) continue;
			JSONObject j = new JSONObject();
			j.put("message", message);
			server.getOutputStream().write((j.toString() + "\n\r").getBytes());
		}
		} catch(Exception e){
			e.printStackTrace();
			return;
		}
	}
}
