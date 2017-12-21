import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SigninServlet extends HttpServlet
{

public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    // Do-Nothing
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    String userType = request.getParameter("userType");
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");

    PrintWriter out = response.getWriter();
    String htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\signin.html");

    // Check if the userid and password are valid
    if(!userId.equals("") && userId != null && DataStore.getUsers().containsKey(userId) && !password.equals("") && password != null)
    {
      String actualPassword = DataStore.getUsers().get(userId).getPassword();

      if(userType.equals("storemanager") && 
    		  userId.equals(DataStore.getUsers().get("storemanager").getUserId()) && 
    		  password.equals(DataStore.getUsers().get("storemanager").getPassword()))
      {
    	  HttpSession session = request.getSession();
          session.setAttribute("userId", userId);
          session.setAttribute("userIdForOrder", userId);
          
    	  htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\storemanager.html");
      }
      else if(userType.equals("salesman") && 
    		  userId.equals(DataStore.getUsers().get("salesman").getUserId()) && 
    		  password.equals(DataStore.getUsers().get("salesman").getPassword()))
      {
    	  HttpSession session = request.getSession();
          session.setAttribute("userId", userId); 
          
    	  htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\salesman.html");
      }
      else if (!userType.equals("storemanager") && !userType.equals("salesman"))
      {        
        if(password.equals(actualPassword))
        {
        	HttpSession session = request.getSession();
            session.setAttribute("userId", userId);  
            session.setAttribute("userIdForOrder", userId);
            
          htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\login.html");
        }
      }
    }
    out.println(htmlContent);
  }
}
