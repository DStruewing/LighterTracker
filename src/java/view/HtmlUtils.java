package view;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HtmlUtils {   
    public static String buildSelectTag(String tagName, String sql, DbConn dbc, String extraOptionName, String extraOptionId, String selectedOptionId) {
        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        if(selectedOptionId == null) {
            selectedOptionId = "";
        }
        try {
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            sb.append("<select name='");
            sb.append(tagName);
            sb.append("'>\n");
            if(extraOptionName != null) {
                sb.append("<option");
                sb.append(" value='");
                sb.append(extraOptionId);
                sb.append("'");
                if(extraOptionId.equals(selectedOptionId)) {
                    sb.append(" selected='selected'");
                }
                sb.append(">");
                sb.append(extraOptionName);
                sb.append("</option>\n");
            }
            while(results.next()) {
                String strId = FormatUtils.formatInteger(results.getObject(1));
                String strName = FormatUtils.formatString(results.getObject(2));
                sb.append("<option");
                sb.append(" value='");
                sb.append(strId);
                sb.append("'");
                if(strId.equals(selectedOptionId)) {
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
