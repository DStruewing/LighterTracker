/*
 * David Struewing, 2015.
 */
package Release.Record;

import Release.dbUtils.*;

/**
 *
 * @author david
 */
public class Validate {

	// validation error messages, one per field to be validated
	private String webUserIdMsg = "";
	private String lighterIdMsg = "";
	private String dateMsg = "";
	private String latitudeMsg = "";
	private String longitudeMsg = "";

	private boolean isValidated = false; // true iff all fields validate ok.
	private String debugMsg = "";

	// Web User data fields from form (all String, pre-validation), bundled in this object
	private StringData recordStringData = new StringData();

	// Web User data fields after validation (various data types), bundled into this object
	private TypedData recordTypedData = new TypedData();

	// default constructor is good for first rendering 
	//   -- all error messages are set to "" (empty string).
	public Validate() {
	}

	public Validate(StringData recordStringData) {
		// validationUtils method validates each user input (String even if destined for other type) from WebUser object
		// side effect of validationUtils method puts validated, converted typed value into TypedData object
		this.recordStringData = recordStringData;

		ValidateInteger vi = new ValidateInteger(recordStringData.lighterId, true);
		recordTypedData.setLighterId(vi.getConvertedInteger());
		this.lighterIdMsg = vi.getError();
		
		vi = new ValidateInteger(recordStringData.webUserId, true);
		recordTypedData.setWebUserId(vi.getConvertedInteger());
		this.webUserIdMsg = vi.getError();
		
		ValidateDate vdate = new ValidateDate(recordStringData.date, true);
		recordTypedData.setDate(vdate.getConvertedDate());
		this.dateMsg = vdate.getError();
		
		ValidateFloat vfloat = new ValidateFloat(recordStringData.latitude, true);
		recordTypedData.setLatitude(vfloat.getConvertedFloat());
		this.latitudeMsg = vfloat.getError();

		vfloat = new ValidateFloat(recordStringData.longitude, true);
		recordTypedData.setLongitude(vfloat.getConvertedFloat());
		this.longitudeMsg = vfloat.getError();

		String allMessages = this.latitudeMsg + this.longitudeMsg + this.dateMsg
				+ this.webUserIdMsg + this.lighterIdMsg;
		isValidated = (allMessages.length() == 0);
	}

	public StringData getStringData() {
		return this.recordStringData;
	}

	public TypedData getTypedData() {
		return this.recordTypedData;
	}

	public String getLatitudeMsg() {
		return latitudeMsg;
	}

	public String getLongitudeMsg() {
		return longitudeMsg;
	}

	public String getDateMsg() {
		return dateMsg;
	}

	public String getWebUserIdMsg() {
		return webUserIdMsg;
	}

	public String getLighterIdMsg() {
		return lighterIdMsg;
	}

	public boolean isValidated() {
		return this.isValidated;
	}

	public String getDebugMsg() {
		return this.debugMsg;
	}

	public String getAllValidationErrors() {
		String allMessages = "latitude error: " + this.latitudeMsg
				+ ", longitude error: " + this.longitudeMsg
				+ ", date error: " + this.dateMsg
				+ ", webUserId error: " + this.webUserIdMsg
				+ ", lighterId error: " + this.lighterIdMsg;
		return allMessages;
	}
} // class
