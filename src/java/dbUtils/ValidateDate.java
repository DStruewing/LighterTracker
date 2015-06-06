package dbUtils;

public class ValidateDate {

    private java.sql.Date convertedDate = null;
    private String error = "";

    /* Check string "val" to see if it has a valid integer in it.
     * if required is false, "" (empty string is OK), convertedInteger => null.
     * error holds "" if value passes validation.
     */

    public ValidateDate(String val, boolean required) {
        // System.out.println("*************trying to convert ["+val+"] to date");

        if (val == null) {
            this.error = "Programmer error: trying to validate null in ValidateDate constructor";
            return;
        }
        if ((val.length() == 0) && !required) {
            this.error =  "";  // Since this field is not required, empty string is valid user entry.
            return;
        }
        try {
            java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat("MM/dd/yyyy"); //please notice the capital M
            dateformat.setLenient(false);
            java.util.Date myDate = dateformat.parse(val);
            convertedDate = new java.sql.Date(myDate.getTime());
            //return d.toString(); // debugging...
            this.error =  ""; // means date is good
        } catch (Exception e) {
            this.error = "Please enter a valid date (format: MM/DD/YYYY)";  // can also add (to debug) + e.getMessage();
        }
    }

    public String getError() {
        return this.error;
    }

    public java.sql.Date getConvertedDate() {
        return this.convertedDate;
    }
} // class