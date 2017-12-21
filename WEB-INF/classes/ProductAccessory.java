import java.util.Random;

/*
ProductAccesory class to hold details of each productaccessory
*/
public class ProductAccessory implements java.io.Serializable
{
private int productId;
  private String productName;
  private String productImage;
  private String productRetailer;
  private String productManufacturer;
  private String productCondition;
  private String productType;
  private int productPrice;
  private int productRetailerDiscount;
  private int productManufacturerRebate;
  private int productZip;
  private int productCount;

  /* Default Constructor must be defined when parameterized constructor is defined */
  public ProductAccessory(){}

  /* Parameterized constructor */
  public ProductAccessory(int id, String name, String image, String retailer, String manufacturer, String condition, String type, int price,
  int retailerDiscount, int manufacturerRebate, int zip)
  {
	Random random = new Random();
	productId = id;
    productName = (name==null || name == "")? "ABC" : name;
    productImage = (image==null || image == "")? "noimage.jpg" : image;
    productRetailer = (retailer==null || retailer == "")? "BestBuy" : retailer;
    productManufacturer = (manufacturer==null || manufacturer == "")? "Samsung" : manufacturer;
    productCondition = (condition==null || condition == "")? "New" : condition;
    productType = (type==null || type == "")? "SmartWatch" : type;
    productPrice = price;
    productRetailerDiscount = retailerDiscount;
    productManufacturerRebate = manufacturerRebate;
    productZip = zip;
    productCount = 100; // default value
  }

  /* Getter and Setter properties of productId */
  public int getProductId()
  {
    return productId;
  }

  public void setProductId(int id)
  {
    productId = id;
  }

  /* Getter and Setter properties of productName */
  public String getProductName()
  {
    return productName;
  }

  public void setProductName(String name)
  {
    productName = name;
  }

  /* Getter and Setter properties of productImage */
  public String getProductImage()
  {
    return productImage;
  }

  public void setProductImage(String image)
  {
    productImage = image;
  }

  /* Getter and Setter properties of productRetailer */
  public String getProductRetailer()
  {
    return productRetailer;
  }

  public void setProductRetailer(String retailer)
  {
    productRetailer = retailer;
  }

  /* Getter and Setter properties of productManufacturer */
  public String getProductManufacturer()
  {
    return productManufacturer;
  }

  public void setProductManufacturer(String manufacturer)
  {
    productManufacturer = manufacturer;
  }

  /* Getter and Setter properties of productCondition */
  public String getProductCondition()
  {
    return productCondition;
  }

  public void setProductCondition(String condition)
  {
    productCondition = condition;
  }

  /* Getter and Setter properties of productType */
  public String getProductType()
  {
    return productType;
  }

  public void setProductType(String type)
  {
    productType = type;
  }

  /* Getter and Setter properties of productPrice */
  public int getProductPrice()
  {
    return productPrice;
  }

  public void setProductPrice(int price)
  {
    productPrice = price;
  }

  /* Getter and Setter properties of productRetailerDiscount */
  public int getProductRetailerDiscount()
  {
    return productRetailerDiscount;
  }

  public void setProductRetailerDiscount(int retailerDiscount)
  {
    productRetailerDiscount = retailerDiscount;
  }

  /* Getter and Setter properties of productManufacturerRebate */
  public int getProductManufacturerRebate()
  {
    return productManufacturerRebate;
  }

  public void setProductManufacturerRebate(int manufacturerRebate)
  {
    productManufacturerRebate = manufacturerRebate;
  }
  
  /* Getter and Setter properties of productZip */
  public int getProductZip()
  {
    return productZip;
  }

  public void setProductZip(int zip)
  {
    productZip = zip;
  }
  
  /* Getter and Setter properties of productCount */
  public int getProductCount()
  {
    return productCount;
  }

  public void setProductCount(int count)
  {
	  productCount = count;
  }
}
