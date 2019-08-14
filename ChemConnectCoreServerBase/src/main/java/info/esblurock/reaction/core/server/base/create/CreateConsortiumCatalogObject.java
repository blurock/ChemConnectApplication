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
		
		DatabaseObjectHierarchy multimemhier = hierarchy.getSubObject(consortium.getConsortiumMember());
		ChemConnectCompoundMultiple multilnk = (ChemConnectCompoundMultiple) multimemhier.getObject();
		DatabaseObjectHierarchy memhierarchy = InterpretBaseData.ConsortiumMember.createEmptyObject(multilnk);
		ConsortiumMember member = (ConsortiumMember) memhierarchy.getObject();
		multilnk.setNumberOfElements(1);
		String newid = member.getIdentifier() + "1";
		member.setIdentifier(newid);
		member.setConsortiumMemberName(catid.getOwner());
		member.setConsortiumName(consortiumName);
		multimemhier.addSubobject(memhierarchy);
		return hierarchy;
	}
}
