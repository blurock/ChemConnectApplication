package info.esblurock.reaction.core.server.base.authorization;


import java.io.IOException;

import org.junit.jupiter.api.Test;

import info.esblurock.reaction.chemconnect.core.base.authorization.ClientIDInformation;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.core.server.base.authentification.AuthorizationIDs;

class AutthorizationIDsReadTest {

	@Test
	void test() {
		System.out.println();
		String homedir = System.getProperty("user.home");
		String filename = homedir + "/.authorization-chemconnect/clientIDs.yaml";
		AuthorizationIDs.readInAuthorizationIDs(filename);
		System.out.println(AuthorizationIDs.printOutAuthorizationIDMap());
		
		try {
			ClientIDInformation info = AuthorizationIDs.getClientAuthorizationInfo(UserAccountKeys.LinkedInClientKey);
			System.out.println(info.toString("LinkedIn: "));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ClientIDInformation info = AuthorizationIDs.getClientAuthorizationInfo(UserAccountKeys.FacebookClientKey);
			System.out.println(info.toString("Facebook: "));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ClientIDInformation info = AuthorizationIDs.getClientAuthorizationInfo(UserAccountKeys.GoogleClientKey);
			System.out.println(info.toString("Google: "));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
