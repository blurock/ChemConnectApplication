package info.esblurock.reaction.chemconnect.core.base.client.catalog.gcs.repository;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialTooltip;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHeirarchy;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.concepts.ChooseFromConceptHierarchies;
import info.esblurock.reaction.chemconnect.core.base.client.view.RepositoryFileManagerView;
import info.esblurock.reaction.chemconnect.core.base.metadata.MetaDataKeywords;
import info.esblurock.reaction.chemconnect.core.base.client.view.ManageCatalogHierarchyView.Presenter;

public class RepositoryFileManager extends Composite implements RepositoryFileManagerView,ChooseFromConceptHeirarchy {

	private static RepositoryFileManagerUiBinder uiBinder = GWT.create(RepositoryFileManagerUiBinder.class);

	interface RepositoryFileManagerUiBinder extends UiBinder<Widget, RepositoryFileManager> {
	}

	Presenter listener;
	String titlename;
	
	@UiField
	MaterialPanel modalpanel;
	@UiField
	MaterialTooltip identifiertip;
	@UiField
	MaterialLink filetype;
	
	ArrayList<String> choices;
	boolean filetypechosen;

	public RepositoryFileManager() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	
	private void init() {
		identifiertip.setText("Choose Repository File type");
		filetype.setText("Repository File Type");
		choices = new ArrayList<String>();
		choices.add(MetaDataKeywords.dataTypeFileFormat);
		filetypechosen = false;
	}
	

	public void refresh() {
		
	}
	
	@UiHandler("filetype")
	public void filetypeClick(ClickEvent event) {
		setUpConceptChoices();
	}
	
	private void  setUpConceptChoices() {
		ChooseFromConceptHierarchies choosedevice = new ChooseFromConceptHierarchies(choices,this);
		modalpanel.clear();
		modalpanel.add(choosedevice);
		choosedevice.open();				
	}
	@Override
	public void conceptChosen(String topconcept, String concept, ArrayList<String> path) {
		Window.alert("Concept Chosen concept: " + concept + "/n" 
	               + "                  path:" + path);
		filetype.setText(concept);
		identifiertip.setText(path.toString());
		filetypechosen = true;
	}

	
	@Override
	public void setName(String titlename) {
		this.titlename = titlename;
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		
	}


}
