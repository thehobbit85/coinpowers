package casimir;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import casimir.cryptoAPI.BitcoinAPI;
import casimir.cryptoAPI.MastercoinAPI;
import casimir.dataBase.DatabaseAdapter;
import casimir.dataBase.objects.Asset;

public class Controller {
	static final Logger logger = LogManager.getLogger(Controller.class
			.getName());

	private boolean active = false;
	private BitcoinAPI bitcoinAPI;
	private VendingMachine vend;

	/**
	 * 
	 */
	public Controller() {

		super();
		logger.entry();
		DatabaseAdapter.init();
		this.bitcoinAPI = new BitcoinAPI();
		logger.exit(this);
	}

	// Done
	/**
	 * @param property_category
	 * @param property_subcategory
	 * @param property_name
	 * @param property_url
	 * @param property_data
	 * @param number_properties
	 * @param price_in_satoshi
	 * @return
	 */
	public String createNewAsset(String property_category,
			String property_subcategory, String property_name,
			String property_url, String property_data, int number_properties,
			int price_in_satoshi) {
		logger.entry();
		if (DatabaseAdapter.getAssetByName(property_name) == null) {
			try {
				String transaction_from = this.bitcoinAPI
						.getnewaddress(property_name);
				if (transaction_from != "") {

					String from_private_key = this.bitcoinAPI
							.dumpPrivateKey(transaction_from);
					JsonUtil.createNewAssetJson(property_category,
							property_subcategory, property_name, property_url,
							property_data, number_properties, transaction_from,
							from_private_key);

					Asset newAsset = new Asset(transaction_from, 50, 1, 2, 0,
							property_category, property_subcategory,
							property_name, property_url, property_data,
							number_properties, price_in_satoshi,"idle","MSC");
					DatabaseAdapter.addAsset(newAsset);

					logger.exit(transaction_from);

					return transaction_from;
				} else {
					logger.exit("There was an error creating the new Asset");
					return "";
				}

			} catch (Exception e) {
				logger.catching(e);
				System.out.println("Input error");
				logger.exit("");
				return "";
			}
		} else {
			logger.exit("Asset: " + property_name + " already Exists");
			return "";
		}
	}

	// Done
	/**
	 * @param jsonConfigFile
	 * @return
	 */
	public void broadcastNewAsset(String assetName) {
		logger.entry(assetName);
		try {
			Asset asset = DatabaseAdapter.getAssetByName(assetName);
			
			if (asset != null && asset.getBlock_broadcasted_in() == -1) {
				this.bitcoinAPI.openWallet();
				String rawTrasactionHex = MastercoinAPI
						.getAssetRawTransactionHex(assetName);
				this.bitcoinAPI.closeWallet();
				String returnAnswer = this.bitcoinAPI
						.sendRawTransaction(rawTrasactionHex);

				DatabaseAdapter.startTrackingAsset(asset);
				DatabaseAdapter.broadcastAsset(assetName);
				DatabaseAdapter.setBlockBroadcastedIn(assetName,Integer.parseInt(this.bitcoinAPI.getblockcount()));
				logger.exit(returnAnswer);
			}
			else {
				logger.exit("Already Broadcasted this Asset");
			}
		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Input error");
		}
	}

	// Done
	/**
	 * 
	 */
	public void startStopVendingMachine() {
		if (this.active) {
			this.active = false;
			this.vend.stop();
		}
		else {
			if (this.vend == null)
				this.vend = new VendingMachine();
			Thread newVendThread = new Thread(this.vend);
			newVendThread.start();
		}
	}

	// Done
	/**
	 * 
	 */
	public void shutDown() {
		if (this.vend != null) {
			this.vend.stop();
		}
	}

	// Done - Needs testing
	/**
	 * @param name
	 * @param destination
	 * @param amount
	 */
	public void manualSend(String name, String destination, double amount) {
		logger.entry();
		try {
			Asset asset = DatabaseAdapter.getAssetByName(name);

			float property_id = asset.getProperty_id();

			if (property_id != -1) {
				String transaction_from = asset.getTransaction_from();
				String transactionFileName = JsonUtil.createTransaction(
						transaction_from, destination, property_id, amount,
						this.bitcoinAPI.dumpPrivateKey(transaction_from));

				this.bitcoinAPI.openWallet();
				String rawTrasactionHex = MastercoinAPI
						.createTransaction(transactionFileName);
				this.bitcoinAPI.closeWallet();

				this.bitcoinAPI.sendRawTransaction(rawTrasactionHex);

				logger.exit("Sent");
			} else {
				logger.exit("No currency ID");
			}
		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Input error");
		}

	}

	/**
	 * @param property_name
	 * @param property_id
	 */
	public void updatePropertyId(String property_name, float property_id) {
		DatabaseAdapter.updatePropertyId(property_name, property_id);
	}

}
