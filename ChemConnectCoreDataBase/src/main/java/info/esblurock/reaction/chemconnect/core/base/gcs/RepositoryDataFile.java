package info.esblurock.reaction.chemconnect.core.base.gcs;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.base.ChemConnectDataStructure;

@SuppressWarnings("serial")
@Entity
public class RepositoryDataFile extends ChemConnectDataStructure {
	

	public RepositoryDataFile() {
	}

	public RepositoryDataFile(ChemConnectDataStructure datastructure) {
		super(datastructure);
	}

	
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Repository Data File\n" );
		return build.toString();
	}
}
