package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.DbConn;
import dbUtils.FormatUtils;
 
public class LighterView {

    /* This method returns a HTML table displaying all the records of the web_user table. 
     * cssClassForResultSetTable: the name of a CSS style that will be applied to the HTML table.
     *   (This style should be defined in the JSP page (header or style sheet referenced by the page).
     * dbc: an open database connection.
     */
    public static String listAllLighters(String cssClassForResultSetTable, DbConn dbc) {
        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "select lighter_id, lighter_name, purchase_date, lighter_color, lighter_code from lighter order by lighter_id;";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table cellspacing='0' class='");
            sb.append(cssClassForResultSetTable);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:right'>Lighter ID</th>");
            sb.append("<th style='text-align:left'>Lighter Name</th>");
            sb.append("<th style='text-align:left'>Purchase Date</th>");
            sb.append("<th style='text-align:right'>Lighter Color</th>");
			sb.append("<th style='text-align:right'>Tracking Code</th>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append(FormatUtils.formatIntegerTd(results.getObject("lighter_id")));
                sb.append(FormatUtils.formatStringTd(results.getObject("lighter_name")));
                sb.append(FormatUtils.formatDateTd(results.getObject("purchase_date")));
                sb.append(FormatUtils.formatStringTd(results.getObject("lighter_color")));
				sb.append(FormatUtils.formatStringTd(results.getObject("lighter_code")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in LighterView.listAllLighters(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
    
    public static String listUpdateLighters(String cssClassForResultSetTable,
            String updateFn, String updateIcon, DbConn dbc) {

        // Prepare some HTML that will be used repeatedly for the update icon that
        // calls an update javascript function (see below).
        if ((updateIcon == null) || (updateIcon.length() == 0)) {
            return "LighterView.listUpdateLighters() error: update Icon file name (String input parameter) is null or empty.";
        }
        if ((updateFn == null) || (updateFn.length() == 0)) {
            return "LighterView.listUpdateLighters() error: update javascript function name (String input parameter) is null or empty.";
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
            String sql = "select lighter_id, lighter_name, purchase_date, "
                    + "lighter_color, lighter_code from lighter order by lighter_name;";
            stmt = dbc.getConn().prepareStatement(sql);
            rst = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table cellspacing='0' class='" + cssClassForResultSetTable + "'>");
            
            sb.append("<tr>");
            sb.append("<th style='background-color:transparent;'>&nbsp;</th>");// extra column at left for update icon
            sb.append("<th style='text-align:right'>ID</th>");
            sb.append("<th style='text-align:left'>Name</th>");
            sb.append("<th style='text-align:left'>Purchase Date</th>");
            sb.append("<th style='text-align:right'>Color</th>");
			sb.append("<th style='text-align:right'>Tracking Code</th>");
            while (rst.next()) {
                Object primaryKeyObj = rst.getObject(1);
                Integer primaryKeyInt = (Integer) primaryKeyObj;
                sb.append("<tr>");
                sb.append(updateStart + primaryKeyInt.toString() + updateEnd);
                sb.append(FormatUtils.formatIntegerTd(rst.getObject("lighter_id")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_name")));
                sb.append(FormatUtils.formatDateTd(rst.getObject("purchase_date")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_color")));
				sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_code")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");        

            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in LighterView.listUpdateLighters(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
    
    public static String listUpdateDeleteLighters(String cssClassForResultSetTable,
            String updateFn, String updateIcon, String deleteFn, String deleteIcon, DbConn dbc) {

        // Prepare some HTML that will be used repeatedly for the update icon that
        // calls an update javascript function (see below).
        if ((updateIcon == null) || (updateIcon.length() == 0)) {
            return "LighterView.listUpdateDeleteLighters() error: update Icon file name (String input parameter) is null or empty.";
        }
        if ((updateFn == null) || (updateFn.length() == 0)) {
            return "LighterView.listUpdateDeleteLighters() error: update javascript function name (String input parameter) is null or empty.";
        }
        if ((deleteIcon == null) || (deleteIcon.length() == 0)) {
            return "LighterView.listUpdateDeleteLighters() error: delete Icon file name (String input parameter) is null or empty.";
        }
        if ((deleteFn == null) || (deleteFn.length() == 0)) {
            return "LighterView.listUpdateDeleteLighters() error: delete javascript function name (String input parameter) is null or empty.";
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
            String sql = "select lighter_id, lighter_name, purchase_date, "
                    + "lighter_color, lighter_code from lighter order by lighter_name;";
            stmt = dbc.getConn().prepareStatement(sql);
            rst = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table cellspacing='0' class='" + cssClassForResultSetTable + "'>");
            
            sb.append("<tr>");
            sb.append("<th style='background-color:transparent;'>&nbsp;</th>");// extra column at left for delete icon
            sb.append("<th style='background-color:transparent;'>&nbsp;</th>");// extra column at left for update icon
            sb.append("<th style='text-align:right'>ID</th>");
            sb.append("<th style='text-align:left'>Name</th>");
            sb.append("<th style='text-align:left'>Purchase Date</th>");
            sb.append("<th style='text-align:right'>Color</th>");
			sb.append("<th style='text-align:right'>Tracking Code</th>");
            while (rst.next()) {
                Object primaryKeyObj = rst.getObject(1);
                Integer primaryKeyInt = (Integer) primaryKeyObj;
                sb.append("<tr>");
                sb.append(deleteStart + primaryKeyInt.toString() + deleteEnd);
                sb.append(updateStart + primaryKeyInt.toString() + updateEnd);
                sb.append(FormatUtils.formatIntegerTd(rst.getObject("lighter_id")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_name")));
                sb.append(FormatUtils.formatDateTd(rst.getObject("purchase_date")));
                sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_color")));
				sb.append(FormatUtils.formatStringTd(rst.getObject("lighter_code")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");        

            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in LighterView.listUpdateDeleteLighters(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
}