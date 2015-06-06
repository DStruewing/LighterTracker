/*
 * David Struewing, 2015.
 */
package Release.Lighter;

import Release.dbUtils.DbConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author david
 */
public class LighterMods {

    private DbConn dbc;  // Open, live database connection

    // All methods of this class require an open database connection.
    public LighterMods(DbConn dbc) {
        this.dbc = dbc;
    }

    public StringData find(String lighterCode) {

        StringData foundLighter = new StringData();
        PreparedStatement stmt = null;
        ResultSet results = null;

        try {
            String sql = "select lighter_id, lighter_name, lighter_color "
                    + "from lighter where lighter_code = ?";

            stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, lighterCode);

            results = stmt.executeQuery();
            if (results.next()) {
                foundLighter.lighterId = results.getString("lighter_id");
                foundLighter.name = results.getString("lighter_name");
                foundLighter.color = results.getString("lighter_color");
                results.close();
                stmt.close();
                return foundLighter;
            } else {
                return null; // means customer not found with given credentials.
            }
        } catch (Exception e) {
            foundLighter.errorMsg = "Exception thrown in Lighter.LighterMods.find(): " + e.getMessage();
            return foundLighter;
        }
    }

}
