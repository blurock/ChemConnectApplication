package info.esblurock.reaction.core.ontology.base.generation;


import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
public class StandardInformationGeneration extends StandardInformation {
	
	GeneratedClassObjects generated;
	
	public StandardInformationGeneration(StandardInformation info, GeneratedClassObjects generated) {
		super(info);
		this.generated = generated;
	}

	public String generation() {
		String enumeration = generationEnumeration();
		
		return enumeration;
	}
	
	public String generationEnumeration() {
		StringBuilder build = new StringBuilder();
		
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		build.append("  " + datastructure + "  {\n\n");
		
		stringCreateObject(build);
		stringCreateFillFromYamlString(build);
		stringCreateYamlFromObject(build);
		stringDatabaseWrite(build);
		stringCononicalName(build);
		
		build.append("  }");
		
		return build.toString();
	}
	
	void stringCreateFillFromYamlString(StringBuilder build) {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String subclass = ChemConnectCompoundDataStructure.removeNamespace(this.getSuperClass());
				
		String preamble = "   @Override\n" + 
				"   public DatabaseObject fillFromYamlString(\n" + 
				"             DatabaseObject top, Map<String, Object> yaml,\n" + 
				"             String sourceID) throws IOException {\n" + 
				"      " + datastructure + " datastructure = null;\n" + 
				"      InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf(\"" + subclass + "\");\n" + 
				"      " + subclass + "  subdata = (" + subclass + ") interpret.fillFromYamlString(top, yaml, sourceID);					\n" + 
				"";
		build.append(preamble);
		for(StandardGenerationInterface generation : information) {
			String genS = generation.stringCreateYamlObject();
			build.append("      " + genS + "\n");
		}
		String suffix = "      datastructure = new " + datastructure + "(subdata);\n";
		build.append(suffix);
		for(StandardGenerationInterface generation : information) {
			String statement = generation.stringCreateSetObject("datastructure",true);
			if(statement != null) {
				build.append(statement + "\n");
			}
		}
		
		String returnS = "      return datastructure;\n" + 
				"   }\n";
		build.append(returnS);
		
	}
	
	void stringCreateObject(StringBuilder build) {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String superclass = ChemConnectCompoundDataStructure.removeNamespace(this.getSuperClass());
		String preamble = "   @Override\n" + 
				"   public DatabaseObjectHierarchy createEmptyObject(DatabaseObject obj) {\n" + 
				"      DatabaseObject refobj = new DatabaseObject(obj);\n" + 
				"      refobj.nullKey();\n" + 
				"      DataElementInformation element = DatasetOntologyParseBase\n" + 
				"           .getSubElementStructureFromIDObject(\"" + this.getConceptname() + "\");\n" + 
				"      String catid = InterpretBaseDataUtilities.createSuffix(obj, element);\n" + 
				"      refobj.setIdentifier(catid);\n" + 
				"\n";
		
		build.append(preamble);
		
		String subclassS = 
				"      InterpretDataInterface interpret = InitializationBase.interpretDataBase.valueOf(\"" + superclass + "\");\n" + 
				"      DatabaseObjectHierarchy structurehier = interpret.createEmptyObject(refobj);\n" + 
				"      " + superclass + " structure = (" + superclass + ") structurehier.getObject();\n" + 
				"\n";
		build.append(subclassS);
		
		for(StandardGenerationInterface generation : information) {
			String create = generation.stringCreateObject();
			if(create != null) {
				build.append(create);
			}
		}

		String constructor = "      " + datastructure + " info = new " + datastructure + "(structure);\n";
		build.append(constructor);
		
		for(StandardGenerationInterface generation : information) {
			String create = generation.stringCreateObject();
			if(create != null) {
				String statement = generation.stringCreateSetObject("info",false);
				if(statement != null) {
					build.append(statement + "\n");
				}
			}
			
		}
		String createcon = 
				"      info.setIdentifier(refobj.getIdentifier());\n" + 
				"      DatabaseObjectHierarchy infohier = new DatabaseObjectHierarchy(info);\n" + 
				"      infohier.transferSubObjects(structurehier);\n";
		build.append(createcon);
		
		for(StandardGenerationInterface generation : information) {
			String create = generation.stringCreateObject();
			if(create != null) {
				String addhier = "      infohier.addSubobject(" + generation.getHierarchyName()+ ");\n";
				build.append(addhier);
			}
			
		}
		
		
		String returnS = "      return infohier;\n   }\n";
		build.append(returnS);
	}

	
	void stringCreateYamlFromObject(StringBuilder build) {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String subclass = ChemConnectCompoundDataStructure.removeNamespace(this.getSuperClass());
		String preamble = 
				  "   @Override\n"
				+ "   public Map<String, Object> createYamlFromObject(DatabaseObject object) throws IOException {\n" + 
				"      " + datastructure + " structure = (" + datastructure + ") object;\n" + 
				"      InterpretDataInterface interpret = InterpretBaseData." + subclass + ";\n" + 
				"      Map<String, Object> map = interpret.createYamlFromObject(object);\n" + 
				"\n";
		
		build.append(preamble);
		for(StandardGenerationInterface generation : information) {
			String genS = generation.stringCreateYamlFromObject();
			build.append("           " + genS + "\n");
		}
		String suffix = "      return map;\n" + 
				"   }\n";
		build.append(suffix);
	}
	
	void stringDatabaseWrite(StringBuilder build) {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());		
		String statement = "   @Override\n" + 
				"   public " + datastructure + " readElementFromDatabase(\n" + 
				"         String identifier) throws IOException {\n" + 
				"      return QueryBase.getDatabaseObjectFromIdentifier(" + datastructure + ".class.getCanonicalName(),\n" + 
				"                          identifier);\n" + 
				"   }\n";
		build.append(statement);
	}
				
	void stringCononicalName(StringBuilder build) {
		String datastructure = ChemConnectCompoundDataStructure.removeNamespace(this.getConceptname());
		String proc = 
				"   @Override\n" + 
				"   public String canonicalClassName() {\n" + 
				"       return " + datastructure + ".class.getCanonicalName();\n" + 
				"   }\n" + 
				"";
		build.append(proc);
		}
}
