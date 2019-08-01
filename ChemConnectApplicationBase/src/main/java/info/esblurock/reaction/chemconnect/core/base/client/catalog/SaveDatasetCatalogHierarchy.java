package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;

public class SaveDatasetCatalogHierarchy extends Composite {

	private static SaveDatasetCatalogHierarchyUiBinder uiBinder = GWT.create(SaveDatasetCatalogHierarchyUiBinder.class);

	interface SaveDatasetCatalogHierarchyUiBinder extends UiBinder<Widget, SaveDatasetCatalogHierarchy> {
	}

	@UiField
	MaterialDialog modal;
	@UiField
	MaterialCheckBox databasecheckbox;
	@UiField
	MaterialCheckBox yamlcheckbox;
	@UiField
	MaterialLabel databasetextbox;
	@UiField
	MaterialLabel yamltextbox;
	@UiField
	MaterialLink close;
	@UiField
	MaterialLink done;

	StandardDatasetObjectHierarchyItem topitem;

	public SaveDatasetCatalogHierarchy(StandardDatasetObjectHierarchyItem topitem) {
		initWidget(uiBinder.createAndBindUi(this));
		this.topitem = topitem;
		init();
	}

	void init() {
		databasetextbox.setText("Save to database");
		yamltextbox.setText("Save as yaml string");
	}

	public void openModal() {
		String owner = topitem.getHierarchy().getObject().getOwner();
		String account = Cookies.getCookie("user");
		if (account != null) {
			MaterialToast.fireToast("Saving under user: '" + account + "'   (" + owner + ")");

			/*
			String accessInput = Cookies.getCookie(UserAccountKeys.accessDataInput);
			String userInput = Cookies.getCookie(UserAccountKeys.accessUserDataInput);
			boolean vInput = Boolean.FALSE;
			boolean vUserInput = Boolean.FALSE;

			if (accessInput != null) {
				vInput = accessInput.compareTo(Boolean.TRUE.toString()) == 0;
			}
			if (accessInput != null) {
				vUserInput = userInput.compareTo(Boolean.TRUE.toString()) == 0;
			}
			boolean allowed = Boolean.FALSE;
			if (vInput) {
				allowed = Boolean.TRUE;
			}
			
			if (vUserInput && owner != null) {
				if (owner.compareTo(account) == 0) {
					allowed = Boolean.TRUE;
				} else {
					MaterialToast.fireToast("Owner: " + owner + " and log in: " + account + " don't match");
				}
			}
			*/
			boolean allowed = Boolean.TRUE;
			if (!allowed) {
				MaterialToast.fireToast("Have to have write authorization to save: not allowed for user: " + account);
				StandardWindowVisualization.errorWindowMessage("SaveDatasetCatalogHierarchy", "don't open modal");
			} else {
				modal.open();
			}
		} else {
			MaterialToast.fireToast("The user is not logged in (or session expired");
		}
	}

	@UiHandler("close")
	void onClickClose(ClickEvent e) {
		MaterialToast.fireToast("Cancel Save");
		modal.close();
	}

	@UiHandler("done")
	void onClickDone(ClickEvent e) {
		modal.close();
		if (databasecheckbox.getValue()) {
			MaterialToast.fireToast("Save into database");
			topitem.writeDatabaseObjectHierarchy();
		}
		if (yamlcheckbox.getValue()) {
			MaterialToast.fireToast("Save into Yaml");
			topitem.writeYamlObjectHierarchy();
		}
	}

}
