package info.esblurock.reaction.chemconnect.core.base.client.catalog.choose;

import java.util.List;

public interface RetrieveOwnerPrivilegesInterface {
	public void setInOwnerPrivilegesSuccess(List<String> owners);
	public void setInOwnerPrivilegesFailure(Throwable caught);
}
