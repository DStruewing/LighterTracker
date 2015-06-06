/*
 * David Struewing, 2015.
 */
package Release.Record;

import Release.dbUtils.DbConn;
import Release.dbUtils.PrepStatement;
import java.sql.SQLException;

/**
 *
 * @author david
 */
public class RecordMods {
	
	private DbConn dbc;  // Open, live database connection
	public String errorMsg;

    // All methods of this class require an open database connection.
    public RecordMods(DbConn dbc) {
        this.dbc = dbc;
    }
	
	public String insert(Validate recordValidate) {

        this.errorMsg = "";// empty error message means it worked.

        // dont even try to insert if the data didnt pass validation.
        if (!recordValidate.isValidated()) {
            this.errorMsg = "Cannot insert due to validation errors. Please try again.";
            return this.errorMsg;
        }

        TypedData recordTypedData = (TypedData) recordValidate.getTypedData();
        String sql = "INSERT INTO tracking_record (web_user_id, lighter_id, record_date, latitude, longitude, lighter_condition)"
                + " VALUES (?,?,?,?,?,?)";
        try {
            // This is Sally's wrapper class for java.sql.PreparedStatement
            PrepStatement pStat = new PrepStatement(dbc, sql);
            pStat.setInt(1, recordTypedData.getWebUserId());
            pStat.setInt(2, recordTypedData.getLighterId());
            pStat.setDate(3, recordTypedData.getDate());
            pStat.setFloat(4, recordTypedData.getLatitude());
            pStat.setFloat(5, recordTypedData.getLongitude());
			pStat.setString(6, "Inserted from release site.");
            this.errorMsg = pStat.getAllErrors();
            if (this.errorMsg.length() != 0) {
                return this.errorMsg;
            }

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
                    this.errorMsg = "Cannot insert: Foreign key does not exist."; // for example a unique key constraint.
                } else {
                    this.errorMsg = "WebUserMods.insert: SQL Exception while attempting insert. "
                            + "SQLState:" + e.getSQLState()
                            + ", Error message: " + e.getMessage();
                    // this message would show up in the NetBeans log window (below the editor)
                }
                return this.errorMsg;
            } // catch
            catch (Exception e) {
                // this message would show up in the NetBeans log window (below the editor)
                this.errorMsg = "RecordMods.insert: General Error while attempting the insert. " + e.getMessage();
                return this.errorMsg;
            } // catch
        } // trying to prepare the statement
        catch (Exception e) {
            this.errorMsg = "RecordMods.insert: General Error while trying to prepare the SQL INSERT statement. " + e.getMessage();
            return this.errorMsg;
        }
    }// method
	
}
