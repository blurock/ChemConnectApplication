package info.esblurock.reaction.chemconnect.core.base.client.catalog.consortium;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.chemconnect.core.base.client.view.ConsortiumManagementView;

public class ConsortiumManagement extends Composite implements ConsortiumManagementView {

	private static ConsortiumManagementUiBinder uiBinder = GWT.create(ConsortiumManagementUiBinder.class);

	interface ConsortiumManagementUiBinder extends UiBinder<Widget, ConsortiumManagement> {
	}

	
	
	Presenter listener;
	
	public ConsortiumManagement() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setName(String helloName) {
		
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void refresh() {
		
		
	}

}
