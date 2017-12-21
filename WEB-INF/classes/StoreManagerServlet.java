import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class StoreManagerServlet extends HttpServlet
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
      case "1": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\storemanager.html");
      break;
      case "2": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\addproduct.html");
      break;
      case "3": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\deleteproduct.html");
      break;
      case "4": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\updateproduct.html");
      break;
      case "5": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\inventory.html");
      break;
      case "6": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\salesreport.html");
      break;
    }

    out.println(htmlContent);
  }
}
