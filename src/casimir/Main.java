package casimir;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import casimir.userInterface.CommandLineInterface;
import casimir.userInterface.GuiInterface;

public class Main {

	static final Logger logger = LogManager.getLogger(Main.class.getName());

	// Done
	public static void main(String[] args) {
		runProgram(args);
	}
	
	private static void runProgram(String[] args) {
		logger.entry();
		Controller controller = new Controller();
		if (args.length > 0) {
			String runningMode = args[0];
			if (runningMode != null) {
				int runningModeNumber = Integer.parseInt(runningMode);
				switch (runningModeNumber) {
				case 1:
					// Start a self running vending machine with no java interface
					VendingMachine vend = new VendingMachine();
					vend.run();
					break;
				case 2:
					// Start the command line interface of the app
					CommandLineInterface.mainMenu(controller);
					break;
				case 3:
					// Start the GUI interface of the app
					GuiInterface.mainMenu(controller);
					break;
				default:
					System.out.println("Choose a number between 1-3");
					break;
				}
			} else {
				System.out.println("Choose a number between 1-3");
			}
		} else {
			System.out.println("            Needs to run with parameter: #number             ");
			System.out.println("-------------------------------------------------------------");
			System.out.println("1.Start a self running vending machine with no java interface");
			System.out.println("2.Start the command line interface of the app");
			System.out.println("2.Start the GUI interface of the app");
		}
		logger.exit();
	}
}
