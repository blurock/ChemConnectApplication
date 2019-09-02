package info.esblurock.reaction.core.server.base.register;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


public class ResetDatabaseObjects {
	@SuppressWarnings("rawtypes")
	public static void resetClass(Class cls) {
		@SuppressWarnings("unchecked")
		List<Key<?>> keys = ObjectifyService.ofy().load().type(cls).keys().list();
		ObjectifyService.ofy().delete().keys(keys).now();
	}
	public static void clearDatabase() {
		RegisterContactData.reset();
		RegisterDescriptionData.reset();
		RegisterUserLoginData.reset();
		RegisterImageInformation.reset();
		RegisterGCSClasses.reset();
		RegisterConsortiumObjects.reset();
	}
}
