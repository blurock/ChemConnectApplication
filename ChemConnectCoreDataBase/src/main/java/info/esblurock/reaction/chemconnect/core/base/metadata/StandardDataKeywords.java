package info.esblurock.reaction.chemconnect.core.base.metadata;

public class StandardDataKeywords {
	
	// DatabaseObject
	public static String identifierKeyS = "dc:identifier";
	public static String ownerKeyS = "dcterms:publisher";
	public static String accessKeyS = "dataset:accessibility";
	public static String sourceIDS = "dataset:sourceID";
	
	public static String access = "access";

	// ChemConnectDataStructure
	public static String descriptionDataDataS = "dc:description";
	public static String dataSetReferenceS = "terms:BibliographicResource";
	public static String parameterObjectLinkS = "skos:mappingRelation";
	public static String DataCatalogIDID = "qb:ObservationGroup";
	public static String ContactHasSiteID = "foaf:page";

	public static String chemConnectDataStructure = "dataset:ChemConnectDataStructure";
	
	public static String contactHasSite = "dataset:ContactHasSite";
	public static String dataSetReference = "dataset:DataSetReference";
	public static String dataObjectLink = "dataset:DataObjectLink";

	// ChemConnectCompoundDataStructure
	public static String parentCatalogS = "dcterms:CatalogRecord";
	public static String chemConnectCompoundDataStructure = "dataset:ChemConnectCompoundDataStructure";

	// DataCatalogID
	public static String datacatalogid = "dataset:DataCatalogID";
	public static String CatalogBaseName = "skos:hasTopConcept";
	public static String DataCatalog = "skos:inScheme";
	public static String SimpleCatalogName = "qb:DataSet";
	public static String catalogPath = "dataset:CatalogPath";
	

	// ChemConnectCompoundMultiple
	public static String elementType = "dataset:elementType";

	// GCSBlobFileInformation
	public static String filename = "dataset:GCSFileName";
	public static String filepath = "dataet:GCSFilePath";
	public static String bucketName = "dataset:GCSBucketName";
	public static String fileTypeS = "dataset:fileType";
	public static String descriptionKeyS = "dcterms:description";

	// ImageInformation
	public static String imageType = "dataset:ImageType";
	public static String imageURL = "dataset:ImageURL";
	public static String imageInformation = "dataset:ImageInformation";

	// DatasetImage
	public static String datasetImage = "dataset:DatasetImage";
	public static String imageInformationID = "dataset:ImageInformation";
	
	// DatasetCatalogHierarchy
	public static String datasetCatalogHierarchy = "dataset:DatasetCatalogHierarchy";


	// PurposeConceptPair
	public static String datacubeConcept = "qb:concept";
	public static String purposeS = "dataset:purpose";
	public static String purposeConceptPair = "dataset:PurposeConceptPair";

	// DescriptionDataData
	public static String titleKeyS = "dcterms:title";
	public static String parameterPurposeConceptPairS = "prov:influenced";
	public static String dataTypeKeyS = "dcterms:type";
	public static String sourceDateKeyS = "dcterms:created";
	public static String keyWord = "dataset:DescriptionKeyword";
	public static String keywordKeyS = "dcat:keyword";
	public static String descriptionDataData = "dataset:DescriptionDataData";

	// ContactHasSite
	public static String siteOfS = "dataset:HttpAddress";
	public static String siteTypeS = "dataset:HttpAddressType";

	// ContactInfoData
	public static String contactKeyS = "vcard:Contact";
	public static String contactTypeS = "dataset:ContactType";
	
	// ContactLocationInformation
	public static String  streetaddressKeyS = "vcard:street-address";  
	public static String  localityKeyS = "vcard:locality";
	public static String  postalcodeKeyS = "vcard:postal-code";
	public static String  countryKeyS = "vcard:country-name";
	public static String  gpsCoordinatesID = "geo:location";
	public static String contactLocationInformation = "dataset:ContactLocationInformation";

	// GPSLocation
	public static String  latitudeKeyS = "geo:lat";
	public static String  longitudeKeyS = "geo:long";
	public static String gPSLocation = "dataset:GPSLocation";

	// OrganizationDescription
	public static String organizationUnit = "org:OrganizationalUnit";
	public static String organizationClassification = "org:role";
	public static String organizationName = "org:FormalOrganization";
	public static String subOrganizationOf = "org:unitOf";
	public static String organizationDescription = "dataset:OrganizationDescription";

	// DataSetReference
	public static String referenceTitle = "dcterms:title";
	public static String referenceBibliographicString = "dcterms:isReferencedBy";
	public static String referenceAuthors = "foaf:name";
	public static String referenceDOI = "datacite:PrimaryResourceIdentifier";

	// DataObjectLink
	public static String dataStructureIdentifierS = "qb:structure";

	// PersonalDescription
	public static String userClassification = "vcard:role";
	public static String userNameID = "foaf:name";

	// NameOfPerson
	public static String titleName = "foaf:title";
	public static String givenName = "foaf:givenName";
	public static String familyName = "foaf:familyName";

	// AuthorInformation
	public static String linkToContact = "dataset:linkToContact";

	// IndividualInformation
	public static String locationKeyS = "vcard:Location";
	public static String personS = "foaf:Person";
	public static String contactInfoData = "dataset:ContactInfoData";
	public static String individualInformation = "dataset:DatabasePerson";

	// Organization
	public static String originfoKeyS = "org:Organization";
	public static String organization = "dataset:Organization";

	// UserAccountInformation
	public static String  emailKeyS = "vcard:email";
	public static String userrole = "foaf:UserAccountRole";
	public static String useraccount = "dataset:UserAccountInformation";

	// DatabaseObjectHierarchy
	public static String author = "dataset:Author";

	// UserAccount
	public static String accountUserName = "foaf:account";
	public static String authorizationName = "dataset:AuthorizationName";
	public static String authorizationType = "dataset:AuthorizationType";
	public static String accountPrivilege = "dataet:AccountPrivilege";

	// PersonalDescription
	public static String personalDescription = "dataset:PersonalDescription";

	// NameOfPerson
	public static String nameOfPerson = "dataset:NameOfPerson";

	// AuthorInformation
	public static String authorInformation = "dataset:AuthorInformation";
	
	// IndividualInformation


	// Consortium
	public static String DatabaseUserIDReadAccess = "dataset:userReadAccess";
	public static String DatabaseUserIDWriteAccess = "dataset:userWriteAccess";
	public static String parameterSetDescriptionsS = "rdf:Property";

	// ConvertInputDataBase
	public static String inputTypeS = "dataset:inputType";
	public static String outputTypeS = "dataset:outputType";


	public static String noPurposeS = "no purpose";
	public static String noConceptS = "no concept";


}
