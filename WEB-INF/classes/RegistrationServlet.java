import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class RegistrationServlet extends HttpServlet
{

boolean registrationSuccessful = false;

public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
    String userType = "customer";
    String confirmPassword = request.getParameter("confirmPassword");

    // Create a user account if the userid and password are valid
    if(!userId.equals("") && userId != null && !DataStore.getUsers().containsKey(userId) &&
    !password.equals("") && password != null && password.equals(confirmPassword))
    {      
    	registrationSuccessful = true;
    	DataStore.AddUser(new User(userId, password, userType));
    }
    else
    {
    	String errorMessage = "Username already exists";
    }

    PrintWriter out = response.getWriter();
    displayRegistrationPage(out);
  }

public void displayRegistrationPage(PrintWriter out)
{
    out.println("	<!doctype html>                                                                                                       ");
    out.println("	<html>                                                                                                                ");
    out.println("		<head>                                                                                                            ");
    out.println("			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                                     ");
    out.println("		  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                               ");
    out.println("		  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />                 ");
    out.println("			<title>                                                                                                       ");
    out.println("				Smart Portables Registration                                                                              ");
    out.println("			</title>                                                                                                      ");
    out.println("		  <style>                                                                                                         ");
    out.println("			body {                                                                                                        ");
    out.println("					color:white;                                                                                          ");
    out.println("					padding:10px 20px 10px 10px;                                                                          ");
    out.println("					border:1px solid black;                                                                               ");
    out.println("					margin:50px auto;                                                                                     ");
    out.println("					box-shadow: 0px 0px 30px gray;                                                                        ");
    out.println("			}                                                                                                             ");
    out.println("		  </style>                                                                                                        ");
    out.println("		</head>                                                                                                           ");
    out.println("		<body>                                                                                                            ");
    out.println("			<div id=\"container\">                                                                                        ");
    out.println("				<header>                                                                                                  ");
    out.println("		    	<div class=\"width\">                                                                                     ");
    out.println("			    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>                                            ");
    out.println("			        <h3>The best ever deal you can get</h3>                                                               ");
    out.println("			    </div>                                                                                                    ");
    out.println("					<!-- <a href=\"Home?id=1\" style='font-weight: bold;float:right'>Home</a> -->                         ");
    out.println("		    </header>                                                                                                     ");
    out.println("				<div id=\"width\">                                                                                        ");
    out.println("					<article>                                                                                             ");
    if(!registrationSuccessful)
    {
      out.println("			    	<h3>Create Account</h3>                                                                               ");
    }
    else
    {
      registrationSuccessful = false;
      out.println("			    	<h3>Account created successfully</h3>                                                                               ");
    }
    out.println("				  </article>                                                                                              ");
    out.println("					<article>                                                                                             ");
    out.println("						<form method=\"post\" action=\"RegistrationServlet\">                                             ");
    out.println("							<table>                                                                                       ");
    out.println("								<tr>                                                                                      ");
    out.println("									<td>User name</td>                                                                    ");
    out.println("									<td>                                                                                  ");
    out.println("										<input style=\"color:white\" type=\"text\" name=\"userId\" required/>             ");
    out.println("									</td>                                                                                 ");
    out.println("								</tr>                                                                                     ");
    out.println("								<tr>                                                                                      ");
    out.println("									<td>Password</td>                                                                     ");
    out.println("									<td>                                                                                  ");
    out.println("										<input style=\"color:white\" type=\"password\" name=\"password\" required/>       ");
    out.println("									</td>                                                                                 ");
    out.println("								</tr>                                                                                     ");
    out.println("								<tr>                                                                                      ");
    out.println("									<td>Confirm Password</td>                                                             ");
    out.println("									<td>                                                                                  ");
    out.println("										<input style=\"color:white\" type=\"password\" name=\"confirmPassword\" required/>");
    out.println("									</td>                                                                                 ");
    out.println("								</tr>                                                                                     ");
    out.println("								<tr>                                                                                      ");
    out.println("									<td>                                                                                  ");
    out.println("										<input style=\"color:white\" type=\"submit\" value=\"Continue\">                  ");
    out.println("									</td>                                                                                 ");
    out.println("								</tr>                                                                                     ");
    out.println("							</table>                                                                                      ");
    out.println("						</form>                                                                                           ");
    out.println("					</article>                                                                                            ");
    out.println("				</div>                                                                                                    ");
    out.println("				<footer>                                                                                                  ");
    out.println("	        <div class=\"footer-content width\">                                                                          ");
    out.println("	          <ul>                                                                                                        ");
    out.println("	            <li><h2>Helpful Links</h2></li>                                                                           ");
    out.println("	            <li><a href=\"Home?id=2\">About Us</a></li>                                                               ");
    out.println("	            <li><a href=\"Home?id=3\">Contact Us</a></li>                                                             ");
    out.println("	          </ul>                                                                                                       ");
    out.println("	          <div class=\"clear\"></div>                                                                                 ");
    out.println("	        </div>                                                                                                        ");
    out.println("	        <div class=\"footer-bottom\">                                                                                 ");
    out.println("	          <p>&copy;2017 Developed by Vinay Jagannath</p>                                                              ");
    out.println("	        </div>                                                                                                        ");
    out.println("	      </footer>                                                                                                       ");
    out.println("		  </div>                                                                                                          ");
    out.println("		</body>                                                                                                           ");
    out.println("	</html>                                                                                                               ");

}
}
