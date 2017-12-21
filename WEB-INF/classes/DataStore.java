
import java.util.*;

public class DataStore
{
  // Define different categories of the products
  enum Category
  {
    SmartWatch, Speaker, HeadPhone, Phone, Laptop, ExternalStorage, AllProducts;
  }

  public static String TOMCAT_HOME;

  public static HashMap<Integer, Product> allproducts = new HashMap<Integer, Product>();
  public static HashMap<Integer, Product> smartWatch = new HashMap<Integer, Product>();
  public static HashMap<Integer, Product> speaker = new HashMap<Integer, Product>();
  public static HashMap<Integer, Product> headphone = new HashMap<Integer, Product>();
  public static HashMap<Integer, Product> phone = new HashMap<Integer, Product>();
  public static HashMap<Integer, Product> laptop = new HashMap<Integer, Product>();
  public static HashMap<Integer, Product> externalStorage = new HashMap<Integer, Product>();
  public static HashMap<Integer, ProductAccessory> allProductAccessories = new HashMap<Integer, ProductAccessory>();
  public static HashMap<String, User> users = new HashMap<String, User>();
  public static HashMap<Integer, ArrayList<ProductReview>> productReviews = new HashMap<Integer, ArrayList<ProductReview>>();
  public static XmlSaxParserProductDataStore productParser;
  public static XmlSaxParserUserDataStore userDataParser;

  //Database
  public static MySQLDataStoreUtilities mySqlDataStore = new MySQLDataStoreUtilities();
  public static MongoDBDataStoreUtilities myMongoDataStore = new MongoDBDataStoreUtilities();

  // Read products details using the parser
  public static void LoadProductData()
  {
    //String TOMCAT_HOME = System.getProperty("catalina.home");
    //new XmlSaxParserProductDataStore(TOMCAT_HOME + "\\webapps\\Assignment1_September18\\WEB-INF\\classes\\ProductCatalog.xml");
	  productParser = new XmlSaxParserProductDataStore(DataStore.TOMCAT_HOME + "\\webapps\\csj\\WEB-INF\\classes\\ProductCatalog.xml");
  }

  // Read user details using the parser
  public static void LoadUserData()
  {
	  //String TOMCAT_HOME = System.getProperty("catalina.home");
	  //new XmlSaxParserUserDataStore(TOMCAT_HOME + "\\webapps\\Assignment1_September18\\WEB-INF\\classes\\UserData.xml");
	  userDataParser = new XmlSaxParserUserDataStore(DataStore.TOMCAT_HOME + "\\webapps\\csj\\WEB-INF\\classes\\UserData.xml");
  }

  //Add the product accessory
  public static void AddProductAccessory(ProductAccessory productAccessory)
  {
	 allProductAccessories.put(productAccessory.getProductId(), productAccessory);

	 // Add to DataBase
	 AddProductAccessoryToDataBase(productAccessory);
  }

  public static void AddProductAccessoryToDataBase(ProductAccessory productAccessory)
  {
	  // Add to DataBase
	  mySqlDataStore.addAccessory(productAccessory);
  }

  // Add the product to the specific category list and the complete product list - allProducts
  public static void AddProduct(Product product)
  {
	// Add to HashMap
    allproducts.put(product.getProductId(), product);

    Category category = Category.valueOf(product.getProductType());
    switch(category)
    {
      case SmartWatch: smartWatch.put(product.getProductId(), product);
      break;
      case Speaker: speaker.put(product.getProductId(), product);
      break;
      case HeadPhone: headphone.put(product.getProductId(), product);
      break;
      case Phone: phone.put(product.getProductId(), product);
      break;
      case Laptop:  laptop.put(product.getProductId(), product);
      break;
      case ExternalStorage: externalStorage.put(product.getProductId(), product);
      break;
    }

    // Add to DataBase
    AddProductToDataBase(product);
  }

  public static void AddProductToDataBase(Product product)
  {
	// Add to DataBase
	mySqlDataStore.addProduct(product);
  }

  // Delete the product from the specific category list and the complete product list - allProducts
  public static void DeleteProduct(Integer productId)
  {
    Category category = Category.valueOf(allproducts.get(productId).getProductType());
    switch(category)
    {
      case SmartWatch: smartWatch.remove(productId);
      break;
      case Speaker: speaker.remove(productId);
      break;
      case HeadPhone: headphone.remove(productId);
      break;
      case Phone: phone.remove(productId);
      break;
      case Laptop:  laptop.remove(productId);
      break;
      case ExternalStorage: externalStorage.remove(productId);
      break;
    }
    allproducts.remove(productId);

    // Delete from DataBase
    DeleteProductFromDataBase(productId);
  }

  public static void DeleteProductFromDataBase(Integer productId)
  {
	  mySqlDataStore.deleteProduct(productId);
  }

  // Update the product in the specific category list and the complete product list - allProducts
  public static void UpdateProduct(Integer id, Integer price, Integer retailerDiscount, Integer manufacturerRebate, Integer zip)
  {
    Product product= allproducts.get(id);
    product.setProductPrice(price);
    product.setProductRetailerDiscount(retailerDiscount);
    product.setProductManufacturerRebate(manufacturerRebate);
    product.setProductZip(zip);
    allproducts.put(id, product);

    Category category = Category.valueOf(product.getProductType());
    switch(category)
    {
      case SmartWatch: smartWatch.put(product.getProductId(), product);
      break;
      case Speaker: speaker.put(product.getProductId(), product);
      break;
      case HeadPhone: headphone.put(product.getProductId(), product);
      break;
      case Phone: phone.put(product.getProductId(), product);
      break;
      case Laptop:  laptop.put(product.getProductId(), product);
      break;
      case ExternalStorage: externalStorage.put(product.getProductId(), product);
      break;
    }

    // Update DataBase
    UpdateProductToDataBase(product);
  }

  public static void UpdateProductToDataBase(Product product)
  {
	  mySqlDataStore.updateProduct(product);
  }

  // Add user to the users list
  public static void AddUser(User user)
  {
	// Add to HashMap
    users.put(user.getUserId(), user);

    // Add to DataBase
    AddUserToDataBase(user);

    for(Map.Entry<Integer,Order> entrySet : user.getOrders().entrySet())
    {
    	// Add order details of user to DataBase
    	AddOrderForUser(user.getUserId(), entrySet.getValue());
    }
  }

  public static void AddUserToDataBase(User user)
  {
	mySqlDataStore.addUser(user);

	for(Map.Entry<Integer,Order> entrySet : user.getOrders().entrySet())
	{
		// Add order details of user to DataBase
	  AddOrderForUserInDataBase(user.getUserId(), entrySet.getValue());
	}
  }

  // Add order
  public static void AddOrderForUser(String userId, Order order)
  {
	  User user = getUsers().get(userId);

	  // Add to Hash Table
	  user.getOrders().put(order.getOrderId(), order);

	  // Add to DataBase
	  AddOrderForUserInDataBase(userId, order);
  }

  public static void AddOrderForUserInDataBase(String userId, Order order)
  {
	  mySqlDataStore.addOrder(userId, order);
  }

  // Delete order
  public static void DeleteOrderForUser(String userId, Order order)
  {
	  User user = getUsers().get(userId);

	  // Delete from Hash Table
	  user.getOrders().remove(order.getOrderId());

	  // Delete from DataBase
	  DeleteOrderForUserFromDataBase(userId, order);
  }

  public static void DeleteOrderForUserFromDataBase(String userId, Order order)
  {
	  mySqlDataStore.deleteOrder(userId, order);
  }

  public static void AddProductReview(ProductReview review)
  {
	  Integer productId = review.getProductId();

	  // Add to Hash Table
	  if(productReviews.containsKey(productId))
	  {
		  productReviews.get(productId).add(review);
	  }
	  else
	  {
		  productReviews.put(productId, new ArrayList<ProductReview>(Arrays.asList(review)));
	  }

	  // Add to Data Base
	  myMongoDataStore.addProductReview(review);
  }

  public static ArrayList<ProductReview> GetProductReviews(Integer productId)
  {
	  return myMongoDataStore.getProductReviews(productId);
  }

  public static HashMap<String, User> getUsers()
  {
    return users;
  }

  public static HashMap<Integer, Product> getAllProducts()
  {
    return allproducts;
  }

  public static HashMap<Integer, ProductAccessory> getAllProductAccessories()
  {
    return allProductAccessories;
  }

  public static HashMap<Integer, Product> getSmartWatches()
  {
    return smartWatch;
  }

  public static HashMap<Integer, Product> getSpeakers()
  {
    return speaker;
  }

  public static HashMap<Integer, Product> getHeadPhones()
  {
    return headphone;
  }

  public static HashMap<Integer, Product> getPhones()
  {
    return phone;
  }

  public static HashMap<Integer, Product> getLaptops()
  {
    return laptop;
  }

  public static HashMap<Integer, Product> getExternalStorages()
  {
    return externalStorage;
  }

  public static void FillHashMaps()
  {
	  // Fill User Hash Map from Data Base
	  users = mySqlDataStore.getUsers();

	  // Fill All Products Hash Map from Data Base
	  allproducts = mySqlDataStore.getProducts();
	  for(Map.Entry<Integer, Product> entry : allproducts.entrySet())
	  {
		    Product product = entry.getValue();
			Category category = Category.valueOf(product.getProductType());
			switch(category)
			{
			  case SmartWatch: smartWatch.put(product.getProductId(), product);
			  break;
			  case Speaker: speaker.put(product.getProductId(), product);
			  break;
			  case HeadPhone: headphone.put(product.getProductId(), product);
			  break;
			  case Phone: phone.put(product.getProductId(), product);
			  break;
			  case Laptop:  laptop.put(product.getProductId(), product);
			  break;
			  case ExternalStorage: externalStorage.put(product.getProductId(), product);
			  break;
			}
	  }

	  allProductAccessories = mySqlDataStore.getProductAccessories();
  }

  // Trend Data
  public static HashMap<Integer, Integer> getTopFiveSoldProducts()
  {
	  return mySqlDataStore.getTopFiveSoldProducts();
  }

  public static HashMap<Integer, Integer> getTopFiveLikedProducts()
  {
	  return myMongoDataStore.getTopFiveLikedProducts();
  }

  public static HashMap<Integer, Integer> getTopFiveLikedProductsGT3()
  {
	  return myMongoDataStore.getTopFiveLikedProductsGT3();
  }

  public static HashMap<Integer, Integer> getTopFiveZipCodesOfSoldProducts()
  {
	  return mySqlDataStore.getTopFiveZipCodesOfSoldProducts();
  }

  // Data Analytics
  public static HashMap<Integer, Integer> getAllProductsRatings()
  {
	  return myMongoDataStore.getAllProductsRatings();
  }

  public static HashMap<Integer, Integer> allProductsReviewRating5Price1000()
  {
	  return myMongoDataStore.allProductsReviewRating5Price1000();
  }

  public static HashMap<Integer, Integer> reviewCountForEachProduct()
  {
	  return myMongoDataStore.reviewCountForEachProduct();
  }

  public static int getproductCount(int productId)
  {
	  return mySqlDataStore.getProductCount(productId);
  }

  // Inventory
  public static HashMap<Integer, Integer> getProductsAvailabilityDetails()
  {
	  return mySqlDataStore.getProductsAvailabilityDetails();
  }

  public static ArrayList<Product> getProductsOnSaleDetails()
  {
	  return mySqlDataStore.getProductsOnSaleDetails();
  }

  public static ArrayList<Product> getProductsWithRebateDetails()
  {
	  return mySqlDataStore.getProductsWithRebateDetails();
  }

  // Sales Report
  public static HashMap<Integer, Integer> getProductsSalesDetails()
  {
	  return mySqlDataStore.getProductsSalesDetails();
  }

  public static HashMap<String, Integer> getProductsDailyTransactionsDetails()
  {
	  return mySqlDataStore.getProductsDailyTransactionsDetails();
  }
}
