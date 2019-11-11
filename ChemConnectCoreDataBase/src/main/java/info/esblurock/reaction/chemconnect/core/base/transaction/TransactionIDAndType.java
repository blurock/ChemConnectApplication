package info.esblurock.reaction.chemconnect.core.base.transaction;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class TransactionIDAndType extends ChemConnectCompoundDataStructure {
	
	@Index
	String transactionInfoType;
	@Index
	String databaseIDTransaction;
	
	public TransactionIDAndType() {
	}
	public TransactionIDAndType(ChemConnectCompoundDataStructure structure, 
			String transactionInfoType, String databaseIDTransaction) {
		super(structure);
		this.transactionInfoType = transactionInfoType;
		this.databaseIDTransaction = databaseIDTransaction;
	}
	public TransactionIDAndType(TransactionIDAndType structure) {
		super(structure);
		this.transactionInfoType = structure.getTransactionInfoType();
		this.transactionInfoType = structure.getDatabaseIDTransaction();
	}
	public String getTransactionInfoType() {
		return transactionInfoType;
	}
	public void setTransactionInfoType(String transactionInfoType) {
		this.transactionInfoType = transactionInfoType;
	}

	public String getDatabaseIDTransaction() {
		return databaseIDTransaction;
	}
	public void setDatabaseIDTransaction(String databaseIDTransaction) {
		this.databaseIDTransaction = databaseIDTransaction;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + "Transaction Type:       " + transactionInfoType + "\n");
		return build.toString();
	}
}
