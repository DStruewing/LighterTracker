package model.Lighter;

import dbUtils.*;

/* This class validates a Lighter object (bundle of pre-validated user entered string values)
 * and saves the validated data into a TypedData object (bundle of typed data values).
 * This class provides one error message per field in a WebUser object.
 * This class demonstrates the use of "object composition" and
 * "Single Responsibility" software design principles.
 */
public class Validate {

    // validation error messages, one per field to be validated
    private String lighterNameMsg = "";
    private String purchaseDateMsg = "";
    private String lighterColorMsg = "";
	private String trackingCodeMsg = "";

    private boolean isValidated = false; // true iff all fields validate ok.
    private String debugMsg = "";

    // Lighter data fields from form (all String, pre-validation), bundled in this object
    private StringData lighterStringData = new StringData();

    // Lighter data fields after validation (various data types), bundled into this object
    private TypedData lighterTypedData = new TypedData();

    // default constructor is good for first rendering 
    //   -- all error messages are set to "" (empty string).
    public Validate() {
    }

    public Validate(StringData lighterStringData) {
        // validationUtils method validates each user input (String even if destined for other type) from Lighter object
        // side effect of validationUtils method puts validated, converted typed value into TypedData object
        this.lighterStringData = lighterStringData;

        // this is not needed for insert, but will be needed for update.
        if (lighterStringData.lighterId != null && lighterStringData.lighterId.length() != 0) {
            ValidateInteger vi = new ValidateInteger(lighterStringData.lighterId, true);
            lighterTypedData.setLighterId(vi.getConvertedInteger());
        }

        ValidateString vstr = new ValidateString(lighterStringData.lighterName, 20, true);
        lighterTypedData.setLighterName(vstr.getConvertedString());
        this.lighterNameMsg = vstr.getError();

        ValidateDate vdate = new ValidateDate(lighterStringData.purchaseDate, false);
        lighterTypedData.setPurchaseDate(vdate.getConvertedDate());
        this.purchaseDateMsg = vdate.getError();

        vstr = new ValidateString(lighterStringData.lighterColor, 10, true);
        lighterTypedData.setLighterColor(vstr.getConvertedString());
        this.lighterColorMsg = vstr.getError();
		
		vstr = new ValidateString(lighterStringData.trackingCode, 5, true);
		lighterTypedData.setTrackingCode(vstr.getConvertedString());
		this.trackingCodeMsg = vstr.getError();

        String allMessages = this.lighterNameMsg + this.purchaseDateMsg + this.lighterColorMsg + this.trackingCodeMsg;
        isValidated = (allMessages.length() == 0);
    }

    public String getLighterNameMsg() {
        return lighterNameMsg;
    }

    public String getPurchaseDateMsg() {
        return purchaseDateMsg;
    }

    public String getLighterColorMsg() {
        return lighterColorMsg;
    }
	
	public String getTrackingCodeMsg() {
		return trackingCodeMsg;
	}

    public boolean isValidated() {
        return isValidated;
    }

    public String getDebugMsg() {
        return debugMsg;
    }
    
    public TypedData getTypedData() {
        return this.lighterTypedData;
    }

    public String getAllValidationErrors() {
        String allMessages = "lighterName error: " + this.lighterNameMsg
                + ", purchaseDate error: " + this.purchaseDateMsg
                + ", lighterColor error: " + this.lighterColorMsg
				+ ", trackingCode error: " + this.trackingCodeMsg;
        return allMessages;
    }

}
