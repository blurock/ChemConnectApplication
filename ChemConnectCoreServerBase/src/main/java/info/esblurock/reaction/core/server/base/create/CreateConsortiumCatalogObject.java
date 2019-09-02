package info.esblurock.reaction.core.server.base.create;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.Consortium;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.ConsortiumMember;
import info.esblurock.reaction.core.server.base.services.util.InterpretBaseData;

public class CreateConsortiumCatalogObject {
	
	public static DatabaseObjectHierarchy createConsortiumCatalogObject(DataCatalogID catid, String consortiumName) {
		DatabaseObjectHierarchy hierarchy = InterpretBaseData.Consortium.createEmptyObject(catid);
		Consortium consortium = (Consortium) hierarchy.getObject();
		consortium.setConsortiumName(consortiumName);
		catid.setIdentifier(consortium.getCatalogDataID());
		CreateContactObjects.insertDataCatalogID(hierarchy, catid);
		
		addConsortiumMember(hierarchy, consortiumName, catid.getOwner());
		return hierarchy;
	}
	
	public static DatabaseObjectHierarchy addConsortiumMember(DatabaseObjectHierarchy consortiumhierarchy, 
			String consortiumName, String consortiumMember) {
		Consortium consortium = (Consortium) consortiumhierarchy.getObject();

		DatabaseObjectHierarchy hierarchy = InterpretBaseData.ConsortiumMember.createEmptyObject(consortium);
		ConsortiumMember member = (ConsortiumMember) hierarchy.getObject();
		member.setConsortiumName(consortiumName);
		member.setConsortiumMemberName(consortiumMember);
		
		DatabaseObjectHierarchy membershier = consortiumhierarchy.getSubObject(consortium.getConsortiumMember());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) membershier.getObject();

		int numlinks = membershier.getSubobjects().size();
		String numlinkS = Integer.toString(numlinks);
		String newid = member.getIdentifier() + numlinkS;
		member.setIdentifier(newid);
		multilnk.setNumberOfElements(numlinks+1);
		membershier.addSubobject(hierarchy);
		
		
		return hierarchy;
		
	}
}
