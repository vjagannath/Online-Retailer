import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

public class TrendServlet extends HttpServlet
{
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	displayProductDetails(out);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String htmlContent = null;
    out.println(htmlContent);
  }
  
  private void displayProductDetails(PrintWriter out)
  {
    // Add headercontent
    printHeader(out);

    // Add body content
    printBody(out);

    // Add footer html content
    printFooter(out);
  }
  
  private void printHeader(PrintWriter out)
  {
	  out.println("	<!doctype html>                                                                                        ");
	  out.println("	<html>                                                                                                 ");
	  out.println("		<head>                                                                                             ");
	  out.println("			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                      ");
	  out.println("		  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                ");
	  out.println("		  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />  ");
	  out.println("			<title>                                                                                        ");
	  out.println("				Trend                                                                                      ");
	  out.println("			</title>                                                                                       ");
	  out.println("		  <style>                                                                                          ");
	  out.println("			body {                                                                                         ");
	  out.println("					color:white;                                                                           ");
	  out.println("					padding:10px 20px 10px 10px;                                                           ");
	  out.println("					border:1px solid black;                                                                ");
	  out.println("					margin:50px auto;                                                                      ");
	  out.println("					box-shadow: 0px 0px 30px gray;                                                         ");
	  out.println("			}                                                                                              ");
	  out.println("		  </style>                                                                                         ");
	  out.println("		</head>                                                                                            ");
	  out.println("		<body>                                                                                             ");
	  out.println("			<div id=\"container\">                                                                         ");
	  out.println("				<header>                                                                                   ");
	  out.println("		    	<div class=\"width\">                                                                      ");
	  out.println("			    		<h1><a href=\"/\">Smart<span>Portables</span></a></h1>                             ");
	  out.println("			        <h3>The best ever deal you can get</h3>                                                ");
	  out.println("			    </div>                                                                                     ");
	  out.println("		       </header>                                                                                   ");
  }
  
  private void printBody(PrintWriter out)
  {
	out.println("	<div id=\"width\">	");
	
	// Print top 5 zip codes
	printZipCodeAnalytics(out);
	
	// Print top 5 liked products
	printLikedProductsAnaytics(out);
	
	// Print products with rating GT 3
	printLikedProductsAnayticsGT3(out);
	
	// Print top 5 sold products
	printSoldProductsAnalytics(out);
	
	out.println("	</div>				");
  }
  
  private void printZipCodeAnalytics(PrintWriter out) 
  {	
	  HashMap<Integer, Integer> topFiveZipCodesOfSoldProducts = DataStore.getTopFiveZipCodesOfSoldProducts();
	  
	  out.println("	<article>                                            ");
	  out.println("	   <h3>Top 5 Zip Codes</h3>                          ");
	  out.println("	   <table>                                           ");
	  out.println("		<tr>                                             ");
	  out.println("			<td> Zip Code </td> <td> Product Sold Count </td> ");
	  out.println("		</tr>                                            ");
	  for(Map.Entry<Integer, Integer> entry : topFiveZipCodesOfSoldProducts.entrySet())
	  {
		  out.println("		<tr><td>"+entry.getKey()+"</td> <td>"+entry.getValue()+"</td></tr>      ");
	  }
	  out.println("	   </table>                                          ");
	  out.println("	</article>                                           ");
	  out.println("	<article>                                            ");
	  out.println("	</article>                                           ");
  }
  
  private void printLikedProductsAnayticsGT3(PrintWriter out) 
  {	
	  HashMap<Integer, Integer> topFiveLikedProductsGT3 = DataStore.getTopFiveLikedProductsGT3();
	  
	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Products With Rating Greater Than 3</h3>                                                                    ");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  
	  int count = 0;
	  for(Map.Entry<Integer, Integer> entry : topFiveLikedProductsGT3.entrySet())
	  {
		  if(count == 3)
		  {
			  out.println("</tr><tr>");
			  count = 0;
		  }
		  ProductAccessory product = null;
		  if(DataStore.getAllProducts().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProducts().get(entry.getKey());
		  }
		  else if(DataStore.getAllProductAccessories().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProductAccessories().get(entry.getKey());
		  }
		  out.println("		<td>	                                                                                    ");
		  out.println("			<table  style=\"box-shadow: 0px 0px 30px red\">                                         ");
		  out.println("				<tr><td><img width=\"50px\" height=\"50px\" src=\"images/"+product.getProductImage()+"\" /></td></tr> ");
		  out.println("				<tr><td>Product Name</td><td>"+product.getProductName()+"</td></tr>                 ");
		  out.println("				<tr><td>Review Rating</td><td>"+entry.getValue()+"</td></tr>                        ");
		  out.println("			</table>	                                                                            ");
		  out.println("		</td>                                                                                       ");
		  count++;
	  }
	  out.println("		   </tr>                                                                                            ");
	  out.println("	  </table>                                                                                              ");
	  out.println("	</article>                                                                                              ");
  }
  
  private void printLikedProductsAnaytics(PrintWriter out) 
  {	
	  HashMap<Integer, Integer> topFiveLikedProducts = DataStore.getTopFiveLikedProducts();
	  
	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Top 5 Most Liked Products</h3>                                                                    ");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  
	  int count = 0;
	  for(Map.Entry<Integer, Integer> entry : topFiveLikedProducts.entrySet())
	  {
		  if(count == 3)
		  {
			  out.println("</tr><tr>");
			  count = 0;
		  }
		  ProductAccessory product = null;
		  if(DataStore.getAllProducts().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProducts().get(entry.getKey());
		  }
		  else if(DataStore.getAllProductAccessories().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProductAccessories().get(entry.getKey());
		  }
		  out.println("		<td>	                                                                                    ");
		  out.println("			<table  style=\"box-shadow: 0px 0px 30px red\">                                         ");
		  out.println("				<tr><td><img width=\"50px\" height=\"50px\" src=\"images/"+product.getProductImage()+"\" /></td></tr> ");
		  out.println("				<tr><td>Product Name</td><td>"+product.getProductName()+"</td></tr>                 ");
		  out.println("				<tr><td>Rating</td><td>"+entry.getValue()+"</td></tr>                        ");
		  out.println("			</table>	                                                                            ");
		  out.println("		</td>                                                                                       ");
		  count++;
	  }
	  out.println("		   </tr>                                                                                            ");
	  out.println("	  </table>                                                                                              ");
	  out.println("	</article>                                                                                              ");
  }
  
  private void printSoldProductsAnalytics(PrintWriter out) 
  {	
	  HashMap<Integer, Integer> topFiveSoldProducts = DataStore.getTopFiveSoldProducts();
	  
	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Top 5 Most Sold Products</h3>                                                                    ");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  
	  int count = 0;
	  for(Map.Entry<Integer, Integer> entry : topFiveSoldProducts.entrySet())
	  {
		  if(count == 3)
		  {
			  out.println("</tr><tr>");
			  count = 0;
		  }
		  ProductAccessory product = null;
		  if(DataStore.getAllProducts().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProducts().get(entry.getKey());
		  }
		  else if(DataStore.getAllProductAccessories().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProductAccessories().get(entry.getKey());
		  }
		  out.println("		<td>	                                                                                    ");
		  out.println("			<table  style=\"box-shadow: 0px 0px 30px red\">                                         ");
		  out.println("				<tr><td><img width=\"50px\" height=\"50px\" src=\"images/"+product.getProductImage()+"\" /></td></tr> ");
		  out.println("				<tr><td>Name</td><td>"+product.getProductName()+"</td></tr>                 ");
		  out.println("				<tr><td>Sale Count</td><td>"+entry.getValue()+"</td></tr>                        ");
		  out.println("			</table>	                                                                            ");
		  out.println("		</td>                                                                                       ");
		  count++;
	  }
	  out.println("		   </tr>                                                                                            ");
	  out.println("	  </table>                                                                                              ");
	  out.println("	</article>                                                                                              ");
  }

  private void printFooter(PrintWriter out)
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
