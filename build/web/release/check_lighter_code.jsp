<%-- 
    Document   : get_lighter_JSON
    Created on : Apr 15, 2015, 10:07:48 PM
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page language="java" import="Release.dbUtils.DbConn" %>
<%@page language="java" import="Release.Lighter.StringData" %>
<%@page language="java" import="Release.Lighter.LighterMods" %>


<%
    
    String lighterCode = request.getParameter("lighterCode");
    
    StringData lighter = new StringData();
    DbConn dbc = new DbConn();  // get an OPEN db connection. 
    String dbError = dbc.getErr();
    
    if(dbError.length() != 0) {
        lighter.errorMsg = "Database connection error in check_lighter_code.jsp: " + dbError;
    } else {
        LighterMods lighterMods = new LighterMods(dbc);
        lighter = lighterMods.find(lighterCode);
        if(lighter == null) {
            lighter = new StringData();
            lighter.errorMsg = "Not found";
        }
    }
    
    out.print(lighter.toJSON());
    dbc.close();
    
%>