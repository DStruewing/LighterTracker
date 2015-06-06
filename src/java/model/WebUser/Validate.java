package model.WebUser;

import dbUtils.*;

/* This class validates a WebUser object (bundle of pre-validated user entered string values)
 * and saves the validated data into a TypedData object (bundle of typed data values).
 * This class provides one error message per field in a WebUser object.
 * This class demonstrates the use of "object composition" and
 * "Single Responsibility" software design principles.
 */
public class Validate {

    // validation error messages, one per field to be validated
    private String userEmailMsg = "";
    private String userPwMsg = "";
    private String userPw2Msg = "";
    private String membershipFeeMsg = "";
    private String userRoleMsg = "";
    private String birthdayMsg = "";

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

        // this is not needed for insert, but will be needed for update.
        if (webUserStringData.webUserId != null && webUserStringData.webUserId.length() != 0) {
            ValidateInteger vi = new ValidateInteger(webUserStringData.webUserId, true);
            webUserTypedData.setWebUserId(vi.getConvertedInteger());
        }

        ValidateEmail vemail = new ValidateEmail(webUserStringData.userEmail, true);
        webUserTypedData.setUserEmail(vemail.getConvertedEmail());
        this.userEmailMsg = vemail.getError();

        ValidateString vstr = new ValidateString(webUserStringData.userPw, 45, true);
        webUserTypedData.setUserPw(vstr.getConvertedString());
        this.userPwMsg = vstr.getError();

        vstr = new ValidateString(webUserStringData.userPw2, 45, true);
        webUserTypedData.setUserPw2(vstr.getConvertedString());
        if (webUserTypedData.getUserPw().compareTo(webUserTypedData.getUserPw2()) != 0) {
            this.userPw2Msg = "Both passwords must match.";
        }

        ValidateDecimal vdec = new ValidateDecimal(webUserStringData.membershipFee, false);
        webUserTypedData.setMembershipFee(vdec.getConvertedDecimal());
        this.membershipFeeMsg = vdec.getError();

        ValidateInteger vi = new ValidateInteger(webUserStringData.userRoleId, true);
        webUserTypedData.setUserRoleId(vi.getConvertedInteger());
        this.userRoleMsg = vi.getError();

        ValidateDate vdate = new ValidateDate(webUserStringData.birthday, false);
        webUserTypedData.setBirthday(vdate.getConvertedDate());
        this.birthdayMsg = vdate.getError();

        String allMessages = this.userEmailMsg + this.userPwMsg + this.userPw2Msg + this.membershipFeeMsg
                + this.userRoleMsg + this.birthdayMsg;
        isValidated = (allMessages.length() == 0);
    }

    public StringData getStringData() {
        return this.webUserStringData;
    }

    public TypedData getTypedData() {
        return this.webUserTypedData;
    }

    public String getUserEmailMsg() {
        return this.userEmailMsg;
    }

    public String getUserPwMsg() {
        return this.userPwMsg;
    }

    public String getUserPw2Msg() {
        return this.userPw2Msg;
    }

    public String getMembershipFeeMsg() {
        return this.membershipFeeMsg;
    }

    public String getUserRoleMsg() {
        return this.userRoleMsg;
    }

    public String getBirthdayMsg() {
        return this.birthdayMsg;
    }

    public boolean isValidated() {
        return this.isValidated;
    }

    public String getDebugMsg() {
        return this.debugMsg;
    }

    public String getAllValidationErrors() {
        String allMessages = "userEmail error: " + this.userEmailMsg
                + ", userPw error: " + this.userPwMsg
                + ", userPw2 error: " + this.userPw2Msg
                + ", membershipFee error: " + this.membershipFeeMsg
                + ", userRoleMsg error: " + this.userRoleMsg
                + ", dateAdded error: " + this.birthdayMsg;
        return allMessages;
    }
} // class
