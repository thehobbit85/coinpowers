package casimir;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bitsofproof.supernode.common.ECPublicKey;
import com.bitsofproof.supernode.common.ExtendedKey;
import com.bitsofproof.supernode.common.ValidationException;




public class Main {

	static final Logger logger = LogManager.getLogger(Main.class.getName());
	static String pubKey = "03adeac97886ec6a0cb23d66b4e293b97516d3bc9b25e861cca4f9d254345e8b42";
	// Done
	public static void main(String[] args) throws ValidationException {
		
	
		ECPublicKey key = new ECPublicKey(pubKey.getBytes(),true);
		System.out.println(key.toString());
		
		String temp = "";
		byte[] chainCode = temp.getBytes();
		
		ExtendedKey bip32key = new ExtendedKey(key,chainCode,0,0,0);
		System.out.println(bip32key.toString());
		
		ExtendedKey child = bip32key.getChild(0);
		System.out.println(child.toString());
		
		//VendingMachine vend = new VendingMachine();
		//vend.run();

	}

}
