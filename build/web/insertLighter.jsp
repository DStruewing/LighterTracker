<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="model.Lighter.*" %>
<%@page language="java" import="dbUtils.*" %>

<%

    // Constructor sets all fields of WebUser.StringData to "" (empty string) - good for 1st rendering
    StringData lighterStringData = new StringData();

    // Default constructor sets all error messages to "" - good for 1st rendering
    Validate lighterValidate = new Validate();

    String msg = "";

    if (request.getParameter("lighterName") != null) { // postback 

        // fill WebUserData object with form data (form data is always String)
        lighterStringData.lighterName = request.getParameter("lighterName");
        lighterStringData.purchaseDate = request.getParameter("purchaseDate");
        lighterStringData.lighterColor = request.getParameter("lighterColor");
		lighterStringData.trackingCode = request.getParameter("trackingCode");

        lighterValidate = new Validate(lighterStringData); // validate user input, set error messages.
        if (lighterValidate.isValidated()) { // data is good, proceed to try to insert

            DbConn dbc = new DbConn();
            msg = dbc.getErr();
            if (msg.length() == 0) { // means no error getting db connection

                // Instantiate Web User Mod object and pass validated String Data to its insert method
                LighterMods lighterMods = new LighterMods(dbc);
                msg = lighterMods.insert(lighterValidate);

                if (msg.length() == 0) { // empty string means record was sucessfully inserted
                    msg = "Record inserted. ";
                }
            }
            dbc.close(); // NEVER have db connection leaks !!!
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
            document.getElementById("other").className = "tab selected";
        </script> 

        <div class="content">

            <h1>Register a new lighter</h1>
            <form name="myForm" action="insertLighter.jsp" method="POST">
                <table style="text-align:left; padding:5px;">
                    <tr>
                        <td>Lighter Name</td>
                        <td><input type="text" name="lighterName" value="<%= lighterStringData.lighterName%>" /></td>
                        <td class="error"><%=lighterValidate.getLighterNameMsg()%></td>
                    </tr>
                    <tr>
                        <td>Purchase date</td>
                        <td><input type="text" name="purchaseDate" value="<%= lighterStringData.purchaseDate%>" /></td>
                        <td class="error"><%=lighterValidate.getPurchaseDateMsg()%></td>
                    </tr>
                    <tr>
                        <td>Color</td>
                        <td><input type="text" name="lighterColor" value="<%= lighterStringData.lighterColor%>" /></td>
                        <td class="error"><%=lighterValidate.getLighterColorMsg()%></td>
                    </tr>
					<tr>
                        <td>Tracking code</td>
                        <td><input type="text" name="trackingCode" value="<%= lighterStringData.trackingCode%>" /></td>
                        <td class="error"><%=lighterValidate.getTrackingCodeMsg()%></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit" /></td>
                        <td colspan="2" class="error"><%=msg%></td>
                    </tr>
                </table>
            </form>
            <h2><a href="other.jsp">Back to lighters</a><br/></h2>

        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

    </body>

</html>
