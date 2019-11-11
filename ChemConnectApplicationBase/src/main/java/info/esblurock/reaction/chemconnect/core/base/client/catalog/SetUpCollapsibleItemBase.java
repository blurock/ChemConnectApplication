package info.esblurock.reaction.chemconnect.core.base.client.catalog;

public class SetUpCollapsibleItemBase {
	
	public SetUpCollapsibleItemInterface valueOf(String element) {
		SetUpCollapsibleItemInterface ans = null;
		try {
			ans = SetUpCollapsibleItem.valueOf(element);
		} catch(Exception ex) {
			
		}
		
		return ans;
	}
	
	public SetUpCollapsibleItem[] values() {
		return SetUpCollapsibleItem.values();
	}

}
