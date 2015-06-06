<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="view.WebUserView" %>
<%@page language="java" import="dbUtils.DbConn" %>

<%@page language="java" import="model.WebUser.StringData" %>
<%@page language="java" import="model.WebUser.Validate" %>
<%@page language="java" import="model.WebUser.WebUserMods" %>

<%@page import="view.HtmlUtils"%>

<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />       

        <link id="theme" href="css/resultSetFormat.css" rel="stylesheet">

    </head>

    <body onload="setInputArea()">

        <%
            // All properties of a new webUserStringData object are "" (empty string).
            StringData webUserStringData = new StringData();

            // Will contain an HTML select tag for selecting the user role.
            String userRoleSelectTag = "";

            // All error messages in the new Validate object are "" (empty string).
            // This is good for first display.
            Validate webUserValidate = new Validate();

            String strDeleteWebUserId = ""; // will be null or "" unless user is trying to delete.
            String strUpdateWebUserId = ""; // will be null or "" unless user is trying to update.

            // This will hold a confirmation or error message relating to user's delete attempt.
            String deleteMsg = "";
            // This will hold a confirmation or error message relating to user's update attempt.
            String updateMsg = "";

            DbConn dbc = new DbConn();
            String dbDataOrError = dbc.getErr();

            if (dbDataOrError.length() == 0) { // Got open connection.

                // Generate the user role select tag.
                userRoleSelectTag = HtmlUtils.buildSelectTag("userRoleId", "SELECT * from user_role", dbc, null, null, request.getParameter("userRoleId"));

                // Web user CRUD object.
                WebUserMods webUserMods = new WebUserMods(dbc);

                strDeleteWebUserId = request.getParameter("deleteWebUserId");
                strUpdateWebUserId = request.getParameter("webUserId");

                if (strDeleteWebUserId != null && strDeleteWebUserId.length() > 0) { // User wants to to delete a record.
                    String delMsg = webUserMods.delete(strDeleteWebUserId);
                    if (delMsg.length() == 0) { // Delete was successful.
                        deleteMsg = "Web User " + strDeleteWebUserId + " has been deleted";
                    } else { // Delete was not successful.
                        deleteMsg = "Unable to delete Web User " + strDeleteWebUserId + ". " + webUserMods.getErrorMsg();
                    }
                    
                } else if (strUpdateWebUserId != null && strUpdateWebUserId.length() > 0) { // User wants to update a record.
                    // Populate webUserStringData with postback values.
                    webUserStringData.webUserId = request.getParameter("webUserId");
                    webUserStringData.userEmail = request.getParameter("userEmail");
                    webUserStringData.userPw = request.getParameter("userPw");
                    webUserStringData.userPw2 = request.getParameter("userPw2");
                    webUserStringData.membershipFee = request.getParameter("membershipFee");
                    webUserStringData.birthday = request.getParameter("birthday");
                    webUserStringData.userRoleId = request.getParameter("userRoleId");

                    // Validate webUserStringData and generate validation error messages, if there are any.
                    webUserValidate = new Validate(webUserStringData);

                    // Try to update the Web User record. Returns error message or empty string.
                    updateMsg = webUserMods.update(webUserValidate); // Empty string means went in OK.
                    if (updateMsg.length() == 0) { // Update was successful.
                        updateMsg = "Record " + webUserStringData.userEmail + " updated. ";
                    }
                }

                // After delete/update is complete, get HTML table of all web users for display.
                dbDataOrError = WebUserView.listUpdateDeleteUsers("resultSetFormat",
                        "javascript:requestWebUser", "icons/update.png", 
                        "javascript:deleteRow", "icons/delete.png", dbc);
            }
            
            // PREVENT DB connection leaks: shouldnt hurt to close it even if it was never opened.
            dbc.close();
        %>

        <jsp:include page="global/pre-body.jsp" />

        <script>
            document.getElementById("users").className = "tab selected";
        </script> 

        <div class="content">
            <h2><a href="insertUser.jsp">Create Account</a></h2>

            <div id="inputArea" style="display:none;"> <!-- user input area for updating field values -->
                <form name="updateForm" action="users.jsp" method="get">           
                    <input type="hidden"  size="6" name="webUserId" value="<%= webUserStringData.webUserId%>" /> 
                    <table class="inputTable">
                        <tr>
                            <td class="prompt">User Email:</td>
                            <td><input type="text" name="userEmail" size="30" value="<%= webUserStringData.userEmail%>" /></td>
                            <td class="error"><%=webUserValidate.getUserEmailMsg()%></td>
                        </tr>
                        <tr>
                            <td class="prompt">Password:</td>
                            <td><input type="password" name="userPw" size="30" value="<%= webUserStringData.userPw%>" /></td>
                            <td class="error"><%=webUserValidate.getUserPwMsg()%></td>
                        </tr>
                        <tr>
                            <td class="prompt">Retype Password:</td>
                            <td><input type="password" name="userPw2" size="30" value="<%= webUserStringData.userPw%>" /></td>
                            <td class="error"><%=webUserValidate.getUserPw2Msg()%></td>
                        </tr>
                        <tr>
                            <td class="prompt">Membership Fee:</td>
                            <td><input type="text" name="membershipFee" value="<%= webUserStringData.membershipFee%>" /></td>
                            <td class="error"><%=webUserValidate.getMembershipFeeMsg()%></td>   
                        <tr>
                            <td class="prompt">User Role:</td>
                            <td><%=userRoleSelectTag%></td>
                            <td class="error"><%=webUserValidate.getUserRoleMsg()%></td>
                        </tr>
                        <tr>
                            <td class="prompt">Birthday:</td>
                            <td><input type="text" name="birthday" value="<%= webUserStringData.birthday%>" /></td>
                            <td class="error"><%=webUserValidate.getBirthdayMsg()%></td>                    
                        </tr>
                        <tr>
                            <td class="prompt"><input type="submit" value="Update" /></td>
                            <td><input type="button" value="Cancel / Done" onclick="cancelDone()"/></td>
                            <td id="updateMsg"><%=updateMsg%></td>
                        </tr>
                    </table>          
                </form>
                <br>
            </div>  <!-- end of user input area -->

            <span id="deleteMsg"><%=deleteMsg%></span>
            <form name="deleteForm" action="users.jsp" method="post">
                <input type="hidden" name="deleteWebUserId">
            </form>

            <%=dbDataOrError%>

        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

        <script type="text/javascript" src="js/users.js"></script>

    </body>

</html>
