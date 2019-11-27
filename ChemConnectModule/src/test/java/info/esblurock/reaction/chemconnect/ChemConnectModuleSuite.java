package info.esblurock.reaction.chemconnect;

import info.esblurock.reaction.chemconnect.client.ChemConnectModuleTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ChemConnectModuleSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for ChemConnectModule");
		suite.addTestSuite(ChemConnectModuleTest.class);
		return suite;
	}
}
