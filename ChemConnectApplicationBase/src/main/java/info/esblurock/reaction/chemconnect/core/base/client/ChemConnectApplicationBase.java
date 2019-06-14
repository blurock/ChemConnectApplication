package info.esblurock.reaction.chemconnect.core.base.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ChemConnectApplicationBase implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		BaseChemConnectPanel toppanel = new BaseChemConnectPanel();
		RootPanel.get().add(toppanel);

	}
}
