package info.esblurock.reaction.chemconnect.expdata.client.activity;

import info.esblurock.reaction.chemconnect.core.base.client.activity.ClientFactoryBaseImpl;
import info.esblurock.reaction.chemconnect.expdata.client.observations.IsolateMatrixBlock;
import info.esblurock.reaction.chemconnect.expdata.client.view.IsolateMatrixBlockView;

public class ClientFactoryExpDataImpl extends ClientFactoryBaseImpl implements ClientFactoryExpData {
	private final IsolateMatrixBlockView isolateMatrixBlock = new IsolateMatrixBlock();

	@Override
	public IsolateMatrixBlockView getIsolateMatrixBlockView() {
		return isolateMatrixBlock;
	}

}
