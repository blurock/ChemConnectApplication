package info.esblurock.reaction.chemconnect.core.base.client.catalog.person;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.StandardDatasetObjectHierarchyItem;
import info.esblurock.reaction.chemconnect.core.base.login.UserAccount;

public class StandardDatabaseUserAccountHeader extends Composite {

	private static StandardDatabaseUserAccountHeaderUiBinder uiBinder = GWT
			.create(StandardDatabaseUserAccountHeaderUiBinder.class);

	interface StandardDatabaseUserAccountHeaderUiBinder extends UiBinder<Widget, StandardDatabaseUserAccountHeader> {
	}

	@UiField
	MaterialTooltip usernametooltip;
	@UiField
	MaterialLink username;
	@UiField
	MaterialTooltip authorizationtype;
	@UiField
	MaterialLink authorizationname;
	@UiField
	MaterialTooltip privilegetooltip;
	@UiField
	MaterialLink accountPrivilege;
	@UiField
	MaterialLink save;
	@UiField
	MaterialLink delete;

	StandardDatasetObjectHierarchyItem item;
	UserAccount account;
	
	public StandardDatabaseUserAccountHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public StandardDatabaseUserAccountHeader(StandardDatasetObjectHierarchyItem item) {
		initWidget(uiBinder.createAndBindUi(this));
		this.item = item;
		account = (UserAccount) item.getObject();
		usernametooltip.setText("User account name");
		username.setText(account.getAccountUserName());
		authorizationtype.setText(account.getAuthorizationType());
		authorizationname.setText(account.getAuthorizationName());
		privilegetooltip.setText("Account Priviledges");
		accountPrivilege.setText(account.getAccountPrivilege());
	}
}
