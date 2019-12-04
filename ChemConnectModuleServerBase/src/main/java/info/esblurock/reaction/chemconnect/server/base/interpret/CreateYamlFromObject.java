package info.esblurock.reaction.chemconnect.server.base.interpret;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.data.DatabaseObject;
import info.esblurock.reaction.chemconnect.data.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class CreateYamlFromObject {
	
	public static Map<String, Object> createYamlFromHierarchy(String structure, DatabaseObjectHierarchy hierarchy) {
		Map<String, Object> map = createYamlFromObject(structure, hierarchy.getObject());
		
		for(DatabaseObjectHierarchy subhierarchy : hierarchy.getSubobjects()) {
			Map<String, Object> submap = createYamlFromObject(structure, subhierarchy.getObject());
			map.put(subhierarchy.getObject().getIdentifier(), submap);
		}
		return map;
	}

	public static Map<String, Object> createYamlFromObject(String structure, DatabaseObject obj) {
		
		Map<String, Object> objmap = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		
		obj.fillMapOfValues(map);
		Set<String> nameset = map.keySet();
		for(String name : nameset) {
			objmap.put(name, map.get(name));
		}
		
		return objmap;
	}
	
	public static DatabaseObject fillFromYamlString(String structure, DatabaseObject top, 
			Map<String, Object> yaml, String sourceID)
			throws IOException {
		
		Map<String, String> map = new HashMap<String, String>();
		retrieveFromMap(structure,map,yaml);
		DatabaseObject topclass = GenericCreateEmptyObject.getObjectClassFromCanonicalName(structure);
		topclass.retrieveFromMap(map);
		
		return topclass;
	}
	
	public static void retrieveFromMap(String structure, Map<String, String> map, Map<String, Object> yaml) {
		
		Set<String> nameset = new HashSet<String>();
		List<String> params = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				" <http://purl.org/dc/terms/hasPart>",false);
		List<String> records = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				"<http://www.w3.org/ns/dcat#record>",false);
		List<String> multrecords = DatasetOntologyParseBase.subObjectsOfConcept(structure,
				"<http://www.w3.org/ns/dcat#record>",true);
		nameset.addAll(params);
		nameset.addAll(records);
		nameset.addAll(multrecords);
		
		for(String name : nameset) {
			String param = (String) yaml.get(name);
			map.put(name, param);
		}
		String superclass = DatasetOntologyParseBase.getDirectSuperClass(structure);
		retrieveFromMap(superclass,map,yaml);
	}
	
	
}
