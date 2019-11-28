package info.esblurock.reaction.chemconnect.server.base.authentification;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.chemconnect.data.session.UserSessionData;
import info.esblurock.reaction.chemconnect.server.base.ontology.UserQueries;


public class UserPrivileges {

	@Test
	public void test() {
		UserSessionData session1 = new UserSessionData();
		session1.setUserLevel("dataset:DataUser");
		Set<String> privileges1 = UserQueries.getListOfInputPriviledges(session1);
		System.out.println("getListOfInputPriviledges: " + session1.getUserLevel() + "\n" + privileges1.toString());
		
		UserSessionData session2 = new UserSessionData();
		session2.setUserLevel("dataset:StandardUser");
		Set<String> privileges2 = UserQueries.getListOfInputPriviledges(session2);
		System.out.println("getListOfInputPriviledges: " + session2.getUserLevel() + "\n" + privileges2.toString());
		
		UserSessionData session3 = new UserSessionData();
		session3.setUserLevel("dataset:Administrator");
		Set<String> privileges3 = UserQueries.getListOfInputPriviledges(session3);
		System.out.println("getListOfInputPriviledges: " + session3.getUserLevel() + "\n" + privileges3.toString());
		
		UserSessionData session4 = new UserSessionData();
		session4.setUserLevel("dataset:DataUser");
		Set<String> privileges4 = UserQueries.getListOfModifyPriviledges(session4);
		System.out.println("getListOfModifyPriviledges: " + session4.getUserLevel() + "\n" + privileges4.toString());
		
		UserSessionData session5 = new UserSessionData();
		session5.setUserLevel("dataset:StandardUser");
		Set<String> privileges5 = UserQueries.getListOfModifyPriviledges(session5);
		System.out.println("getListOfModifyPriviledges: " + session5.getUserLevel() + "\n" + privileges5.toString());
		
		UserSessionData session6 = new UserSessionData();
		session6.setUserLevel("dataset:Administrator");
		Set<String> privileges6 = UserQueries.getListOfModifyPriviledges(session6);
		System.out.println("getListOfModifyPriviledges: " + session6.getUserLevel() + "\n" + privileges6.toString());
		
	}

}
