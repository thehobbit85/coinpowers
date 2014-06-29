package casimir.userInterface;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import casimir.Controller;

public class CommandLineInterface {
	static final Logger logger = LogManager
			.getLogger(CommandLineInterface.class.getName());

	// Done
	/**
	 * @param controller
	 */
	public static void mainMenu(Controller controller) {
		logger.entry();
		int menu = 0;
		boolean first = true;
		while (menu != 6) {
			if (first)
				first = false;
			else
				System.out.println();
			System.out.println("       Mastercoin Automated Vending Machine (MAVM)   ");
			System.out.println("-----------------------------------------------------");
			System.out.println("1. Create new Asset Json file - Will only create the file, still needs to be broadcasted.");
			System.out.println("2. Broadcast new Asset to the network - Make sure the asset has atleast 0.5mB on it's address and that Id is set.");
			System.out.println("3. Stop/Start MAVM - Should be notice that by default MAVM is on on start up.");
			System.out.println("4. Manualy send assets - Bypass the MAVM compeletly and send manualy.");
			System.out.println("5. Update property Id - Mandatory before broadcasting Asset, Can get Id from omniwallet.");
			System.out.println("6. Exit.");
			System.out.println("-----------------------------------------------------");
			System.out.print("Enter your choice: ");
			Scanner sc = new Scanner(System.in);
			try {
				menu = sc.nextInt();
				switch (menu) {
				case 1:
					createNewAsset(controller);
					break;
				case 2:
					broadcastNewAsset(controller);
					break;
				case 3:
					startStopVendingMachine(controller);
					break;
				case 4:
					manualSend(controller);
					break;
				case 5:
					updatePropertyId(controller);
					break;
				case 6:
					
					shutDown(controller);
					break;

				default:
					System.out.println("Wrong input, choose between 1-5");
					break;
				}
			} catch (Exception e) {
				logger.catching(e);
				System.out.println("Wrong input, Enter a number "
						+ e.toString());

			}

		}
		logger.exit();
	}

	// Done
	private static void createNewAsset(Controller controller) {
		logger.entry();
		Scanner sc = new Scanner(System.in);
		try {
			
			System.out.print("Enter the property category: ");
			String property_category = sc.next();
			System.out.print("Enter the property subcategory: ");
			String property_subcategory = sc.next();
			System.out.print("Enter the property name: ");
			String property_name = sc.next();
			System.out.print("Enter the property url: ");
			String property_url = sc.next();
			System.out.print("Enter the property data: ");
			String property_data = sc.next();
			System.out.print("Enter number of properties to create: ");
			String number_properties = sc.next();
			System.out.print("Enter price in satoshi of your asset: ");
			String price_in_satoshi = sc.next();
			
			/**
			// ////////////Creating Tons of new Properties///////////
			
			  for (int i = 101; i < 1000; i++) { String a = "Zend" + i;
			  
			  
			  String property_category = a; String property_subcategory = a;
			  String property_name = a; String property_url = a; String
			  property_data = a; String number_properties =
			  Integer.toString(100 + (int)(Math.random() * ((100000 - 100) +
			  1))); String price_in_satoshi = Integer.toString(100 +
			  (int)(Math.random() * ((100000 - 100) + 1)));
			 
			// ///////////////////////////////////////////////////////
			**/
			String newAssetAddress = controller.createNewAsset(
					property_category, property_subcategory, property_name,
					property_url, property_data,
					Integer.parseInt(number_properties),
					Integer.parseInt(price_in_satoshi));

			if (newAssetAddress != "") {
				logger.exit(newAssetAddress);
				System.out.println("Send at least 0.5 mBit to the address: "
						+ newAssetAddress);
				System.out
						.println("And wait for 6 confirmations before attempting to broadcast this asset");
			} else {
				logger.exit("");
				System.out.println("There was an error in creating the Asset");
			}
			  

		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Input error");

		}

	}

	// Done
	private static void broadcastNewAsset(Controller controller) {
		System.out
				.print("Enter the json configuration file name without the .json: ");
		Scanner sc = new Scanner(System.in);

		try {
			String jsonConfigFile = sc.next();
			controller.broadcastNewAsset(jsonConfigFile);
			logger.exit();
		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Input error");

		}

	}

	// Done
	private static void startStopVendingMachine(Controller controller) {
		controller.startStopVendingMachine();
	}

	private static void shutDown(Controller controller) {
		controller.shutDown();
	}

	// Done - Needs testing
	private static void manualSend(Controller controller) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Enter the property name: ");
			String name = sc.next();
			System.out.print("Enter the destination address: ");
			String destination = sc.next();
			System.out.print("Enter the amount: ");
			double amount = sc.nextDouble();

			controller.manualSend(name, destination, amount);
		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Input error");
		}
	}
	
	private static void updatePropertyId(Controller controller) {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("Enter the property name: ");
			String name = sc.next();
			System.out.print("Enter the property id: ");
			float property_id = sc.nextFloat();

			controller.updatePropertyId(name, property_id);
		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Input error");
		}
		
		
	}
	
}
