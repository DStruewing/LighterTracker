<%@page import="model.WebUser.StringData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String msg = "";
    if (request.getParameter("denyMsg") != null) {
        msg = request.getParameter("denyMsg");
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
            <h1><%=msg%></h1>
        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

    </body>

</html>
