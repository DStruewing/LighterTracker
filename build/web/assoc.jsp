<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="view.RecordView" %>
<%@page language="java" import="dbUtils.DbConn" %>

<%@page language="java" import="model.Record.*" %>

<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />
        <link id="theme" href="css/resultSetFormat.css" rel="stylesheet">

    </head>

    <body>

        <%
            String strDeleteTrackingRecordId = ""; // will be null or "" unless user is trying to delete 

            // This will hold a confirmation or error message relating to user's delete attempt
            String message = request.getParameter("message");
            if(message == null || message.length() == 0) {
                message = "";
            }
            
            DbConn dbc = new DbConn();
            String dbDataOrError = dbc.getErr();

            if (dbDataOrError.length() == 0) { // got open connection

                // Record CRUD object.
                RecordMods trackingRecordMods = new RecordMods(dbc);

                strDeleteTrackingRecordId = request.getParameter("deleteTrackingRecordId");

                if (strDeleteTrackingRecordId != null && strDeleteTrackingRecordId.length() > 0) { // User wants to to delete a record.
                    String delMsg = trackingRecordMods.delete(strDeleteTrackingRecordId);
                    if (delMsg.length() == 0) { // Delete was successful.
                        message = "Tracking record " + strDeleteTrackingRecordId + " has been deleted";
                    } else { // Delete was not successful.
                        message = "Unable to delete tracking record " + strDeleteTrackingRecordId + ". " + trackingRecordMods.getErrorMsg();
                    }
                }

                // After delete is complete, get HTML table of all lighters.
                dbDataOrError = RecordView.listDeleteRecords("resultSetFormat",
                        "javascript:deleteRow", "icons/delete.png", dbc);
            }

            // PREVENT DB connection leaks: shouldnt hurt to close it even if it was never opened.
            dbc.close();
        %>
        <jsp:include page="global/pre-body.jsp" />

        <script>
            document.getElementById("assoc").className = "tab selected";
        </script> 

        <div class="content">

            <br>
            <span id="deleteMsg"><%=message%></span>
            <form name="deleteForm" action="assoc.jsp" method="post">
                <input type="hidden" name="deleteTrackingRecordId">
            </form>

            <%=dbDataOrError%>

        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

        <script type="text/javascript" src="js/assoc.js"></script>

    </body>

</html>
