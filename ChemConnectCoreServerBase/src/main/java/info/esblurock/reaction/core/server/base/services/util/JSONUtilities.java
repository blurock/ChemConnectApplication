package info.esblurock.reaction.core.server.base.services.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class JSONUtilities {
	public static JSONObject getJSONObject(String response) throws IOException {
		URL url = new URL(response);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "x-www-form-urlencoded");
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException(
					"Failed : HTTP error code : \n" + conn.getResponseCode() + ": " + conn.getResponseMessage() + "\n");
		}
		return getJSON(conn.getInputStream());
	}

	public static JSONObject getJSON(InputStream inputStream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
		String output;
		StringBuilder build = new StringBuilder();
		while ((output = br.readLine()) != null) {
			build.append(output);
			build.append("\n");
		}
		String msg = build.toString();
		System.out.println("Message:\n" + msg);
		JSONObject jsonobj = new JSONObject(msg);
		return jsonobj;
	}

}
