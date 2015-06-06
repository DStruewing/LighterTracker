/*
 * David Struewing, 2015.
 */
package Release.WebUser;

import Release.dbUtils.DbConn;
import Release.dbUtils.PrepStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONObject;

/**
 *
 * @author david
 */
public class WebUserMods {

	private DbConn dbc;  // Open, live database connection
	public String errorMsg;

	// All methods of this class require an open database connection.
	public WebUserMods(DbConn dbc) {
		this.dbc = dbc;
	}

	public StringData login(String strEmailAddress, String strUserPwd) {

		StringData foundCust = new StringData();
		PreparedStatement stmt = null;
		ResultSet results = null;

		try {
			String sql = "select web_user_id, user_email "
					+ "from web_user where user_email = ? and user_password = ?";

			stmt = dbc.getConn().prepareStatement(sql);
			stmt.setString(1, strEmailAddress);
			stmt.setString(2, strUserPwd);

			results = stmt.executeQuery();
			if (results.next()) {
				foundCust.webUserId = results.getString("web_user_id");
				results.close();
				stmt.close();
				return foundCust;
			} else {
				return null; // means customer not found with given credentials.
			}
		} catch (Exception e) {
			foundCust.errorMsg = "Exception thrown in WebUser.WebUserMods.logon(): " + e.getMessage();
			return foundCust;
		}
	}

	public String insert(Validate webUserValidate) {

		JSONObject response = new JSONObject();

		if (!webUserValidate.isValidated()) {
			response.put("emailError", webUserValidate.getEmailMsg());
			response.put("passwordError", webUserValidate.getPasswordMsg());
			return response.toString();
		}

		TypedData webUserTypedData = (TypedData) webUserValidate.getTypedData();
		String sql = "INSERT INTO web_user (user_email, user_password, user_role_id)"
				+ " VALUES (?,?,?)";
		try {
			// This is Sally's wrapper class for java.sql.PreparedStatement
			PrepStatement pStat = new PrepStatement(dbc, sql);
			pStat.setString(1, webUserTypedData.getEmail());
			pStat.setString(2, webUserTypedData.getPassword());
			pStat.setInt(3, 2);

			if (pStat.getAllErrors().length() != 0) {
				response.put("error", pStat.getAllErrors());
				return response.toString();
			}

			try {
				// extract the real java.sql.PreparedStatement from Sally's wrapper class.
				int numRows = pStat.getPreparedStatement().executeUpdate();
				if (numRows == 1) {
					response.put("success", "true");
					response.put("email", webUserTypedData.getEmail());
					return response.toString();
				} else {
					response.put("error", "Error: " + new Integer(numRows).toString() + " records were inserted where only 1 expected.");
					return response.toString();
				}
			} // try execute the statement
			catch (SQLException e) {
				if (e.getSQLState().equalsIgnoreCase("S1000")) {
					response.put("error", "Cannot insert: a record with that ID already exists.");
					return response.toString();
				} else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
					response.put("duplicateError", "true");
					return response.toString();
				} else {
					response.put("error", "WebUserMods.insert: SQL Exception while attempting insert. "
							+ "SQLState:" + e.getSQLState()
							+ ", Error message: " + e.getMessage());
					return response.toString();
				}
			} // catch
			catch (Exception e) {
				response.put("error", "WebUserMods.insert: General Error while attempting the insert. " + e.getMessage());
				return response.toString();
			} // catch
		} // trying to prepare the statement
		catch (Exception e) {
			response.put("error", "WebUserMods.insert: General Error while trying to prepare the SQL INSERT statement. " + e.getMessage());
			return response.toString();
		}
	}
}
