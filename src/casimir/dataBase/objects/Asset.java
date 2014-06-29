package casimir.dataBase.objects;

public class Asset {

	private String transaction_from;
	private int  transaction_type;  
	private int  ecosystem;        
	private int  property_type;    
	private int  previous_property_id;
	private String  property_category;
	private String  property_subcategory;
	private String  property_name;       
	private String  property_url;        
	private String  property_data;       
	private int  number_properties;      
	private String tracking_status;    
	private int  price_in_satoshi;
	private int  broadcast_status = 0;
	private float  property_id = -1;
	private int block_broadcasted_in = -1;
	private String property_protocol;
	
	/**
	 * @param transaction_from
	 * @param transaction_type
	 * @param ecosystem
	 * @param property_type
	 * @param previous_property_id
	 * @param property_category
	 * @param property_subcategory
	 * @param property_name
	 * @param property_url
	 * @param property_data
	 * @param number_properties
	 * @param price_in_satoshi
	 * @param tracking_status
	 */
	public Asset(String transaction_from, int transaction_type, int ecosystem,
			int property_type, int previous_property_id,
			String property_category, String property_subcategory,
			String property_name, String property_url, String property_data,
			int number_properties, int price_in_satoshi,String tracking_status,String property_protocol) {
		super();
		this.transaction_from = transaction_from;
		this.transaction_type = transaction_type;
		this.ecosystem = ecosystem;
		this.property_type = property_type;
		this.previous_property_id = previous_property_id;
		this.property_category = property_category;
		this.property_subcategory = property_subcategory;
		this.property_name = property_name;
		this.property_url = property_url;
		this.property_data = property_data;
		this.number_properties = number_properties;
		this.price_in_satoshi = price_in_satoshi;
		this.tracking_status = tracking_status;
		this.property_protocol = property_protocol;
	}
	
	
	/**
	 * @return
	 */
	public int getBroadcast_status() {
		return broadcast_status;
	}


	/**
	 * @param broadcast_status
	 */
	public void setBroadcast_status(int broadcast_status) {
		this.broadcast_status = broadcast_status;
	}


	/**
	 * @return
	 */
	public String getTransaction_from() {
		return transaction_from;
	}
	/**
	 * @param transaction_from
	 */
	public void setTransaction_from(String transaction_from) {
		this.transaction_from = transaction_from;
	}
	/**
	 * @return
	 */
	public int getTransaction_type() {
		return transaction_type;
	}
	/**
	 * @param transaction_type
	 */
	public void setTransaction_type(int transaction_type) {
		this.transaction_type = transaction_type;
	}
	/**
	 * @return
	 */
	public int getEcosystem() {
		return ecosystem;
	}
	/**
	 * @param ecosystem
	 */
	public void setEcosystem(int ecosystem) {
		this.ecosystem = ecosystem;
	}
	/**
	 * @return
	 */
	public int getProperty_type() {
		return property_type;
	}
	/**
	 * @param property_type
	 */
	public void setProperty_type(int property_type) {
		this.property_type = property_type;
	}
	/**
	 * @return
	 */
	public int getPrevious_property_id() {
		return previous_property_id;
	}
	/**
	 * @param previous_property_id
	 */
	public void setPrevious_property_id(int previous_property_id) {
		this.previous_property_id = previous_property_id;
	}
	/**
	 * @return
	 */
	public String getProperty_category() {
		return property_category;
	}
	/**
	 * @param property_category
	 */
	public void setProperty_category(String property_category) {
		this.property_category = property_category;
	}
	/**
	 * @return
	 */
	public String getProperty_subcategory() {
		return property_subcategory;
	}
	/**
	 * @param property_subcategory
	 */
	public void setProperty_subcategory(String property_subcategory) {
		this.property_subcategory = property_subcategory;
	}
	/**
	 * @return
	 */
	public String getProperty_name() {
		return property_name;
	}
	/**
	 * @param property_name
	 */
	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}
	/**
	 * @return
	 */
	public String getProperty_url() {
		return property_url;
	}
	/**
	 * @param property_url
	 */
	public void setProperty_url(String property_url) {
		this.property_url = property_url;
	}
	/**
	 * @return
	 */
	public String getProperty_data() {
		return property_data;
	}
	/**
	 * @param property_data
	 */
	public void setProperty_data(String property_data) {
		this.property_data = property_data;
	}
	/**
	 * @return
	 */
	public int getNumber_properties() {
		return number_properties;
	}
	/**
	 * @param number_properties
	 */
	public void setNumber_properties(int number_properties) {
		this.number_properties = number_properties;
	}
	/**
	 * @return
	 */
	public String getTracking_status() {
		return tracking_status;
	}
	/**
	 * @param status
	 */
	public void setTracking_status(String status) {
		this.tracking_status = status;
	}
	/**
	 * @return
	 */
	public int getPrice_in_satoshi() {
		return price_in_satoshi;
	}
	/**
	 * @param price_in_satoshi
	 */
	public void setPrice_in_satoshi(int price_in_satoshi) {
		this.price_in_satoshi = price_in_satoshi;
	}

	/**
	 * @return
	 */
	public float getProperty_id() {
		return property_id;
	}

	/**
	 * @param property_id
	 */
	public void setProperty_id(float property_id) {
		this.property_id = property_id;
	}

	/**
	 * @return
	 */
	public int getBlock_broadcasted_in() {
		return block_broadcasted_in;
	}


	/**
	 * @param block_broadcasted_in
	 */
	public void setBlock_broadcasted_in(int block_broadcasted_in) {
		this.block_broadcasted_in = block_broadcasted_in;
	}


	public String getProperty_protocol() {
		return property_protocol;
	}


	public void setProperty_protocol(String property_protocol) {
		this.property_protocol = property_protocol;
	}
	
	
	
}
