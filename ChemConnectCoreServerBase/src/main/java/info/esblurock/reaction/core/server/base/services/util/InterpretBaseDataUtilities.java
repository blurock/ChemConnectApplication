package info.esblurock.reaction.core.server.base.services.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.metadata.StandardDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.utilities.DataElementInformation;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;

public class InterpretBaseDataUtilities {
	
	public static String createSuffix(DatabaseObject obj, DataElementInformation element) {
		return obj.getIdentifier() + "-" + element.getSuffix();
	}


	
	public static ArrayList<String> interpretMultipleYamlList(String key, Map<String, Object> yaml) {
		//System.out.println("interpretMultipleYamlList: \n" + yaml.toString());
		ArrayList<String> answers = new ArrayList<String>();
		Object yamlobj = yaml.get(key);
		@SuppressWarnings("unchecked")
		List<String> lst = (List<String>) yamlobj;
		for (String answer : lst) {
			answers.add(answer);
		}
		return answers;
	}
	
	@SuppressWarnings("unchecked")
	public static HashSet<String> interpretMultipleYaml(String key, Map<String, Object> yaml) {
		HashSet<String> answers = new HashSet<String>();
		Object yamlobj = yaml.get(key);
	if (yamlobj != null) {
			if (yamlobj.getClass().getCanonicalName().compareTo("java.util.ArrayList") == 0) {
				List<String> lst = (List<String>) yamlobj;
				for (String answer : lst) {
					answers.add(answer);
				}
			} else if (yamlobj.getClass().getCanonicalName().compareTo("java.util.HashMap") == 0) {
				HashMap<String, Object> map = (HashMap<String, Object>) yamlobj;
				Set<String> keyset = map.keySet();
				for(String mapkey : keyset) {
					HashMap<String, Object> submap = (HashMap<String, Object>) map.get(mapkey);
					String identifier = (String) submap.get(StandardDataKeywords.identifierKeyS);
					answers.add(identifier);
				}
			} else {
				String answer = (String) yamlobj;
				answers.add(answer);
			}
		}
		return answers;
	}
	public static void putMultipleInYamlList(String key, Map<String, Object> yaml, ArrayList<String> lst) {
		if(lst != null) {
			yaml.put(key, lst);
		} else {
			yaml.put(key, new ArrayList<String>());
		}
		
	}
	public static void setChemConnectCompoundMultipleType(DatabaseObjectHierarchy hierarchy, String dataType) {
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) hierarchy.getObject();
		multiple.setType(dataType);
		DataElementInformation refelement = DatasetOntologyParseBase
				.getSubElementStructureFromIDObject(dataType);
		String refid = InterpretBaseDataUtilities.createSuffix(multiple, refelement);
		multiple.setIdentifier(refid);
	}
	public static String dateToString(Date dateD) {
		String formatS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		DateFormat format = new SimpleDateFormat(formatS);
		String dateS = format.format(dateD);
		return dateS;
	}
	public static Date parseDate(String dateS) throws IOException {
		Date dateD = null;
		dateD = parseDateWithFormat(dateS, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		if (dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyy-MM-dd");
		}
		if (dateD == null) {
			dateD = parseDateWithFormat(dateS, "yyyyMMdd");
		}
		if (dateD == null) {
			throw new IOException("Date parse exception");
		}
		return dateD;
	}

	public static Date parseDateWithFormat(String dateS, String formatS) {
		Date dateD = null;
		try {
			DateFormat format = new SimpleDateFormat(formatS);
			dateD = format.parse(dateS);
		} catch (ParseException e) {
		}
		return dateD;

	}
	public static void putMultipleInYaml(String key, Map<String, Object> yaml, HashSet<String> set) {
		ArrayList<String> arr = new ArrayList<String>();
		if(set != null) {
		for(String name : set) {
			arr.add(name);
		}
		}
		yaml.put(key, arr);
	}


}
