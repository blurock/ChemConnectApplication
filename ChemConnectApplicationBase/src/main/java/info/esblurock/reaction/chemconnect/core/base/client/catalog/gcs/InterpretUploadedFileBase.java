package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs;

public class InterpretUploadedFileBase {
	public InterpretUploadedFileInterface valueOf(String element) {
		InterpretUploadedFileInterface ans = null;
		try {
			ans = InterpretUploadedFile.valueOf(element);
		} catch(Exception ex) {
			
		}
		return ans;
	}
}
