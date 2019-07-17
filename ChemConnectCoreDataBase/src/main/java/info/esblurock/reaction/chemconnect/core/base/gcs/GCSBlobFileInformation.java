package info.esblurock.reaction.chemconnect.core.base.gcs;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@Entity
@SuppressWarnings("serial")
public class GCSBlobFileInformation extends ChemConnectCompoundDataStructure {

	@Index
	String path;
	@Index
	String filename;
	@Index
	String filetype;
	@Index
	String description;
	
	public GCSBlobFileInformation() {
	}
	public GCSBlobFileInformation(ChemConnectCompoundDataStructure obj, 
			String path, String filename, String filetype, 
			String description) {
		super(obj);
		this.path = path;
		this.filename = filename;
		this.filetype = filetype;
		this.description = description;
}
	public GCSBlobFileInformation(GCSBlobFileInformation info) {
		super(info);
		this.path = info.getPath();
		this.filename = info.getFilename();
		this.filetype = info.getFiletype();
		this.description = info.getDescription();		
	}
	public String getPath() {
		return path;
	}
	public String getFilename() {
		return filename;
	}
	public String getFiletype() {
		return filetype;
	}
	public String getDescription() {
		return description;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGSFilename() {
		String name = filename;
		if(path != null) {
			if(path.length() > 0) {
				name =  path + "/" + filename;
			}
		}
		return name;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Full path name: '" + getGSFilename() + "'\n");
		build.append(prefix + "Type          : " + filetype + "\n");
		build.append(prefix + "Description   :" + description + ")\n");
		return build.toString();
	}
	
}
