<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="view.LighterView" %>
<%@page language="java" import="dbUtils.DbConn" %>

<%@page language="java" import="model.Lighter.StringData" %>
<%@page language="java" import="model.Lighter.Validate" %>
<%@page language="java" import="model.Lighter.LighterMods" %>

<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />

        <link id="theme" href="css/resultSetFormat.css" rel="stylesheet">

    </head>

    <body onload="setInputArea()">

        <%
            // All properties of a new lighterStringData object are "" (empty string).
            StringData lighterStringData = new StringData();

            // All error messages in the new Validate object are "" (empty string)  
            // This is good for first display.
            Validate lighterValidate = new Validate();

            String strDeleteLighterId = ""; // will be null or "" unless user is trying to delete 
            String strUpdateLighterId = ""; // will be null or "" unless user is trying to update 

            // This will hold a confirmation or error message relating to user's delete attempt
            String deleteMsg = "";
            // This will hold a confirmation or error message relating to user's update attempt
            String updateMsg = "";

            DbConn dbc = new DbConn();
            String dbDataOrError = dbc.getErr();

            if (dbDataOrError.length() == 0) { // Got open connection.

                // Lighter CRUD object.
                LighterMods lighterMods = new LighterMods(dbc);

                strDeleteLighterId = request.getParameter("deleteLighterId");
                strUpdateLighterId = request.getParameter("lighterId");

                if (strDeleteLighterId != null && strDeleteLighterId.length() > 0) { // User wants to to delete a record.
                    String delMsg = lighterMods.delete(strDeleteLighterId);
                    if (delMsg.length() == 0) { // Delete was successful.
                        deleteMsg = "Lighter " + strDeleteLighterId + " has been deleted";
                    } else { // Delete was not successful.
                        deleteMsg = "Unable to delete Lighter " + strDeleteLighterId + ". " + lighterMods.getErrorMsg();
                    }
                    
                } else if (strUpdateLighterId != null && strUpdateLighterId.length() > 0) { // User wants to update a record.
                    // Populate lighterStringData with postback values.
                    lighterStringData.lighterId = request.getParameter("lighterId");
                    lighterStringData.lighterName = request.getParameter("lighterName");
                    lighterStringData.lighterColor = request.getParameter("lighterColor");
                    lighterStringData.purchaseDate = request.getParameter("purchaseDate");
					lighterStringData.trackingCode = request.getParameter("trackingCode");

                    // Validate webUserStringData and generate validation error messages, if there are any.
                    lighterValidate = new Validate(lighterStringData);

                    // Try to update the Lighter record. Returns error message or empty string.
                    updateMsg = lighterMods.update(lighterValidate); // Empty string means went in OK.
                    if (updateMsg.length() == 0) { // Update was successful.
                        updateMsg = "Record " + lighterStringData.lighterName + " updated. ";
                    }
                }

                // After delete/update is complete, get HTML table of all lighters.
                dbDataOrError = LighterView.listUpdateDeleteLighters("resultSetFormat",
                        "javascript:requestLighter", "icons/update.png", 
                        "javascript:deleteRow", "icons/delete.png", dbc);
            }

            // PREVENT DB connection leaks: shouldnt hurt to close it even if it was never opened.
            dbc.close();
        %>

        <jsp:include page="global/pre-body.jsp" />

        <script>
            document.getElementById("other").className = "tab selected";
        </script> 

        <div class="content">
            <h2><a href="insertLighter.jsp">Register a new lighter</a></h2>

            <div id="inputArea" style="display:none;"> <!-- user input area for updating field values -->
                <form name="updateForm" action="other.jsp" method="get">           
                    <input type="hidden"  size="6" name="lighterId" value="<%= lighterStringData.lighterId%>" /> 
                    <table class="inputTable">
                        <tr>
                            <td class="prompt">Lighter Name:</td>
                            <td><input type="text" name="lighterName" size="30" value="<%= lighterStringData.lighterName%>" /></td>
                            <td class="error"><%=lighterValidate.getLighterNameMsg()%></td>
                        </tr>
                        <tr>
                            <td class="prompt">Purchase Date:</td>
                            <td><input type="text" name="purchaseDate" size="30" value="<%= lighterStringData.purchaseDate%>" /></td>
                            <td class="error"><%=lighterValidate.getPurchaseDateMsg()%></td>
                        </tr>
                        <tr>
                            <td class="prompt">Lighter Color:</td>
                            <td><input type="text" name="lighterColor" size="30" value="<%= lighterStringData.lighterColor%>" /></td>
                            <td class="error"><%=lighterValidate.getLighterColorMsg()%></td>
                        </tr> 
						<tr>
                            <td class="prompt">Tracking Code:</td>
                            <td><input type="text" name="trackingCode" size="30" value="<%= lighterStringData.trackingCode%>" /></td>
                            <td class="error"><%=lighterValidate.getTrackingCodeMsg()%></td>
                        </tr>
                        <tr>
                            <td class="prompt"><input type="submit" value="Update" /></td>
                            <td><input type="button" value="Cancel / Done" onclick="cancelDone()"/></td>
                            <td id="message"><%=updateMsg%></td>
                        </tr>
                    </table>          
                </form>
            </div>  <!-- end of user input area -->

            <span id="deleteMsg"><%=deleteMsg%></span>
            <form name="deleteForm" action="other.jsp" method="post">
                <input type="hidden" name="deleteLighterId">
            </form>

            <%=dbDataOrError%>

        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

        <script type="text/javascript" src="js/other.js"></script>

    </body>

</html>
