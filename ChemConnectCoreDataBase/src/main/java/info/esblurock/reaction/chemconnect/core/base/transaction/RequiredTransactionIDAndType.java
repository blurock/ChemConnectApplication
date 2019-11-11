package info.esblurock.reaction.chemconnect.core.base.transaction;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class RequiredTransactionIDAndType extends TransactionIDAndType {

	public RequiredTransactionIDAndType() {
	}
	public RequiredTransactionIDAndType(TransactionIDAndType infoandtype) {
		super(infoandtype);
	}
	
	public RequiredTransactionIDAndType(ChemConnectCompoundDataStructure structure, 
			String transactionInfoType, String databaseIDTransaction) {
		super(structure,transactionInfoType,databaseIDTransaction);
	}

}
