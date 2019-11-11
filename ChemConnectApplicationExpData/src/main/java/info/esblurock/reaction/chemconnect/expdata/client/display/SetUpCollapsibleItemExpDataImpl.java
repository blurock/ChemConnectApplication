package info.esblurock.reaction.chemconnect.expdata.client.display;

import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetUpCollapsibleItem;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetUpCollapsibleItemBase;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.SetUpCollapsibleItemInterface;

public class SetUpCollapsibleItemExpDataImpl extends SetUpCollapsibleItemBase {

	public SetUpCollapsibleItemInterface valueOf(String element) {

		SetUpCollapsibleItemInterface ans = super.valueOf(element);
		if (ans == null) {
			try {
				ans = SetUpCollapsilbleItemExpData.valueOf(element);
			} catch (Exception ex) {

			}

		}

		return ans;
	}

	public SetUpCollapsibleItem[] values() {
		return SetUpCollapsibleItem.values();
	}

}
