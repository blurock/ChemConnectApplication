package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.Consortium;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.ConsortiumMember;

public class RegisterConsortiumObjects {
	public static void register() {
		ObjectifyService.register(Consortium.class);
		ObjectifyService.register(ConsortiumMember.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(Consortium.class);
		ResetDatabaseObjects.resetClass(ConsortiumMember.class);
	}
}
