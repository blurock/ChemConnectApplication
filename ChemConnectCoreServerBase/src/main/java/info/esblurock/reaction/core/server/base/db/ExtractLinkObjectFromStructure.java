package info.esblurock.reaction.core.server.base.db;

import java.io.IOException;
import java.util.Iterator;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataObjectLink;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class ExtractLinkObjectFromStructure {

	public static DatabaseObjectHierarchy extract(DatabaseObjectHierarchy hierarchy, String linktypeid) throws IOException {
		DatabaseObjectHierarchy objecthierarchy = null;
		
		ChemConnectDataStructure structure = (ChemConnectDataStructure) hierarchy.getObject();
		DatabaseObjectHierarchy linkhier = hierarchy.getSubObject(structure.getChemConnectObjectLink());
		Iterator<DatabaseObjectHierarchy> iter = linkhier.getSubobjects().iterator();
		boolean notfound = true;
		String id = null;
		while(iter.hasNext() && notfound) {
			DatabaseObjectHierarchy subhier = iter.next();
			DataObjectLink link = (DataObjectLink) subhier.getObject();
			if(link.getLinkConcept().compareTo(linktypeid) == 0) {
				notfound = false;
				id = link.getDataStructure();
			}
		}
		if(id != null) {
			
			String structuretype = DatasetOntologyParseBase.findObjectTypeFromLinkConcept(linktypeid);
			System.out.println("ExtractLinkObjectFromStructure structuretype=" + structuretype);
			if(structuretype != null) {
				objecthierarchy = ExtractCatalogInformation.getCatalogObject(id, structuretype);
			} else {
				throw new IOException("Link structure type='" + linktypeid + "' not found");				
			}
		} else {
			throw new IOException("Link structure (type=" + linktypeid + ") not found in links");
		}
		
		return objecthierarchy;
	}
}
