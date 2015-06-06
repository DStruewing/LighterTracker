<%-- 
    Document   : logon_JSON
    Created on : Apr 15, 2015, 8:35:40 PM
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page language="java" import="Release.dbUtils.DbConn" %>
<%@page language="java" import="Release.WebUser.StringData" %>
<%@page language="java" import="Release.WebUser.WebUserMods" %>

<%

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    StringData webUser = new StringData();
    DbConn dbc = new DbConn();  // get an OPEN db connection. 
    String dbError = dbc.getErr();

    if(dbError.length() != 0) {
        webUser.errorMsg = "Database connection error in attempt_login.jsp: " + dbError;
    } else {
        WebUserMods webUserMods = new WebUserMods(dbc);
        webUser = webUserMods.login(email, password);
        if(webUser == null) {
            webUser = new StringData();
            webUser.errorMsg = "Not found";
        }
    }

    out.print(webUser.toJSON());
    dbc.close();

%>