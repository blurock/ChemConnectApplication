package info.esblurock.reaction.chemconnect.core.base.transaction;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;


// TODO: Auto-generated Javadoc
/**
 * The Class TransactionInfo. This is the set of {@link KeywordRDF}.
 * If the number of {@link KeywordRDF} exceeds maxSize, 
 * 
 * The array of {@link KeywordRDF} elements are labels as persistent dependent
 * so that they all are stored with the {@link TransactionInfo} is made persistent 
 * (in the finish function of the {@link StoreObject} finish function
 */
@SuppressWarnings("serial")
@Entity
public class TransactionInfo extends DatabaseObject {

	/** The stored object key. */
	@Index
	Long storedObjectKey;
	    
    /** The transaction object class name (as a string)**/
    @Index
    String transactionObjectType;
    /** The event **/
    @Index
    String event;
    
   /** empty constructor
     * Instantiates a new transaction info.
     */
    public TransactionInfo() {
    }

	/**
	 * Instantiates a new transaction info.
	 *
	 * @param user the user who is creating the transaction
	 * @param keyword the keyword string name of the object being represented in the transaction
	 * @param transactionObjectType the transaction object classname as String
	 */
	public TransactionInfo(DatabaseObject obj, String event) {
		super(obj);
		this.transactionObjectType = obj.getClass().getCanonicalName();
		this.storedObjectKey = obj.getKey();
		this.event = event;
	}
	
	/**
	 * Adds the {@link KeywordRDF}.
	 * If the number of {@link KeywordRDF} elements exceeds maxSize,
	 * then they are transfered to a {@link SetOfTransactionRDFs}
	 * and that structure is stored here.
	 * This was done so as to not have too much info in this class
	 *
	 * @param rdf the rdf
	 */
	

	/**
	 * Gets the transaction object classname.
	 *
	 * @return the transaction object type
	 */
	public String getTransactionObjectType() {
		return transactionObjectType;
	}
	
	/**
	 * Gets the stored object key.
	 *
	 * @return the stored object key
	 */
	public Long getStoredObjectKey() {
		return storedObjectKey;
	}
	
	/**
	 * Sets the stored object key.
	 *
	 * @param storedObjectKey the new stored object key
	 */
	public void setStoredObjectKey(Long storedObjectKey) {
		this.storedObjectKey = storedObjectKey;
	}
	
	
	public String getEvent() {
		return event;
	}

	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Object Type:       " + transactionObjectType + "\n");
		build.append(prefix + "Stored Object Key: " + storedObjectKey + "\n");
		build.append(prefix + "Object Type: " + transactionObjectType + "\n");
		
		
		return build.toString();
	}
	
}
