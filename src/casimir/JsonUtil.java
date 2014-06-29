package casimir;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public class JsonUtil {
		static final Logger logger = LogManager.getLogger(JsonUtil.class.getName());

		// TODO Done - Need testing
	    /**
	     * @param property_category
	     * @param property_subcategory
	     * @param property_name
	     * @param property_url
	     * @param property_data
	     * @param number_properties
	     * @param transaction_from
	     * @param from_private_key
	     * @throws IOException
	     */
		public static void createNewAssetJson(String property_category,String property_subcategory,
	    		String property_name,String property_url,String property_data,int
	    		number_properties,String transaction_from,String from_private_key) throws Exception {
	        JSONObject obj = new JSONObject();
	        obj.put("transaction_type", 50);
	        obj.put("ecosystem", 1);
	        obj.put("property_type", 2);
	        obj.put("previous_property_id", 0);
	        obj.put("property_category", property_category);
	        obj.put("property_subcategory", property_subcategory);
	        obj.put("property_name", property_name);
	        obj.put("property_url", property_url);
	        obj.put("property_data", property_data);
	        obj.put("number_properties", number_properties);
	        obj.put("transaction_from", transaction_from);
	        obj.put("from_private_key", from_private_key);
	    
	        FileWriter file = new FileWriter("assets/" + property_name+".json");
	        try {
	            if (transaction_from != "") {
	            	file.write(obj.toString());
		            System.out.println();
		            System.out.println("Successfully created new Asset JSON and saved to file: " + property_name+".json");
		            System.out.println();
		            logger.exit(property_name+".json");
	            }
	            else {
	            	  System.out.println();
			          System.out.println("Didn't create JSON becuase address was null");
			          System.out.println();
			          logger.exit("Didn't create JSON becuase address was null");
	            }
	        	
	            // System.out.println("\nJSON Object: " + obj);
	 
	        } catch (IOException e) {
	        	logger.catching(e);
	        	e.printStackTrace();
	 
	        } finally {
	            file.flush();
	            file.close();
	        }
	    }
	     
		// TODO Done - Need testing
	    /**
	     * @param transaction_from
	     * @param transaction_to
	     * @param currency_id
	     * @param send_amt
	     * @param from_private_key
	     * @return
	     * @throws IOException
	     */
	  	public static String createTransaction(String transaction_from,String transaction_to, float currency_id,  double send_amt,  String from_private_key) throws Exception {

	  	    	JSONObject obj = new JSONObject();
	  	        obj.put("transaction_from", transaction_from);
	  	        obj.put("transaction_to", transaction_to);
	  	        obj.put("currency", currency_id);
	  	        obj.put("msc_send_amt", send_amt);
	  	        obj.put("from_private_key", from_private_key);
	  	   
	  	        Date now = new Date();
	  	        
	  	        FileWriter file = new FileWriter("transactions/" + currency_id+"-"+now.getTime()+".json");
	  	        try {
	  	            file.write(obj.toString());
	  	            System.out.println("Successfully created new transaction JSON and saved to file: " + currency_id+"-"+now.getTime()+".json");
	  	            // System.out.println("\nJSON Object: " + obj);
	  	            return currency_id+"-"+now.getTime();
	  	 
	  	        } catch (IOException e) {
	  	        	logger.catching(e);
	  	        	e.printStackTrace();
	  	            return "";
	  	 
	  	        } finally {
	  	            file.flush();
	  	            file.close();
	  	        }
	  	   
	  	    }
	  	
	    
}

