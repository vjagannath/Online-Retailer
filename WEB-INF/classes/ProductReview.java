
public class ProductReview {
	private Integer productId = null;
	private String productName = null;
	private String productType = null;
	private Integer productPrice = null;
	private String productRetailer = null;
	private Integer productRetailerZip = null;
	private String productRetailerCity = null;
	private String productRetailerState = null;
	private boolean productOnSale = false;
	private String productManufacturer = null;
	private boolean productManufacturerRebate = false;
	private String userId = null;
	private Integer userAge = null;
	private String userGender = null;
	private String userOccupation = null;
	private Integer reviewRating = null;
	private String reviewDate = null;
	private String reviewText= null;
    
    public ProductReview(Integer pid, String name, String type, Integer price, String retailer, Integer retailerZip, String retailerCity, String retailerState,
    		boolean onSale, String manufacturer, boolean manufacturerRebate, String uid, Integer age, String gender, String occupation, Integer rating,
    		String date, String text)
    {
    	productId = pid;
        productName = name;
        productType = type;
        productPrice = price;
        productRetailer = retailer;
        productRetailerZip = retailerZip;
        productRetailerCity = retailerCity;
        productRetailerState = retailerState;
        productOnSale = onSale;
        productManufacturer = manufacturer;
        productManufacturerRebate = manufacturerRebate;
        userId = uid;
        userAge = age;
        userGender = gender;
        userOccupation = occupation;
        reviewRating = rating;
        reviewDate = date;
        reviewText= text;
    }

	public Integer getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductType() {
		return productType;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public String getProductRetailer() {
		return productRetailer;
	}

	public Integer getProductRetailerZip() {
		return productRetailerZip;
	}

	public String getProductRetailerCity() {
		return productRetailerCity;
	}

	public String getProductRetailerState() {
		return productRetailerState;
	}

	public boolean isProductOnSale() {
		return productOnSale;
	}

	public String getProductManufacturer() {
		return productManufacturer;
	}

	public boolean isProductManufacturerRebate() {
		return productManufacturerRebate;
	}

	public String getUserId() {
		return userId;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public String getUserGender() {
		return userGender;
	}

	public String getUserOccupation() {
		return userOccupation;
	}

	public Integer getReviewRating() {
		return reviewRating;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public String getReviewText() {
		return reviewText;
	}
}
