package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.DbConn;
import dbUtils.FormatUtils;

public class RecordView {

    /* This method returns a HTML table displaying all the records of the web_user table. 
     * cssClassForResultSetTable: the name of a CSS style that will be applied to the HTML table.
     *   (This style should be defined in the JSP page (header or style sheet referenced by the page).
     * dbc: an open database connection.
     */
    public static String listAllRecords(String cssClassForResultSetTable, DbConn dbc) {
        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "select record_date, web_user.user_email, lighter.lighter_name, latitude, longitude, lighter_condition, dead from lighter, web_user, tracking_record where web_user.web_user_id = tracking_record.web_user_id and lighter.lighter_id = tracking_record.lighter_id order by record_date;";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table cellspacing='0' class='");
            sb.append(cssClassForResultSetTable);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:right'>Date</th>");
            sb.append("<th style='text-align:right'>Lighter Name</th>");
            sb.append("<th style='text-align:left'>User Email</th>");
            sb.append("<th style='text-align:left'>Latitude</th>");
            sb.append("<th style='text-align:right'>Longitude</th>");
            sb.append("<th style='text-align:right'>Condition</th>");
            sb.append("<th style='text-align:right'>Dead</th>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append(FormatUtils.formatDateTd(results.getObject("record_date")));
                sb.append(FormatUtils.formatStringTd(results.getObject("lighter_name")));
                sb.append(FormatUtils.formatStringTd(results.getObject("user_email")));
                sb.append(FormatUtils.formatFloatTd(results.getObject("latitude")));
                sb.append(FormatUtils.formatFloatTd(results.getObject("longitude")));
                sb.append(FormatUtils.formatStringTd(results.getObject("lighter_condition")));
                sb.append(FormatUtils.formatBoolTd(results.getObject("dead")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in RecordView.listAllRecords(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }

    public static String listDeleteRecords(String cssClassForResultSetTable,
            String deleteFn, String deleteIcon, DbConn dbc) {

        // Prepare some HTML that will be used repeatedly for the update icon that
        // calls an update javascript function (see below).
        if ((deleteIcon == null) || (deleteIcon.length() == 0)) {
            return "RecordView.listDeleteRecords() error: delete Icon file name (String input parameter) is null or empty.";
        }
        if ((deleteFn == null) || (deleteFn.length() == 0)) {
            return "RecordView.listDeleteRecords() error: delete javascript function name (String input parameter) is null or empty.";
        }

        // First half of the HTML that defines a table cell for the delete icon
        String deleteStart = "<td style='border:none; background-color:transparent; text-align:center;'><a href='" + deleteFn + "(";
        // Second half of the delete HTML
        String deleteEnd = ")'><img src='" + deleteIcon + "'></a></td>";

        // use StringBuilder object instead of plain String because it is more efficient
        // (with all the appending that we are doing here).
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "select tracking_record_id, record_date, web_user.user_email, lighter.lighter_name, "
                    + "latitude, longitude, lighter_condition, dead from lighter, web_user, "
                    + "tracking_record where web_user.web_user_id = tracking_record.web_user_id "
                    + "and lighter.lighter_id = tracking_record.lighter_id order by record_date;";
            stmt = dbc.getConn().prepareStatement(sql);
            rst = stmt.executeQuery();
            sb.append("<table cellspacing='0' class='" + cssClassForResultSetTable + "'>");
            sb.append("<tr>");
            sb.append("<th style='background-color:transparent;'>&nbsp;</th>");// extra column at left for delete icon
            sb.append("<th style='text-align:right'>Date</th>");
            sb.append("<th style='text-align:right'>Lighter Name</th>");
            sb.append("<th style='text-align:left'>User Email</th>");
            sb.append("<th style='text-align:left'>Latitude</th>");
            sb.append("<th style='text-align:right'>Longitude</th>");
            sb.append("<th style='text-align:right'>Condition</th>");
            sb.append("<th style='text-align:right'>Dead</th>");
            while (rst.next()) {
                Object primaryKeyObj = rst.getObject(1);
                Integer primaryKeyInt = (Integer) primaryKeyObj;
                sb.append("<tr>");
                sb.append(deleteStart + primaryKeyInt.toString() + deleteEnd);
                sb.append(FormatUtils.formatDateTd(rst.getObject("record_date")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_name")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("user_email")));
                sb.append(FormatUtils.formatFloatTd(rst.getObject("latitude")));
                sb.append(FormatUtils.formatFloatTd(rst.getObject("longitude")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_condition")));
                sb.append(FormatUtils.formatBoolTd(rst.getObject("dead")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            rst.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in RecordView.listDeleteRecords(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
}
