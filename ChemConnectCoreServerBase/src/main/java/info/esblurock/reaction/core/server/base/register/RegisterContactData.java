package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.contact.ContactInfoData;
import info.esblurock.reaction.chemconnect.core.base.contact.ContactLocationInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.GPSLocation;
import info.esblurock.reaction.chemconnect.core.base.contact.IndividualInformation;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.contact.Organization;
import info.esblurock.reaction.chemconnect.core.base.contact.OrganizationDescription;
import info.esblurock.reaction.chemconnect.core.base.contact.PersonalDescription;
import info.esblurock.reaction.chemconnect.core.base.dataset.ContactHasSite;

public class RegisterContactData {
	public static void register() {
		ObjectifyService.register(ContactInfoData.class);
		ObjectifyService.register(ContactLocationInformation.class);
		ObjectifyService.register(GPSLocation.class);
		ObjectifyService.register(IndividualInformation.class);
		ObjectifyService.register(NameOfPerson.class);
		ObjectifyService.register(Organization.class);
		ObjectifyService.register(OrganizationDescription.class);
		ObjectifyService.register(PersonalDescription.class);

		ObjectifyService.register(ContactHasSite.class);
	}
	
	public static void reset() {
		ResetDatabaseObjects.resetClass(Organization.class);
		ResetDatabaseObjects.resetClass(OrganizationDescription.class);
		ResetDatabaseObjects.resetClass(ContactInfoData.class);
		ResetDatabaseObjects.resetClass(ContactLocationInformation.class);
		ResetDatabaseObjects.resetClass(PersonalDescription.class);
		ResetDatabaseObjects.resetClass(GPSLocation.class);
		ResetDatabaseObjects.resetClass(IndividualInformation.class);
		ResetDatabaseObjects.resetClass(NameOfPerson.class);
		ResetDatabaseObjects.resetClass(ContactHasSite.class);
	}
}
