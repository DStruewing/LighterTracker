package Release.dbUtils;

import java.math.BigDecimal;
import java.sql.*;

/* This is Sally's wrapper class around PreparedStatement. PreparedStatement (normal version)
 does not handle converting from String to the desired type, nor does it encode null very
 easily. 
 */
public class PrepStatement {

    PreparedStatement ps = null;
    String sql = "";
    String allErrors = "";
    String log = "<br/><br/>PreStatement Log<br/><br/>";
    final boolean NOERROR = false;
    final boolean ERROR = true;

    public PrepStatement(DbConn dbc, String sql) {
        this.sql = sql;
        try {
            this.ps = dbc.getConn().prepareStatement(sql);
            writeLog("PrepStatement constructor(). No error in sql: " + this.sql, NOERROR);
        } catch (Exception e) {
            writeLog("PrepStatement constructor(): sql is " + sql + " error message is " + e.getMessage(), ERROR);
        }
    }

    private void writeLog(String msg, boolean isError) {
        msg += "<br/>";
        this.log += msg;
        if (isError) {
            this.allErrors += msg;
        }
    }

    public String getSql() {
        return this.sql;
    }

    public String getAllErrors() {
        return this.allErrors; // empty string means there was no error.
    }

    public String getLog() {
        return this.log;
    }

    public PreparedStatement getPreparedStatement() {
        return this.ps;
    }

    /* If you forget to close a Prepared Statement, it does not cause a database connection leak, 
     so it is not horrible, but it probably will not allow the JSP page to be able to reuse the 
     database connection (which is why we have the JSP page being the code that is responsible for
     declaring a database connection object (using it once or several times, then closing it).  */
    public void close() {
        try {
            this.ps.close();
            writeLog("PrepStatement was closed (and it had been open).", NOERROR);
        } catch (Exception e) {
            writeLog("PrepStatement was closed (when it had not been open) - should not be a problem.", NOERROR);
        }
    }

    /* setDate: encodes a date into the position-th question mark of a sql statement. 
     works like java.sql.PreparedStatement.setDate() except that it also encodes null 
     whenever necessary. */
    public void setDate(int position, java.sql.Date dateVal) {

        if (dateVal == null) {
            try {
                ps.setNull(position, java.sql.Types.DATE);
                writeLog("Successfully encoded empty string (as null Date) into question mark number "
                        + position + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("problem trying to encode empty string (as null Date) into question mark number "
                        + position + " of sql " + this.sql + ". Exception message: " + e.getMessage(), ERROR);
            } // end of catch trying to encode null Date
        } else {
            try {
                ps.setDate(position, dateVal);
                writeLog("Sucessfully encoded non-null Date [" + dateVal.toString()
                        + "] into question mark number " + position + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("Problem trying to encode non-null Date [" + dateVal.toString()
                        + "] into question mark number " + position + " of sql " + this.sql
                        + ". Exception message: " + e.getMessage(), ERROR);
            } // end of catch trying to encode non-null Date
        } // else
    }// method

    /* setInt: encodes an Integer value into the position-th question mark of a sql statement. 
     works like java.sql.PreparedStatement.setInt() except that it also encodes null 
     whenever necessary. */
    public void setInt(int position, Integer intVal) {

        if (intVal == null) {
            try {
                ps.setNull(position, java.sql.Types.INTEGER);
                writeLog("Sucessfully encoded null into Integer question mark number "
                        + position + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("Problem trying to encode null into Integer question mark number "
                        + position + " of sql " + this.sql + ". Exception message: " + e.getMessage(), ERROR);
            }
        } else {
            try {
                ps.setInt(position, intVal);
                writeLog("Sucessfully encoded non-null Integer value [" + intVal.toString()
                        + "] into question mark number " + position
                        + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("Problem trying to encode non-null Integer [" + intVal.toString()
                        + "] into question mark number " + position
                        + " of sql " + this.sql + ". Exception message: " + e.getMessage(), ERROR);
            } // catch
        } // else
    }// method
    
    /* setFloat: encodes a Float value into the position-th question mark of a sql statement. 
     works like java.sql.PreparedStatement.setInt() except that it also encodes null 
     whenever necessary. */
    public void setFloat(int position, Float floatVal) {

        if (floatVal == null) {
            try {
                ps.setNull(position, java.sql.Types.FLOAT);
                writeLog("Sucessfully encoded null into Float question mark number "
                        + position + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("Problem trying to encode null into Float question mark number "
                        + position + " of sql " + this.sql + ". Exception message: " + e.getMessage(), ERROR);
            }
        } else {
            try {
                ps.setFloat(position, floatVal);
                writeLog("Sucessfully encoded non-null Float value [" + floatVal.toString()
                        + "] into question mark number " + position
                        + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("Problem trying to encode non-null Float [" + floatVal.toString()
                        + "] into question mark number " + position
                        + " of sql " + this.sql + ". Exception message: " + e.getMessage(), ERROR);
            } // catch
        } // else
    }// method


    /* setDecimal: encodes an Decimal value into the position-th question mark of a sql statement. 
     works like java.sql.PreparedStatement.setDecimal() except that it also encodes null 
     whenever necessary. */
    public void setBigDecimal(int position, BigDecimal decVal) {

        if (decVal == null) {
            try {
                ps.setNull(position, java.sql.Types.DECIMAL);
                writeLog("Successfully encoded null into Big Decimal value into question mark number "
                        + position + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("Problem trying to encode null into Big Decimal question mark number "
                        + position + " of sql " + this.sql + ". Exception message: " + e.getMessage(), ERROR);
            }
        } else {
            try {
                ps.setBigDecimal(position, decVal);
                writeLog("Sucessfully encoded non-null Big Decimal value [" + decVal.toString()
                        + "] into question mark number " + position
                        + " of sql " + this.sql, NOERROR);
            } catch (Exception e) {
                writeLog("problem trying to encode not-null Decimal value [" + decVal.toString()
                        + "] into (Decimal) position " + position
                        + " of sql " + this.sql + ". Exception message: " + e.getMessage(), ERROR);
            } // catch
        } // else
    }// method

    /* Encode String into the position-th question mark of a prepared statement. 
     (That particular question mark should be expecting something
     typed String.) Return "" empty string if all is good, else return an error message. 
     If the input is a null value, encode empty string. */
    public void setString(int position, String strVal) {

        // This should not happen (would be programmer error), but just prevent 
        // exception by replacing null value with empty string. Programmer will notice
        // if empty string gets inserted into database where non-empty string expected.
        if (strVal == null) {
            strVal = ""; 
        }

        try {
            ps.setString(position, strVal);
            writeLog("Sucessfully encoded String value [" + strVal
                    + "] into question mark number " + position
                    + " of sql " + this.sql, NOERROR);
        } catch (Exception e) {
            writeLog("Problem trying to encode String value [" + strVal
                    + "] into question mark number " + position
                    + " of sql " + this.sql + ". Exception: " + e.getMessage(), ERROR);
        } // catch
    }// method
} // class
