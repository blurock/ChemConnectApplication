package info.esblurock.reaction.chemconnect.core.base.client.catalog.consortium;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.autocomplete.MaterialAutoComplete;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundMultiple;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.base.dataset.consortium.Consortium;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class AddMemberToConsortiumDialog extends Composite {

	private static AddMemberToConsortiumDialogUiBinder uiBinder = GWT.create(AddMemberToConsortiumDialogUiBinder.class);

	interface AddMemberToConsortiumDialogUiBinder extends UiBinder<Widget, AddMemberToConsortiumDialog> {
	}
	
	@UiField
	MaterialDialog dialog;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialAutoComplete autocomplete;
	
	DatabaseObjectHierarchy hierarchy;
	Consortium consortium;
	MaterialPanel consortiapanel;
	MaterialPanel modalpanel;
	
	public AddMemberToConsortiumDialog(MaterialPanel consortiapanel, MaterialPanel modalpanel, 
			DatabaseObjectHierarchy hierarchy) {
		initWidget(uiBinder.createAndBindUi(this));
		this.modalpanel = modalpanel;
		this.hierarchy = hierarchy;
		this.consortiapanel = consortiapanel;
		consortium = (Consortium) hierarchy.getObject();
		title.setTitle("Add Member to Consortium: " + consortium.getConsortiumName());
		addAutoCompleteUsers();
	}
	
	private void addAutoCompleteUsers() {
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getAvailableUsernames(new AsyncCallback<List<String>>() {
			
			@Override
			public void onSuccess(List<String> result) {
				Window.alert("addAutoCompleteUsers()\n" + result);
				MultiWordSuggestOracle  oracle = new MultiWordSuggestOracle ();
				oracle.addAll(result);
				autocomplete.setSuggestions(oracle);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				StandardWindowVisualization.errorWindowMessage("Getting User Names", caught.toString());
			}
		});
	}

	@UiHandler("autocomplete")
	void onAutoCompleteSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
		Suggestion suggestion = event.getSelectedItem();
		String selected = event.getSelectedItem().getReplacementString();
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.addConsortiumMember(hierarchy, consortium.getConsortiumName(), selected, 
				new AsyncCallback<DatabaseObjectHierarchy>() {

					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Create Consortium Member", 
								caught.toString());
					}

					@Override
					public void onSuccess(DatabaseObjectHierarchy result) {
						ConsortiaChip chip = new ConsortiaChip(modalpanel,consortiapanel,result);
						consortiapanel.add(chip);
						Consortium consortium = (Consortium) hierarchy.getObject();				
					}
				});
	}
	
	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		dialog.close();
	}
	
	public void open() {
		dialog.open();
	}
}
