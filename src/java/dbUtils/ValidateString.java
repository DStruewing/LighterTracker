package dbUtils;

public class ValidateString {

    private String convertedString = "";
    private String error = "";

    /* Check string "val" to see if it is less than the max length
     * and also if it has at least one character (if required is true).
     *
     * error will provide the validation error for the user.
     * convertedString will be the truncated string (if necessary) or
     * empty string (if original value was null).
     */
    public ValidateString(String val, int maxlen, boolean required) {

        if (val == null) {
            this.error = "Programmer error: should not be trying to validate null in ValidatString constructor";
            return;
        }
        if (val.length() == 0) {
            if (required) {
                this.error = "Input is required";
                return;
            } else {
                return; // Empty string OK if fld not req'd.
            }
        }
        
        this.convertedString = val;
        if (val.length() > maxlen) {
            this.convertedString = val.substring(0, maxlen);
            this.error = "Please shorten to [" + convertedString + "]";
        }
    }

    public String getError() {
        return this.error;
    }

    public String getConvertedString() {
        return this.convertedString;
    }
} // class

