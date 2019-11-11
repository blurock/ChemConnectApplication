package info.esblurock.reaction.core.server.expdata.util;

import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataInterface;

public class InterpretDataExpData extends InterpretDataBase {
	
	@Override
	public InterpretDataInterface valueOf(String element) throws IllegalArgumentException {
		InterpretDataInterface ans = InterpretBaseData.valueOf(element);
		if(ans == null) {
			ans = InterpretExpDataData.valueOf(element);
		}
		return ans;
	}


}
