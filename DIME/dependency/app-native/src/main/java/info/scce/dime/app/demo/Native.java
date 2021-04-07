package info.scce.dime.app.demo;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;
/**
 * Collection of static methods for native SIBs
 */
public class Native {
	
	public static void sayHello(String name) {
		System.out.println("---------------------");
		System.out.println("  Hello " + name + "!");
		System.out.println("---------------------");
	}
	static String urlToJson(String customURL)
	{
		String inlineReceivedJson = "";
		try {
			//server connection and getting response
			URL url = new URL(customURL);
			
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.connect();

	        //Getting the response code
	        int responsecode = conn.getResponseCode();

	        if (responsecode != 200) {
	            throw new RuntimeException("HttpResponseCode: " + responsecode);
	        } else {

	            Scanner scanner = new Scanner(url.openStream());
	            //Write all the JSON data into a string using a scanner
	            while (scanner.hasNext()) {
	            	inlineReceivedJson += scanner.nextLine(); 
	            }
	            //Close the scanner
	            scanner.close();
	           // System.out.println(inlineReceivedJson);
	            // by now complete json is written in inlineReceivedJson variable
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return inlineReceivedJson;
	}
	
	public static int fnApiI2I(int input)
	{return input*input;}
	public static String fnApiI2S(int input)
	{return "Lahore";}
	public static String fnApiI2J(int input)
	{return "Lahore";}
	public static String fnApiS2S(String input)
	{
		String customURL="https://api.lmss.pk/?code="+input;
		JSONObject obj = new JSONObject(urlToJson(customURL));
		if (obj.getString("country_name")!=null)
		{
		return obj.getString("country_name");
		}else {
			return "empty";
		}
		//return "Testtt";
	}
	public static String fnApiS2Surl(String url,String input_var,String input, String output)
	{
		String customURL=url+"/?"+input_var+"="+input;
		JSONObject obj = new JSONObject(urlToJson(customURL));
		try
		{
			if (obj.getString(output)!=null)
			{
			return obj.getString(output);
			}else {
				return "No Result";
			}
		} catch (Exception e) {
			return "No Result";
		}
	}
	public static List<String> fnApiS2Lurl(String url,String input_var,String input, String output)
	{
		String customURL=url+"/?"+input_var+"="+input;
		JSONObject obj = new JSONObject(urlToJson(customURL));
		try
		{
			if (!obj.isEmpty())
			{
				List<String> tempResult=new ArrayList<>() ;
				
				for(int i = 0; i < obj.getJSONArray(output).length(); i++)
				{
					tempResult.add(obj.getJSONArray(output).getString(i));				    
				}							
			return tempResult;
			}else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}		
	}
		
		
		//return "Testtt";
	
	
}