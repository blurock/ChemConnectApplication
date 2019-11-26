package info.esblurock.reaction.chemconnect.core.base.activity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;

@Entity
@SuppressWarnings("serial")
public class ActivityInformationRecord extends DatabaseObject {


   public ActivityInformationRecord() {
   }

   public ActivityInformationRecord(DatabaseObject structure) {
      super(structure);
   }


}
