package info.esblurock.reaction.chemconnect.core.base.client.authentication;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.Cookies;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.client.util.TextUtilities;
import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;

public class SetUpUserCookies {
	public static void setupDefaultGuestUserCookies() {
		/*
		ArrayList<String> privs = new ArrayList<String>();
		privs.add(UserAccountKeys.accessLogin);
		privs.add(UserAccountKeys.accessQuery);
		*/
		setDefaultCookie("account_name", "Guest");
		UserSessionData usession = new UserSessionData("guest", "", "", "", UserAccountKeys.accessTypeQuery);
		setup(usession);
	}
	
	
	public static void setup(UserSessionData result) {
		String sessionID = result.getSessionID();
		final long DURATION = 1000 * 60 * 60;
		setDefaultCookie("sid", sessionID);
		setDefaultCookie("user", result.getUserName());
		setDefaultCookie("level", result.getUserLevel());
		setDefaultCookie("account_name", result.getUserName());
		MaterialToast.fireToast("Welcome: " + result.getUserName() +
				"(" + TextUtilities.removeNamespace(result.getUserLevel()) + ")");
	}

	public static void setDefaultCookie(String name, String value) {
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis()
				+ DURATION);
		Cookies.setCookie(name, value,
				expires, null, "/", false);		
	}
	
	public static void setCookie(String access, ArrayList<String> accesslist) {
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis()
				+ DURATION);
		String ansB = Boolean.FALSE.toString();
		if(accesslist.contains(access)) {
			ansB = Boolean.TRUE.toString();
		}
		Cookies.setCookie(access, ansB, expires, null, "/", false);
	}

	public static void removeCookie(String name) {
		Cookies.removeCookie(name);
	}
	
	public static void zeroAllCookies() {
		removeCookie("account_name");
		removeCookie("family_name");
		removeCookie("given_name");
		removeCookie("user");
		removeCookie("auth_id");
		removeCookie("authorizationType");
		removeCookie("hasAccount");
		removeCookie("sid");
		removeCookie("level");
		/*
		removeCookie(MetaDataKeywords.accessUserDataInput);
		removeCookie(MetaDataKeywords.accessUserDataDelete);
		removeCookie(MetaDataKeywords.accessDataInput);
		removeCookie(MetaDataKeywords.accessDataDelete);
		*/
		/*
		Collection<String> names = Cookies.getCookieNames();
		for(String name: names) {
			Cookies.removeCookie(name);
		}
		*/
	}
	
	
}
