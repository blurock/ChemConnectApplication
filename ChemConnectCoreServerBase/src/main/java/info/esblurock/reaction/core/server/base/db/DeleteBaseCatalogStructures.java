package info.esblurock.reaction.core.server.base.db;

import java.io.IOException;
import java.util.StringTokenizer;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.base.image.ImageInformation;
import info.esblurock.reaction.chemconnect.core.base.transfer.DataElementInformation;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;
import info.esblurock.reaction.core.server.base.authentification.InitializationBase;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataBase;
import info.esblurock.reaction.core.server.base.services.util.InterpretDataInterface;

public enum DeleteBaseCatalogStructures {


	DatasetImage {

		@Override
		public String deleteStructure(String ID) throws IOException {
			DatasetImage image = (DatasetImage) getEntity("DatasetImage",ID);
			String imageinfoid = image.getImageInformation();
			ImageInformation imageinfo = (ImageInformation) QueryBase
					.getDatabaseObjectFromIdentifier(ImageInformation.class.getCanonicalName(), imageinfoid);
			String imageurl = imageinfo.getImageURL();
			GCSServiceRoutines.deleteBlobFromURL(imageurl);
			return null;
		}

	},
	GCSBlobFileInformation {

		@Override
		public String deleteStructure(String ID) throws IOException {
			GCSBlobFileInformation gcsinfo = (GCSBlobFileInformation) getEntity("GCSBlobFileInformation",ID);
			//UserImageServiceImpl.deleteBlob(gcsinfo);
			return null;
		}

	},
	GCSInputFileInterpretation {

		@Override
		public String deleteStructure(String ID) throws IOException {
			return null;
		}

	},
	DatasetCatalogHierarchy {

		@Override
		public String deleteStructure(String ID) throws IOException {
			return null;
		}

	},
	Consortium {

		@Override
		public String deleteStructure(String ID) throws IOException {
			return null;
		}
		
	};

	public abstract String deleteStructure(String ID) throws IOException;

	public void deleteFromBlobURL(String url) {

	}

	/**
	 * Find key root.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	protected static String findKeyRoot(String key) {
		StringTokenizer tok = new StringTokenizer(key, ".");
		String ans = "";
		while (tok.hasMoreTokens()) {
			ans = tok.nextToken();
		}
		return ans;
	}

	public static String deleteObject(String datatype, String ID) throws IOException {
		DataElementInformation element = DatasetOntologyParseBase.getSubElementStructureFromIDObject(datatype);
		String chemconnecttype = element.getChemconnectStructure();
		String ans = "No special delete";
		try {
			DeleteBaseCatalogStructures deletedata = valueOf(chemconnecttype);
			if (deletedata != null) {
				ans = deletedata.deleteStructure(ID);
			}
		} catch(IllegalArgumentException ex) {
			
		}
		
		return ans;
	}

	public static String deleteObject(DatabaseObject entity) throws IOException {
		String root = findKeyRoot(entity.getClass().getCanonicalName());
		DeleteBaseCatalogStructures deletedata = valueOf(root);
		String ans = "No special delete";
		if (deletedata != null) {
			ans = valueOf(root).deleteStructure(entity.getIdentifier());
		}
		return ans;
	}

	public static DatabaseObject getEntity(String sourceClass, String ID) throws IOException {
		DatabaseObject entity = null;
		Class<?> typeclass;
		InterpretDataInterface interpret = InitializationBase.getInterpret().valueOf(sourceClass);
		if (interpret != null) {
			interpret.canonicalClassName();
			try {
				typeclass = Class.forName(interpret.canonicalClassName());
			} catch (ClassNotFoundException e) {
				throw new IOException("Delete: Can't resolve source class: " + sourceClass);
			}
			entity = (DatabaseObject) ObjectifyService.ofy().load().type(typeclass)
					.filter("identifier", ID).first().now();
			if (entity == null) {
				throw new IOException("Entity not found: sourceClass: " + sourceClass + "  ID=" + ID);
			}
		} else {
			throw new IOException("No object associated with: " + sourceClass);
		}
		return entity;
	}

}
