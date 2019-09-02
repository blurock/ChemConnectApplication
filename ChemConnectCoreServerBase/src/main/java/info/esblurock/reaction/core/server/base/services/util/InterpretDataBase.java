package info.esblurock.reaction.core.server.base.services.util;

public class InterpretDataBase {
	
	public static InterpretDataInterface valueOf(String element) {
		InterpretDataInterface ans = null;
		try {
			ans = InterpretBaseData.valueOf(element);
		} catch(IllegalArgumentException ex) {
			
		}
		return ans;
	}

}
