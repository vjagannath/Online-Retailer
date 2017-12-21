import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

public class MongoDBDataStoreUtilities 
{	
	private DBCollection productReviews = null;
	MongoClient mongoClient = null;
	
	public MongoDBDataStoreUtilities()
	{		
		try
		{
			mongoClient = new MongoClient("localhost", 27017);
			
			//DB database = mongoClient.getDB("customerreviews");
			//productReviews = database.getCollection("productreviews");
			
			createCollection();
			
			// Fill Data Store product reviews HashMap List
			getProductReviews();
		}
		catch(Exception e)
		{
			// To-Do
		}
	}
	
	public void createCollection()
	{
		try
		{
			// Creating credential
			MongoCredential credential = MongoCredential.createCredential("root", "customerreviews", "root".toCharArray());
			
			// Accessing database
			DB database = mongoClient.getDB("customerreviews");
			
			boolean collectionExists = database.collectionExists("productreviews");
		    if (collectionExists == false) 
		    {
		    	database.createCollection("productreviews", null);
		    }
						
		    productReviews = database.getCollection("productreviews");
		}
		catch(Exception e)
		{
			// To-Do
		}
	}
	
	public void addProductReview(ProductReview review)
	{
		BasicDBObject dbObject = new BasicDBObject();
		
		//dbObject.append("productid", review);
		dbObject.append("productid", review.getProductId());
		dbObject.append("productname", review.getProductName());
		dbObject.append("producttype", review.getProductType());
		dbObject.append("productprice", review.getProductPrice());
		dbObject.append("productretailer", review.getProductRetailer());
		dbObject.append("productretailerzip", review.getProductRetailerZip());
		dbObject.append("productretailercity", review.getProductRetailerCity());
		dbObject.append("productretailerstate", review.getProductRetailerState());
		dbObject.append("productonsale", review.isProductOnSale());
		dbObject.append("productmanufacturer", review.getProductManufacturer());
		dbObject.append("productmanufacturerrebate", review.isProductManufacturerRebate());
		dbObject.append("userid", review.getUserId());
		dbObject.append("userage", review.getUserAge());
		dbObject.append("usergender", review.getUserGender());
		dbObject.append("useroccupation", review.getUserOccupation());
		dbObject.append("reviewrating", review.getReviewRating());
		dbObject.append("reviewdate", review.getReviewDate());
		dbObject.append("reviewtext", review.getReviewText());
		
		productReviews.insert(dbObject);
	}
	
	public void getProductReviews()
	{
		DBCursor cursor = productReviews.find();
		
		while(cursor.hasNext())
		{
			BasicDBObject record = (BasicDBObject)cursor.next();
			Integer productId = record.getInt("productid");
		    String productName = record.getString("productname");
		    String productType = record.getString("producttype");
		    Integer productPrice = record.getInt("productprice");
		    String productRetailer = record.getString("productretailer");
		    Integer productRetailerZip = record.getInt("productretailerzip");
		    String productRetailerCity = record.getString("productretailercity");
		    String productRetailerState = record.getString("productretailerstate");
		    boolean productOnSale = record.getBoolean("productonsale");
		    String productManufacturer = record.getString("productmanufacturer");
		    boolean productManufacturerRebate = record.getBoolean("productmanufacturerrebate");
		    String userId = record.getString("userid");
		    Integer userAge = record.getInt("userage");
		    String userGender = record.getString("usergender");
		    String userOccupation = record.getString("useroccupation");
		    Integer reviewRating = record.getInt("reviewrating");
		    String reviewDate = record.getString("reviewdate");
		    String reviewText= record.getString("reviewtext");
		    
		    if(productName != null && productType != null && productPrice != null
		    && productRetailer != null && productRetailerZip != null && productRetailerCity != null && productRetailerState != null && productManufacturer != null
		    && userId != null && userAge != null && userGender != null && userOccupation != null && reviewRating != null && reviewDate != null && reviewText != null)
		    {				
				DataStore.AddProductReview(new ProductReview(productId, productName, productType, productPrice, productRetailer, productRetailerZip, productRetailerCity,
				    	productRetailerState, productOnSale, productManufacturer, productManufacturerRebate, userId, userAge, userGender, userOccupation, reviewRating,
				    	reviewDate, reviewText));		    	
		    }	   
		}
	}
	
	public ArrayList<ProductReview> getProductReviews(Integer pid)
	{
		ArrayList<ProductReview> reviews = new ArrayList<ProductReview>();
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("productid", pid);
		
		DBCursor cursor = productReviews.find(whereQuery);
		
		while(cursor.hasNext())
		{
			BasicDBObject record = (BasicDBObject)cursor.next();
			Integer productId = record.getInt("productid");
		    String productName = record.getString("productname");
		    String productType = record.getString("producttype");
		    Integer productPrice = record.getInt("productprice");
		    String productRetailer = record.getString("productretailer");
		    Integer productRetailerZip = record.getInt("productretailerzip");
		    String productRetailerCity = record.getString("productretailercity");
		    String productRetailerState = record.getString("productretailerstate");
		    boolean productOnSale = record.getBoolean("productonsale");
		    String productManufacturer = record.getString("productmanufacturer");
		    boolean productManufacturerRebate = record.getBoolean("productmanufacturerrebate");
		    String userId = record.getString("userid");
		    Integer userAge = record.getInt("userage");
		    String userGender = record.getString("usergender");
		    String userOccupation = record.getString("useroccupation");
		    Integer reviewRating = record.getInt("reviewrating");
		    String reviewDate = record.getString("reviewdate");
		    String reviewText= record.getString("reviewtext");
		    
		    if(productName != null && productType != null && productPrice != null
		    && productRetailer != null && productRetailerZip != null && productRetailerCity != null && productRetailerState != null && productManufacturer != null
		    && userId != null && userAge != null && userGender != null && userOccupation != null && reviewRating != null && reviewDate != null && reviewText != null)
		    {				
				reviews.add(new ProductReview(productId, productName, productType, productPrice, productRetailer, productRetailerZip, productRetailerCity,
				    	productRetailerState, productOnSale, productManufacturer, productManufacturerRebate, userId, userAge, userGender, userOccupation, reviewRating,
				    	reviewDate, reviewText));		    	
		    }	   
		}
		return reviews;
	}
	
	public HashMap<Integer, Integer> getTopFiveLikedProducts()
	{
		LinkedHashMap<Integer, Integer> productLikeDetails = new LinkedHashMap<Integer, Integer>();
		
		/*int returnLimit = 5;
		
		BasicDBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("count", new BasicDBObject("$sum", 1));
		groupFields.put("_id", "$productid");
		BasicDBObject group = new BasicDBObject("$group", groupFields);
		
		BasicDBObject sort = new BasicDBObject();
		sort.put("reviewrating", -1);
		
		BasicDBObject projectFields = new BasicDBObject();
		BasicDBObject project = new BasicDBObject("$project", projectFields);
		projectFields.put("value", "$_id");
		projectFields.put("reviewrating", "$count");
		projectFields.put("productid", 1);
		
		BasicDBObject limit = new BasicDBObject("$limit", 5);
		BasicDBObject orderBy = new BasicDBObject("$sort", sort);
		
		AggregationOutput aggregate = productReviews.aggregate(group, project, orderBy, limit);
		
		for(DBObject result : aggregate.results())
		{
			BasicDBObject obj = (BasicDBObject)result;
			productLikeDetails.put(obj.getInt("reviewrating"), obj.getInt("value"));
		}*/
		
		// Query creation ****************************************************************************
		/*DBCursor cursor = productReviews.find().sort(new BasicDBObject("reviewrating", -1));
		
		while(cursor.hasNext())
		{
			BasicDBObject record = (BasicDBObject)cursor.next();
			Integer productId = record.getInt("productid");
			Integer productRating = record.getInt("reviewrating");
			productLikeDetails.put(productId, productRating);
		}*/
		
		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productid");
		BasicDBObject avgrating = new BasicDBObject("$avg", "$reviewrating");
		groupFields.put("avgrating", avgrating);
		BasicDBObject groupBy = new BasicDBObject("$group", groupFields);
		
		BasicDBObject avg = new BasicDBObject("avgrating", -1); 
		BasicDBObject sort = new BasicDBObject("$sort", avg);
		
		BasicDBObject limit = new BasicDBObject("$limit", 5);
		AggregationOutput aggregate = productReviews.aggregate(groupBy, sort, limit);
		
		for(DBObject result : aggregate.results())
		{
			BasicDBObject obj = (BasicDBObject)result;
			productLikeDetails.put(obj.getInt("_id"), obj.getInt("avgrating"));
		}
		
		return productLikeDetails;
	}
	
	public HashMap<Integer, Integer> getTopFiveLikedProductsGT3()
	{
		HashMap<Integer, Integer> productLikeDetails = new HashMap<Integer, Integer>();
		
		BasicDBObject query = new BasicDBObject();
		query.put("reviewrating", new BasicDBObject("$gt", 3));
		DBCursor dbCursor = productReviews.find(query).limit(5);
		
		while(dbCursor.hasNext())
		{
			BasicDBObject record = (BasicDBObject)dbCursor.next();
			Integer productId = record.getInt("productid");
			Integer productRating = record.getInt("reviewrating");
			productLikeDetails.put(productId, productRating);
		}
		
		return productLikeDetails;
	}	
	
	private void BasicLearningInformation()
	{
		// Query creation ****************************************************************************
		BasicDBObject query1 = new BasicDBObject();
		
		// Addingmultiple conditions *****************************************************************
		query1.put("reviewRating", 5);
		DBCursor dbCursor1 = productReviews.find(query1);
		
		// Operators *********************************************************************************
		// Suppose we want to filter only those reviews from our collection which have a rating of more than 3
		BasicDBObject query2 = new BasicDBObject();
		query2.put("reviewrating", new BasicDBObject("$gt",3));
		DBCursor dbCursor2 = productReviews.find(query2);
		
		// Limit and Sort
		// limit() accepts and integer value
		// sort() accepts an object of type DBObject
		
		// example : return top 5 products based on maximum rating ************************************
		int returnLimit = 5;
		BasicDBObject query3 = new BasicDBObject();
		
		// Specify the field you want to sort on, and the direction of the sort
		query3.put("reviewrating", -1);
		
		DBCursor dbCursor3 = productReviews.find(query3).limit(returnLimit).sort(query3);
		
		// Aggregation 
		// $match - this is similar to "where"
		// example- match the documents where rating is 5
		BasicDBObject query4 = new BasicDBObject("$match", new BasicDBObject("reviewrating", 5));
			
		// $group - this is similar to "group by" clause
		// example - grouping based on retailer city
		BasicDBObject query5 = new BasicDBObject("_id", 0);
		query5.put("_id", "$productretailercity");
		query5.put("count", new BasicDBObject("$sum",1));
		BasicDBObject groupQuery = new BasicDBObject("$group", query5);

	}
	
	// Data Analytics
	public HashMap<Integer, Integer> getAllProductsRatings()
	{
		HashMap<Integer, Integer> productDetails = new HashMap<Integer, Integer>();
		
		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productid");
		BasicDBObject avgrating = new BasicDBObject("$avg", "$reviewrating");
		groupFields.put("avgrating", avgrating);
		BasicDBObject groupBy = new BasicDBObject("$group", groupFields);
		
		BasicDBObject avg = new BasicDBObject("avgrating", -1); 
		BasicDBObject sort = new BasicDBObject("$sort", avg);
		
		AggregationOutput aggregate = productReviews.aggregate(groupBy, sort);
		
		for(DBObject result : aggregate.results())
		{
			BasicDBObject obj = (BasicDBObject)result;
			productDetails.put(obj.getInt("_id"), obj.getInt("avgrating"));
		}
		
		return productDetails;
	}
	
	public HashMap<Integer, Integer> allProductsReviewRating5Price1000()
	{
		HashMap<Integer, Integer> productLikeDetails = new HashMap<Integer, Integer>();
		
		BasicDBObject query = new BasicDBObject();
		query.put("reviewrating", new BasicDBObject("$eq", 5));
		query.put("productprice", new BasicDBObject("$gt", 1000));
		DBCursor dbCursor = productReviews.find(query);
		
		while(dbCursor.hasNext())
		{
			BasicDBObject record = (BasicDBObject)dbCursor.next();
			Integer productId = record.getInt("productid");
			Integer productRating = record.getInt("reviewrating");
			productLikeDetails.put(productId, productRating);
		}
		
		return productLikeDetails;
	}
	
	public HashMap<Integer, Integer> reviewCountForEachProduct()
	{
		HashMap<Integer, Integer> productReviewCountDetails = new HashMap<Integer, Integer>();
		
		DBObject groupFields = new BasicDBObject("_id", 0);
		groupFields.put("_id", "$productid");
		BasicDBObject count = new BasicDBObject("$sum", 1);
		groupFields.put("count", count);
		BasicDBObject groupBy = new BasicDBObject("$group", groupFields);
		
		AggregationOutput aggregate = productReviews.aggregate(groupBy);
		
		for(DBObject result : aggregate.results())
		{
			BasicDBObject obj = (BasicDBObject)result;
			productReviewCountDetails.put(obj.getInt("_id"), obj.getInt("count"));
		}
		
		return productReviewCountDetails;
	}
}
