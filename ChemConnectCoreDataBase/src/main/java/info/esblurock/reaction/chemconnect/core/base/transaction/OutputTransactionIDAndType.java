package info.esblurock.reaction.chemconnect.core.base.transaction;

import com.googlecode.objectify.annotation.Entity;

import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class OutputTransactionIDAndType extends TransactionIDAndType {
	public OutputTransactionIDAndType() {
	}
	public OutputTransactionIDAndType(ChemConnectCompoundDataStructure structure, 
			String transactionInfoType, String databaseIDTransaction) {
		super(structure,transactionInfoType,databaseIDTransaction);
	}
	public OutputTransactionIDAndType(TransactionIDAndType transidandtype) {
		super(transidandtype);
	}

}
