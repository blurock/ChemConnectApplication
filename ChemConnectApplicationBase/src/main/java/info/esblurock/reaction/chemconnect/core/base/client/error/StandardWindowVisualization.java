package info.esblurock.reaction.chemconnect.core.base.client.error;

import com.google.gwt.user.client.Window;

public class StandardWindowVisualization {

	public static void errorWindowMessage(String context, String message) {
		Window.alert("ERROR\n" + context + "\n\n" + message );
	}
	
	public static void successWindowMessage(String message) {
		Window.alert(message);
	}
}
