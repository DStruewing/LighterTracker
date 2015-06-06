<%-- 
    Document   : insert_record
    Created on : Apr 17, 2015, 2:27:13 AM
    Author     : david
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page language="java" import="Release.dbUtils.DbConn" %>
<%@page language="java" import="Release.Record.*" %>

<%

	String errorMsg = "";

	StringData stringData = new StringData();

	stringData.lighterId = request.getParameter("lighterId");
	stringData.webUserId = request.getParameter("webUserId");
	stringData.date = request.getParameter("date");
	stringData.latitude = request.getParameter("latitude");
	stringData.longitude = request.getParameter("longitude");

	Validate validate = new Validate(stringData);

	DbConn dbc = new DbConn();
	String dbError = dbc.getErr();

	if (validate.isValidated()) {
		if (dbError.length() == 0) {
			RecordMods recordMods = new RecordMods(dbc);
			recordMods.insert(validate);
			if (recordMods.errorMsg.length() != 0) {
				errorMsg = recordMods.errorMsg;
			}
		} else {
			errorMsg = dbError;
		}
	} else {
		errorMsg = validate.getAllValidationErrors();
	}
	
	dbc.close();

	out.print("({errorMsg: '" + errorMsg + "'})");

%>

