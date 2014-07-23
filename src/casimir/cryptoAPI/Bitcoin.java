package casimir.cryptoAPI;


public interface Bitcoin {

	
	public String getnewaddress(String label); 
	
	public String dumpPrivateKey(String address);

	public String sendRawTransaction(String rawTransactionHex);
	
}
