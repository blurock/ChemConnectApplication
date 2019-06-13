package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.session.DataSourceIdentification;
import info.esblurock.reaction.chemconnect.core.base.session.EventCount;
import info.esblurock.reaction.chemconnect.core.base.session.SessionEvent;
import info.esblurock.reaction.chemconnect.core.base.transaction.TransactionInfo;

public class RegisterTransactionData {
	public static void register() {
		ObjectifyService.register(TransactionInfo.class);
		ObjectifyService.register(DataSourceIdentification.class);
		ObjectifyService.register(SessionEvent.class);
		ObjectifyService.register(EventCount.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(TransactionInfo.class);
		ResetDatabaseObjects.resetClass(DataSourceIdentification.class);
		ResetDatabaseObjects.resetClass(SessionEvent.class);
		ResetDatabaseObjects.resetClass(EventCount.class);
	}	
}
