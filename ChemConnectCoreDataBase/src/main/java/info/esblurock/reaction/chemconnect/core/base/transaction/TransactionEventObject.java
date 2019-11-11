package info.esblurock.reaction.chemconnect.core.base.transaction;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.dataset.SimpleCatalogObject;

@SuppressWarnings("serial")
@Entity
public class TransactionEventObject extends SimpleCatalogObject {
	@Index
	String requiredTransaction;
	@Index
	String outputTransaction;
	@Index
	String activityInfo;
	
	public TransactionEventObject() {
	}
	
	public TransactionEventObject(SimpleCatalogObject simple, 
			String requiredTransaction, String outputTransaction, String activityInfo) {
		super(simple);
		this.requiredTransaction = requiredTransaction;
		this.outputTransaction = outputTransaction;
		this.activityInfo = activityInfo;
	}

	public String getRequiredTransaction() {
		return requiredTransaction;
	}

	public void setRequiredTransaction(String requiredTransaction) {
		this.requiredTransaction = requiredTransaction;
	}

	public String getOutputTransaction() {
		return outputTransaction;
	}

	public void setOutputTransaction(String outputTransaction) {
		this.outputTransaction = outputTransaction;
	}

	public String getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}
	
	

}
