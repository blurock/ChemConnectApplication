package info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts;

import java.util.ArrayList;

import info.esblurock.reaction.chemconnect.core.common.base.client.async.BaseCatalogAccess;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.BaseCatalogAccessAsync;

public class ChooseFromConceptHierarchyFromDefinition extends ChooseFromConceptHierarchies {

	public ChooseFromConceptHierarchyFromDefinition(ArrayList<String> choices, ChooseFromConceptHeirarchy chosen) {
		super(choices, chosen);
	}
	@Override
	public void treeHierarchyCall(String topconcept) {
		BaseCatalogAccessAsync async = BaseCatalogAccess.Util.getInstance();
		ConceptHierarchyCallback callback = new ConceptHierarchyCallback(this);
		async.hierarchyFromPrimitiveStructure(topconcept,callback);
	}
	
}
