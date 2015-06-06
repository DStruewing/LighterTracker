<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="view.SearchView" %>
<%@page language="java" import="dbUtils.DbConn" %>
<%@page language="java" import="view.HtmlUtils" %>

<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="global/head.jsp" />
        <link id="theme" href="css/search.css" rel="stylesheet">
        <link id="theme" href="css/resultSetFormat.css" rel="stylesheet">
        <link id="theme" href="css/searchFormFormat.css" rel="stylesheet">

    </head>

    <body>

        <jsp:include page="global/pre-body.jsp" />

        <script>
            document.getElementById("search").className = "tab selected";
        </script> 

        <div class="content">
            <%
                DbConn dbc = new DbConn();
                String webUserIdParam = request.getParameter("webUserId");
                String lighterIdParam = request.getParameter("lighterId");
                String startDateParam = request.getParameter("startDate");
                String endDateParam = request.getParameter("endDate");

                if (dbc.getErr().length() == 0) {
                    out.print(SearchView.buildSearchFormTag(dbc, "searchFormFormat", webUserIdParam, lighterIdParam, startDateParam, endDateParam));
                }
                
                if (webUserIdParam != null && webUserIdParam.equals("0")) {
                    webUserIdParam = "";
                }
                if (lighterIdParam != null && lighterIdParam.equals("0")) {
                    lighterIdParam = "";
                }
                
                out.print(SearchView.search(dbc, "resultSetFormat", webUserIdParam, lighterIdParam, startDateParam, endDateParam));
                
                // PREVENT DB connection leaks:
                dbc.close(); //    EVERY code path that opens a db connection, must also close it.

            %>
        </div> <!-- content -->

        <jsp:include page="global/post-body.jsp" />

    </body>

</html>
