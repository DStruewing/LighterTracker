package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

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

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "global/head.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("        <link href=\"css/index.css\" rel=\"stylesheet\" type=\"text/css\">\n");
      out.write("        <script type=\"text/javascript\" src=\"js/slidetoggle.js\"></script>  \n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "global/pre-body.jsp?logonPage=yes", out, false);
      out.write("\n");
      out.write("\n");
      out.write("        <script>\n");
      out.write("\t\t\tdocument.getElementById(\"home\").className = \"tab selected\";\n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("        <div class=\"content\">\n");
      out.write("\n");
      out.write("            <h1>How It Works</h1>\n");
      out.write("\n");
      out.write("            <div>\n");
      out.write("                <p>Have you ever wondered where your lighter came from, \n");
      out.write("                    or where it will go when you lose it? Lighter Tracker \n");
      out.write("                    is designed to shed light on the question of lighter \n");
      out.write("                    movement. \n");
      out.write("                <p>Contribute to the project by using this website to record \n");
      out.write("                    the location and condition of any lighters with a Lighter \n");
      out.write("                    Tracker ID number. The more people who track their lighters, \n");
      out.write("                    the more we'll learn about their mysterious and transient \n");
      out.write("                    lives.</p>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("            <h2>Get Started</h2>\n");
      out.write("\n");
      out.write("            <div id=\"startForm\">\n");
      out.write("                <p id=\"startFormMsg\">To get started, enter your lighter's tracking code below:</p>\n");
      out.write("                <p id=\"lighterCodeMsg\" style=\"color:red; display:none;\">That lighter doesn't exist. Please try again.</p>\n");
      out.write("                <form name=\"startForm\" class=\"bigForm\" action=\"javascript:submitLighterCode()\" method=\"get\">\n");
      out.write("                    <input autofocus type=\"text\" name=\"lighterCode\"><br>\n");
      out.write("                    <input type=\"submit\" value=\"GO\">\n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("\n");
      out.write("        </div> <!-- content -->\n");
      out.write("\n");
      out.write("        ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "global/post-body.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("        <script type=\"text/javascript\"\n");
      out.write("                src=\"https://maps.googleapis.com/maps/api/js?sensor=false\">\n");
      out.write("        </script>\n");
      out.write("        <script type=\"text/javascript\"\n");
      out.write("\t\t\t\tsrc=\"js/index.js\">\n");
      out.write("\t\t</script>\n");
      out.write("    </body>\n");
      out.write("\n");
      out.write("</html>\n");
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
