package model.WebUser;

import java.sql.*;
import dbUtils.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains all code that modifies records in a table in the
 * database. So, Insert, Update, and Delete code will be in this class
 * (eventually). Right now, it's just doing DELETE.
 *
 * This class requires an open database connection for its constructor method.
 */
public class WebUserMods {

    private DbConn dbc;  // Open, live database connection
    private String errorMsg = "";
    private String debugMsg = "";

    // all methods of this class require an open database connection.
    public WebUserMods(DbConn dbc) {
        this.dbc = dbc;
    }

    public String getDebugMsg() {
        return this.debugMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String insert(Validate wuValidate) {

        this.errorMsg = "";// empty error message means it worked.
        this.debugMsg = "";

        // dont even try to insert if the user data didnt pass validation.
        if (!wuValidate.isValidated()) {
            this.errorMsg = "Cannot insert due to validation errors. Please try again.";
            return this.errorMsg;
        }

        TypedData wuTypedData = (TypedData) wuValidate.getTypedData();
        String sql = "INSERT INTO web_user (user_email, user_password, membership_fee, birthday, user_role_id"
                + ") VALUES (?,?,?,?,?)";
        try {
            // This is Sally's wrapper class for java.sql.PreparedStatement
            PrepStatement pStat = new PrepStatement(dbc, sql);
            pStat.setString(1, wuTypedData.getUserEmail());
            pStat.setString(2, wuTypedData.getUserPw());
            pStat.setBigDecimal(3, wuTypedData.getMembershipFee());
            pStat.setDate(4, wuTypedData.getBirthday());
            pStat.setInt(5, wuTypedData.getUserRoleId());
            this.errorMsg = pStat.getAllErrors();
            if (this.errorMsg.length() != 0) {
                return this.errorMsg;
            }

            //System.out.println("************* got past encoding");
            try {
                // extract the real java.sql.PreparedStatement from Sally's wrapper class.
                int numRows = pStat.getPreparedStatement().executeUpdate();
                if (numRows == 1) {
                    return ""; // all is GOOD, one record inserted is what we expect
                } else {
                    this.errorMsg = "Error: " + new Integer(numRows).toString()
                            + " records were inserted where only 1 expected."; // probably never get here, would be bulk sql insert
                    return this.errorMsg;
                }
            } // try execute the statement
            catch (SQLException e) {
                if (e.getSQLState().equalsIgnoreCase("S1000")) {
                    // this error would only be possible for a non-auto-increment primary key.
                    this.errorMsg = "Cannot insert: a record with that ID already exists.";
                } else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
                    this.errorMsg = "Cannot insert: duplicate entry."; // for example a unique key constraint.
                } else if (e.getMessage().toLowerCase().contains("foreign key")) {
                    this.errorMsg = "Cannot insert: User Role does not exist."; // for example a unique key constraint.
                } else {
                    this.errorMsg = "WebUserMods.insert: SQL Exception while attempting insert. "
                            + "SQLState:" + e.getSQLState()
                            + ", Error message: " + e.getMessage();
                    // this message would show up in the NetBeans log window (below the editor)
                    System.out.println("************* " + this.errorMsg);
                }
                return this.errorMsg;
            } // catch
            catch (Exception e) {
                // this message would show up in the NetBeans log window (below the editor)
                this.errorMsg = "WebUserMods.insert: General Error while attempting the insert. " + e.getMessage();
                System.out.println("****************** " + this.errorMsg);
                return this.errorMsg;
            } // catch
        } // trying to prepare the statement
        catch (Exception e) {
            this.errorMsg = "WebUserMods.insert: General Error while trying to prepare the SQL INSERT statement. " + e.getMessage();
            System.out.println("****************** " + this.errorMsg);
            return this.errorMsg;
        }
    }// method

    public static StringData find(DbConn dbc, String strEmailAddress, String strUserPwd) {
        StringData foundCust = new StringData();

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            System.out.println("*** before preparing statement");

            String sql = "select web_user_id, user_email "
                    + "from web_user where user_email = ? and user_password = ?";

            stmt = dbc.getConn().prepareStatement(sql);
            System.out.println("*** statement prepared");

            // this puts the user's input (from variable strEmailAddress)
            // into the 1st question mark of the sql statement above.
            stmt.setString(1, strEmailAddress);
            System.out.println("*** email address specified");

            // this puts the user's input (from variable strUserPwd)
            // into the 2nd question mark of the sql statement above.
            stmt.setString(2, strUserPwd);
            System.out.println("*** pwd specified");

            results = stmt.executeQuery();
            System.out.println("*** query executed");

            if (results.next()) {
                System.out.println("*** record selected");
                foundCust.userEmail = results.getString("user_email");
                foundCust.webUserId = results.getString("web_user_id");
                System.out.println("*** 1 field extracted");
                results.close();
                stmt.close();
                return foundCust;
            } else {
                return null; // means customer not found with given credentials.
            }
        } catch (Exception e) {
            foundCust.errorMsg = "Exception thrown in model.WebUser.WebUserMods.find(): " + e.getMessage();
            return foundCust;
        }
    }

    public StringData find(String primaryKey) {
        this.errorMsg = "";  // clear any error message from before.
        try {
            String sql = "SELECT * FROM web_user where web_user_id=?";
            PreparedStatement sqlSt = dbc.getConn().prepareStatement(sql);
            sqlSt.setString(1, primaryKey);

            try {
                ResultSet results = sqlSt.executeQuery(); // expecting only one row in result set
                StringData stringData = this.extractResultSetToStringData(results);//

                if (stringData != null) {
                    System.out.println("*** WebUserMods.find: Web User (found or not found) is " + stringData.toString());
                    return stringData; // if stringData is full, record found. else all fields will be blank "".
                } else { // stringData null means there was a problem extracting data
                    // check the System.out message in the log to see exact exception error msg.
                    return null;
                }
            } catch (Exception e) {
                this.errorMsg = e.getMessage();
                System.out.println("*** WebUserMods.find: exception thrown running Select Statement " + primaryKey
                        + ". Error is: " + this.errorMsg);
                return null;
            }
        }// try
        catch (Exception e) {
            this.errorMsg = e.getMessage();
            System.out.println("*** WebUserMods.find: exception thrown Preparing Select Statement with PK " + primaryKey
                    + ". Error is: " + this.errorMsg);
            return null;
        }
    }

    public StringData extractResultSetToStringData(ResultSet results) {
        StringData wuStringData = new StringData();
        try {
            if (results.next()) { // we are expecting only one rec in result set, so while loop not needed.
                //select web_user_id, user_email, user_password, membership_fee,  user_role_id, birthday from web_user

                wuStringData.webUserId = FormatUtils.objectToString(results.getObject("web_user_id"));
                wuStringData.userEmail = FormatUtils.objectToString(results.getObject("user_email"));
                wuStringData.userPw = FormatUtils.objectToString(results.getObject("user_password"));
                wuStringData.userPw2 = wuStringData.userPw;
                wuStringData.membershipFee = FormatUtils.objectToString(results.getObject("membership_fee"));
                wuStringData.userRoleId = FormatUtils.objectToString(results.getObject("user_role_id"));
                wuStringData.birthday = FormatUtils.formatDate(results.getObject("birthday"));
                wuStringData.recordStatus = "Record Found";

                System.out.println("*** WebUserMods.extractResultSetToStringData: record values are "
                        + wuStringData.toString());

                return wuStringData; // means OK, record found and wu has been filled
            } else {
                wuStringData.recordStatus = "Record Not Found";
                return wuStringData; // not found, all fields will be blank
            }
        } catch (Exception e) {
            System.out.println("*** WebUserMods.extractResultSetToStringData() Exception: " + e.getMessage());
            return null;
        } // catch misc error
    } // method  

    // Returning "" empty string means the UPDATE was successful
    public String update(Validate validate) {
        this.errorMsg = "";

        // dont even try to insert if the user data didnt pass validation.
        if (!validate.isValidated()) {
            this.errorMsg = "Please edit record and resubmit";
            return this.errorMsg;
        }

        TypedData wuTypedData = (TypedData) validate.getTypedData();
        String sql = "UPDATE web_user SET user_email=? "
                + ", user_password=?, membership_fee=?  "
                + ", birthday=?, user_role_id=? where web_user_id = ?";

        try {

            // This is Sally's wrapper class for java.sql.PreparedStatement
            PrepStatement pStat = new PrepStatement(dbc, sql);
            pStat.setString(1, wuTypedData.getUserEmail());
            pStat.setString(2, wuTypedData.getUserPw());
            pStat.setBigDecimal(3, wuTypedData.getMembershipFee());
            pStat.setDate(4, wuTypedData.getBirthday());
            pStat.setInt(5, wuTypedData.getUserRoleId());
            pStat.setInt(6, wuTypedData.getWebUserId());
            this.errorMsg = pStat.getAllErrors();
            if (this.errorMsg.length() != 0) {
                return this.errorMsg;
            }

            //System.out.println("******* Trying to update Web User with id: ["+ wu.getIdWebUser() + "]");
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
                    this.errorMsg = "That email is already taken, please try again.";
                    System.out.println(this.errorMsg);
                    return this.errorMsg;
                } else if (e.getErrorCode() == 1452) {
                    this.errorMsg = "Please enter a valid user role key.";
                    System.out.println(this.errorMsg);
                    return this.errorMsg;
                } else {
                    this.errorMsg = "WebUserMods.update: SQL Exception during update operation. "
                            + "SQLState [" + e.getSQLState()
                            + "], error message [" + e.getMessage() + "]";
                    System.out.println(this.errorMsg);
                    //e.printStackTrace();
                    return this.errorMsg;
                }
            } // catch
            catch (Exception e) {
                this.errorMsg = "WebUserMods.update: General Exception during update operation. "
                        + e.getMessage();
                System.out.println(this.errorMsg);
                //e.printStackTrace();
                return this.errorMsg;
            } // catch
        } // try
        catch (Exception e) {
            this.errorMsg = "WebUserMods.update: Problem preparing statement (and/or substituting parameters). "
                    + e.getMessage();
            System.out.println(this.errorMsg);
            //e.printStackTrace();
            return this.errorMsg;
        } // catch
    }// method

    public String delete(String primaryKey) {
        this.errorMsg = "";  // clear any error message from before.

        String sql = "DELETE FROM web_user where web_user_id=?";
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

            // If there are still tracking records associated with this web user...
            if (e.getSQLState().equalsIgnoreCase("23000")) {
                // Here, we will check how many records need to be deleted in the associative
                // table and notify the user.
                int numTrackingRecords = 0; // number of tracking records that need to be deleted
                // SQL statement to select all tracking records associated with web user.
                sql = "SELECT * FROM tracking_record WHERE web_user_id = ?";
                try {
                    PreparedStatement sqlSt = dbc.getConn().prepareStatement(sql);
                    sqlSt.setString(1, primaryKey);
                    if (sqlSt.execute()) {
                        ResultSet results = sqlSt.getResultSet();
                        while (results.next()) { // count the number of records 
                            numTrackingRecords += 1;
                        }
                        this.errorMsg = "Please delete the " + numTrackingRecords + " tracking record(s) associated with this user and try again.";
                    } else {
                        this.errorMsg = "Error in WebUserMods.delete: could not determine how many tracking records need to be deleted.";
                    }
                } catch (SQLException ex) {
                    this.errorMsg = "Problem with SQL in WebUserMods.delete: "
                            + "SQLState [" + e.getSQLState()
                            + "], error message [" + e.getMessage() + "]";
                }
            } else if (e.getSQLState().equalsIgnoreCase("S1000")) {
                this.errorMsg = "Could not delete.";
            } else {
                this.errorMsg += "Problem with SQL in WebUserMods.delete: "
                        + "SQLState [" + e.getSQLState()
                        + "], error message [" + e.getMessage() + "]";
                System.out.println(this.errorMsg);
            }
            return this.errorMsg;
        } // catch
        catch (Exception e) {
            this.errorMsg = "General Error in WebUserMods.delete: "
                    + e.getMessage();
            System.out.println(this.errorMsg);
            return this.errorMsg;
        } // catch
    }// method delete

} // class
