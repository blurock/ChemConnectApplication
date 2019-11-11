package info.esblurock.reaction.chemconnect.core.base.client.visualize;

public class VisualizeMediaBase {

	public VisualizeMediaInterface valueOf(String element) {
		VisualizeMediaInterface visual = null;
		try {
			visual = VisualizeMedia.valueOf(element);
		} catch(Exception ex) {
			
		}
		return visual;
	}
}
