package casimir.userInterface;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import casimir.Controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiInterface {

	// Note: Typically the main method will be in a
	// separate class. As this is a simple one class
	// example it's all in the one class.
	/**
	 * @param controller
	 */
	public static void mainMenu(Controller controller) {
		
		new GuiInterface(controller);
	}

	public GuiInterface(Controller controller) {

		JFrame guiFrame = new JFrame();
		
		// make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Example GUI");
		guiFrame.setSize(800, 600);

		// This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);

		// Options for the JComboBox
		String[] fruitOptions = { "Apple", "Apricot", "Banana", "Cherry",
				"Date", "Kiwi", "Orange", "Pear", "Strawberry" };

		// Options for the JList
		String[] vegOptions = { "Asparagus", "Beans", "Broccoli", "Cabbage",
				"Carrot", "Celery", "Cucumber", "Leek", "Mushroom", "Pepper",
				"Radish", "Shallot", "Spinach", "Swede", "Turnip" };

		// The first JPanel contains a JLabel and JCombobox
		final JPanel comboPanel = new JPanel();
		JLabel comboLbl = new JLabel("Fruits:");
		JComboBox fruits = new JComboBox(fruitOptions);

		comboPanel.add(comboLbl);
		comboPanel.add(fruits);

		// Create the second JPanel. Add a JLabel and JList and
		// make use the JPanel is not visible.
		final JPanel listPanel = new JPanel();
		listPanel.setVisible(false);
		JLabel listLbl = new JLabel("Vegetables:");
		JList vegs = new JList(vegOptions);
		vegs.setLayoutOrientation(JList.HORIZONTAL_WRAP);

		listPanel.add(listLbl);
		listPanel.add(vegs);

		JButton newAssetBut = new JButton("Create new Asset");
		JButton broadcastBut = new JButton("Broadcast asset to network");
		JButton statusBut = new JButton(" Start/Stop vending machine");
		JButton manualSendBut = new JButton("Manualy send assets");
		JButton exitBut = new JButton("Exit");

		// The ActionListener class is used to handle the
		// event that happens when the user clicks the button.
		// As there is not a lot that needs to happen we can
		// define an anonymous inner class to make the code simpler.
		newAssetBut.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent event) {
				// When the fruit of veg button is pressed
				// the setVisible value of the listPanel and
				// comboPanel is switched from true to
				// value or vice versa.
				listPanel.setVisible(!listPanel.isVisible());
				comboPanel.setVisible(!comboPanel.isVisible());

			}
		});
		broadcastBut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {

			}
		});
		statusBut.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent event) {

			}
		});
		manualSendBut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {

			}
		});
		exitBut.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent event) {

			}
		});

		// The JFrame uses the BorderLayout layout manager.
		// Put the two JPanels and JButton in different areas.

		guiFrame.setLayout(new BoxLayout(guiFrame.getContentPane(),
				BoxLayout.Y_AXIS));

		

		addAButton(newAssetBut, guiFrame);
		addAButton(broadcastBut, guiFrame);
		addAButton(statusBut, guiFrame);
		addAButton(manualSendBut, guiFrame);
		addAButton(exitBut, guiFrame);

		guiFrame.add(comboPanel);
		guiFrame.add(listPanel);
		// make sure the JFrame is visible
		guiFrame.setVisible(true);

	}

	private static void addAButton(JButton button, Container container) {
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		container.add(button);
	}
	
	/**
	private static void createNewAsset(Container container,Controller controller ) {
		
	}
		
	private static void broadcastNewAsset(Container container,Controller controller) {
		
	}
	
	private static void startStopVendingMachine(Container container,Controller controller) {
		
	}	
	
	private static void shutDown(Container container,Controller controller) {
		
	}
	
	private static void manualSend(Container container,Controller controller) {
		
	}
	**/


}