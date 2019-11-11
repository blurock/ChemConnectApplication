package info.esblurock.reaction.chemconnect.core.base.client;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetUpCollapsibleItemBase;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.InterpretUploadedFileBase;
import info.esblurock.reaction.chemconnect.core.base.client.visualize.VisualizeMediaBase;

public class ClientEnumerateUtilities {
	
	public static SetUpCollapsibleItemBase setUpCollapsible;
	public static VisualizeMediaBase visualizeMedia;
	public static InterpretUploadedFileBase interpretUploadedFile;
	
	public static void setSetUpCollapsibleItemBase(SetUpCollapsibleItemBase setup) {
		setUpCollapsible = setup;
	}
		
}
