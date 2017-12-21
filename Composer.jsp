<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Product Information</title>
		<link rel="stylesheet" href="styles.css" type="text/css" />
		<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
	</head>
	<body>
	  <div id="container">
		  <header>
		      <div class="width">
		    		<h1><a href="/">Smart<span>Portables</span></a></h1>
		        <h3>The best ever deal you can get</h3>
		      </div>
	      </header>
			<form method="post" action="Home">
				<table>
					<tr>
						<th colspan = "2">Product Information</th>
					</tr>
					<tr>
						<td><img width="200px" height="200px" src="images/${requestScope.composer.getProductImage() }" /></td>
					</tr>
					<tr>
						<td>Product Name</td>
						<td>${requestScope.composer.getProductName() }</td>
					</tr>
					<tr>
						<td>Manufacturer</td>
						<td>${requestScope.composer.getProductManufacturer() }</td>
					</tr>
					<tr>
						<td>Price</td>
						<td>${requestScope.composer.getProductPrice() }</td>
					</tr>
					<tr>
						<td><input type='hidden' name='productId' value="${ requestScope.composer.getProductId() }"/></td>
					</tr>
					<tr>
						<td><input style="color:white" type="submit" name="submitId" value="AddToCart"/></td>
						<td><input style="color:white" type="submit" name="submitId" value="DeleteFromCart"/></td>
					</tr>
				</table>
			</form>
		   <footer>
			    <div class="footer-content width">
			      <ul>
			        <li><h2>Helpful Links</h2></li>
			        <li><a href="Home?id=2">About Us</a></li>
			        <li><a href="Home?id=3">Contact Us</a></li>
			      </ul>
			      <div class="clear"></div>
			    </div>
			    <div class="footer-bottom">
			      <p>&copy;2017 Developed by Vinay Jagannath</p>
			    </div>
		  </footer>
	   </div>
	</body>
</html>
