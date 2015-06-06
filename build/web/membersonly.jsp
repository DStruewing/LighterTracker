<%@page import="model.WebUser.StringData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String msg = "";

    // Check the session to see if the web user object is in there.
    // Must use same name ("user") when getting the object out of the session
    // as you used when you put it into the session (in pre-body.jsp).
    // You have to type cast the object as it comes out of the session (StringData)
    // so that you can put the object into a customer StringData object.  
    StringData loggedOnUser = (StringData) session.getAttribute("user");
    if (loggedOnUser == null) { // means user is not logged in
        try {
            response.sendRedirect("deny.jsp?denyMsg="
                    + "You are not authorized to view the Members Only page.");
        } catch (Exception e) {
            msg += " Exception was thrown: " + e.getMessage();
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />

        <script type="text/javascript" src="js/slidetoggle.js"></script>  

    </head>

    <body>

        <jsp:include page="global/pre-body.jsp?" />

        <script>
            document.getElementById("membersonly").className = "tab selected";
        </script> 

        <div class="content">

            <h1>Members only content displayed.</h1>

        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

    </body>

</html>
