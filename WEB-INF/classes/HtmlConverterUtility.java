import java.io.*;

/*
Utility class to support conversion of html content into string
*/
public class HtmlConverterUtility
{
  // Utlity function to convert html content into string
  public static String ConvertHtmlToString(String htmlFilePath)
  {
    StringBuilder htmlBuilder = new StringBuilder();
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(htmlFilePath));
      String htmlString;
      while ((htmlString = reader.readLine()) != null)
      {
        htmlBuilder.append(htmlString);
      }
      reader.close();
    }
    catch (IOException e)
    {
      // Do-Nothing
    }
    return htmlBuilder.toString();
  }
}
