package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.Date;

public class SearchView {

    public static String search(DbConn dbc, String cssClassForResultSetTable, String webUserId, String lighterId, String startDate, String endDate) {
        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        sb.append("SELECT * FROM web_user, lighter, tracking_record");
        sb.append(" WHERE web_user.web_user_id = tracking_record.web_user_id");
        sb.append(" AND lighter.lighter_id = tracking_record.lighter_id");
        if (webUserId != null && !webUserId.equals("")) {
            sb.append(" AND web_user.web_user_id = ?");
        }
        if (lighterId != null && !lighterId.equals("")) {
            sb.append(" AND lighter.lighter_id = ?");
        }
        if (startDate != null && !startDate.equals("")) {
            sb.append(" AND record_date >= ?");
        }
        if (endDate != null && !endDate.equals("")) {
            sb.append(" AND record_date <= ?");
        }
        sb.append(" ORDER BY record_date ASC;");
        String sql = sb.toString();

        try {
            stmt = dbc.getConn().prepareStatement(sql);
            int questionMarkNum = 1;
            if (webUserId != null && !webUserId.equals("")) {
                stmt.setInt(questionMarkNum, Integer.valueOf(webUserId));
                questionMarkNum++;
            }
            if (lighterId != null && !lighterId.equals("")) {
                stmt.setInt(questionMarkNum, Integer.valueOf(lighterId));
                questionMarkNum++;
            }
            if (startDate != null && !startDate.equals("")) {
                stmt.setDate(questionMarkNum, Date.valueOf(startDate));
                questionMarkNum++;
            }
            if (endDate != null && !endDate.equals("")) {
                stmt.setDate(questionMarkNum, Date.valueOf(endDate));
                questionMarkNum++;
            }
            results = stmt.executeQuery();
            if (!results.first()) {
                return "<br><div>No results found for your search criteria.</div>";
            }
            sb = new StringBuilder("");
            sb.append("<table cellspacing='0' class='");
            sb.append(cssClassForResultSetTable);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:right'>Date</th>");
            sb.append("<th style='text-align:right'>User Email</th>");
            sb.append("<th style='text-align:left'>Lighter Name</th>");
            sb.append("<th style='text-align:left'>Latitude</th>");
            sb.append("<th style='text-align:right'>Longitude</th>");
            sb.append("<th style='text-align:right'>Condition</th>");
            sb.append("<th style='text-align:right'>Dead</th>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append(FormatUtils.formatDateTd(results.getObject("record_date")));
                sb.append(FormatUtils.formatStringTd(results.getObject("user_email")));
                sb.append(FormatUtils.formatStringTd(results.getObject("lighter_name")));
                sb.append(FormatUtils.formatFloatTd(results.getObject("latitude")));
                sb.append(FormatUtils.formatFloatTd(results.getObject("longitude")));
                sb.append(FormatUtils.formatStringTd(results.getObject("lighter_condition")));
                if (results.getObject("dead") != null) {
                    sb.append(FormatUtils.formatStringTd("Yes"));
                } else {
                    sb.append(FormatUtils.formatStringTd(""));
                }
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in SearchView.search(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }

    public static String buildSearchFormTag(DbConn dbc, String cssClassForForm, String webUserIdParam, String lighterIdParam, String startDateParam, String endDateParam) {
        StringBuilder sb = new StringBuilder("");
        sb.append("<form action='search.jsp' method='GET' class='");
        sb.append(cssClassForForm);
        sb.append("'>\n");
        sb.append("User: ");
        sb.append(HtmlUtils.buildSelectTag("webUserId", "Select web_user_id, user_email from web_user order by user_email", dbc, "All Users", "0", webUserIdParam));
        sb.append("<br>Lighter: ");
        sb.append(HtmlUtils.buildSelectTag("lighterId", "Select lighter_id, lighter_name from lighter order by lighter_name", dbc, "All Lighters", "0", lighterIdParam));
        sb.append("<br>Date range: ");
        sb.append("<input type='date' name='startDate'");
        if (startDateParam != null) {
            sb.append(" value='");
            sb.append(startDateParam);
            sb.append("'");
        }
        sb.append("/>");
        sb.append("to ");
        sb.append("<input type='date' name='endDate'");
        if (endDateParam != null) {
            sb.append("value='");
            sb.append(endDateParam);
            sb.append("'");
        }
        sb.append("/>");
        sb.append("<br>");
        sb.append("<a href='search.jsp'><input type='button' value='Reset'/></a>");
        sb.append("<input type='submit' value='Search'/>\n");
        sb.append("</form>");

        return sb.toString();
    }

    public static String buildSelectTag(String tagName, String sql, DbConn dbc, String extraOptionName, String extraOptionId, String selectedOptionId) {
        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        if (selectedOptionId == null) {
            selectedOptionId = "";
        }
        try {
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            sb.append("<select name='");
            sb.append(tagName);
            sb.append("'>\n");
            if (extraOptionName != null) {
                sb.append("<option");
                sb.append(" value='");
                sb.append(extraOptionId);
                sb.append("'");
                if (extraOptionId.equals(selectedOptionId)) {
                    sb.append(" selected='selected'");
                }
                sb.append(">");
                sb.append(extraOptionName);
                sb.append("</option>\n");
            }
            while (results.next()) {
                String strId = FormatUtils.formatInteger(results.getObject(1));
                String strName = FormatUtils.formatString(results.getObject(2));
                sb.append("<option");
                sb.append(" value='");
                sb.append(strId);
                sb.append("'");
                if (strId.equals(selectedOptionId)) {
                    sb.append(" selected='selected'");
                }
                sb.append(">");
                sb.append(strName);
                sb.append("</option>\n");
            }
            sb.append("</select>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in SearchView.buildSelectTag(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
}
