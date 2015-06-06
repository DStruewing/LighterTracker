<% 
    session.invalidate();
    try {
        response.sendRedirect("index.jsp");
    } catch (Exception e) {
        System.out.println("**** Exception was thrown in logoff.jsp: " + e.getMessage());
    }
%>