package model.Record;

import dbUtils.*;

/* This class validates a WebUser object (bundle of pre-validated user entered string values)
 * and saves the validated data into a TypedData object (bundle of typed data values).
 * This class provides one error message per field in a WebUser object.
 * This class demonstrates the use of "object composition" and
 * "Single Responsibility" software design principles.
 */
public class Validate {

    // validation error messages, one per field to be validated
    private String latitudeMsg = "";
    private String longitudeMsg = "";
    private String dateMsg = "";
    private String conditionMsg = "";
    private String webUserIdMsg = "";
    private String lighterIdMsg = "";

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

        ValidateFloat vfloat = new ValidateFloat(recordStringData.latitude, true);
        recordTypedData.setLatitude(vfloat.getConvertedFloat());
        this.latitudeMsg = vfloat.getError();
        
        vfloat = new ValidateFloat(recordStringData.longitude, true);
        recordTypedData.setLongitude(vfloat.getConvertedFloat());
        this.longitudeMsg = vfloat.getError();
        
        ValidateDate vdate = new ValidateDate(recordStringData.date, true);
        recordTypedData.setDate(vdate.getConvertedDate());
        this.dateMsg = vdate.getError();

        ValidateString vstr = new ValidateString(recordStringData.condition, 200, true);
        recordTypedData.setCondition(vstr.getConvertedString());
        this.conditionMsg = vstr.getError();

        ValidateInteger vi = new ValidateInteger(recordStringData.webUserId, true);
        recordTypedData.setWebUserId(vi.getConvertedInteger());
        this.webUserIdMsg = vi.getError();
        
        vi = new ValidateInteger(recordStringData.lighterId, true);
        recordTypedData.setLighterId(vi.getConvertedInteger());
        this.lighterIdMsg = vi.getError();

        String allMessages = this.latitudeMsg + this.longitudeMsg + this.dateMsg + this.conditionMsg
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

    public String getConditionMsg() {
        return conditionMsg;
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
                + ", condition error: " + this.conditionMsg
                + ", webUserId error: " + this.webUserIdMsg
                + ", lighterId error: " + this.lighterIdMsg;
        return allMessages;
    }
} // class
