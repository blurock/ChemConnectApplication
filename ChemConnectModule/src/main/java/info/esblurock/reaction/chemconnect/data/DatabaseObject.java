package info.esblurock.reaction.chemconnect.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/** This is the base class for all data objects in the database.
 * ALL classes are derived from this one.
 * 
 * In searching, the access is used to determine whether the data is accessible to the user.
 * 
 * 'owner' represents who inputted the data and who can give access rights. 
 * It can be used as a cleanup measure
 * @author edwardblurock
 */
@Entity
public class DatabaseObject  implements Serializable {
	 private static final long serialVersionUID = 1L;
	
	@Id  Long key;
	@Index  String identifier;
	@Index  String access;
	@Index  String sourceID;
	@Index  Date creationDate;
	@Index String owner;
	
	/** Empty constructor
	 *  fills with current date and public access and owner
	 */
	public DatabaseObject() {
		this.fill(null,UserAccountKeys.publicAccess,UserAccountKeys.publicOwner,"");
	}
	
	/**
	 * @param id The identifier of the data object
	 */
	public DatabaseObject(String id,String sourceID) {
		this.fill(id,UserAccountKeys.publicAccess,UserAccountKeys.publicOwner,sourceID);
	}

	/** Database object with access and owner
	 * @param access Access keyword (username, group or 'Public')
	 * @param owner The owner username who created the data
	 * 
	 * Current date (and null for key)
	 */
	public DatabaseObject(String id, String access, String owner,String sourceID) {
		this.fill(id,access,owner,sourceID);
	}
	
	public DatabaseObject(DatabaseObject obj) {
		this.fill(obj.getIdentifier(),obj.getAccess(),obj.getOwner(),obj.getSourceID());
		
		key = obj.getKey();
	}
	public void fill(DatabaseObject obj) {
		this.fill(obj.getIdentifier(),obj.getAccess(),obj.getOwner(),obj.getSourceID());
		key = obj.getKey();
	}
	
	public void fill(String id, String access, String owner,String sourceID) {
		this.sourceID = sourceID;
		this.identifier = id;
		this.access = access;
		this.owner = owner;
		creationDate = new Date();
		key = null;
	}	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Long getKey() {
		return key;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getIdentifier() {
		return identifier;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	public void nullKey() {
		key = null;
	}
	
	public void fillMapOfValues(Map<String,String> map) {
		      map.put("identifier", this.getIdentifier());
		      map.put("access", this.getAccess());
		      map.put("sourceID", this.getSourceID());
		      //map.put("creationDate", this.getCreationDate());
		      map.put("owner", this.getOwner());
		   }
	public void retrieveFromMap(Map<String,String> map) {
		      this.setIdentifier( map.get("identifier"));
		      this.setAccess( map.get("access"));
		      this.setSourceID( map.get("sourceID"));
		      this.setOwner( map.get("owner"));
		   }

	
	
	@Override
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(prefix);
		builder.append(this.getClass().getSimpleName() + ": " + identifier + "\n");
		builder.append(prefix + "(");
		builder.append(access);
		builder.append(", ");
		builder.append(sourceID);
		builder.append(", ");
		builder.append(owner);
		builder.append(", ");
		builder.append(creationDate);
		builder.append(")\n");
		
		return builder.toString();
	}
	
}
