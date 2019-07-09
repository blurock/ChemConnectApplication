package info.esblurock.reaction.core.server.base.services.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.esblurock.reaction.chemconnect.core.base.metadata.UserAccountKeys;

public class ManageServerCookies {
	
	public static void setCookie(HttpServletResponse resp, String cookiename, String value) {
		Cookie redirectC = new Cookie(cookiename,value);
		redirectC.setMaxAge(60 * 60);
		resp.addCookie(redirectC);
	}
	
	
	public static String findCookie(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		String ans = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().compareTo(UserAccountKeys.SECRET_COOKIE_NAME) == 0) {
					ans = cookie.getValue();
				}
			}
		} else {
			System.out.println("Oauth2CallbackServlet after sendRedirect  SC_UNAUTHORIZED no cookies");
		}
		return ans;
	}
	
	public static void replaceCookieValue(HttpServletRequest req, String cookiename, String value) {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().compareTo(cookiename) == 0) {
				cookie.setValue(value);
			}
		}
	}
	


}
