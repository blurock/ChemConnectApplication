package info.esblurock.reaction.core.server.base.activity.repositoryfile;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecord;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityInformationRecordBase;
import info.esblurock.reaction.chemconnect.core.base.activity.ActivityRepositoryInformation;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadLocalFile;
import info.esblurock.reaction.chemconnect.core.base.activity.read.ActivityRepositoryInitialReadInfo;
import info.esblurock.reaction.chemconnect.core.base.session.UserSessionData;
import info.esblurock.reaction.core.server.base.activity.repositoryfile.util.InitialStagingUtilities;
import info.esblurock.reaction.core.server.base.db.DatabaseWriteBase;
import info.esblurock.reaction.core.server.base.queries.QueryBase;
import info.esblurock.reaction.core.server.base.services.util.ContextAndSessionUtilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!ServletFileUpload.isMultipartContent(request)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not a multipart request");
			return;
		}
		ServletFileUpload upload = new ServletFileUpload(); // from Commons
		try {
			FileItemIterator iter = upload.getItemIterator(request);
			if (iter.hasNext()) {
				ContextAndSessionUtilities util = new ContextAndSessionUtilities(getServletContext(), request.getSession());
				util.printOutSessionAttributes();

				FileItemStream fileItem = iter.next();
				String ip = request.getSession().getId();
				UserSessionData usession = DatabaseWriteBase.getUserSessionDataFromSessionID(ip);
				String sourceID = QueryBase.getDataSourceIdentification(usession.getUserName());
				String name = fileItem.getFieldName();
				
				DatabaseObject obj = new DatabaseObject();
				ActivityInformationRecord baseinfo = new ActivityInformationRecord(obj);
				ActivityInformationRecordBase recordbaseinfo = new ActivityInformationRecordBase(baseinfo);
				ActivityRepositoryInformation repinfo = new ActivityRepositoryInformation(recordbaseinfo);
				ActivityRepositoryInitialReadInfo repread = new ActivityRepositoryInitialReadInfo(repinfo);
				ActivityRepositoryInitialReadLocalFile info = new ActivityRepositoryInitialReadLocalFile(repread,
						InitialStagingUtilities.extractNameFromURL(name),name);
				InitialStagingUtilities.localFileUpload(info,fileItem,usession,sourceID);
				
			}
		} catch (Exception caught) {
			throw new RuntimeException(caught);
		}
	}
	


}