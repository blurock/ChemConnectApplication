package info.esblurock.reaction.chemconnect.data;

import java.util.Map;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@SuppressWarnings("serial")
@Entity
public class ChemConnectCompoundMultiple extends ChemConnectCompoundDataStructure {
	@Index
	String type;
	@Index
	String numberOfElements;

	public ChemConnectCompoundMultiple() {
		super();
		numberOfElements = "0";
	}

	public ChemConnectCompoundMultiple(ChemConnectCompoundDataStructure obj, String type) {
		super(obj);
		this.type = type;
		numberOfElements = "0";
	}

	public ChemConnectCompoundMultiple(ChemConnectCompoundDataStructure obj, String type, int numberOfElements) {
		super(obj);
		this.type = type;
		this.numberOfElements = Integer.toString(numberOfElements);
	}

	public void fill(ChemConnectCompoundDataStructure obj) {
		super.fill(obj);
		ChemConnectCompoundMultiple multiple = (ChemConnectCompoundMultiple) obj;
		this.type = multiple.getType();
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public int getNumberOfElements() {
		return Integer.parseInt(numberOfElements);
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = Integer.toString(numberOfElements);
	}

	public void fillMapOfValues(Map<String, String> map) {
		super.fillMapOfValues(map);
		map.put("catobjidS", this.getType());
		map.put("elementcountS", numberOfElements);
	}

	public void retrieveFromMap(Map<String, String> map) {
		super.retrieveFromMap(map);
		String param = map.get("catobjidS");
		if(param != null) {
			this.setType(param);
		}
		String countS = map.get("elementcountS");
		if(countS != null) {
			this.setNumberOfElements(Integer.parseInt(countS));
		}
	}

	public String toString() {
		return toString("");
	}

	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append(super.toString(prefix));
		build.append(prefix + numberOfElements + " elements of type: '" + type + "'\n");
		return build.toString();
	}
}
