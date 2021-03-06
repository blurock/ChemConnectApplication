package info.esblurock.reaction.core.server.base.interpret;

import org.junit.Assert;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataInterface;

class InterpretDataValueOf {

	@Test
	void test() {
		InterpretDataBase interpretDataBase = new InterpretDataBase();
		InterpretDataInterface interpret1 = interpretDataBase.valueOf("DatabaseObject");
		System.out.println("DatabaseObject: " + interpret1.canonicalClassName());
		
		InterpretDataInterface interpret2 = interpretDataBase.valueOf("XXXX");
		if(interpret2 == null) {
			System.out.println("XXXX returned null as it should");
		} else {
			Assert.fail();
		}
		
	}

}
