package casimir;

import org.json.JSONException;

import casimir.cryptoAPI.Chain;

public class PaymentProccesor {

	private String masterPublicKey = "";
	private Chain chain;
	
	public PaymentProccesor(String masterPublicKey) {
		this.masterPublicKey = masterPublicKey;
		this.chain = Chain.getChain();
	}
	
	public boolean checkPayemnt(String publicKey, int amount) {
		String childAddress = deriveChild(masterPublicKey, publicKey, 0);
		int balance = addressBalance(childAddress);
		
		if (balance >= amount)
			return true;
		return false;
	}
	
	private int addressBalance(String address) {
		try {
			return Integer.parseInt(this.chain.getAddress(address).get("balance").toString());
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return 0;
	}
	
	private String deriveChild(String master, String publicKey, int num) {
		return publicKey;
	}
}
