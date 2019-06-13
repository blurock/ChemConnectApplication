package info.esblurock.reaction.core.server.base.register;

import com.googlecode.objectify.ObjectifyService;

import info.esblurock.reaction.chemconnect.core.base.image.DatasetImage;
import info.esblurock.reaction.chemconnect.core.base.image.ImageInformation;
import info.esblurock.reaction.chemconnect.core.base.image.ImageUploadTransaction;
import info.esblurock.reaction.chemconnect.core.base.image.UploadedImage;

public class RegisterImageInformation {
	public static void register() {
		ObjectifyService.register(UploadedImage.class);
		ObjectifyService.register(ImageUploadTransaction.class);
		ObjectifyService.register(ImageInformation.class);
		ObjectifyService.register(DatasetImage.class);
	}
	public static void reset() {
		ResetDatabaseObjects.resetClass(UploadedImage.class);
		ResetDatabaseObjects.resetClass(ImageUploadTransaction.class);
		ResetDatabaseObjects.resetClass(ImageInformation.class);
		ResetDatabaseObjects.resetClass(DatasetImage.class);
	}

}
