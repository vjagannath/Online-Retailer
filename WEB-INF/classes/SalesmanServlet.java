import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class SalesmanServlet extends HttpServlet
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
      case "1": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\salesman.html");
      break;
      case "2": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\register.html");
      break;
      case "3": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\addorder.html");
      break;
      case "4": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\deleteorder.html");
      break;
      case "5": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\updateorder.html");
      break;      
    }

    out.println(htmlContent);
  }
}
