package networking.server;

import org.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject j = new JSONObject("{key: value}");
		System.out.println(j.toString());
		System.out.println(j.get("key"));
	}

}
