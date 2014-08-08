package casimir;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

	static final Logger logger = LogManager.getLogger(Main.class.getName());

	// Done
	public static void main(String[] args) {
		VendingMachine vend = new VendingMachine();
		vend.run();

	}

}
