package networking.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;;

public class ApiServer {

	public final static int port = 8000; 
	
	public static void main(String[] args) throws IOException {
		final ServerSocket server = new ServerSocket (port);
		System.out.println("Listening on port: " + port);
		JSONObject j = new JSONObject();
		while (true) {
			Socket client  = server.accept();
			InputStreamReader isr = new InputStreamReader(client.getInputStream());
			BufferedReader read = new BufferedReader(isr);
			
			String line = read.readLine();
			StringBuilder str = new StringBuilder();
			while (!line.isEmpty()){
				str.append(line + "\n");
				line = read.readLine();
			}
			System.out.println(str);
			
			
						client.close();

		}
		
	}
}
