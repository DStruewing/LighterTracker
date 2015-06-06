<%@page contentType="text/html" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.DbConn" %>
<%@page language="java" import="model.Lighter.StringData" %>
<%@page language="java" import="model.Lighter.LighterMods" %>

<%
    String lighterId = request.getParameter("primaryKey");
    String lighterCode = request.getParameter("lighterCode");
    DbConn dbc = new DbConn();  // get an OPEN db connection. 
    StringData lighterStringData = new StringData();// all properties empty
    String dbError = dbc.getErr();
    LighterMods sqlMods = new LighterMods(dbc);

    if (dbError.length() == 0) { // DB connection was successful.
        if (lighterId != null && lighterId.length() != 0) { // Searching for lighter with id.
            lighterStringData = sqlMods.find(lighterId);
            if (lighterStringData == null) {
                lighterStringData.setRecordStatus("get_lighter.JSON.jsp. Problem finding record with id " + lighterId + ": " + sqlMods.getErrorMsg());
            } else {
                lighterStringData.setRecordStatus("If found, fields have values. If not found, all fields are empty string.");
            }
            out.print(lighterStringData.toJSON());
        } else if (lighterCode != null && lighterCode.length() != 0) { // Searching for lighter with code.
            lighterStringData = sqlMods.findFromCode(lighterCode);
            if(lighterStringData == null) {
                lighterStringData.setRecordStatus("get_lighter_JSON.jsp. Problem finding record with lighter_code " + lighterCode + ": " + sqlMods.getErrorMsg());
            } else {
                lighterStringData.setRecordStatus("If found, fields have values. If not found, all fields are empty string.");
            }
            out.print(lighterStringData.toJSON());
        }
    } else { // DB error.
        lighterStringData.setRecordStatus("Database connection error in get_lighter_JSON.jsp: " + dbError);
    }
%>
