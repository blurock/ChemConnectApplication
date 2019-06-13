package info.esblurock.reaction.core.ontology.base.user;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import info.esblurock.reaction.core.ontology.base.authentification.UserQueries;

public class UserTaskPriviledgeTest {

	@Test
	public void test() {
		String task = "dataset:ChemConnectTaskQueryCatalogConsortium";
		String userlevel = "dataset:DataUser";
		boolean ans1 = UserQueries.allowedTask(task, userlevel);
		System.out.println("(" + task + ": " + userlevel + ")  "+ ans1);

		userlevel = "dataset:Query";
		boolean ans2 = UserQueries.allowedTask(task, userlevel);
		System.out.println("(" + task + ": " + userlevel + ")  "+ ans2);

		userlevel = "dataset:StandardUser";
		boolean ans3 = UserQueries.allowedTask(task, userlevel);
		System.out.println("(" + task + ": " + userlevel + ")  "+ ans3);
		
		userlevel = "dataset:Administrator";
		boolean ans4 = UserQueries.allowedTask(task, userlevel);
		System.out.println("(" + task + ": " + userlevel + ")  "+ ans4);
		
		userlevel = "dataset:SuperUser";
		boolean ans5 = UserQueries.allowedTask(task, userlevel);
		System.out.println("(" + task + ": " + userlevel + ")  "+ ans5);
}

}
