import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class LoginServlet extends HttpServlet
{

public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    String id = request.getParameter("id");

    if(id == null)
    {
        id = "1";
    }

    PrintWriter out = response.getWriter();
    String htmlContent = "";

    switch(id)
    {
      case "1": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\login.html");
      break;
      case "2": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\index.html");
      break;
    }

    out.println(htmlContent);
  }
}
