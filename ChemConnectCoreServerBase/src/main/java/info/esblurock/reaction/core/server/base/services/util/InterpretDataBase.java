package info.esblurock.reaction.core.server.base.services.util;

public class InterpretDataBase {
	
	/**
	 * @param element: The element to retrieve
	 * @return The interpretation
	 * @throws IllegalArgumentException This is thrown if the element is not found.  
	 * 
	 * The calling interface determines what to do if the interface is not found (for example if a simple item that doesn't need an interpretation).
	 */
	public InterpretDataInterface valueOf(String element) throws IllegalArgumentException {
		InterpretDataInterface ans = InterpretBaseData.valueOf(element);
		return ans;
	}

}
