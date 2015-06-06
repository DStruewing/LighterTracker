package model.Lighter;

import dbUtils.*;
import java.sql.*;

/**
 * This class contains all code that modifies records in a table in the
 * database. So, Insert, Update, and Delete code will be in this class
 * (eventually). Right now, it's just doing DELETE.
 *
 * This class requires an open database connection for its constructor method.
 */
public class LighterMods {

    private DbConn dbc;  // Open, live database connection
    private String errorMsg = "";
    private String debugMsg = "";

    // all methods of this class require an open database connection.
    public LighterMods(DbConn dbc) {
        this.dbc = dbc;
    }

    public String getDebugMsg() {
        return this.debugMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String insert(Validate lighterValidate) {

        this.errorMsg = ""; // Empty error message means it worked.
        this.debugMsg = "";

        // Dont even try to insert if the lighter data didnt pass validation.
        if (!lighterValidate.isValidated()) {
            this.errorMsg = "Cannot insert due to validation errors. Please try again.";
            return this.errorMsg;
        }

        TypedData lighterTypedData = (TypedData) lighterValidate.getTypedData();
        String sql = "INSERT INTO lighter (lighter_name, purchase_date, lighter_color, lighter_code"
                + ") VALUES (?,?,?,?)";
        try {
            // This is Sally's wrapper class for java.sql.PreparedStatement
            PrepStatement pStat = new PrepStatement(dbc, sql);
            pStat.setString(1, lighterTypedData.getLighterName());
            pStat.setDate(2, lighterTypedData.getPurchaseDate());
            pStat.setString(3, lighterTypedData.getLighterColor());
			pStat.setString(4, lighterTypedData.getTrackingCode());
            this.errorMsg = pStat.getAllErrors();
            if (this.errorMsg.length() != 0) {
                return this.errorMsg;
            }

            try {
                // Extract the real java.sql.PreparedStatement from Sally's wrapper class.
                int numRows = pStat.getPreparedStatement().executeUpdate();
                if (numRows == 1) {
                    return ""; // All is GOOD, one record inserted is what we expect.
                } else {
                    this.errorMsg = "Error: " + new Integer(numRows).toString()
                            + " records were inserted where only 1 expected."; // Probably never get here, would be bulk sql insert.
                    return this.errorMsg;
                }
            } // Try execute the statement.
            catch (SQLException e) {
                if (e.getSQLState().equalsIgnoreCase("S1000")) {
                    // This error would only be possible for a non-auto-increment primary key.
                    this.errorMsg = "Cannot insert: a record with that ID already exists.";
                } else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
                    this.errorMsg = "Cannot insert: duplicate entry."; // For example a unique key constraint.
                } else {
                    this.errorMsg = "LighterMods.insert: SQL Exception while attempting insert. "
                            + "SQLState:" + e.getSQLState()
                            + ", Error message: " + e.getMessage();
                    // This message would show up in the NetBeans log window (below the editor).
                    System.out.println("************* " + this.errorMsg);
                }
                return this.errorMsg;
            } // catch
            catch (Exception e) {
                // this message would show up in the NetBeans log window (below the editor)
                this.errorMsg = "LighterMods.insert: General Error while attempting the insert. " + e.getMessage();
                System.out.println("****************** " + this.errorMsg);
                return this.errorMsg;
            } // catch
        } // trying to prepare the statement // trying to prepare the statement
        catch (Exception e) {
            this.errorMsg = "LighterMods.insert: General Error while trying to prepare the SQL INSERT statement. " + e.getMessage();
            System.out.println("****************** " + this.errorMsg);
            return this.errorMsg;
        }
    }// method

    // Returning "" empty string means the UPDATE was successful
    public String update(Validate validate) {
        this.errorMsg = "";

        // dont even try to insert if the lighter data didnt pass validation.
        if (!validate.isValidated()) {
            this.errorMsg = "Please edit record and resubmit";
            return this.errorMsg;
        }

        TypedData lighterTypedData = (TypedData) validate.getTypedData();
        String sql = "UPDATE lighter SET lighter_name=? "
                + ", purchase_date=?, lighter_color=? "
                + ", lighter_code=? where lighter_id = ?";

        try {

            // This is Sally's wrapper class for java.sql.PreparedStatement
            PrepStatement pStat = new PrepStatement(dbc, sql);
            pStat.setString(1, lighterTypedData.getLighterName());
            pStat.setDate(2, lighterTypedData.getPurchaseDate());
            pStat.setString(3, lighterTypedData.getLighterColor());
			pStat.setString(4, lighterTypedData.getTrackingCode());
            pStat.setInt(5, lighterTypedData.getLighterId());
            this.errorMsg = pStat.getAllErrors();
            if (this.errorMsg.length() != 0) {
                return this.errorMsg;
            }

            //System.out.println("******* Trying to update Lighter with id: ["+ lighterTypedData.getLighterId() + "]");
            try {
                int numRows = pStat.getPreparedStatement().executeUpdate();
                if (numRows == 1) {
                    this.errorMsg = "";
                    return this.errorMsg; // all is GOOD, one record was updated like we expected.
                } else {
                    // we could be here (numRows==0) if record was not found.
                    // we could be here (numRows>1) if we forgot where clause -- would update all recs.
                    // In either case, it would probalby be a programmer error.
                    this.errorMsg = "Error: " + new Integer(numRows).toString()
                            + " records were updated (when only 1 record expected for update).";
                    return this.errorMsg;
                }
            } // try
            catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    this.errorMsg = "Duplicate name or tracking code. Please try again.";
                    System.out.println(this.errorMsg);
                    return this.errorMsg;
                } else {
                    this.errorMsg = "LighterMods.update: SQL Exception during update operation. "
                            + "SQLState [" + e.getSQLState()
                            + "], error message [" + e.getMessage() + "]";
                    System.out.println(this.errorMsg);
                    //e.printStackTrace();
                    return this.errorMsg;
                }
            } // catch
            catch (Exception e) {
                this.errorMsg = "LighterMods.update: General Exception during update operation. "
                        + e.getMessage();
                System.out.println(this.errorMsg);
                //e.printStackTrace();
                return this.errorMsg;
            } // catch
        } // try // try
        catch (Exception e) {
            this.errorMsg = "LighterMods.update: Problem preparing statement (and/or substituting parameters). "
                    + e.getMessage();
            System.out.println(this.errorMsg);
            //e.printStackTrace();
            return this.errorMsg;
        } // catch
    }// method

    public StringData findFromCode(String lighterCode) {
        this.errorMsg = ""; // Clear any previous error message.
        try {
            String sql = "SELECT * FROM lighter WHERE lighter_code=?";
            PreparedStatement sqlSt = dbc.getConn().prepareStatement(sql);
            sqlSt.setString(1, lighterCode);

            try {
                ResultSet results = sqlSt.executeQuery(); // expecting only one row in result set
                StringData stringData = this.extractResultSetToStringData(results);//

                if (stringData != null) {
                    return stringData; // if stringData is full, record found. else all fields will be blank "".
                } else { // stringData null means there was a problem extracting data
                    return null;
                }
            } catch (Exception e) {
                this.errorMsg = e.getMessage();
                System.out.println("*** LighterMods.findFromCode: exception thrown running Select Statement " + lighterCode
                        + ". Error is: " + this.errorMsg);
                return null;
            }
        } catch (Exception e) {
            this.errorMsg = e.getMessage();
            System.out.println("*** LighterMods.findFromCode: exception thrown Preparing Select Statement with code " + lighterCode
                    + ". Error is: " + this.errorMsg);
            return null;
        }
    }

    public StringData find(String primaryKey) {
        this.errorMsg = "";  // clear any error message from before.
        try {
            String sql = "SELECT * FROM lighter where lighter_id=?";
            PreparedStatement sqlSt = dbc.getConn().prepareStatement(sql);
            sqlSt.setString(1, primaryKey);

            try {
                ResultSet results = sqlSt.executeQuery(); // expecting only one row in result set
                StringData stringData = this.extractResultSetToStringData(results);//

                if (stringData != null) {
                    System.out.println("*** LighterMods.find: Lighter (found or not found) is " + stringData.toString());
                    return stringData; // if stringData is full, record found. else all fields will be blank "".
                } else { // stringData null means there was a problem extracting data
                    // check the System.out message in the log to see exact exception error msg.
                    return null;
                }
            } catch (Exception e) {
                this.errorMsg = e.getMessage();
                System.out.println("*** LighterMods.find: exception thrown running Select Statement " + primaryKey
                        + ". Error is: " + this.errorMsg);
                return null;
            }
        }// try
        catch (Exception e) {
            this.errorMsg = e.getMessage();
            System.out.println("*** LighterMods.find: exception thrown Preparing Select Statement with PK " + primaryKey
                    + ". Error is: " + this.errorMsg);
            return null;
        }
    }

    public StringData extractResultSetToStringData(ResultSet results) {
        StringData lighterStringData = new StringData();
        try {
            if (results.next()) { // we are expecting only one rec in result set, so while loop not needed.

                lighterStringData.lighterId = FormatUtils.objectToString(results.getObject("lighter_id"));
                lighterStringData.lighterName = FormatUtils.objectToString(results.getObject("lighter_name"));
                lighterStringData.purchaseDate = FormatUtils.formatDate(results.getObject("purchase_date"));
                lighterStringData.lighterColor = FormatUtils.objectToString(results.getObject("lighter_color"));
				lighterStringData.trackingCode = FormatUtils.objectToString(results.getObject("lighter_code"));
                lighterStringData.recordStatus = "Record Found";

                System.out.println("*** LighterMods.extractResultSetToStringData: record values are "
                        + lighterStringData.toString());

                return lighterStringData; // means OK, record found and lighter has been filled
            } else {
                lighterStringData.recordStatus = "Record Not Found";
                return lighterStringData; // not found, all fields will be blank
            }
        } catch (Exception e) {
            System.out.println("*** LighterMods.extractResultSetToStringData() Exception: " + e.getMessage());
            return null;
        } // catch misc error
    } // method  

    public String delete(String primaryKey) {
        this.errorMsg = "";  // clear any error message from before.

        String sql = "DELETE FROM lighter where lighter_id=?";
        try {
            PreparedStatement sqlSt = dbc.getConn().prepareStatement(sql);
            sqlSt.setString(1, primaryKey);

            int numRows = sqlSt.executeUpdate();
            if (numRows == 1) {
                this.errorMsg = "";
                return this.errorMsg; // all is GOOD
            } else {
                this.errorMsg = "Error - " + new Integer(numRows).toString()
                        + " records deleted when 1 was expected."; // probably never get here
                return this.errorMsg;
            }
        } // try
        catch (SQLException e) {
            this.errorMsg = "";

            // If there are still tracking records associated with this lighter...
            if (e.getSQLState().equalsIgnoreCase("23000")) {
                int numTrackingRecords = 0; // number of tracking records that need to be deleted
                // SQL statement to select all tracking records associated with lighter.
                sql = "SELECT * FROM tracking_record WHERE lighter_id = ?";
                try {
                    PreparedStatement sqlSt = dbc.getConn().prepareStatement(sql);
                    sqlSt.setString(1, primaryKey);
                    if (sqlSt.execute()) {
                        ResultSet results = sqlSt.getResultSet();
                        while (results.next()) { // count the number of records 
                            numTrackingRecords += 1;
                        }
                        this.errorMsg = "Please delete the " + numTrackingRecords + " tracking record(s) associated with this lighter and try again.";
                    } else {
                        this.errorMsg = "Error in LighterMods.delete: could not determine how many tracking records need to be deleted.";
                    }
                } catch (SQLException ex) {
                    this.errorMsg = "Problem with SQL in LighterMods.delete: "
                            + "SQLState [" + e.getSQLState()
                            + "], error message [" + e.getMessage() + "]";
                }
            } else if (e.getSQLState().equalsIgnoreCase("S1000")) {
                this.errorMsg = "Could not delete.";
            } else {
                this.errorMsg += "Problem with SQL in LighterMods.delete: "
                        + "SQLState [" + e.getSQLState()
                        + "], error message [" + e.getMessage() + "]";
                System.out.println(this.errorMsg);
            }
            return this.errorMsg;
        } // catch
        catch (Exception e) {
            this.errorMsg = "General Error in LighterMods.delete: "
                    + e.getMessage();
            System.out.println(this.errorMsg);
            return this.errorMsg;
        } // catch
    }// method delete
}
