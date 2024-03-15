package info.esblurock.reaction.chemconnect.base.data.reposfile;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import java.util.HashMap;
import java.util.Map;

import info.esblurock.reaction.chemconnect.base.data.reposfile.RepositoryFile;

@Entity
@SuppressWarnings("serial")
public class TransferFileIntoCatagoryHierarchy extends RepositoryFile {


   public TransferFileIntoCatagoryHierarchy() {
   }

   public TransferFileIntoCatagoryHierarchy(RepositoryFile structure) {
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
