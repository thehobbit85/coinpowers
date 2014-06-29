package casimir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.logging.log4j.*;
import org.json.JSONObject;

import casimir.cryptoAPI.BitcoinAPI;
import casimir.cryptoAPI.MastercoinAPI;
import casimir.dataBase.*;
import casimir.dataBase.objects.*;

//import mastercoinVend.dataBase.DatabaseAdapter;

public class VendingMachine implements Runnable {

	static final Logger logger = LogManager.getLogger(VendingMachine.class
			.getName());
	private boolean isActive = false;
	private BitcoinAPI bitcoinAPI;
	private double currentBitcoinBlock;
	private long sleepTime;
	private Block lastBlockHandled;
	private int lastBlockHandledHeight;
	private double numberOfMinConfirmation;
	private Properties p;
	private int returnableFee;

	/**
	 * 
	 */
	public VendingMachine() {

		super();
		logger.entry();
		try {
			this.p = new Properties();
			p.loadFromXML(new FileInputStream(
					"config/vendingMachineSettings.xml"));

			this.sleepTime = Long.parseLong(p.getProperty("sleepTime"));
			this.lastBlockHandled = DatabaseAdapter.getLastBlockHandeled();
			this.numberOfMinConfirmation = Double.parseDouble(p
					.getProperty("numberOfMinConfirmation"));
			this.lastBlockHandledHeight = Integer.parseInt(p
					.getProperty("lastBlockHandledHeight"));
			this.returnableFee = Integer.parseInt(p
					.getProperty("returnableFee"));

		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Unable to load properties" + e);
		}
		logger.exit(this);

	}

	// Done
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.entry();
		isActive = true;
		try {
			bitcoinAPI = new BitcoinAPI();
			while (isActive) {

				addTransactionsToDatabase(); // Adds transaction that got recived while sleeping
				broadcastAssets(); // Broadcast new Unbroadcasted assets that has been set to go live while sleeping

				this.currentBitcoinBlock = Double.parseDouble(bitcoinAPI
						.getblockcount());
				while (this.lastBlockHandledHeight < this.currentBitcoinBlock
						- this.numberOfMinConfirmation
						&& isActive) {
					processBlock(this.lastBlockHandledHeight + 1);
					this.lastBlockHandledHeight = this.lastBlockHandled
							.getHeight();
				}
				Thread.sleep(sleepTime);
			}
		} catch (InterruptedException e) {

			logger.catching(e);
			System.out
					.println("The vending mechine has stoped working with error: "
							+ e.toString());
		}
		logger.exit();
	}

	// Done
	private void processBlock(int blockNumber) {
		logger.entry(blockNumber);
		try {

			this.p.setProperty("lastBlockHandledHeight",
					Integer.toString(blockNumber));
			this.p.storeToXML(new FileOutputStream(
					"config/vendingMachineSettings.xml"), null);

			String blockJsonString = this.bitcoinAPI.getblock(this.bitcoinAPI
					.getblockhash(blockNumber));
			JSONObject blockJson = new JSONObject(blockJsonString);
			Block processedBlock = new Block(blockNumber, blockJson.get("hash")
					.toString(), Integer.parseInt(blockJson.get("time")
					.toString()), "proccesed");

			processWaitingTransactions(blockNumber);

			this.lastBlockHandled = processedBlock;
			DatabaseAdapter.addBlock(this.lastBlockHandled);
			// bitcoinAPI.
			// String newTransaction =
			// JsonUtil.createSignAndSendTransactionFile("transaction_from","transaction_to"
			// , currency_id, send_amt, property_type, "from_private_key");
			// MastercoinAPI.createTransaction(newTransaction);

		} catch (Exception e) {
			logger.catching(e);
			System.out.println("Unable to load properties" + e);
		}
		logger.exit();
	}

	// Done
	private void processWaitingTransactions(int blockNumber) {
		Vector<Transaction> transactionsToProcess = DatabaseAdapter
				.getTransactionReadyForProcess(blockNumber);
		for (int i = 0; i < transactionsToProcess.size(); i++) {
			processTransaction(transactionsToProcess.get(i));
		}
	}

	// Done
	private void processTransaction(Transaction transaction) {

		logger.entry(transaction.getTxHash());

		try {
			Asset asset = DatabaseAdapter.getAssetByName(transaction
					.getAssetName());

			float property_id = asset.getProperty_id();

			if (property_id != -1) {

				String transaction_from = transaction.getAssetAddress();
				String transaction_to = transaction.getInputAddress();
				float send_amt = transaction.getAmountOfShares();
				String transactionFileName = JsonUtil.createTransaction(
						transaction_from, transaction_to, property_id,
						send_amt,
						this.bitcoinAPI.dumpPrivateKey(transaction_from));

				this.bitcoinAPI.openWallet();
				String rawTrasactionHex = MastercoinAPI
						.createTransaction(transactionFileName);
				this.bitcoinAPI.closeWallet();
				String result = this.bitcoinAPI
						.sendRawTransaction(rawTrasactionHex);

				if (result != null && result.length() == 64) {
					DatabaseAdapter.updateTransactionRawHash(
							transaction.getTxHash(), rawTrasactionHex);
					DatabaseAdapter.updateTransactionStatus(
							transaction.getTxHash(), "sent");
					logger.exit("Sent");
				} else {
					logger.exit("Problam with sending, will try again later");
				}
			} else {
				logger.exit("No currency ID");
			}

		} catch (Exception e) {
			logger.catching(e);
		}

	}

	// Done
	private void addTransactionsToDatabase() {
		String trackedAddresses[] = DatabaseAdapter.getTrackedAssetsAddresses();
		if (trackedAddresses != null) {
			for (int i = 0; i < trackedAddresses.length; i++) {

				Cheat.addTransactionsToDatabase(trackedAddresses[i],
						this.returnableFee);

			}
		}
	}

	// Done
	private void broadcastAssets() {
		logger.entry();
		String[] assetsToBroadcast = DatabaseAdapter.getAssetsToBroadcast();
		if (assetsToBroadcast != null) {
			for (int i = 0; i < assetsToBroadcast.length; i++) {
				Asset asset = DatabaseAdapter.getAssetByName(assetsToBroadcast[i]);

				if (asset.getProperty_protocol().equals("MSC")) {
					 broadcastNewMasterCoinAsset(asset);
					 logger.info(asset.getProperty_name());
				}
			}
		}
		logger.exit();
	}

	// Done
	private void broadcastNewMasterCoinAsset(Asset asset) {
			String property_name = asset.getProperty_name();
			logger.entry(property_name);
		
			File f = new File("assets/" + property_name + ".json");
			if (!f.exists() || f.isDirectory()) {
				String transaction_from = asset.getTransaction_from();
				String property_category = asset.getProperty_category();
				String property_subcategory = asset.getProperty_subcategory();
				String property_url = asset.getProperty_url();
				String property_data = asset.getProperty_data();
				int number_properties = asset.getNumber_properties();
				String from_private_key = this.bitcoinAPI
						.dumpPrivateKey(transaction_from);
				try {
					JsonUtil.createNewAssetJson(property_category,
							property_subcategory, property_name, property_url,
							property_data, number_properties, transaction_from,
							from_private_key);
					logger.info("Created JSON");

				} catch (Exception e) {
					
					logger.catching(e);
				}
			}

			f = new File("assets/" + property_name + ".json");
			if (f.exists() && f.isDirectory() && asset != null
					&& asset.getBlock_broadcasted_in() != -1) {
				this.bitcoinAPI.openWallet();
				String rawTrasactionHex = MastercoinAPI
						.getAssetRawTransactionHex(property_name);
				this.bitcoinAPI.closeWallet();
				String returnAnswer = this.bitcoinAPI
						.sendRawTransaction(rawTrasactionHex);
				if (returnAnswer != "") {

					DatabaseAdapter.startTrackingAsset(asset);
					DatabaseAdapter.broadcastAsset(property_name);
					DatabaseAdapter.setBlockBroadcastedIn(property_name,
							Integer.parseInt(this.bitcoinAPI.getblockcount()));
					logger.exit(returnAnswer);
					
				} 
			} else {
				logger.exit("Already Broadcasted this Asset or JSON Corupted/Dons't exist");
			}
		}
	
	// Done
	public void stop() {
		logger.entry();
		if (isActive)
			isActive = false;

		logger.exit();
	}

}
