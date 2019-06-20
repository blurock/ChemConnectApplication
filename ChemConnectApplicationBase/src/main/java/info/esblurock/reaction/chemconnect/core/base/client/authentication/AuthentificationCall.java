package info.esblurock.reaction.chemconnect.core.base.client.authentication;

public abstract class AuthentificationCall {
	protected AuthentificationTopPanelInterface toppanel;
	
	public AuthentificationCall(AuthentificationTopPanelInterface toppanel) {
		this.toppanel = toppanel;
	}

	public abstract void initiateAthentification();
}
