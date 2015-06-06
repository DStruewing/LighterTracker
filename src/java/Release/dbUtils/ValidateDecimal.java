package Release.dbUtils;

public class ValidateDecimal {

    private java.math.BigDecimal convertedDecimal = null;
    private String error = "";

    /* Check string "val" to see if it has a valid integer in it.
     * if required is false, "" (empty string is OK), convertedInteger => null.
     * error holds "" if value passes validation.
     */
    public ValidateDecimal(String val, boolean required) {

        if (val == null) {
            this.error = "Programmer error: should not be trying to validate null in ValidateDecimal constructor";
            return;
        }
        if ((val.length() == 0) && !required) {
            //System.out.println("******************ValidateDecimal: Null is OK.");
            this.error = "";  // Since this field is not required, empty string is valid user entry.
            return;
        }
        try {
            this.convertedDecimal = new java.math.BigDecimal(val);
            this.error = "";
            //System.out.println("******************ValidateDecimal: "+val+ " is a good decimal.");
        } catch (Exception e) {
            this.error = "Please enter a dollar amount";
        }
    }

    public String getError() {
        return this.error;
    }

    public java.math.BigDecimal getConvertedDecimal() {
        return this.convertedDecimal;
    }
} // class