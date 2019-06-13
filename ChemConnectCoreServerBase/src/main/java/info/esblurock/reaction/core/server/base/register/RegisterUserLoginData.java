package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccountInformation;

public class RegisterUserLoginData {
	public static void register() {
		ObjectifyService.register(UserAccount.class);
		ObjectifyService.register(UserAccountInformation.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(UserAccount.class);
		ResetDatabaseObjects.resetClass(UserAccountInformation.class);
	}
}
