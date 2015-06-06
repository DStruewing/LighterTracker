/*
 * David Struewing, 2015.
 */
package Release.WebUser;

import Release.dbUtils.ValidateEmail;
import Release.dbUtils.ValidateString;
import org.json.JSONObject;

/**
 *
 * @author david
 */
public class Validate {
	
	// validation error messages, one per field to be validated
	private String emailMsg = "";
	private String passwordMsg = "";

	private boolean isValidated = false; // true iff all fields validate ok.
	private String debugMsg = "";

	// Web User data fields from form (all String, pre-validation), bundled in this object
	private StringData webUserStringData = new StringData();

	// Web User data fields after validation (various data types), bundled into this object
	private TypedData webUserTypedData = new TypedData();

	// default constructor is good for first rendering 
	//   -- all error messages are set to "" (empty string).
	public Validate() {
	}
	
	public Validate(StringData webUserStringData) {
        // validationUtils method validates each user input (String even if destined for other type) from WebUser object
        // side effect of validationUtils method puts validated, converted typed value into TypedData object
        this.webUserStringData = webUserStringData;
		
		ValidateEmail vemail = new ValidateEmail(webUserStringData.email, true);
        webUserTypedData.setEmail(vemail.getConvertedEmail());
        this.emailMsg = vemail.getError();
		
		ValidateString vstr = new ValidateString(webUserStringData.password1, 45, true);
        webUserTypedData.setPassword(vstr.getConvertedString());
		this.passwordMsg = vstr.getError();
        if (webUserTypedData.getPassword().compareTo(webUserStringData.password2) != 0) {
            this.passwordMsg = "Both passwords must match.";
        }

        String allMessages = this.emailMsg + this.passwordMsg;
        isValidated = (allMessages.length() == 0);
    }
	
	public boolean isValidated() {
		return this.isValidated;
	}
	
	public TypedData getTypedData() {
		return this.webUserTypedData;
	}

	public String getEmailMsg() {
		return emailMsg;
	}

	public String getPasswordMsg() {
		return passwordMsg;
	}
	
	public String getAllValidationErrors() {
		String allMessages = ", email error: " + this.emailMsg
				+ ", password error: " + this.passwordMsg;
		return allMessages;
	}
	
	public String getAllValidationErrorsJSON() {
		JSONObject obj = new JSONObject();
		obj.put("emailError", this.emailMsg);
		obj.put("passwordError", this.passwordMsg);
		return obj.toString();
	}
	
}