package dbUtils;

public class ValidateInteger {

    private Integer convertedInteger = null;
    private String error = "";

    /* Check string "val" to see if it has a valid integer in it.
     * if required is false, "" (empty string is OK), convertedInteger => null.
     * error holds "" if value passes validation.
     */
    public ValidateInteger(String val, boolean required) {
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
            this.convertedInteger = new Integer(val);
            this.error = "";
            //System.out.println("******************ValidateInteger: "+val+ " is a good integer.");
        } catch (Exception e) {
            this.error = "Please enter an integer";
        }
    }

    public String getError() {
        return this.error;
    }

    public Integer getConvertedInteger() {
        return this.convertedInteger;
    }
} // class