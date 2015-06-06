<%@page import="dbUtils.FormatUtils"%>
<%@page import="dbUtils.DbConn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page language="java" import="model.Record.*" %>

<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />
        <link href="css/index.css" rel="stylesheet" type="text/css">
        <link href="css/insertRecord.css" rel="stylesheet" type="text/css">

    </head>

    <body>

        <%
            Validate recordValidate = new Validate();
            String errorMsg = "";
            
            String lighterId = FormatUtils.nullToEmptyString(request.getParameter("lighterId"));
            String webUserId = FormatUtils.nullToEmptyString(request.getParameter("webUserId"));
            String date = FormatUtils.nullToEmptyString(request.getParameter("date"));
            String latitude = FormatUtils.nullToEmptyString(request.getParameter("latitude"));
            String longitude = FormatUtils.nullToEmptyString(request.getParameter("longitude"));
            String condition = FormatUtils.nullToEmptyString(request.getParameter("condition"));
            
            if (request.getParameter("firstRendering") == null) {
                StringData recordStringData = new StringData();
                
                recordStringData.lighterId = lighterId;
                recordStringData.webUserId = webUserId;
                recordStringData.date = FormatUtils.YMDtoMDY(date);
                recordStringData.latitude = latitude;
                recordStringData.longitude = longitude;
                recordStringData.condition = condition;
                
                recordValidate = new Validate(recordStringData);

                if (recordValidate.isValidated()) {
                    DbConn dbc = new DbConn();
                    errorMsg = dbc.getErr();
                    if (errorMsg.length() == 0) { // means no error getting db connection
                        // Instantiate Web User Mod object and pass validated String Data to its insert method
                        RecordMods recordMods = new RecordMods(dbc);
                        errorMsg = recordMods.insert(recordValidate);
                        if (errorMsg.length() == 0) { // empty string means record was sucessfully inserted
                            recordStringData = new StringData(); // clear out javabean
                            response.sendRedirect("assoc.jsp?message=" + "Record inserted.");
                        }
                    }
                    dbc.close();
                }
            }
        %>

        <jsp:include page="global/pre-body.jsp?logonPage=yes" />

        <div class="content">

            <p>Navigate to your current location:</p>
            <div id="map-canvas"></div>
            
            <br>
            <p>Describe the lighter's condition (max 200 characters):
                <br><span style="color: red"><%=recordValidate.getConditionMsg()%></span></p>
            
            <form name="recordForm" action="insertRecord.jsp" method="get" class="bigForm">
                
                <input type="hidden" name="lighterId" value="<%=lighterId%>" />
                <input type="hidden" name="webUserId" value="<%=webUserId%>" />
                <input type="hidden" name="date" value="<%=date%>" />
                <input type="hidden" name="latitude" value="<%=latitude%>" />
                <input type="hidden" name="longitude" value="<%=longitude%>" />
                
                <textarea name="condition" rows="7"><%=condition%></textarea>
                <br><br>
                
                <input type="submit" value="Submit" />
            </form>
                        
            <p id="lat"></p>
            <p id="lng"></p>

        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

        <script type="text/javascript"
                src="https://maps.googleapis.com/maps/api/js?sensor=false">
        </script>
        <script type="text/javascript" src="js/insertRecord.js"></script>

    </body>

</html>
