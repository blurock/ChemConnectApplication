package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ChooseFullNameFromCatagoryRow;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.ObjectVisualizationInterface;
import info.esblurock.reaction.chemconnect.core.base.dataset.DataCatalogID;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;

public class TransformRepositoryFile extends Composite implements ObjectVisualizationInterface {

	private static TransformRepositoryFileUiBinder uiBinder = GWT.create(TransformRepositoryFileUiBinder.class);

	interface TransformRepositoryFileUiBinder extends UiBinder<Widget, TransformRepositoryFile> {
	}
	
	@UiField
	MaterialPanel choosename;
	@UiField
	MaterialTextArea description;
	@UiField
	MaterialLink close;
	@UiField
	MaterialDialog modal;
	
	boolean descriptionentered;
	String user;
	String objectS;
	ArrayList<String> choices;
	MaterialPanel modalpanel;
	int minimumNumberOfCharacters;
	DataCatalogID repositoryfiledat;
	StandardDatabaseRepositoryFileStaging top;
	
	public TransformRepositoryFile(
			StandardDatabaseRepositoryFileStaging top,
			String user, 
			String description,
			String objectS, 
			ArrayList<String> choices, 
			MaterialPanel modalpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.user = user;
		this.objectS = objectS;
		this.choices = choices;
		this.description.setText(description);
		init();
	}

	private void init() {
		description.setLabel("Description of file (more than " + minimumNumberOfCharacters + " characters)");
		ChooseFullNameFromCatagoryRow choose = new ChooseFullNameFromCatagoryRow(this, user, objectS, 
				choices, choosename);
		choosename.add(choose);
		minimumNumberOfCharacters = 10;
		descriptionentered = false;
		
	}
	
	@UiHandler("description")
	public void clickDescription(KeyPressEvent event) {
		if(description.getText().length() > 10) {
			descriptionentered = true;
		} else {
			descriptionentered = false;
		}
	}
	
	@UiHandler("close")
	public void clickClose(ClickEvent event) {
		modal.close();
	}
	
	public void open() {
		modal.open();
	}

	@Override
	public void createCatalogObject(DatabaseObject obj, DataCatalogID catid) {
		if(descriptionentered) {
			top.createRepositoryDataFile(obj, catid, description.getText());
		} else {
			MaterialToast.fireToast("Enter a description of more than 10 characters");
		}
	}

	@Override
	public void insertCatalogObject(DatabaseObjectHierarchy subs) {
		top.insertCatalogObject(subs);
		
	}

	
}
