package dbUtils;

import org.apache.commons.validator.routines.EmailValidator;


public class ValidateEmail {

    private String convertedEmail = null;
    private String error = "";

    /* Check string "val" to see if it has a valid integer in it.
     * if required is false, "" (empty string is OK), convertedInteger => null.
     * error holds "" if value passes validation.
     */
    public ValidateEmail(String val, boolean required) {
        if (val == null) {
            this.error = "Programmer error: should not be trying to validate null in ValidateInteger constructor";
            return;
        }
        if ((val.length() == 0) && !required) {
            //System.out.println("******************ValidateInteger: Null is OK.");
            this.error = "";  // Since this field is not required, empty string is a valid user entry.
            return;
        }
        try {
            EmailValidator validator = EmailValidator.getInstance();
            if(validator.isValid(val)) {
                this.convertedEmail = val;
                this.error = "";
            } else {
                this.error = "Please enter a valid email address";
            }
            //System.out.println("******************ValidateInteger: "+val+ " is a good integer.");
        } catch (Exception e) {
            this.error = "Please enter an integer";
        }
    }

    public String getError() {
        return this.error;
    }

    public String getConvertedEmail() {
        return this.convertedEmail;
    }
} // class