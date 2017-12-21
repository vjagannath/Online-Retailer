import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mongodb.util.JSON;

public class InventoryServlet extends HttpServlet {	
	
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
	String option = request.getParameter("report");
	displayProductDetails(option, request, response);	
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String htmlContent = null;
    out.println(htmlContent);
  }
  
  private void displayProductDetails(String option, HttpServletRequest request, HttpServletResponse response)
  {
	  try
	  {
	    // Add headercontent
	    printHeader(response.getWriter(), response);
	
	    // Add body content
	    printBody(option, request, response);
	
	    // Add footer html content
	    printFooter(response.getWriter());
	  }
	  catch(Exception e)
	  {
		  //
	  }
  }  
  
  private void printHeader(PrintWriter out , HttpServletResponse response)
  {
	  out.println("	<!doctype html>                                                                                        ");
	  out.println("	<html>                                                                                                 ");
	  out.println("		<head>                                                                                             ");
	  out.println("		<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script> 	   ");
	  printJavaScript(out, response);
	  out.println("		<script type=\"text/javascript\" src=\"datavisualizationinventory.js\"></script>  				   ");
	  out.println("			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                      ");
	  out.println("		  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                ");
	  out.println("		  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />  ");
	  out.println("			<title>                                                                                        ");
	  out.println("				Inventory                                                                                      ");
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
  
  private void printJavaScript(PrintWriter out, HttpServletResponse response)
  {
	  out.println("		<script type=\"text/javascript\">");
	  out.print("          var obj = '");
	  response.setContentType("application/JSON");
	  response.setCharacterEncoding("UTF-8");  
	  out.print(generateProductsAvailabilityBarChart());	  
	  response.setContentType("text/html");
	  out.print("';");
	  out.println("		</script>");
  }
  
  private void printBody(String option, HttpServletRequest request, HttpServletResponse response)
  {
	    PrintWriter out = null;
	    try
	    {
	    	out = response.getWriter();
	    }
	    catch(Exception e){}
	    
		out.println("	<div id=\"width\">	");
		
		switch(option)
		{
		 case "option1" : generateProductsAvailabilityDetails(out);
			 break;
		 case "option2" : 
			 out.println("	<div id=\"chart_div\"> </div>	");
			 break;
		 case "option3" : generateProductsOnSaleDetails(out);
			 break;
		 case "option4" : generateProductsWithRebateDetails(out);
			 break;
		}	
		
		out.println("	</div>				");
  }
  
  private String generateProductsAvailabilityBarChart()
  {
	  try
	  {
		  HashMap<Integer, Integer> productsAvailabilityDetails = DataStore.getProductsAvailabilityDetails();
		  HashMap<String, Integer> productsAvailabilityDetailsNameCount= new HashMap<String, Integer>();
		  for(Map.Entry<Integer, Integer> entry : productsAvailabilityDetails.entrySet())
		  {
			  ProductAccessory product = null;
			  if(DataStore.getAllProducts().containsKey(entry.getKey()))
			  {
				  product = DataStore.getAllProducts().get(entry.getKey());
			  }
			  else if(DataStore.getAllProductAccessories().containsKey(entry.getKey()))
			  {
				  product = DataStore.getAllProductAccessories().get(entry.getKey());
			  }
			  productsAvailabilityDetailsNameCount.put(product.getProductName(), entry.getValue());
		  }
		  
		  String productsAvailabilityJson = new Gson().toJson(productsAvailabilityDetailsNameCount);	
		  return productsAvailabilityJson;
	  }
	  catch(Exception e)
	  {
		  return "";
	  }
  }
  
  private void generateProductsAvailabilityDetails(PrintWriter out)
  {
	  HashMap<Integer, Integer> productsAvailabilityDetails = DataStore.getProductsAvailabilityDetails();

	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Product Availability Details</h3>                                                      			");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  out.println("				<td>Product Name</td><td> Price </td><td> Availability Count </td>   						");
	  out.println("			</tr>																							");
	  for(Map.Entry<Integer, Integer> entry : productsAvailabilityDetails.entrySet())
	  {
		  ProductAccessory product = null;
		  if(DataStore.getAllProducts().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProducts().get(entry.getKey());
		  }
		  else if(DataStore.getAllProductAccessories().containsKey(entry.getKey()))
		  {
			  product = DataStore.getAllProductAccessories().get(entry.getKey());
		  }
		  out.println("		<tr>	                                                                                    ");
		  out.println("				<td>"+product.getProductName()+"</td>                 								");
		  out.println("				<td>"+product.getProductPrice()+"</td>                 								");
		  out.println("				<td>"+entry.getValue()+"</td>                        								");
		  out.println("		</tr>                                                                                       ");
	  }
	  out.println("	  </table>                                                                                              ");
	  out.println("	</article>                                                                                              ");
  }
  
  private void generateProductsOnSaleDetails(PrintWriter out)
  {
	  ArrayList<Product> productsAvailabilityDetails = DataStore.getProductsOnSaleDetails();

	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Product On Sale Details</h3>                                                      			");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  out.println("				<td>Product Id</td>												   						");
	  out.println("				<td>Product Name</td>												   						");
	  out.println("			</tr>																							");
	  for(Product product : productsAvailabilityDetails)
	  {			  
		  out.println("		<tr>	                                                                                    ");
		  out.println("				<td>"+product.getProductId()+"</td>                 								");
		  out.println("				<td>"+product.getProductName()+"</td>                 								");
		  out.println("		</tr>                                                                                       ");
	  }
	  out.println("	  </table>                                                                                              ");
	  out.println("	</article>                                                                                              ");
  }
  
  private void generateProductsWithRebateDetails(PrintWriter out)
  {
	  ArrayList<Product> productsWithRebateDetails = DataStore.getProductsWithRebateDetails();

	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Product With Rebate Details</h3>                                                      			");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  out.println("				<td>Product Name</td><td> Rebate </td>   													");
	  out.println("			</tr>																							");
	  for(Product product : productsWithRebateDetails)
	  {	
		  out.println("		<tr>	                                                                                    ");
		  out.println("				<td>"+product.getProductName()+"</td>                 								");
		  out.println("				<td>"+product.getProductManufacturerRebate()+"</td>          						");
		  out.println("		</tr>                                                                                       ");
	  }
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