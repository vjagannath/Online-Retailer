import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class SalesReportServlet extends HttpServlet {
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
	  out.println("		<script type=\"text/javascript\" src=\"datavisualizationsalesreport.js\"></script>  				   ");
	  out.println("			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />                      ");
	  out.println("		  <link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />                                ");
	  out.println("		  <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maximum-scale=1.0\" />  ");
	  out.println("			<title>                                                                                        ");
	  out.println("				Sales Report                                                                                      ");
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
	  out.print(generateProductsSalesBarChart());	  
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
		 case "option1" : generateProductsSalesDetails(out);
			 break;
		 case "option2" : 
			 out.println("	<div id=\"chart_div\"> </div>	");
			 break;
		 case "option3" : generateProductsDailyTransactionsDetails(out);
			 break;
		}	
		
		out.println("	</div>				");
  }
  
  private String generateProductsSalesBarChart()
  {
	  try
	  {
		  HashMap<Integer, Integer> productsSalesDetails = DataStore.getProductsSalesDetails();
		  HashMap<String, Integer> productsSalesDetailsNameCount= new HashMap<String, Integer>();
		  for(Map.Entry<Integer, Integer> entry : productsSalesDetails.entrySet())
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
			  productsSalesDetailsNameCount.put(product.getProductName(), entry.getValue());
		  }
		  
		  String productsSalesJson = new Gson().toJson(productsSalesDetailsNameCount);	
		  return productsSalesJson;
	  }
	  catch(Exception e)
	  {
		  return "";
	  }
  }
  
  private void generateProductsSalesDetails(PrintWriter out)
  {
	  HashMap<Integer, Integer> productsSalesDetails = DataStore.getProductsSalesDetails();
	  int totalSales = 0;

	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Product Sales Details</h3>                                                      			");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  out.println("				<td>Product Name</td><td> Price </td><td> Sales Count </td>   								");
	  out.println("			</tr>																							");
	  for(Map.Entry<Integer, Integer> entry : productsSalesDetails.entrySet())
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
		  
		  totalSales = totalSales + entry.getValue(); 
	  }
	  out.println("			<tr>                                                                                            ");
	  out.println("				<td>Total Sales</td><td> Price </td><td> "+totalSales+" </td>   							");
	  out.println("			</tr>																							");
	  
	  out.println("	  </table>                                                                                              ");
	  out.println("	</article>                                                                                              ");
  }
  
  private void generateProductsDailyTransactionsDetails(PrintWriter out)
  {
	  HashMap<String, Integer> productsDailyTransactionsDetails = DataStore.getProductsDailyTransactionsDetails();

	  out.println("	<article>                                                                                               ");
	  out.println("	   <h3>Product Daily Transaction Details</h3>                                                      			");
	  out.println("	   <table style=\"color:white\">                                                                        ");
	  out.println("			<tr>                                                                                            ");
	  out.println("				<td>Date</td><td> Sales Count </td>   														");
	  out.println("			</tr>																							");
	  for(Map.Entry<String, Integer> entry : productsDailyTransactionsDetails.entrySet())
	  {
		  out.println("		<tr>	                                                                                    ");
		  out.println("				<td>"+entry.getKey()+"</td>                 										");
		  out.println("				<td>"+entry.getValue()+"</td>                        								");
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
