package info.esblurock.reaction.chemconnect.core.base.client.catalog.gps;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.client.modal.cards.CardModalInterface;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.BaseCatalogAccess;
import info.esblurock.reaction.chemconnect.core.common.base.client.async.BaseCatalogAccessAsync;
public class AskGPSLocationModal extends CardModalInterface {

	private static AskGPSLocationModalUiBinder uiBinder = GWT.create(AskGPSLocationModalUiBinder.class);

	interface AskGPSLocationModalUiBinder extends UiBinder<Widget, AskGPSLocationModal> {
	}

	PrimitiveGPSLocationRow gpsrow;
	DatabaseObject obj;
	
	@UiField
	MaterialPanel panel;
	@UiField
	MaterialTitle title;
	@UiField
	MaterialTextBox city;
	@UiField
	MaterialTextBox country;

	public AskGPSLocationModal(DatabaseObject obj, PrimitiveGPSLocationRow gpsrow) {
		initWidget(uiBinder.createAndBindUi(this));
		this.gpsrow = gpsrow;
		this.obj = obj;
		init();
	}
	
	private void init() {
		title.setTitle("Enter city and Country");
		city.setLabel("City");
		country.setLabel("Country");
	}
	
	
	public void actionOnOK() {
		BaseCatalogAccessAsync async = BaseCatalogAccess.Util.getInstance();
		InsertGPSCoordinatesCallback callback = new InsertGPSCoordinatesCallback(gpsrow);
		String cityS = city.getText();
		String countryS = country.getText();
		if(cityS.length() > 0 && countryS.length() > 0) {
			async.getGPSLocation(obj,cityS,countryS,callback);
		} else {
			MaterialToast.fireToast("City and Country not filled in");
		}
	}
}
