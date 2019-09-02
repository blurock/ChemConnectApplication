package info.esblurock.reaction.core.server.base.services.util;

import java.io.IOException;
import java.util.Map;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public interface InterpretDataInterface {
	
	public abstract DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj);
	
	public abstract DatabaseObject fillFromYamlString(DatabaseObject top, Map<String, Object> yaml, String sourceID)
			throws IOException;

	public abstract Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException;

	public abstract DatabaseObject readElementFromDatabase(String identifier) throws IOException;

	public abstract String canonicalClassName();

}
