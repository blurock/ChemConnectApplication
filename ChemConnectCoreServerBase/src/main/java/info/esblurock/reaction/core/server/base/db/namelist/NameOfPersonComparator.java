package info.esblurock.reaction.core.server.base.db.namelist;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.contact.NameOfPerson;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;

public class NameOfPersonComparator extends DatabaseObjectComparator {

	public NameOfPersonComparator() {
		super(StandardDataKeywords.nameOfPerson);
	}

	@Override
	public int compare(DatabaseObject o1, DatabaseObject o2) {
		NameOfPerson person1 = (NameOfPerson) o1;
		NameOfPerson person2 = (NameOfPerson) o2;
		String name1 = person1.getAlphabeticName();
		String name2 = person2.getAlphabeticName();
		return name1.compareTo(name2);
	}

	@Override
	public String generateLabel(DatabaseObject obj) {
		NameOfPerson person = (NameOfPerson) obj;
		return person.getAlphabeticName();
	}


}
