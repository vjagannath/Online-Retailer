import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/*
Start up servlet
*/
public class Home extends HttpServlet
{
	String categoryString = null;

  public void init() throws ServletException
  {
		DataStore.TOMCAT_HOME = System.getProperty("catalina.home");

    // Load product details from ProductCatalog.xml and user details from UserData.xml
    DataStore.LoadProductData();
    DataStore.LoadUserData();
    DataStore.FillHashMaps();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    String id = request.getParameter("id");
    categoryString = request.getParameter("category");

    if(id == null)
    {
        id = "1";
    }

    PrintWriter out = response.getWriter();
    String htmlContent = "";

    switch(id)
    {
      case "1":
    	  HttpSession session=request.getSession();
          session.invalidate();
    	  displayProductDetails(out, categoryString);
      //htmlContent = HtmlConverterUtility.ConvertHtmlToString("../webapps/Assignment1_September18/index.html");
      break;
      case "2": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\aboutus.html");
      break;
      case "3": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\contact.html");
      break;
      case "4": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\register.html");
      break;
      case "5": htmlContent = HtmlConverterUtility.ConvertHtmlToString(DataStore.TOMCAT_HOME + "\\webapps\\csj\\signin.html");
      break;
    }

    out.println(htmlContent);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    //String productId = request.getParameter("productId");
    displaySignInPrompt(out, categoryString);
  }

  public void displaySignInPrompt(PrintWriter out, String category)
  {
    // Add header and navigation bar content
    printHeader(out);
    printNavigationBar(out);

    printSigninPromptMessage(out);
    printBodyBasedOnCategory(out, category);

    // Add side bar and footer html content
    printSideBar(out);
    printFooter(out);
  }

  public void printBodyBasedOnCategory(PrintWriter out, String categoryString)
  {
	if(categoryString == null)
	{
	  categoryString = "AllProducts";
	}
	DataStore.Category category = DataStore.Category.valueOf(categoryString);
	switch(category)
    {
      case SmartWatch: printBody(out, DataStore.getSmartWatches());
      break;
      case Speaker: printBody(out, DataStore.getSpeakers());
      break;
      case HeadPhone: printBody(out, DataStore.getHeadPhones());
      break;
      case Phone: printBody(out, DataStore.getPhones());
      break;
      case Laptop: printBody(out, DataStore.getLaptops());
      break;
      case ExternalStorage: printBody(out, DataStore.getExternalStorages());
      break;
      default : printBody(out, DataStore.getAllProducts());
      break;
    }
  }

  public void printSigninPromptMessage(PrintWriter out)
  {
    out.println("	  <div id=\"body\" class=\"width\">                                                                       ");
    out.println("  		<section id=\"content\">                                                                              ");
    out.println("        <article>                                                                                        ");
    out.println("          <h3>Welcome to Smart portables</h3>                                                                ");
    out.println("          <p>                                                                                                ");
    out.println("            Welcome to Smart portables, the best online retailer website                                     ");
    out.println("          </p>                                                                                               ");
    out.println("          <p>                                                                                                ");
    out.println("            <h4 style=\"color:grey\">You will have to SIGN IN to add/delete to/from the cart !!!!!!</h4>                                        ");
    out.println("          </p>                                                                                               ");
    out.println("        </article>                                                                                        ");
  }

  public void displayProductDetails(PrintWriter out, String category)
  {
    // Add header and navigation bar content
    printHeader(out);
    printNavigationBar(out);

    printWelcomeMessage(out);
    printBodyBasedOnCategory(out, category);

    // Add side bar and footer html content
    printSideBar(out);
    printFooter(out);
  }

  public void printWelcomeMessage(PrintWriter out)
  {
    out.println("	  <div id=\"body\" class=\"width\">                                                                       ");
    out.println("  		<section id=\"content\">                                                                              ");
    out.println("        <article>                                                                                        ");
    out.println("          <h3>Welcome to Smart portables</h3>                                                                ");
    out.println("          <p>                                                                                                ");
    out.println("            Welcome to Smart portables, the best online retailer website                                     ");
    out.println("          </p>                                                                                               ");
    out.println("          <p>                                                                                                ");
    out.println("            Name the product. We assure you for the best deal you can think ok !!!!!!<br/>                   ");
    out.println("            Limited Edition products<br/>                                                                    ");
    out.println("            <h4 style=\"color:grey\">Shop more to save more!!!!!</h4>                                        ");
    out.println("          </p>                                                                                               ");
    out.println("        </article>                                                                                        ");
  }

	public void printBody(PrintWriter out, HashMap<Integer, Product> products)
  {
		displayDeals(out);

    int count = 0;
    out.println("        <article>                                                                                            ");
		out.println("          <h3>Products</h3>           				                                                          ");
    out.println("          <ul>                                                                                               ");
    out.println("            <li>                                                                                             ");
    //out.println("              <form method=\"post\" action=\"Home\">                                                         ");
    out.println("                <table style=\"color:white\">                                                                ");

    for(Map.Entry<Integer, Product> entry : products.entrySet())
    {
      // To ensure only two items are displayed in a row
      if(count%2==0)
      {
        if(count > 0)
        {
          out.println("</tr>");
        }
        out.println("<tr>");
      }
      count++;
      Product product = entry.getValue();
      out.println("<td>                                                                                     ");
      out.println(" <form method=\"post\" action=\"Home\">                                                  ");
      out.println("  <table  style=\"box-shadow: 0px 0px 30px red\">                                        ");
      out.println("    <tr><td><img width=\"150px\" height=\"150px\" src=\"images/"+product.getProductImage()+"\" /></td></tr>");
      out.println("    <tr><td>Product Name</td><td>"+product.getProductName()+"</td></tr>                  ");
      out.println("    <tr><td>Manufacturer</td><td>"+product.getProductManufacturer()+"</td></tr>          ");
      out.println("    <tr><td>Price</td><td>"+product.getProductPrice()+"</td></tr>                        ");
      out.println("      <tr><td><input type=\"hidden\" name=\"productId\" value=" +product.getProductId()+ "/></td></tr>");
      out.println("    <tr><td><input style=\"color:white\" type=\"submit\" name=\"submitId\" value=\"AddToCart\"/></td> ");
      out.println("        <td><input style=\"color:white\" type=\"submit\" name=\"submitId\" value=\"DeleteFromCart\"/></td></tr> ");
      out.println("  </table>                                                                               ");
      out.println(" </form>                                                                                 ");
      out.println("</td>                                                                                    ");
    }

    out.println("                  </tr>                                                                                      ");
    out.println("                </table>                                                                                     ");
    //out.println("              </form>                                                                                        ");
    out.println("            </li>                                                                                            ");
    out.println("          </ul>                                                                                              ");
    out.println("        </article>                                                                                           ");
    out.println("      <!--</section>                                                                                         ");
    out.println("      <section id=\"content\">-->                                                                            ");
    out.println("      </section>                                                                                             ");
  }

  public void displayDeals(PrintWriter out)
  {
	  HashMap<Product, String> bestDeals = DealMatches.GetDealMatchProducts();
	  out.println("        <article style=\"box-shadow: 0px 0px 20px red; padding: 5px;\">                                    ");
	  out.println("          <h3>Best Deals</h3>           				                                                      ");
	  if(bestDeals.size() == 0)
	  {
		  out.println("          <p>No Deals Available</p>  			                                                      ");
	  }
	  else
	  {
		  int count = 0;
		  for(Map.Entry<Product, String> entry : bestDeals.entrySet())
		  {
			  if(count==2)
			  {
				  break;
			  }
			  String[] details = entry.getValue().split("#Deal");
			  out.println("          <p>"+details[0]+" #Deal   		                                                      ");
			  out.println("          <a href="+details[1]+">"+details[1]+"</a>    </p>                                    ");
			  count++;
		  }
		  count = 0;

		  out.println("          <ul>                                                                                         ");
		  out.println("            <li>                                                                                       ");
		  out.println("                <table style=\"color:white\">                                                          ");
		  out.println("                  <tr>                                                                                 ");

		  for(Map.Entry<Product, String> entry : bestDeals.entrySet())
		  {
			  if(count==2)
			  {
				  break;
			  }
			  Product product = entry.getKey();

			  out.println("<td>                                                                                     ");
		      out.println(" <form method=\"post\" action=\"Home\">                                                  ");
		      out.println("  <table  style=\"box-shadow: 0px 0px 30px red\">                                        ");
		      out.println("    <tr><td><img width=\"150px\" height=\"150px\" src=\"images/"+product.getProductImage()+"\" /></td></tr>");
		      out.println("    <tr><td>Product Name</td><td>"+product.getProductName()+"</td></tr>                  ");
		      out.println("    <tr><td>Manufacturer</td><td>"+product.getProductManufacturer()+"</td></tr>          ");
		      out.println("    <tr><td>Price</td><td>"+product.getProductPrice()+"</td></tr>                        ");
		      out.println("      <tr><td><input type=\"hidden\" name=\"productId\" value=" +product.getProductId()+ "/></td></tr>");
		      out.println("    <tr><td><input style=\"color:white\" type=\"submit\" name=\"submitId\" value=\"AddToCart\"/></td> ");
		      out.println("        <td><input style=\"color:white\" type=\"submit\" name=\"submitId\" value=\"DeleteFromCart\"/></td></tr> ");
		      out.println("  </table>                                                                               ");
		      out.println(" </form>                                                                                 ");
		      out.println("</td>                                                                                    ");

			  count++;

		  }
		  out.println("                  </tr>                                                                                ");
		  out.println("                </table>                                                                               ");
		  out.println("            </li>                                                                                      ");
		  out.println("          </ul>                                                                                        ");
	  }
	  out.println("        </article>                                                                                         ");
	}

  public void printHeader(PrintWriter out)
  {
    out.println("<!doctype html>                                                                                    ");
    out.println("<html>                                                                                             ");
    out.println("<head>                                                                                             ");
    out.println(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                         ");
    out.println("    <title>SmartPortables</title>                                                                  ");
    out.println("	 <script type=\"text/javascript\" src=\"autosearch.js\"></script>                               ");
    out.println("    <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                              ");
    out.println("    <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />");
    out.println("  </head>                                                                                          ");
    out.println("  <body onload=\"init()\">                                        						            ");
    out.println("  <div id=\"container\">                                                    			            ");
    out.println("    <header>                                                                			            ");
    out.println("    	<div class=\"width\">                                                			            ");
    out.println("    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>           			            ");
    out.println("        <h3>The best ever deal you can get</h3>                             			            ");
    out.println("      </div>                                                                			            ");
    out.println("    </header>                                                               			            ");
  }

  public void printNavigationBar(PrintWriter out)
  {
    out.println("    <nav>                                                                   						            ");
    out.println("    	<div class=\"width\">                                                						              ");
    out.println("    		<ul>                                                             						                ");
    out.println("          <li class=\"start selected\"><a href=\"Home?id=1\">Home</a></li>  						            ");
    out.println("          <li class=\"\"><a href=\"Home?id=2\">About Us</a></li>            						            ");
    out.println("          <li class=\"\"><a href=\"Home?id=3\">Contact Us</a></li>          						            ");
    out.println("          <li class=\"\"><a href=\"Home?id=4\">Account Registration</a></li>						            ");
    out.println("          <li class=\"end\"><a href=\"Home?id=5\">Sign In</a></li>          						            ");
    out.println("        </ul>                                                               						            ");
    out.println("    	</div>                                                               						              ");
    out.println("    </nav>                                                                  						            ");
    out.println("    <img class=\"header-image\" src=\"images/main.jpg\" width = \"100%\" height = 400 alt=\"Buildings\" />");
  }

  public void printSideBar(PrintWriter out)
  {
    out.println("  <aside class=\"sidebar\">                                                                             ");
    out.println("    <ul>                                                                                              ");
    out.println("      <li>                                                                                            ");
    out.println("        <h4>Categories</h4>                                                                           ");
    out.println("        <ul>                                                                                          ");
    out.println("           <li class=\"\"><a href=\"Home?category=SmartWatch\">Smart Watch</a></li>           ");
    out.println("           <li class=\"\"><a href=\"Home?category=Speaker\">Speaker</a></li>                  ");
    out.println("           <li class=\"\"><a href=\"Home?category=HeadPhone\">Head Phone</a></li>             ");
    out.println("           <li class=\"\"><a href=\"Home?category=Phone\">Phone</a></li>                      ");
    out.println("           <li class=\"\"><a href=\"Home?category=Laptop\">Laptop</a></li>                    ");
    out.println("           <li class=\"\"><a href=\"Home?category=ExternalStorage\">External Storage</a></li> ");
    out.println("        </ul>                                                                                         ");
    out.println("        <h4>Offer Zone</h4> 																		   ");
	out.println("        <ul>																						   ");
	out.println("        <li class=\"\"><a href=\"Home?id=1\">Retailer Warranty</a></li> 							   ");
	out.println("        </ul> 																					       ");
	out.println("      </li>                                                                                           ");
    out.println("      <li>                                                                                            ");
    out.println("        <h4>Search site</h4>                                                                          ");
    out.println("        <ul>                                                                                          ");
    out.println("          <li class=\"text\">                                                                         ");
    out.println("	        <form name=\"autofillform\" action=\"AutoCompleteServlet\">                                ");
    out.println("		      <input style=\"color:black\" type=\"text\" size=\"33\" id=\"complete-field\" onkeyup=\"doCompletion()\" placeholder=\"search here...\"/> ");
    out.println("		      <div id=\"auto-row\" colspan=\"2\">                                                      ");
    out.println("			    <table id=\"complete-table\" class=\"popupBox\" style=\"color:white\"></table>	       ");
    out.println("		     </div>                                                                                    ");
    out.println("	        </form>                                                                                    ");
    out.println("		   </li>                                                                                       ");
    out.println("       </ul>                                                                                          ");
    out.println("      </li>                                                                                           ");
    out.println("      <li>                                                                                            ");
    out.println("        <h4>Analytics</h4>                                                                            ");
    out.println("        <ul>                                                                                          ");
    out.println("          <li class=\"\"><a href=\"TrendServlet?id=1\">Trending</a></li>         					   ");
    out.println("        </ul>                                                                                         ");
    out.println("      </li>                                                                                           ");
    out.println("      <li>                                                                                            ");
    out.println("        <h4>Other Website Links</h4>                                                                  ");
    out.println("        <ul>                                                                                          ");
    out.println("          <li><a href=\"https://www.target.com/\" title=\"premium templates\">Target</a></li>         ");
    out.println("          <li><a href=\"https://www.walmart.com/\" title=\"web hosting\">Walmart</a></li>             ");
    out.println("        </ul>                                                                                         ");
    out.println("      </li>                                                                                           ");
    out.println("    </ul>                                                                                             ");
    out.println("  </aside>                                                                                            ");
    out.println("  <div class=\"clear\"></div>                                                                         ");
    out.println("  </div>																							                                                 ");
  }

  public void printFooter(PrintWriter out)
  {
    out.println("      <footer>                                           ");
    out.println("        <div class=\"footer-content width\">             ");
    out.println("          <ul>                                           ");
    out.println("            <li><h2>Helpful Links</h2></li>              ");
    out.println("            <li><a href=\"Home?id=2\">About Us</a></li>  ");
    out.println("            <li><a href=\"Home?id=3\">Contact Us</a></li>");
    out.println("          </ul>                                          ");
    out.println("          <div class=\"clear\"></div>                    ");
    out.println("        </div>                                           ");
    out.println("        <div class=\"footer-bottom\">                    ");
    out.println("          <p>&copy;2017 Developed by Vinay Jagannath</p> ");
    out.println("        </div>                                           ");
    out.println("      </footer>                                          ");
    out.println("    </div>                                               ");
    out.println("  </body>                                                ");
    out.println("</html>                                                  ");
  }
}
