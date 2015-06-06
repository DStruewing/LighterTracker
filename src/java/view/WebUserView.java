package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.DbConn;
import dbUtils.FormatUtils;

public class WebUserView {

    /* This method returns a HTML table displaying all the records of the web_user table. 
     * cssClassForResultSetTable: the name of a CSS style that will be applied to the HTML table.
     *   (This style should be defined in the JSP page (header or style sheet referenced by the page).
     * dbc: an open database connection.
     */
    public static String listAllUsers(String cssClassForResultSetTable, DbConn dbc) {
        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "select web_user_id, user_email, user_password, membership_fee, user_role.user_role, birthday from web_user, user_role where web_user.user_role_id = user_role.user_role_id order by web_user_id;";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table cellspacing='0' class='");
            sb.append(cssClassForResultSetTable);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:right'>User ID</th>");
            sb.append("<th style='text-align:left'>User Email</th>");
            sb.append("<th style='text-align:left'>User Password</th>");
            sb.append("<th style='text-align:right'>Membership Fee</th>");
            sb.append("<th style='text-align:right'>User Role</th>");
            sb.append("<th style='text-align:center'>Birthday</th></tr>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append(FormatUtils.formatIntegerTd(results.getObject("web_user_id")));
                sb.append(FormatUtils.formatStringTd(results.getObject("user_email")));
                sb.append(FormatUtils.formatStringTd(results.getObject("user_password")));
                sb.append(FormatUtils.formatDollarTd(results.getObject("membership_fee")));
                sb.append(FormatUtils.formatStringTd(results.getObject("user_role")));
                sb.append(FormatUtils.formatDateTd(results.getObject("birthday")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in WebUserSql.listAllUsers(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }

    public static String listUpdateUsers(String cssClassForResultSetTable,
            String updateFn, String updateIcon, DbConn dbc) {

        // Prepare some HTML that will be used repeatedly for the update icon that
        // calls an update javascript function (see below).
        if ((updateIcon == null) || (updateIcon.length() == 0)) {
            return "WebUserView.listUpdateUsers() error: update Icon file name (String input parameter) is null or empty.";
        }
        if ((updateFn == null) || (updateFn.length() == 0)) {
            return "WebUserView.listUpdateUsers() error: update javascript function name (String input parameter) is null or empty.";
        }

        // This is the first half of the HTML that defines a table cell that will hold the update
        // icon which will be linked to a javascript function for updating the current row.
        String updateStart = "<td style='border:none; background-color:transparent; text-align:center;'><a href='" + updateFn + "(";
        // This is the HTML for the second half of that same HTML
        // In between the first half and the second half will be the actual PK of the current row
        // (input parameter to the javascript function).
        String updateEnd = ")'><img src='" + updateIcon + "'></a></td>"; // after PK value/input parameter to js fn.

        // use StringBuilder object instead of plain String because it is more efficient
        // (with all the appending that we are doing here).
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "select web_user_id, user_email, user_password, "
                    + "membership_fee, user_role.user_role, birthday from "
                    + "web_user, user_role where web_user.user_role_id = "
                    + "user_role.user_role_id order by user_email;";
            stmt = dbc.getConn().prepareStatement(sql);
            rst = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table cellspacing='0' class='" + cssClassForResultSetTable + "'>");

            sb.append("<tr>");
            sb.append("<th style='background-color:transparent;'>&nbsp;</th>");// extra column at left for update icon
            sb.append("<th style='text-align:right'>ID</th>");
            sb.append("<th style='text-align:left'>User Email</th>");
            sb.append("<th style='text-align:left'>User Password</th>");
            sb.append("<th style='text-align:right'>Membership Fee</th>");
            sb.append("<th style='text-align:right'>User Role</th>");
            sb.append("<th style='text-align:center'>Birthday</th></tr>");
            while (rst.next()) {
                Object primaryKeyObj = rst.getObject(1);
                Integer primaryKeyInt = (Integer) primaryKeyObj;
                sb.append("<tr>");
                sb.append(updateStart + primaryKeyInt.toString() + updateEnd);
                sb.append(FormatUtils.formatIntegerTd(rst.getObject("web_user_id")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("user_email")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("user_password")));
                sb.append(FormatUtils.formatDollarTd(rst.getObject("membership_fee")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("user_role")));
                sb.append(FormatUtils.formatDateTd(rst.getObject("birthday")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");

            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in WebUserView.listAllUsers(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }

    public static String listUpdateDeleteUsers(String cssClassForResultSetTable,
            String updateFn, String updateIcon, String deleteFn, String deleteIcon, DbConn dbc) {

        // Prepare some HTML that will be used repeatedly for the update icon that
        // calls an update javascript function (see below).
        if ((updateIcon == null) || (updateIcon.length() == 0)) {
            return "WebUserView.listUpdateDeleteUsers() error: update Icon file name (String input parameter) is null or empty.";
        }
        if ((updateFn == null) || (updateFn.length() == 0)) {
            return "WebUserView.listUpdateDeleteUsers() error: update javascript function name (String input parameter) is null or empty.";
        }
        if ((deleteIcon == null) || (deleteIcon.length() == 0)) {
            return "WebUserView.listUpdateDeleteUsers() error: delete Icon file name (String input parameter) is null or empty.";
        }
        if ((deleteFn == null) || (deleteFn.length() == 0)) {
            return "WebUserView.listUpdateDeleteUsers() error: delete javascript function name (String input parameter) is null or empty.";
        }

        // First half of the HTML that defines a table cell for the delete icon
        String deleteStart = "<td style='border:none; background-color:transparent; text-align:center;'><a href='" + deleteFn + "(";
        // Second half of the delete HTML
        String deleteEnd = ")'><img src='" + deleteIcon + "'></a></td>";
        
        // First half of the HTML that defines a table cell for the update icon
        String updateStart = "<td style='border:none; background-color:transparent; text-align:center;'><a href='" + updateFn + "(";
        // Second half of the update HTML
        String updateEnd = ")'><img src='" + updateIcon + "'></a></td>";

        // use StringBuilder object instead of plain String because it is more efficient
        // (with all the appending that we are doing here).
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "select web_user_id, user_email, user_password, "
                    + "membership_fee, user_role.user_role, birthday from "
                    + "web_user, user_role where web_user.user_role_id = "
                    + "user_role.user_role_id order by user_email;";
            stmt = dbc.getConn().prepareStatement(sql);
            rst = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table cellspacing='0' class='" + cssClassForResultSetTable + "'>");
            sb.append("<tr>");
            sb.append("<th style='background-color:transparent;'>&nbsp;</th>");// extra column at left for delete icon
            sb.append("<th style='background-color:transparent;'>&nbsp;</th>");// extra column at left for update icon
            sb.append("<th style='text-align:right'>ID</th>");
            sb.append("<th style='text-align:left'>User Email</th>");
            sb.append("<th style='text-align:left'>User Password</th>");
            sb.append("<th style='text-align:right'>Membership Fee</th>");
            sb.append("<th style='text-align:right'>User Role</th>");
            sb.append("<th style='text-align:center'>Birthday</th></tr>");
            while (rst.next()) {
                Object primaryKeyObj = rst.getObject(1);
                Integer primaryKeyInt = (Integer) primaryKeyObj;
                sb.append("<tr>");
                sb.append(deleteStart + primaryKeyInt.toString() + deleteEnd);
                sb.append(updateStart + primaryKeyInt.toString() + updateEnd);
                sb.append(FormatUtils.formatIntegerTd(rst.getObject("web_user_id")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("user_email")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("user_password")));
                sb.append(FormatUtils.formatDollarTd(rst.getObject("membership_fee")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("user_role")));
                sb.append(FormatUtils.formatDateTd(rst.getObject("birthday")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");

            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in WebUserView.listUpdateDeleteUsers(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
}
