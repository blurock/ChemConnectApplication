package info.esblurock.reaction.core.server.base.authentification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import com.esotericsoftware.yamlbeans.YamlException;

import info.esblurock.reaction.chemconnect.core.base.authorization.AuthorizationIDMap;
import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.core.server.base.db.yaml.ReadWriteYamlDatabaseObjectHierarchy;

/** Manage the authoriztion information (setup and retrieval)
 * 
 * The authorization is read in from a yaml file in the home directory
 * 
 * @author edwardblurock
 *
 */
public class AuthorizationIDs {
	
	private static AuthorizationIDMap authorization = null;
	
	private static String CLIENT_ID_KEY = "clientID";
	private static String CLIENT_SECRET_KEY = "clientSecret";
	private static String HOME_DIR_KEY = "user.home";
	private static String AUTH_DIR_KEY = "authdirectory";
	private static String AUTH_FILENAME_KEY = "authfilename";
	
	
	/** Read in authorization information from home directory
	 * 
	 * @param context The server context (to get the file with the authorization information)
	 * 
	 * From init parameters
	 * <ul>
	 * <li> Home directory 
	 * <li> Authorization directory (relative to home)
	 * <li> Authorization info filename
	 * <ul>
	 * 
	 * The file is read in as yaml (ReadWriteYamlDatabaseObjectHierarchy.stringToYamlMap).
	 * The resulting map is parsed into a set of ClientIDInformation
	 * @throws IOException 
	 * 
	 */
	public static void readInAuthorizationIDs(ServletContext context) throws IOException {
		String authdirectory = context.getInitParameter(AUTH_DIR_KEY);
		String homedirectory = System.getProperty(HOME_DIR_KEY);
		String filenameS = context.getInitParameter(AUTH_FILENAME_KEY);
		
		File directoryF = new File(homedirectory,authdirectory);
		File filenameF = new File(directoryF,filenameS);
		String filename = filenameF.toString();

		readInAuthorizationIDs(filename);
	}
	
	public static void readInAuthorizationIDs(String filename) throws IOException {
		if(authorization == null) {
			authorization = new AuthorizationIDMap();
		}
		File fileF = new File(filename);
		try {
			FileInputStream in = new FileInputStream(fileF);
			Map<String,Object> map = ReadWriteYamlDatabaseObjectHierarchy.stringToYamlMap(in);
			
			Set<String> clientnames = map.keySet();
			for(String clientname : clientnames) {
				Map<String,String> idinfo = (Map<String,String>) map.get(clientname);
				String clientID = idinfo.get(CLIENT_ID_KEY);
				String clientSecret = idinfo.get(CLIENT_SECRET_KEY);
				ClientIDInformation info = new ClientIDInformation(clientname, clientID, clientSecret);
				authorization.insert(info);
			}
		
		} catch (YamlException e) {
			System.err.println("Error in reading authorization file: " + filename);
			throw new IOException("Error in reading authorization file: " + filename);
		} catch (FileNotFoundException e) {
			System.out.println("not found: " + filename);
			e.printStackTrace();
			throw new IOException("");
		}
	}
	
	/** Get client authorization information
	 * 
	 * @param clientname The name of the client of the ids
	 * @return The client authorization information
	 * @throws IOException If the retrieval is not successful
	 */
	public static ClientIDInformation getClientAuthorizationInfo(String clientname) throws IOException {
		if(authorization == null) {
			throw new IOException("Authorization information not read in yet");
		}
		ClientIDInformation info = authorization.getClientInfo(clientname);
		if(info == null) {
			throw new IOException("Authorization info not found for: '" + clientname + "'");
		}
		return info;
	}
	public static String printOutAuthorizationIDMap() {
		return printOutAuthorizationIDMap("");
	}
		
	public static String printOutAuthorizationIDMap(String prefix) {
		return authorization.toString();
	}
}
