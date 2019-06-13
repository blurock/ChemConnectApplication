package info.esblurock.reaction.chemconnect.core.base.login;

public class ExternalAuthorizationInformation {
	String useraccount;
	String given_name;
	String family_name;
	String authorizationID;
	String authorizationType;
	String hasAccount;
	String emailaddress;
	
	public ExternalAuthorizationInformation() {
		
	}
	
	
	public ExternalAuthorizationInformation(String useraccount, String given_name, String family_name, String authorizationID,
			String authorizationType, String hasAccount, String emailaddress) {
		super();
		this.useraccount = useraccount;
		this.given_name = given_name;
		this.family_name = family_name;
		this.authorizationID = authorizationID;
		this.authorizationType = authorizationType;
		this.hasAccount = hasAccount;
		this.emailaddress = emailaddress;
	}


	public String getUseraccount() {
		return useraccount;
	}


	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}


	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	public String getAuthorizationID() {
		return authorizationID;
	}
	public void setAuthorizationID(String authorizationID) {
		this.authorizationID = authorizationID;
	}
	public String getAuthorizationType() {
		return authorizationType;
	}
	public void setAuthorizationType(String authorizationType) {
		this.authorizationType = authorizationType;
	}
	public String getHasAccount() {
		return hasAccount;
	}
	public void setHasAccount(String hasAccount) {
		this.hasAccount = hasAccount;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	
	

}
