package info.esblurock.reaction.core.server.base.authentification;

import info.esblurock.reaction.core.server.base.db.interpret.FileInterpretationBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataInterface;

public class InitializationBase {
	public static InterpretDataBase interpretDataBase;
	public static FileInterpretationBase fileInterpretationBase;
	
	public static InterpretDataBase getInterpret() {
		if(interpretDataBase == null) {
			interpretDataBase = new InterpretDataBase();
		}
		return interpretDataBase;
	}
	
	public static InterpretDataInterface valueOf(String element) {
		return interpretDataBase.valueOf(element);
	}
	
	public static void initialization() {
		interpretDataBase = new InterpretDataBase();
	}

}
