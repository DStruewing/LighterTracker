package dbUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

/**
 * Collection of static methods that format various data types (all passed in as objects).
 * For each data type, there is a method that formats the data type and an associated
 * method that wraps that formatted data in an HTML <td> tag.
 */
public class FormatUtils {

    // DecimalFormat percentFormat = new DecimalFormat("%###.##");
    // Turns a date into a nicely formatted String.
    public static String formatDate(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            java.util.Date dateval = (java.util.Date) obj;
            SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
            dateformat.setLenient(false);
            return dateformat.format(dateval);
        } catch (Exception e) {
            return "bad date in FormatUtils.formatDate: " + obj.toString() + " error: " + e.getMessage();
        }
    } // formatDate
    
    public static String YMDtoMDY(String ymd) {
        String year = ymd.substring(0,4);
        String month = ymd.substring(5, 7);
        String day = ymd.substring(8, 10);
        return month + "/" + day + "/" + year;
    }
    
    public static String nullToEmptyString(String in) {
        if(in == null) {
            return "";
        } else {
            return in;
        }
    }

    public static String formatDateTd(Object obj) {
        String out = "<td style='text-align:center'>";
        String strDate = formatDate(obj);
        if (strDate.length() == 0) {
            // if you don't put a "non-breaking space" in an empty td/cell, 
            // the cell's border doesn't show !
            out += "&nbsp;";
        } else {
            out += strDate;
        }
        out += "</td>";
        return out;
    } // formatDateTd

    public static String formatDollar(Object obj) {
        // null gets converted to empty string
        if (obj == null) {
            return "";
        }
        BigDecimal bd = (BigDecimal) obj;
        try {
            DecimalFormat intFormat = new DecimalFormat("$###,###,###,##0.00");
            return intFormat.format(bd);
        } catch (Exception e) {
            return "bad Dollar Amount in FormatUtils:" + obj.toString() + " Error:" + e.getMessage();
        }
    } // formatDollar

    public static String formatDollarTd(Object obj) {
        String out = "<td style='text-align:right'>";
        String strDollarAmt = formatDollar(obj);
        if (strDollarAmt.length() == 0) {
            // if you don't put a "non-breaking space" in an empty td/cell, 
            // the cell's border doesn't show !
            out += "&nbsp;";
        } else {
            out += strDollarAmt;
        }
        out += "</td>";
        return out;
    } // formatDollarTd
    
    public static String formatFloat(Object obj) {
        if (obj == null) {
            return "";
        } else {
            try {
                Float fval = (Float) obj;
                DecimalFormat intFormat = new DecimalFormat("##.######");
                return intFormat.format(fval);
            } catch (Exception e) {
                return "bad Float in FormatUtils:" + obj.toString() + " Error:" + e.getMessage();
            }
        }
    } // formatFloat
    
    public static String formatFloatTd(Object obj) {
        String out = "<td style='text-align:right'>";
        String strFloat = formatFloat(obj);
        if (strFloat.length() == 0) {
            // if you don't put a "non-breaking space" in an empty td/cell, 
            // the cell's border doesn't show !
            out += "&nbsp;";
        } else {
            out += strFloat;
        }
        out += "</td>";
        return out;
    } // formatFloatTd

    public static String formatInteger(Object obj) {
        if (obj == null) {
            return "";
        } else {
            try {
                Integer ival = (Integer) obj;
                DecimalFormat intFormat = new DecimalFormat("###,###,###,##0");
                return intFormat.format(ival);
            } catch (Exception e) {
                return "bad Integer in FormatUtils:" + obj.toString() + " Error:" + e.getMessage();
            }
        }
    } // formatInteger

    public static String formatIntegerTd(Object obj) {
        String out = "<td style='text-align:right'>";
        String strInteger = formatInteger(obj);
        if (strInteger.length() == 0) {
            // if you don't put a "non-breaking space" in an empty td/cell, 
            // the cell's border doesn't show !
            out += "&nbsp;";
        } else {
            out += strInteger;
        }
        out += "</td>";
        return out;
    } // formatIntegerTd
    
    public static String formatBool(Object obj) {
        if (obj == null) {
            return "";
        } else {
            try {
                Integer ival = (Integer) obj;
                if (ival == 1) {
                    return "Yes";
                } else {
                    return "No";
                }
            } catch (Exception e) {
                return "bad Boolean in FormatUtils:" + obj.toString() + " Error:" + e.getMessage();
            }
        }
    } // formatBool
    
    public static String formatBoolTd(Object obj) {
        String out = "<td style='text-align:left'>";
        String str = formatBool(obj);
        if (str.length() == 0) {
            out += "&nbsp;";
        } else {
            out += str;
        }
        out += "</td>";
        return out;
    } // formatBoolTd

    // this is not really formatting, but just converting to string type.
    public static String formatString(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return (String) obj;
        }
    } // formatString

    public static String formatStringTd(Object obj) {
        String out = "<td style='text-align:left'>";
        String str = formatString(obj);
        if (str.length() == 0) {
            out += "&nbsp;";
        } else {
            out += str;
        }
        out += "</td>";
        return out;
    } // formatString
    
    // convert object to string. If null, convert that to empty string.  
    public static String objectToString(Object o) {
        if (o == null) {
            return "";
        }
        try {
            return o.toString();
        } catch (Exception e) {
            return "Exception converting object to string FormatUtils.objectToString(): " + e.getMessage();
        }
    }

} // FormatUtils class