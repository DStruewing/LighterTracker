<%@page import="view.HtmlUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="model.WebUser.*" %>
<%@page language="java" import="dbUtils.*" %>

<%

    String userRoleSelectTag = "";
    
    // Constructor sets all fields of WebUser.StringData to "" (empty string) - good for 1st rendering
    StringData wuStringData = new StringData();

    // Default constructor sets all error messages to "" - good for 1st rendering
    Validate wuValidate = new Validate();

    String msg = "";
    DbConn dbc = new DbConn();
    String selectedUserRoleId = "0";
    
    userRoleSelectTag = HtmlUtils.buildSelectTag("userRole", "SELECT * from user_role", dbc, "Select user role", "0", selectedUserRoleId);

    if (request.getParameter("userEmail") != null) { // postback 

        selectedUserRoleId = request.getParameter("userRole");

        // fill WebUserData object with form data (form data is always String)
        wuStringData.userEmail = request.getParameter("userEmail");
        wuStringData.userPw = request.getParameter("userPw");
        wuStringData.userPw2 = request.getParameter("userPw2");
        wuStringData.membershipFee = request.getParameter("membershipFee");
        wuStringData.birthday = request.getParameter("birthday");
        wuStringData.userRoleId = request.getParameter("userRole");

        wuValidate = new Validate(wuStringData); // validate user input, set error messages.
        if (wuValidate.isValidated()) { // data is good, proceed to try to insert

            msg = dbc.getErr();
            if (msg.length() == 0) { // means no error getting db connection

                // Instantiate Web User Mod object and pass validated String Data to its insert method
                WebUserMods webUserMods = new WebUserMods(dbc);
                msg = webUserMods.insert(wuValidate);

                if (msg.length() == 0) { // empty string means record was sucessfully inserted
                    msg = "Record inserted. ";
                }
            }
        }
    } // postback 

%>

<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />

    </head>

    <body>

        <jsp:include page="global/pre-body.jsp" />

        <script>
            document.getElementById("users").className = "tab selected";
        </script> 

        <div class="content">

            <h1>Create an account</h1>
            <form name="myForm" action="insertUser.jsp" method="GET">
                <table style="text-align:left; padding:5px;">
                    <tr>
                        <td>User Email</td>
                        <td><input type="text" name="userEmail" value="<%= wuStringData.userEmail%>" /></td>
                        <td class="error"><%=wuValidate.getUserEmailMsg()%></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="userPw" value="<%= wuStringData.userPw%>" /></td>
                        <td class="error"><%=wuValidate.getUserPwMsg()%></td>
                    </tr>
                    <tr>
                        <td>Re-type Password</td>
                        <td><input type="password" name="userPw2" value="<%= wuStringData.userPw2%>" /></td>
                        <td class="error"><%=wuValidate.getUserPw2Msg()%></td>
                    </tr>
                    <tr>
                        <td>Membership Fee</td>
                        <td><input type="text" name="membershipFee" value="<%= wuStringData.membershipFee%>" /></td>
                        <td class="error"><%=wuValidate.getMembershipFeeMsg()%></td>
                    </tr>
                    <tr>
                        <td>User Role</td>
                        <td>
                            <%=userRoleSelectTag%>
                        </td>
                        <td class="error"><%=wuValidate.getUserRoleMsg()%></td>
                    </tr>
                    <tr>
                        <td>Birthday</td>
                        <td><input type="text" name="birthday" value="<%= wuStringData.birthday%>" /></td>
                        <td class="error"><%=wuValidate.getBirthdayMsg()%></td>                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit" /></td>
                        <td colspan="2" class="error"><%=msg%></td>
                    </tr>
                </table>
            </form>
            <h2><a href="users.jsp">Back to users</a><br/></h2>
        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

    </body>

</html>
