package dbUtils;

public class ValidateFloat {

    private Float convertedFloat = null;
    private String error = "";

    /* Check string "val" to see if it has a valid integer in it.
     * if required is false, "" (empty string is OK), convertedFloat => null.
     * error holds "" if value passes validation.
     */
    public ValidateFloat(String val, boolean required) {

        if (val == null) {
            this.error = "Programmer error: should not be trying to validate null in ValidateFloat constructor";
            return;
        }
        if ((val.length() == 0) && !required) {
            //System.out.println("******************ValidateDecimal: Null is OK.");
            this.error = "";  // Since this field is not required, empty string is valid user entry.
            return;
        }
        try {
            this.convertedFloat = new Float(val);
            this.error = "";
            //System.out.println("******************ValidateDecimal: "+val+ " is a good decimal.");
        } catch (Exception e) {
            this.error = "Please enter a valid floating point number.";
        }
    }

    public String getError() {
        return this.error;
    }

    public Float getConvertedFloat() {
        return this.convertedFloat;
    }
} // class