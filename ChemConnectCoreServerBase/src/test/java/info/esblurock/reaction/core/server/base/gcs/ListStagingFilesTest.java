package info.esblurock.reaction.core.server.base.gcs;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import info.esblurock.reaction.core.server.base.db.GCSServiceRoutines;

class ListStagingFilesTest {

	@Test
	void test() {
		System.out.println("ListStagingFilesTest");
		try {
			GCSServiceRoutines.listStagingFiles("EdwardBlurock");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
