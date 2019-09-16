package info.esblurock.reaction.core.server.base.authentification;

import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;

public class InitializationBase {
	static InterpretDataBase interpretDataBase;
	
	public static InterpretDataBase getInterpret() {
		if(interpretDataBase == null) {
			interpretDataBase = new InterpretDataBase();
		}
		return interpretDataBase;
	}
	
	public static void initialization() {
		interpretDataBase = new InterpretDataBase();
	}

}
