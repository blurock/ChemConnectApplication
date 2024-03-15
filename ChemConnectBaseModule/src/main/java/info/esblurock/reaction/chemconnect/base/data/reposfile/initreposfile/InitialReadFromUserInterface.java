package info.esblurock.reaction.chemconnect.base.data.reposfile.initreposfile;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.reposfile.initreposfile.InitialReadInOfRepositoryFile;

@Entity
@SuppressWarnings("serial")
public class InitialReadFromUserInterface extends InitialReadInOfRepositoryFile {


   public InitialReadFromUserInterface() {
   }

   public InitialReadFromUserInterface(InitialReadInOfRepositoryFile structure) {
      super(structure);
   }

   public void fillMapOfValues(Map<String,String> map) {
      super.fillMapOfValues(map);
   }
   public void retrieveFromMap(Map<String,String> map) {
      super.retrieveFromMap(map);
   }
	@Override
	public String toString() {
		return toString("");
	}

   @Override
   public String toString(String prefix) {
      StringBuilder build = new StringBuilder();
      build.append(super.toString(prefix));
      return build.toString();
   }

}
