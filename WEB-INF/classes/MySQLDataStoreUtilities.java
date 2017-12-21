import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MySQLDataStoreUtilities 
{
	
	private Connection conn = null;
	
	public MySQLDataStoreUtilities()
	{
		try
		{
			 Class.forName("com.mysql.jdbc.Driver").newInstance();
			 createDataBase();
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartportablesdatabase","root","root");
		}
		catch(Exception e)
		{
			// 
		}	
	}
	
	public void createDataBase()
	{
		try 
		{		
			// Create Schema
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
			Statement stmt = conn.createStatement();
			String sqlQuery = "CREATE DATABASE IF NOT EXISTS smartportablesdatabase";
			stmt.execute(sqlQuery);
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartportablesdatabase","root","root");
			stmt = conn.createStatement();
			
			// Create Tables in the schema
			sqlQuery = "CREATE TABLE IF NOT EXISTS `accessory` (\n"
							+"`id` int(11) NOT NULL,\n"
				  			+"`productid` int(11) NOT NULL,\n"
							+"PRIMARY KEY (`id`,`productid`),\n"
				  			+"KEY `productid_idx` (`productid`),\n"
				  			+"CONSTRAINT `productid` FOREIGN KEY (`productid`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
				  			+") ENGINE=InnoDB DEFAULT CHARSET=utf8";			
			stmt.execute(sqlQuery);
			
			sqlQuery = "CREATE TABLE IF NOT EXISTS `orderdetails` (\n"
					  +"`oid` int(11) NOT NULL,\n"
					  +"`pid` int(11) NOT NULL,\n"
					  +"`productcount` int(11) NOT NULL,\n"
					  +"KEY `productid_idx` (`pid`),\n"
					  +"KEY `id_idx` (`oid`),\n"
					  +"CONSTRAINT `oid` FOREIGN KEY (`oid`) REFERENCES `userorder` (`orderid`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n"
					  +"CONSTRAINT `pid` FOREIGN KEY (`pid`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
					  +") ENGINE=InnoDB DEFAULT CHARSET=utf8";
			stmt.execute(sqlQuery);
			
			sqlQuery = "CREATE TABLE IF NOT EXISTS `product` (\n"
					  +"`id` int(11) NOT NULL,\n"
					  +"`name` varchar(45) NOT NULL,\n"
					  +"`image` varchar(45) NOT NULL,\n"
					  +"`retailer` varchar(45) NOT NULL,\n"
					  +"`manufacturer` varchar(45) NOT NULL,\n"
					  +"`condition` varchar(45) NOT NULL,\n"
					  +"`type` varchar(45) NOT NULL,\n"
					  +"`price` int(11) NOT NULL,\n"
					  +"`retailerdiscount` int(11) DEFAULT NULL,\n"
					  +"`manufacturerrebate` int(11) DEFAULT NULL,\n"
					  +"`zip` int(11) DEFAULT NULL,\n"
					  +"`count` int(4) NOT NULL DEFAULT '100',\n"
					  +"PRIMARY KEY (`id`)\n"
					  +") ENGINE=InnoDB DEFAULT CHARSET=utf8";
			stmt.execute(sqlQuery);
			
			sqlQuery = "CREATE TABLE IF NOT EXISTS `registration` (\n"
					  +"`userid` varchar(100) NOT NULL,\n"
					  +"`password` varchar(20) NOT NULL,\n"
					  +"`usertype` varchar(30) NOT NULL,\n"
					  +"PRIMARY KEY (`userid`)\n"
					  +") ENGINE=InnoDB DEFAULT CHARSET=utf8";
			stmt.execute(sqlQuery);
			
			sqlQuery = "CREATE TABLE IF NOT EXISTS `userorder` (\n"
					  +"`orderid` int(11) NOT NULL,\n"
					  +"`userid` varchar(45) NOT NULL,\n"
					  +"`address` varchar(100) NOT NULL,\n"
					  +"`orderdate` varchar(45) NOT NULL,\n"
					  +"`deliverydate` varchar(45) NOT NULL,\n"
					  +"PRIMARY KEY (`orderid`),\n"
					  +"KEY `userid_idx` (`userid`),\n"
					  +"CONSTRAINT `userid` FOREIGN KEY (`userid`) REFERENCES `registration` (`userid`) ON DELETE NO ACTION ON UPDATE NO ACTION\n"
					  +") ENGINE=InnoDB DEFAULT CHARSET=utf8";
			stmt.execute(sqlQuery);
		} 
		catch (SQLException e) 
		{
			e.getMessage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// Order table functionalities
	public void addOrder(String userId, Order order)
	{
		try
		{
			String insertIntoCustomerRegisterQuery = "INSERT INTO smartportablesdatabase.userorder(userorder.orderid,userorder.userid,userorder.address,userorder.orderdate,userorder.deliverydate)"
					+ "VALUES (?,?,?,?,?);";
			PreparedStatement statement = conn.prepareStatement(insertIntoCustomerRegisterQuery);

			statement.setInt(1,order.getOrderId());
			statement.setString(2,userId);
			statement.setString(3,order.getAddress());
			statement.setString(4,order.getDate());
			statement.setString(5,order.getDeliveryDate());
			statement.execute();			
			
			for(Map.Entry<Integer, Integer> itemEntry : order.getOrderItems().entrySet())
			{
				insertIntoCustomerRegisterQuery = "INSERT INTO smartportablesdatabase.orderdetails(orderdetails.oid,orderdetails.pid,orderdetails.productcount)"
						+ "VALUES (?,?,?);";
				statement = conn.prepareStatement(insertIntoCustomerRegisterQuery);
				statement.setInt(1, order.getOrderId());
				statement.setInt(2, itemEntry.getKey());
				statement.setInt(3, itemEntry.getValue());
				statement.execute();
			}
		}
		catch(SQLException e)
		{
			//
		}
	}
	
	public void deleteOrder(String userId, Order order)
	{
		try
		{
			String deleteFromCustomerRegisterQuery = "DELETE FROM smartportablesdatabase.userorder WHERE userorder.id = ? AND userorder.userid = ?";
			PreparedStatement statement = conn.prepareStatement(deleteFromCustomerRegisterQuery);
			statement.setInt(1,order.getOrderId());
			statement.setString(2,userId);
			statement.execute();	
			
			deleteFromCustomerRegisterQuery = "DELETE FROM smartportablesdatabase.orderdetails WHERE orderdetails.oid = ?";
			statement = conn.prepareStatement(deleteFromCustomerRegisterQuery);
			statement.setInt(1, order.getOrderId());
			statement.execute();
		}
		catch(SQLException e)
		{
			//
		}
	}
	
	public HashMap<Integer, Product> getProductsAndAccessories()
	{
		HashMap<Integer, Product> productsAndAccessories = new HashMap<Integer, Product>();
		try
		{ 
			String selectProductsQuery = "select * from smartportablesdatabase.product;";
			PreparedStatement statement = conn.prepareStatement(selectProductsQuery);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String image = resultSet.getString("image");
				String retailer = resultSet.getString("retailer");
				String manufacturer = resultSet.getString("manufacturer");
				String condition = resultSet.getString("condition");
				String type = resultSet.getString("type");
				int price = resultSet.getInt("price");
				int retailerdiscount = resultSet.getInt("retailerdiscount");
				int manufacturerrebate = resultSet.getInt("manufacturerrebate");
				int zip = resultSet.getInt("zip");
				
				productsAndAccessories.put(id, new Product(id, name, image, retailer, manufacturer, condition, type, price, retailerdiscount, manufacturerrebate, zip));
			}
		}
		catch(SQLException e)
		{
			//
		}
		return productsAndAccessories;
	}
	
	// Product table functionalities
	public HashMap<Integer, Product> getProducts()
	{
		HashMap<Integer, Product> productsAndAccessories = getProductsAndAccessories();
		
		HashMap<Integer, ArrayList<Integer>> productAccessoryMap = new HashMap<Integer, ArrayList<Integer>>();
		ArrayList<Integer> accessoryList = new ArrayList<Integer>();
		try
		{ 			
			String selectAccessoryQuery = "select * from smartportablesdatabase.accessory;";
			PreparedStatement statement1 = conn.prepareStatement(selectAccessoryQuery);
			ResultSet resultSet1 = statement1.executeQuery();
			while(resultSet1.next())
			{
				int pid = resultSet1.getInt("productid");
				int aid = resultSet1.getInt("id");
				if(productAccessoryMap.containsKey(pid))
				{
					productAccessoryMap.get(pid).add(aid);
				}
				else
				{
					ArrayList<Integer> aids = new ArrayList<Integer>();
					aids.add(aid);
					productAccessoryMap.put(pid, aids);
				}
				
				if(!accessoryList.contains(aid))
				{
					accessoryList.add(aid);
				}
			}
			
			// Add accessories Hash Map
			/*for(Map.Entry<Integer, Product> entry : productsAndAccessories.entrySet())
			{
				if(accessoryList.contains(entry.getKey()))
				{
					DataStore.allProductAccessories.put(entry.getKey(), entry.getValue());
				}
			}*/

			// Update accessory list of each product if any exists
			for(Map.Entry<Integer, ArrayList<Integer>> entry : productAccessoryMap.entrySet())
			{
				for(Integer aid : entry.getValue())
				{
					ProductAccessory accessory = productsAndAccessories.get(aid);
					productsAndAccessories.get(entry.getKey()).getProductAccessories().put(accessory.getProductId(), accessory);
				}
			}
			
			// Remove accessories from the combined list
			for(Integer aid : accessoryList)
			{
				if(productsAndAccessories.containsKey(aid))
				{
					productsAndAccessories.remove(aid);
				}
			}
		}
		catch(SQLException e)
		{
			//
		}
		return productsAndAccessories;
	}
	
	public HashMap<Integer, ProductAccessory> getProductAccessories()
	{
		HashMap<Integer, ProductAccessory> accessories = new HashMap<Integer, ProductAccessory>();
		
		HashMap<Integer, Product> productsAndAccessories = getProductsAndAccessories();
		ArrayList<Integer> accessoryList = new ArrayList<Integer>();
		try
		{
			String selectAccessoryQuery = "select * from smartportablesdatabase.accessory;";
			PreparedStatement statement1 = conn.prepareStatement(selectAccessoryQuery);
			ResultSet resultSet1 = statement1.executeQuery();
			while(resultSet1.next())
			{
				int aid = resultSet1.getInt("id");			
				if(!accessoryList.contains(aid))
				{
					accessoryList.add(aid);
				}
			}
			// Add accessories Hash Map
			for(Map.Entry<Integer, Product> entry : productsAndAccessories.entrySet())
			{
				if(accessoryList.contains(entry.getKey()))
				{
					accessories.put(entry.getKey(), entry.getValue());
				}
			}
		}
		catch(SQLException e)
		{
			//
		}
		return accessories;
	}
	
	public Product getProduct(Integer productId)
	{
		Product product = null;
		try
		{ 
			String selectOrderQuery ="select * from smartportablesdatabase.product where product.id =" + productId +";";
			PreparedStatement statement = conn.prepareStatement(selectOrderQuery);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String image = resultSet.getString("image");
				String retailer = resultSet.getString("retailer");
				String manufacturer = resultSet.getString("manufacturer");
				String condition = resultSet.getString("condition");
				String type = resultSet.getString("type");
				int price = resultSet.getInt("price");
				int retailerdiscount = resultSet.getInt("retailerdiscount");
				int manufacturerrebate = resultSet.getInt("manufacturerrebate");
				int zip = resultSet.getInt("zip");
				
				product = new Product(id, name, image, retailer, manufacturer, condition, type, price, retailerdiscount, manufacturerrebate, zip);
			}			
		}
		catch(SQLException e)
		{
			//
		}
		return product;
	}
	
	public void addAccessory(ProductAccessory product)
	{
		try
		{
			String insertIntoCustomerRegisterQuery = "INSERT INTO smartportablesdatabase.product(product.id,product.name,product.image,product.retailer,product.manufacturer,product.condition,product.type,product.price,product.retailerdiscount,product.manufacturerrebate,product.zip,product.count)"
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement statement = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			
			statement.setInt(1,product.getProductId());
			statement.setString(2,product.getProductName());
			statement.setString(3,product.getProductImage());
			statement.setString(4,product.getProductRetailer());
			statement.setString(5,product.getProductManufacturer());
			statement.setString(6,product.getProductCondition());
			statement.setString(7,product.getProductType());
			statement.setInt(8,product.getProductPrice());
			statement.setInt(9,product.getProductRetailerDiscount());
			statement.setInt(10,product.getProductManufacturerRebate());
			statement.setInt(11,product.getProductZip());
			statement.setInt(12,product.getProductCount());
			statement.execute();
		}
		catch(SQLException e)
		{
			String msg = e.getMessage();
			msg.length();
		}
	}
	
	public void addProduct(Product product)
	{
		try
		{
			// Add Base properties to the product
			addAccessory(product);
			
			for(Map.Entry<Integer, ProductAccessory> accessoryEntry : product.getProductAccessories().entrySet())
			{
				String insertIntoCustomerRegisterQuery = "INSERT INTO smartportablesdatabase.accessory(accessory.id,accessory.productid)"
						+ "VALUES (?,?);";
				PreparedStatement statement = conn.prepareStatement(insertIntoCustomerRegisterQuery);
				statement.setInt(1, accessoryEntry.getKey());
				statement.setInt(2, product.getProductId());
				statement.execute();
			}
		}
		catch(SQLException e)
		{
			//
		}
	}
	
	public void updateProduct(Product product)
	{
		try
		{
			String insertIntoCustomerRegisterQuery = "UPDATE smartportablesdatabase.product SET product.price = ?, product.retailerdiscount = ?, product.manufacturerrebate = ?, product.zip = ? WHERE product.id = ?;"; 
			PreparedStatement statement = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			
			statement.setInt(5,product.getProductId());
			statement.setInt(1,product.getProductPrice());
			statement.setInt(2,product.getProductRetailerDiscount());
			statement.setInt(3,product.getProductManufacturerRebate());
			statement.setInt(4,product.getProductZip());
			statement.execute();
		}
		catch(SQLException e)
		{
			//
		}
	}
	
	public void deleteProduct(Integer productId)
	{
		try
		{
			String deleteFromCustomerRegisterQuery = "DELETE FROM smartportablesdatabase.product WHERE product.id = ?;";
			PreparedStatement statement = conn.prepareStatement(deleteFromCustomerRegisterQuery);			
			statement.setInt(1,productId);
			statement.execute();	
			
			deleteFromCustomerRegisterQuery = "DELETE FROM smartportablesdatabase.accessory WHERE accessory.productid = ?;";
			statement = conn.prepareStatement(deleteFromCustomerRegisterQuery);
			statement.setInt(1, productId);
			statement.execute();
		}
		catch(SQLException e)
		{
			//
		}
	}
	
	// User table functionalities
	public HashMap<String, User> getUsers()
	{
		HashMap<String, User> users = new HashMap<String, User>();
		HashMap<String, ArrayList<Order>> userOrdersMap = new HashMap<String, ArrayList<Order>>();
		HashMap<Integer, HashMap<Integer, Integer>> orderDetails = new HashMap<Integer, HashMap<Integer, Integer>>();
		
		try
		{ 
			String selectOrderQuery ="select * from smartportablesdatabase.registration;";
			PreparedStatement statement = conn.prepareStatement(selectOrderQuery);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				String userId = resultSet.getString("userid");
				String password = resultSet.getString("password");
				String usertype = resultSet.getString("usertype");
				users.put(userId, new User(userId, password, usertype));
			}
			
			String selectOrderQuery2 ="select * from smartportablesdatabase.orderdetails;";
			PreparedStatement statement2 = conn.prepareStatement(selectOrderQuery2);
			ResultSet resultSet2 = statement2.executeQuery();
			while(resultSet2.next())
			{
				Integer oid = resultSet2.getInt("oid");
				Integer pid = resultSet2.getInt("pid");
				Integer productCount = resultSet2.getInt("productcount");
				
				if(orderDetails.containsKey(oid))
				{
					orderDetails.get(oid).put(pid, productCount);
				}
				else
				{
					HashMap<Integer, Integer> itemDetails = new HashMap<Integer, Integer>();
					itemDetails.put(pid,  productCount);
					orderDetails.put(oid, itemDetails);
				}
			}
			
			String selectOrderQuery1 ="select * from smartportablesdatabase.userorder;";
			PreparedStatement statement1 = conn.prepareStatement(selectOrderQuery1);
			ResultSet resultSet1 = statement1.executeQuery();
			while(resultSet1.next())
			{
				String userId = resultSet1.getString("userid");
				Integer orderId = resultSet1.getInt("orderid");
				String address = resultSet1.getString("address");
				String orderDate = resultSet1.getString("orderdate");
				String deliveryDate = resultSet1.getString("deliverydate");
				
				Order order = new Order();
				order.setAddress(address);
				order.setDate(orderDate);
				order.setDeliveryDate(deliveryDate);
				order.setOrderId(orderId);
				order.setOrderItems(orderDetails.get(orderId));
				
				if(userOrdersMap.containsKey(userId))
				{					
					userOrdersMap.get(userId).add(order);
				}
				else
				{
					ArrayList<Order> orders = new ArrayList<Order>();
					orders.add(order);
					userOrdersMap.put(userId, orders);
				}
			}
			
			for(Map.Entry<String, ArrayList<Order>> userOrdersEntry : userOrdersMap.entrySet())
			{
				if(users.containsKey(userOrdersEntry.getKey()))
				{
					User user = users.get(userOrdersEntry.getKey());
					for(Order order : userOrdersEntry.getValue())
					{
						user.getOrders().put(order.getOrderId(), order);
					}
				}
			}
		}
		catch(SQLException e)
		{
			//
		}
		return users;
	}
	
	public User getUser(String userId)
	{
		User user = null;
		try
		{ 
			String selectOrderQuery ="select * from smartportablesdatabase.registration where registration.userid='" + userId +"';";
			PreparedStatement statement = conn.prepareStatement(selectOrderQuery);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next())
			{
				String userid = resultSet.getString("userid");
				String password = resultSet.getString("password");
				String usertype = resultSet.getString("usertype");
				user = new User(userid, password, usertype);
			}
		}
		catch(SQLException e)
		{
			//
		}
		return user;
	}
	
	public void addUser(User user)
	{
		try
		{
			String insertIntoCustomerRegisterQuery = "INSERT INTO smartportablesdatabase.registration(registration.userid,registration.password,registration.usertype)"
					+ "VALUES (?,?,?);";
			PreparedStatement statement = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			statement.setString(1,user.getUserId());
			statement.setString(2,user.getPassword());
			statement.setString(3,user.getUserType());
			statement.execute();
		}
		catch(SQLException e)
		{
			//
		}
	}	
	
	// Trend Functions
	public HashMap<Integer, Integer> getTopFiveSoldProducts()
	{
		LinkedHashMap<Integer, Integer> products = new LinkedHashMap<Integer, Integer>();
		try
		{ 
			String selectOrderDetailsQuery ="select product.id as ProductId, subQuery.TotalCount from product,(select pid, SUM(productcount) as TotalCount from smartportablesdatabase.orderdetails group by pid) as subQuery where id = subQuery.pid order by subQuery.TotalCount desc limit 5;";
			PreparedStatement statement = conn.prepareStatement(selectOrderDetailsQuery);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				Integer productId = resultSet.getInt("ProductId");
				Integer productCount = resultSet.getInt("TotalCount");
				products.put(productId, productCount);
			}
		}
		catch(SQLException e)
		{
			//
		}
		return products;
	}	
	
	public HashMap<Integer, Integer> getTopFiveZipCodesOfSoldProducts()
	{
		LinkedHashMap<Integer, Integer> productZipCodes = new LinkedHashMap<Integer, Integer>();
		try
		{ 
			String selectOrderDetailsQuery ="select zip as ZipCode,sum(productcount) as ProductCount  from product"
											+" inner join orderdetails"
											+" on product.id=orderdetails.pid"
											+" group by zip ORDER BY ProductCount DESC Limit 5;";
			PreparedStatement statement = conn.prepareStatement(selectOrderDetailsQuery);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				Integer zipCode = resultSet.getInt("ZipCode");
				Integer productCount = resultSet.getInt("ProductCount");
				productZipCodes.put(zipCode, productCount);
			}
		}
		catch(SQLException e)
		{
			//
		}
		return productZipCodes;
	}
	
	public int getProductSoldCount(int productId)
	{
		int usedProductCount = 0;
		try
		{
			String selectOrderDetailsQuery = "select pid, SUM(productcount) as TotalCount from smartportablesdatabase.orderdetails"
											 +" where pid = " + productId
											 +" group by pid order by SUM(productcount) desc;";
			PreparedStatement statement = conn.prepareStatement(selectOrderDetailsQuery);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next())
			{
				usedProductCount = resultSet.getInt("TotalCount");
			}
		}
		catch(SQLException e)
		{
			//
		}
		return usedProductCount;
	}
	
	public int getProductCount(int productId)
	{
		int availableProductCount = 0;
		try
		{
			int usedProductCount = getProductSoldCount(productId);	
			
			int totalProductCount = 0;
			String selectOrderDetailsQuery = "select count from smartportablesdatabase.product where id = "+productId+";";
			PreparedStatement statement = conn.prepareStatement(selectOrderDetailsQuery);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next())
			{
				totalProductCount = resultSet.getInt("count");
			}
			
			availableProductCount = totalProductCount - usedProductCount;
		}
		catch(SQLException e)
		{
			//
		}
		return availableProductCount;
	}
	
	// Inventory
	public HashMap<Integer, Integer> getProductsAvailabilityDetails()
	{
		HashMap<Integer, Integer> productsAvailabilityDetails = new HashMap<Integer, Integer>();		
		
		HashMap<Integer, Product> productAndAccessories = getProductsAndAccessories();
		
		for(Integer productId : productAndAccessories.keySet())
		{
			int availabilityCount = getProductCount(productId);
			productsAvailabilityDetails.put(productId, availabilityCount);
		}		
		return productsAvailabilityDetails;
	}
  
	public ArrayList<Product> getProductsOnSaleDetails()
	{
		ArrayList<Product> productOnSaleDetails = new ArrayList<Product>();
		
		// Get all products availability
		HashMap<Integer, Integer> productsAvailabilityDetails = getProductsAvailabilityDetails();	
		
		// Add products to the list if the availability count is > 0
		for(Map.Entry<Integer, Integer> entry : productsAvailabilityDetails.entrySet())
		{
			if(entry.getValue() <= 0)
			{
				continue;
			}
			productOnSaleDetails.add(getProduct(entry.getKey()));
		}
		return productOnSaleDetails;
	}
  
	public ArrayList<Product> getProductsWithRebateDetails()
	{
		ArrayList<Product> productsWithRebateDetails = new ArrayList<Product>();
		
		HashMap<Integer, Product> productAndAccessories = getProductsAndAccessories();
		
		for(Map.Entry<Integer, Product> entry : productAndAccessories.entrySet())
		{
			if(entry.getValue().getProductManufacturerRebate() > 0)
			{
				productsWithRebateDetails.add(entry.getValue());
			}
		}
		return productsWithRebateDetails;
	}
	  
	// Sales Report
	public HashMap<Integer, Integer> getProductsSalesDetails()
	{
		HashMap<Integer, Integer> productsSalesDetails = new HashMap<Integer, Integer>();
		
		try
		{
			String selectOrderDetailsQuery = "select pid, SUM(productcount) as TotalCount from smartportablesdatabase.orderdetails"
											 +" group by pid order by SUM(productcount) desc;";
			PreparedStatement statement = conn.prepareStatement(selectOrderDetailsQuery);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				int usedProductCount = resultSet.getInt("TotalCount");
				int pid = resultSet.getInt("pid");
				productsSalesDetails.put(pid, usedProductCount);
			}
		}
		catch(SQLException e)
		{
			//
		}
		
		return productsSalesDetails;		
	}
	  
	public HashMap<String, Integer> getProductsDailyTransactionsDetails()
	{
		HashMap<String, Integer> productsDailyTransactionsDetails = new HashMap<String, Integer>();
		
		try
		{
			String selectOrderDetailsQuery = "select orderdate as TrsansactionDate, SUM(subQuery.ordercount) as TotalSales from "
					+ "(select A.orderdate, A.orderid, B.productcount as ordercount from userorder A, orderdetails B where "
					+ "A.orderid = B.oid) as subQuery group by orderdate;";
			PreparedStatement statement = conn.prepareStatement(selectOrderDetailsQuery);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				String date = resultSet.getString("TrsansactionDate");
				int totalTransaction = resultSet.getInt("TotalSales");
				productsDailyTransactionsDetails.put(date, totalTransaction);
			}
		}
		catch(SQLException e)
		{
			//
		}
		return productsDailyTransactionsDetails;
	}
}
