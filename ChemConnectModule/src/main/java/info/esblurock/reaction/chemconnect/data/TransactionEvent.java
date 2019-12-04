package info.esblurock.reaction.chemconnect.data;

import java.util.Map;

import com.googlecode.objectify.annotation.Entity;

@Entity
@SuppressWarnings("serial")
public class TransactionEvent extends DatabaseObject {

	public TransactionEvent() {
		super();
	}

	public TransactionEvent(TransactionEvent obj) {
		super(obj);
	}

	public void fillMapOfValues(Map<String, String> map) {
		super.fillMapOfValues(map);
	}

	public void retrieveFromMap(Map<String, String> map) {
		super.retrieveFromMap(map);
	}

	@Override
	public String toString() {
		return toString("");
	}

	@Override
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		return builder.toString();
	}

}
