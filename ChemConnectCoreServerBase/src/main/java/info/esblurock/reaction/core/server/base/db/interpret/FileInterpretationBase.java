package info.esblurock.reaction.core.server.base.db.interpret;

import java.io.IOException;

public class FileInterpretationBase {

	public FileInterpretationInterface valueOf(String element) throws IOException {
		return FileInterpretationImpl.valueOf(element);
	}
}
