package info.esblurock.reaction.core.server.base.services.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import com.google.cloud.storage.BlobInfo;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;
import info.esblurock.reaction.chemconnect.core.base.gcs.GCSBlobFileInformation;
import info.esblurock.reaction.chemconnect.core.base.gcs.ParsedFilename;
import info.esblurock.reaction.chemconnect.core.base.utilities.HierarchyNode;
import info.esblurock.reaction.core.ontology.base.GenericSimpleQueries;
import info.esblurock.reaction.core.ontology.base.dataset.DatasetOntologyParseBase;


public class ParseUtilities {

	public static ParsedFilename getURLInformation(DatabaseObject obj, String url) throws IOException {
		ParsedFilename parsed = null;
		URL urlU = null;
		try {
			urlU = new URL(url);
			URLConnection connect = urlU.openConnection();
			String filetype = connect.getContentType();
			if (filetype == null) {
				filetype = URLConnection.guessContentTypeFromName(urlU.getPath());
			}
			int pos = filetype.indexOf(";");
			if (pos > 0) {
				filetype = filetype.substring(0, pos);
			}

			parsed = fillURLInformation(obj, urlU, filetype);

		} catch (MalformedURLException e) {
			throw new IOException("");
		}
		return parsed;
	}

	public static ParsedFilename fillURLInformation(DatabaseObject obj, URL url, String filetype) {

		String path = url.getPath();

		ParsedFilename parsed = fillFileInformation(obj, url.toString(), path, filetype);
		ParsedFilename urlparsed = new ParsedFilename(obj, url.getHost(), parsed);

		return urlparsed;
	}

	public static ParsedFilename fillFileInformation(DatabaseObject obj, String filename, String filetype) {

		ParsedFilename parsed = fillFileInformation(obj, filename, filename, filetype);

		return parsed;
	}

	public static ParsedFilename fillFileInformation(DatabaseObject obj, String original, String filepath,
			String filetype) {
		StringTokenizer tok = new StringTokenizer(filepath, "/");
		ArrayList<String> dir = new ArrayList<String>();
		String extension = "";

		String filename = null;
		while (tok.hasMoreTokens()) {
			filename = tok.nextToken();
			if (tok.hasMoreTokens()) {
				dir.add(filename);
			}
		}
		int pos = filename.lastIndexOf(".");
		String filenamebase = filename;
		if (pos > 0) {
			extension = filename.substring(pos + 1);
			filenamebase = filename.substring(0, pos);
		}
		ParsedFilename parsed = new ParsedFilename(obj, original, filetype, dir, filenamebase, extension);
		return parsed;
	}

	public static HierarchyNode getFileInterpretionChoices(ParsedFilename parsed) throws IOException {

		HierarchyNode top = new HierarchyNode("Interpret");
		if (parsed.getExtension() != null) {
			ArrayList<String> exttypes = DatasetOntologyParseBase.typesWithExtension(parsed.getExtension());
			addHierachy(top, exttypes, "From Extension");
		}
		if (parsed.getFiletype() != null) {
			ArrayList<String> filetypes = GenericSimpleQueries.typesFromFileType(parsed.getFiletype());
			addHierachy(top, filetypes, "MIME Types");
		}
		return top;
	}


	static void addHierachy(HierarchyNode top, ArrayList<String> lst, String name) {
		HierarchyNode fromExtension = new HierarchyNode(name);
		boolean typefound = lst.size() > 0;
		for (String exttype : lst) {
			HierarchyNode hierarchy = DatasetOntologyParseBase.findClassHierarchy(exttype);
			fromExtension.addSubNode(hierarchy);
		}
		if (typefound) {
			top.addSubNode(fromExtension);
		}
	}

	public static HierarchyNode buildBlobHierarchy(DatabaseObject obj, ArrayList<GCSBlobFileInformation> lst,
			ArrayList<String> types) {
		HierarchyNode top = new HierarchyNode(obj.getIdentifier());
		for (GCSBlobFileInformation info : lst) {
			ParsedFilename parsed = fillFileInformation(obj, info.getFilename(), info.getFiletype());
			if (belongsToMIMETypes(info, types)) {
				fillInHierarchy(top, parsed.getPath(), parsed.getOriginal());
			}
		}

		return top;
	}

	public static boolean belongsToMIMETypes(GCSBlobFileInformation info, ArrayList<String> types) {
		boolean matches = false;
		if (types != null) {
			if (types.size() > 0) {
				String fulltype = info.getFiletype();
				String maintype = isolateMainMIMEType(fulltype);
				if (types.contains(maintype)) {
					matches = true;
				} else if (types.contains(maintype)) {
					matches = true;
				}
			} else {
				matches = true;
			}
		} else {
			matches = true;
		}
		return matches;
	}

	public static String isolateMainMIMEType(String type) {
		int pos = type.indexOf("/");
		String ans = type;
		if(pos >= 0) {
			ans = type.substring(0, pos);
		}
		return ans;
	}

	public static void fillInHierarchy(HierarchyNode top, ArrayList<String> path, String filename) {
		if (path.size() > 0) {
			ArrayList<HierarchyNode> subnodes = top.getSubNodes();
			String topname = path.get(0);
			boolean notfound = true;
			int index = 0;
			if (top.getSubNodes() != null) {
				Iterator<HierarchyNode> iter = subnodes.iterator();
				while (notfound && iter.hasNext()) {
					HierarchyNode subnode = iter.next();
					if (subnode.getIdentifier().compareTo(topname) == 0) {
						notfound = false;
					} else {
						index++;
					}
				}
			}
			path.remove(0);
			if (notfound) {
				HierarchyNode node = new HierarchyNode(topname);
				top.addSubNode(node);
				fillInHierarchy(node, path, filename);
			} else {
				HierarchyNode node = subnodes.get(index);
				fillInHierarchy(node, path, filename);
			}
		} else {
			HierarchyNode node = new HierarchyNode(filename);
			top.addSubNode(node);
		}
	}
	public static HierarchyNode parseIDsToHierarchyNode(String topnodeS, Set<String> ids,boolean simplify) {
		HierarchyNode topnode = new HierarchyNode(topnodeS);
		for(String id : ids) {
			System.out.println(id);
			StringTokenizer tok = new StringTokenizer(id,"-");
			ArrayList<String> path = new ArrayList<String>();
			String last = null;
			while(tok.hasMoreTokens()) {
				last = tok.nextToken();
				path.add(last);
			}
			ParseUtilities.fillInHierarchy(topnode, path, id);
		}
		
		if(simplify) {
			while(topnode.getSubNodes().size() == 1) {
				HierarchyNode newtopnode = topnode.getSubNodes().get(0);
				String id = newtopnode.getLabel();
				StringTokenizer tok = new StringTokenizer(id,"-");
				String current = id;
				String last = current;
				while(tok.hasMoreTokens()) {
					last = current;
					current = tok.nextToken();
				}
				topnode = new HierarchyNode(id,last);
				topnode.setSubNodes(newtopnode.getSubNodes());
			}
		}
		return topnode;
	}
	
	public static GCSBlobFileInformation blobInfoToGCSBlobFileInformation(DatabaseObject object, BlobInfo info) {
		String filetype = info.getContentType();
		String description = "";
		String name = info.getName();
		String path = "";
		String filename = name;
		int pos = name.lastIndexOf("/");
		if(pos >= 0) {
			path = name.substring(1, pos);
			if(pos+1 < name.length()) {
				filename = name.substring(pos+1);
			}
		}
		ChemConnectCompoundDataStructure structure = new ChemConnectCompoundDataStructure(object,null);
		GCSBlobFileInformation gcsinfo = new GCSBlobFileInformation(structure, 
				path, filename, filetype, description);
		return gcsinfo;
	}
	
}
