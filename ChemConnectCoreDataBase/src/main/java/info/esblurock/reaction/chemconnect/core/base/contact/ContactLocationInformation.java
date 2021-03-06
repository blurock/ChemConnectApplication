package info.esblurock.reaction.chemconnect.core.base.contact;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import info.esblurock.reaction.chemconnect.core.base.DatabaseObject;
import info.esblurock.reaction.chemconnect.core.base.dataset.ChemConnectCompoundDataStructure;

@SuppressWarnings("serial")
@Entity
public class ContactLocationInformation extends ChemConnectCompoundDataStructure {

	@Index
	String addressAddress;
	@Index
	String city;
	@Index
	String country;
	@Index
	String postcode;
	@Index
	String gpsLocationID;

	public ContactLocationInformation() {
		super();
		this.addressAddress = "";
		this.city = "";
		this.country = "";
		this.postcode = "";
		this.gpsLocationID = "";
	}

	public ContactLocationInformation(String identifier, String sourceID) {
		super(identifier, sourceID);
		this.addressAddress = "";
		this.city = "";
		this.country = "";
		this.postcode = "";
		this.gpsLocationID = "";
	}
	public ContactLocationInformation(ChemConnectCompoundDataStructure compound, String gpsLocationID) {
		super(compound);
		this.addressAddress = "";
		this.city = "";
		this.country = "";
		this.postcode = "";
		this.gpsLocationID = gpsLocationID;
	}

	public ContactLocationInformation(ChemConnectCompoundDataStructure compound,
			String addressAddress, String city, String country, String postcode, String gpsLocationID) {
		fill(compound, addressAddress, city, country, postcode, gpsLocationID);
	}
	
	public ContactLocationInformation(String identifier, String access, String owner, String sourceID,
			String addressAddress, String city, String country, String postcode, String gpsLocationID) {
		fill(identifier, access, owner, sourceID, addressAddress, city, country, postcode, gpsLocationID);
	}

	public void fill(ChemConnectCompoundDataStructure compound, String addressAddress,
			String city, String country, String postcode, String gpsLocationID) {
		super.fill(compound);
		this.addressAddress = addressAddress;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.gpsLocationID = gpsLocationID;
	}
	public void fill(String identifier, String access, String owner, String sourceID, String addressAddress,
			String city, String country, String postcode, String gpsLocationID) {
		super.fill(identifier, access, owner, sourceID);
		this.addressAddress = addressAddress;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.gpsLocationID = gpsLocationID;
	}
	@Override
	public void fill(DatabaseObject object) {
		super.fill(object);
		ContactLocationInformation location = (ContactLocationInformation) object;
		this.addressAddress = location.getAddressAddress();
		this.city = location.getCity();
		this.country = location.getCountry();
		this.postcode = location.getPostcode();
		this.gpsLocationID = location.getGpsLocationID();
	}
	
	

	public String getAddressAddress() {
		return addressAddress;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setAddressAddress(String addressAddress) {
		this.addressAddress = addressAddress;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getGpsLocationID() {
		return gpsLocationID;
	}
	public String toString() {
		return toString("");
	}
	public String toString(String prefix) {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString(prefix));
		builder.append(prefix + "Address: ");
		builder.append(addressAddress);
		builder.append("\n");
		builder.append(prefix + "Address: ");
		builder.append(city);
		builder.append(", ");
		builder.append(country);
		builder.append(", ");
		builder.append(postcode);
		builder.append("\n");
		builder.append(prefix + "GPS: " + gpsLocationID + "\n");
		return builder.toString();
	}

}
