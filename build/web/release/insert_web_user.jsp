<%-- 
    Document   : insert_record
    Created on : Apr 17, 2015, 2:27:13 AM
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page language="java" import="Release.dbUtils.DbConn" %>
<%@page language="java" import="Release.WebUser.*" %>

<%
	
	String jsonResponse = "";
	String jspError = "";

	StringData stringData = new StringData();

	stringData.name = request.getParameter("name");
	if(stringData.name == null) {
		stringData.name = "";
	}
	stringData.email = request.getParameter("email");
	if(stringData.email == null) {
		stringData.email = "";
	}
	stringData.password1 = request.getParameter("password1");
	if(stringData.password1 == null) {
		stringData.password1 = "";
	}
	stringData.password2 = request.getParameter("password2");
	if(stringData.password2 == null) {
		stringData.password2 = "";
	}

	Validate validate = new Validate(stringData);
	
	DbConn dbc = new DbConn();
	String dbError = dbc.getErr();

	if (validate.isValidated()) {
		if (dbError.length() == 0) {
			WebUserMods webUserMods = new WebUserMods(dbc);
			out.print(webUserMods.insert(validate));
		} else {
			out.print("{ dbError: '" + dbError + "' }");
		}
	} else {
		out.print(validate.getAllValidationErrorsJSON());
	}
	
	dbc.close();
%>

