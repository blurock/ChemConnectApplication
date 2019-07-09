package info.esblurock.reaction.chemconnect.core.base.metadata;

public class UserAccountKeys {
	public static String accessTypeQuery = "dataset:Query";
	public static String accessTypeStandardUser = "dataset:StandardUser";
	public static String accessTypeDataUser = "dataset:DataUser";
	public static String accessTypeAdministrator = "dataset:Administrator";
	public static String accessTypeSuperUser = "dataset:SuperUser";

	public static String accessQueryDataQueryUser = "dataset:AccountPriviledgeDataQueryUser";
	public static String accessQueryDataQueryPublic = "dataset:AccountPriviledgeDataQueryPublic";
	public static String accessQueryDataQueryConsortium = "dataset:AccountPriviledgeDataQueryConsortium";

	public static String publicAccess = "Public";
	public static String publicOwner = "Administration";
	public static String access = "access";
	
	// Authorization keys
	public static String LinkedInClientKey = "LinkedIn";
	public static String GoogleClientKey = "Google";
	public static String FacebookClientKey = "Facebook";
	
	public static String LinkedInSecretKey = "linkedin";
	public static String GoogleSecretKey = "google";
	public static String FacebookSecretKey = "facebook";
	
	public static String SECRET_COOKIE_NAME = "secret";
	
	public static String LinkedInAuthURL = "https://www.linkedin.com/oauth/v2/authorization";
	public static String GoogleAuthURL = "https://accounts.google.com/o/oauth2/v2/auth";
	public static String FacebookAuthURL = "https://graph.facebook.com/oauth/access_token";
	
	// parameters
	public static String AuthCodeParameterKey = "code";
	public static String AuthStateParameterKey = "state";
	// web.xml parameters
	public static String AuthCallbackParameterKey = "callback";
	public static String AuthRemoteHostParameterKey = "remotehost";
	public static String AuthLocalhostParameterKey = "localhost";

}
