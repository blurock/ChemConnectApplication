package info.esblurock.reaction.core.server.base.db;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;



@SuppressWarnings("serial")
@Entity
public class BlobKeyCorrespondence extends DatabaseObject {
	
    @Index
    String keyAsString;

    @Unindex
    BlobKey blobKey;

    public BlobKeyCorrespondence() { }
    public BlobKeyCorrespondence(String keyAsString, BlobKey blobKey) {
        this.keyAsString = keyAsString; 
        this.blobKey = blobKey;
    }
	public String getKeyAsString() {
		return keyAsString;
	}
	public BlobKey getBlobKey() {
		return blobKey;
	}


}
