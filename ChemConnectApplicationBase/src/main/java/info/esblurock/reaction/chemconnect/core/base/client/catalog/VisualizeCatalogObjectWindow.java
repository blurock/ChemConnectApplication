package info.esblurock.reaction.chemconnect.core.base.client.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLoader;
import info.esblurock.reaction.chemconnect.core.base.client.catalog.choose.SubCatagoryHierarchyCallbackInterface;
import info.esblurock.reaction.chemconnect.core.base.client.error.StandardWindowVisualization;
import info.esblurock.reaction.chemconnect.core.base.dataset.DatabaseObjectHierarchy;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageService;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.UserImageServiceAsync;

public class VisualizeCatalogObjectWindow extends Composite implements SubCatagoryHierarchyCallbackInterface{

	private static VisualizeCatalogObjectWindowUiBinder uiBinder = GWT
			.create(VisualizeCatalogObjectWindowUiBinder.class);

	interface VisualizeCatalogObjectWindowUiBinder extends UiBinder<Widget, VisualizeCatalogObjectWindow> {
	}

	public VisualizeCatalogObjectWindow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialCollapsible collapsible;
	@UiField
	MaterialWindow window;

	public VisualizeCatalogObjectWindow(String id, String classType) {
		initWidget(uiBinder.createAndBindUi(this));
		UserImageServiceAsync async = UserImageService.Util.getInstance();
		async.getTopCatalogObject(id, classType,
				new AsyncCallback<DatabaseObjectHierarchy>() {

					@Override
					public void onFailure(Throwable caught) {
						StandardWindowVisualization.errorWindowMessage("Catalog Object", caught.toString());
					}

					@Override
					public void onSuccess(DatabaseObjectHierarchy subs) {
						MaterialLoader.loading(false);
						setInHierarchy(subs);
					}
			
		});
	}

	@Override
	public void setInHierarchy(DatabaseObjectHierarchy subs) {
		StandardDatasetObjectHierarchyItem item = new  StandardDatasetObjectHierarchyItem(subs);
		collapsible.add(item);
		Window.alert(subs.toString());		
	}
	public void open() {
		window.open();
	}
}
