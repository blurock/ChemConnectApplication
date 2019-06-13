package info.esblurock.reaction.core.server.base.services.util;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class ContextAndSessionUtilities {
	public static String userattribute = "user";

	HttpSession session;
	ServletContext context;

	public ContextAndSessionUtilities(
			ServletContext context,
			HttpSession session) {
		this.context = context;
		this.session = session;
	}
	
	public String getId() {
		return session.getId();
	}
	
	public void removeUser() {
		deleteUserFromSession();
		removeUserFromContext();
		
	    if (session != null) {
		      session.invalidate();
		}
	}
	public void removeUserFromContext() {
		System.out.println("removeUserFromContext():");
		context.removeAttribute(userattribute);
	}


	public void deleteUserFromSession() {
		session.removeAttribute(userattribute);
	}
	
	public void printOutSessionAttributes() {
		System.out.println("FileUploadServlet session variables for session: " + this.getId());
		Enumeration<String> attnames = session.getAttributeNames();
		while(attnames.hasMoreElements()) {
			String name = attnames.nextElement();
			System.out.println("FileUploadServlet session variable '" + name + "'\n" + session.getAttribute(name));
		}
		System.out.println("FileUploadServlet session variables for session: done");
	
		attnames = context.getAttributeNames();
		while(attnames.hasMoreElements()) {
			String name = attnames.nextElement();
			System.out.println("FileUploadServlet context variable '" + name + "'\n" + context.getAttribute(name));
		}
		System.out.println("FileUploadServlet context variables for session: done");
	}
}
