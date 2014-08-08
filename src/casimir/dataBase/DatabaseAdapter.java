package casimir.dataBase;

import java.sql.*;
import java.util.Vector;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import casimir.dataBase.objects.*;

public class DatabaseAdapter {

	static final String[] neededTables = { "BLOCKS", "TRANSACTIONS", "ASSETS" };

	static final Logger logger = LogManager.getLogger(DatabaseAdapter.class
			.getName());

	public static ResultSet main(String[] args) throws SQLException,
			ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager
				.getConnection("jdbc:sqlserver://HOSP_SQL1.company.com;user=name;password=abcdefg;database=Test");
		
		Statement sta = conn.createStatement();
		String Sql = "select * from testing_table";
		return sta.executeQuery(Sql);

	}

	// ///////////////////////////
	// /////////INSERTS///////////
	// ///////////////////////////

	// Done
	/**
	 * @param block
	 */
	public static void addBlock(Block block) {
		logger.entry(block.getHeight());
		Block test = DatabaseAdapter.getBlock(block.getHeight());
		if (test == null) {

			Connection c = null;
			Statement stmt = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager
						.getConnection("jdbc:sqlite:database/vender.db");
				c.setAutoCommit(false);
				logger.info("Opened database successfully");

				stmt = c.createStatement();

				String sql = "INSERT INTO BLOCKS (height,status,time,hash) "
						+ "VALUES (" + block.getHeight() + ", '"
						+ block.getStatus() + "', " + block.getTime() + ", '"
						+ block.getHash() + "');";
				stmt.executeUpdate(sql);

				logger.exit("Block number: " + block.getHeight()
						+ " Has been entered to data base");
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": "
						+ e.getMessage());
				System.exit(0);
			}
			logger.info("Records created successfully");
		} else {
			logger.exit("Block:" + block.getHeight()
					+ " already exist in database");
		}
	}

	// Done
	/**
	 * @param transaction
	 */
	public static void addTransaction(Transaction transaction) {

		logger.entry(transaction.getTxHash());

		Transaction test = DatabaseAdapter.getTransaction(transaction
				.getTxHash());
		if (test == null) {

			Connection c = null;
			Statement stmt = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager
						.getConnection("jdbc:sqlite:database/vender.db");
				c.setAutoCommit(false);
				logger.info("Opened database successfully");

				stmt = c.createStatement();

				String sql = "INSERT INTO TRANSACTIONS (txHash,inputAddress,assetAddress,assetName"
						+ ",totalAmountInBitcoins,blockNumber,amountOfShares,rawTxHash,status) "
						+ "VALUES ('"
						+ transaction.getTxHash()
						+ "', '"
						+ transaction.getInputAddress()
						+ "','"
						+ transaction.getAssetAddress()
						+ "', '"
						+ transaction.getAssetName()
						+ "',"
						+ transaction.getTotalAmountInBitcoins()
						+ ","
						+ transaction.getBlockNumber()
						+ ","
						+ transaction.getAmountOfShares()
						+ ""
						+ ",'"
						+ transaction.getRawTxHash()
						+ "','"
						+ transaction.getStatus() + "');";
				stmt.executeUpdate(sql);

				logger.exit("Transaction with raw hash: "
						+ transaction.getTxHash()
						+ " Has been entered to data base");
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": "
						+ e.getMessage());
				System.exit(0);
			}
			logger.info("Records created successfully");
		} else {
			logger.exit("Transaction with raw hash: " + transaction.getTxHash()
					+ " already exist in database");
		}
	}

	// Done
	/**
	 * @param asset
	 */
	public static void addAsset(Asset asset) {
		logger.entry(asset);
		Asset test = DatabaseAdapter.getAssetByAddress(asset
				.getTransaction_from());
		if (test == null) {

			Connection c = null;
			Statement stmt = null;
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager
						.getConnection("jdbc:sqlite:database/vender.db");
				logger.info("Opened database successfully");
				c.setAutoCommit(false);
				stmt = c.createStatement();

				/**
				 * Change when testing is done String sqlFutureMasterCoin =
				 * "INSERT INTO ASSETS (transaction_from,transaction_type,ecosystem"
				 * +
				 * ",property_type,previous_property_id,property_category,property_subcategory"
				 * +
				 * ",property_name,property_url,property_data,number_properties,price_in_satoshi"
				 * +
				 * ",tracking_status,property_id,broadcast_status,block_broadcasted_in,property_protocol)"
				 * + "VALUES ('" + asset.getTransaction_from() + "', '" +
				 * asset.getTransaction_type() + "', " + asset.getEcosystem() +
				 * ", " + asset.getProperty_type() + ", " +
				 * asset.getPrevious_property_id() + ", '" +
				 * asset.getProperty_category() + "', '" +
				 * asset.getProperty_subcategory() + "', '" +
				 * asset.getProperty_name() + "', '" + asset.getProperty_url() +
				 * "', '" + asset.getProperty_data() + "', " +
				 * asset.getNumber_properties() + ", " +
				 * asset.getPrice_in_satoshi() + ", '" +
				 * asset.getTracking_status() + "', " + asset.getProperty_id() +
				 * ", " + asset.getBroadcast_status() + "," +
				 * asset.getBlock_broadcasted_in() + ",'" +
				 * asset.getProperty_protocol() + "');";
				 **/
				String sql = "INSERT INTO ASSETS (transaction_from,transaction_type,ecosystem"
						+ ",property_type,previous_property_id,property_category,property_subcategory"
						+ ",property_name,property_url,property_data,number_properties,price_in_satoshi"
						+ ",tracking_status,property_id,broadcast_status,block_broadcasted_in)"
						+ "VALUES ('"
						+ asset.getTransaction_from()
						+ "', '"
						+ asset.getTransaction_type()
						+ "', "
						+ asset.getEcosystem()
						+ ", "
						+ asset.getProperty_type()
						+ ", "
						+ asset.getPrevious_property_id()
						+ ", '"
						+ asset.getProperty_category()
						+ "', '"
						+ asset.getProperty_subcategory()
						+ "', '"
						+ asset.getProperty_name()
						+ "', '"
						+ asset.getProperty_url()
						+ "', '"
						+ asset.getProperty_data()
						+ "', "
						+ asset.getNumber_properties()
						+ ", "
						+ asset.getPrice_in_satoshi()
						+ ", '"
						+ asset.getTracking_status()
						+ "', "
						+ asset.getProperty_id()
						+ ", "
						+ asset.getBroadcast_status()
						+ ","
						+ asset.getBlock_broadcasted_in() + ");";

				stmt.executeUpdate(sql);

				logger.exit("Asset: " + asset.getProperty_name()
						+ " Has been added to database");
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": "
						+ e.getMessage());
				System.exit(0);
			}
			logger.info("Records created successfully");
		} else {
			logger.exit("Asset: " + asset.getProperty_name()
					+ " Already exists in Database");
		}
	}

	// ///////////////////////////
	// /////////SELECTS///////////
	// ///////////////////////////

	// Done
	public static String[] getAssetsToBroadcast() {
		logger.entry();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM ASSETS WHERE broadcast_status = 1 AND block_broadcasted_in = -1;");

			Vector<String> answer = new Vector<String>();

			while (rs.next()) {
				answer.add(rs.getString("property_name"));
			}

			String[] returnAnswered = null;

			if (answer.size() > 0) {
				returnAnswered = new String[answer.size()];
				for (int i = 0; i < answer.size(); i++)
					returnAnswered[i] = answer.get(i);
			}

			rs.close();
			stmt.close();
			c.close();
			logger.exit(returnAnswered);
			return returnAnswered;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}
	}

	// Done
	/**
	 * @param txHash
	 * @return
	 */
	public static Transaction getTransaction(String txHash) {
		logger.entry(txHash);
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM TRANSACTIONS WHERE txHash = '"
							+ txHash + "';");

			Transaction answer = null;

			while (rs.next()) {
				txHash = rs.getString("txHash");
				String inputAddress = rs.getString("inputAddress");
				String assetAddress = rs.getString("assetAddress");
				String assetName = rs.getString("assetName");
				float totalAmountInBitcoins = rs
						.getFloat("totalAmountInBitcoins");
				int blockNumber = rs.getInt("blockNumber");
				int amountOfShares = rs.getInt("amountOfShares");
				String status = rs.getString("status");
				String rawTxHash = rs.getString("rawTxHash");

				answer = new Transaction(txHash, inputAddress, assetAddress,
						assetName, totalAmountInBitcoins, blockNumber,
						amountOfShares, status, rawTxHash);
			}
			rs.close();
			stmt.close();
			c.close();
			if (answer != null)
				logger.exit(txHash);
			else
				logger.exit("Not in database");
			return answer;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}
	}

	// Done
	/**
	 * @param blockHeight
	 * @return
	 */
	public static Block getBlock(int blockHeight) {
		logger.entry(blockHeight);
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM BLOCKS WHERE height = "
							+ blockHeight + ";");

			Block answer = null;

			while (rs.next()) {
				int height = rs.getInt("height");
				String status = rs.getString("status");
				int time = rs.getInt("time");
				String hash = rs.getString("hash");
				answer = new Block(height, hash, time, status);
			}
			rs.close();
			stmt.close();
			c.close();
			if (answer != null)
				logger.exit(answer.getHeight());
			else
				logger.exit("Not in database");
			return answer;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}
	}

	// Done
	/**
	 * @return
	 */
	public static Block getLastBlockHandeled() {
		logger.entry();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM BLOCKS ORDER BY height DESC");

			Block answer = null;

			if (rs.next()) {
				int height = rs.getInt("height");
				String status = rs.getString("status");
				int time = rs.getInt("time");
				String hash = rs.getString("hash");
				answer = new Block(height, hash, time, status);
			}
			rs.close();
			stmt.close();
			c.close();
			if (answer != null)
				logger.exit(answer.getHeight());
			else
				logger.exit("Not in database");
			return answer;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}

	}

	// Done - Needs Testing
	/**
	 * @param blockNumber
	 * @return
	 */
	public static Vector<Transaction> getTransactionReadyForProcess(
			int blockNumber) {
		logger.entry(blockNumber);
		Vector<Transaction> trans = new Vector<Transaction>();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM TRANSACTIONS WHERE status = 'created' AND "
							+ " blockNumber <= " + blockNumber + ";");

			Transaction answer = null;
			while (rs.next()) {
				String txHash = rs.getString("txHash");
				String inputAddress = rs.getString("inputAddress");
				String assetAddress = rs.getString("assetAddress");
				String assetName = rs.getString("assetName");
				float totalAmountInBitcoins = rs
						.getFloat("totalAmountInBitcoins");
				blockNumber = rs.getInt("blockNumber");
				int amountOfShares = rs.getInt("amountOfShares");
				String status = rs.getString("status");
				String rawTxHash = rs.getString("rawTxHash");
				answer = new Transaction(txHash, inputAddress, assetAddress,
						assetName, totalAmountInBitcoins, blockNumber,
						amountOfShares, status, rawTxHash);
				trans.add(answer);
			}
			rs.close();
			stmt.close();
			c.close();
			if (answer != null)
				logger.exit(trans.size());
			else
				logger.exit("Not in database");
			return trans;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}
	}

	// Done
	/**
	 * @param blockNumber
	 * @return
	 */
	public static Vector<Transaction> getTransactionsPerBlock(int blockNumber) {
		logger.entry(blockNumber);
		Vector<Transaction> trans = new Vector<Transaction>();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM TRANSACTIONS WHERE blockNumber = "
							+ blockNumber + ";");

			Transaction answer = null;

			while (rs.next()) {
				String txHash = rs.getString("txHash");
				String inputAddress = rs.getString("inputAddress");
				String assetAddress = rs.getString("assetAddress");
				String assetName = rs.getString("assetName");
				float totalAmountInBitcoins = rs
						.getFloat("totalAmountInBitcoins");
				blockNumber = rs.getInt("blockNumber");
				int amountOfShares = rs.getInt("amountOfShares");
				String status = rs.getString("status");
				String rawTxHash = rs.getString("rawTxHash");

				answer = new Transaction(txHash, inputAddress, assetAddress,
						assetName, totalAmountInBitcoins, blockNumber,
						amountOfShares, status, rawTxHash);
				trans.add(answer);
			}
			rs.close();
			stmt.close();
			c.close();
			if (answer != null)
				logger.exit(trans.size());
			else
				logger.exit("Not in database");
			return trans;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}
	}

	// Done
	/**
	 * @return
	 */
	public static String[] getTrackedAssetsAddresses() {

		logger.entry();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM ASSETS WHERE tracking_status = 'track';");

			Vector<String> answer = new Vector<String>();

			while (rs.next()) {
				answer.add(rs.getString("transaction_from"));
			}

			String[] returnAnswered = null;

			if (answer.size() > 0) {
				returnAnswered = new String[answer.size()];
				for (int i = 0; i < answer.size(); i++)
					returnAnswered[i] = answer.get(i);
			}

			rs.close();
			stmt.close();
			c.close();
			logger.exit(returnAnswered);
			return returnAnswered;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}
	}

	// Done
	/**
	 * @param property_name
	 * @return
	 */
	public static Asset getAssetByName(String property_name) {
		return getAsset("property_name", property_name);
	}

	// Done
	/**
	 * @param address
	 * @return
	 */
	public static Asset getAssetByAddress(String address) {
		return getAsset("transaction_from", address);
	}

	// Done
	/**
	 * @param property_name
	 * @return
	 */
	public static int checkBroadcastAssetFlag(String property_name) {
		logger.entry(property_name);
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM ASSETS WHERE property_name = '"
							+ property_name + "';");

			int broadcast_status = 0;

			while (rs.next()) {
				broadcast_status = rs.getInt("broadcast_status");
			}
			rs.close();
			stmt.close();
			c.close();

			logger.exit(broadcast_status);
			return broadcast_status;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return 0;
		}
	}

	// ///////////////////////////
	// /////////UPDATES///////////
	// ///////////////////////////
	// Done
	/**
	 * @param asset
	 */
	public static void startTrackingAsset(Asset asset) {
		logger.entry(asset.getTransaction_from());
		Asset test = getAssetByAddress(asset.getTransaction_from());
		if (test == null) {
			logger.exit("Trying to add to db");
			addAsset(asset);
		}
		changeTrackingStatus(asset.getTransaction_from(), "track");
		logger.exit("Change Track status to track");
	}

	// Done
	/**
	 * @param asset
	 */
	public static void stopTrackingAsset(Asset asset) {
		logger.entry(asset.getTransaction_from());
		Asset test = getAssetByAddress(asset.getTransaction_from());
		if (test == null) {
			logger.exit("Trying to add to db");
			addAsset(asset);
		}
		changeTrackingStatus(asset.getTransaction_from(), "idle");
		logger.exit("Change Track status to idle");

	}

	// Done
	/**
	 * @param property_name
	 * @param block_broadcasted_in
	 */
	public static void broadcastAsset(String property_name) {
		logger.entry(property_name);
		updateSetWhere("ASSETS", "broadcast_status", 1, "property_name",
				property_name);
		logger.exit("Asset: " + property_name + " is LIVE");
	}

	public static void setBlockBroadcastedIn(String property_name,
			int block_broadcasted_in) {
		logger.entry(property_name);
		updateSetWhere("ASSETS", "block_broadcasted_in", block_broadcasted_in,
				"property_name", property_name);
		logger.exit("Asset: " + property_name + " is LIVE");
	}

	// Done
	/**
	 * @param property_name
	 * @param property_id
	 */
	public static void updatePropertyId(String property_name, float property_id) {
		updateSetWhere("ASSETS", "property_id", property_id, "property_name",
				property_name);
	}

	// Done
	/**
	 * @param txHash
	 * @param status
	 */
	public static void updateTransactionStatus(String txHash, String status) {
		updateSetWhere("TRANSACTIONS", "status", status, "txHash", txHash);
	}

	// Done
	/**
	 * @param txHash
	 * @param rawTrasactionHex
	 */
	public static void updateTransactionRawHash(String txHash,
			String rawTrasactionHex) {
		updateSetWhere("TRANSACTIONS", "rawTxHash", rawTrasactionHex, "txHash",
				txHash);

	}

	// ///////////////////////////
	// /////////PRIVATES//////////
	// ///////////////////////////

	// Done
	private static void updateSetWhere(String table, String setCol,
			String setParam, String whereCol, String whereParam) {

		logger.entry(table, setCol, setParam, whereParam);
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			c.setAutoCommit(false);
			logger.info("Opened database successfully");

			stmt = c.createStatement();

			String sql = "UPDATE " + table + " SET " + setCol + " = '"
					+ setParam + "' " + " WHERE " + whereCol + " = '"
					+ whereParam + "' ";
			stmt.executeUpdate(sql);

			logger.exit(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
		}
		logger.info("Records created successfully");
	}

	// Done
	private static void updateSetWhere(String table, String setCol,
			int setParam, String whereCol, String whereParam) {

		logger.entry(table, setCol, setParam, whereParam);
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			c.setAutoCommit(false);
			logger.info("Opened database successfully");

			stmt = c.createStatement();

			String sql = "UPDATE " + table + " SET " + setCol + " = "
					+ setParam + " WHERE " + whereCol + " = '" + whereParam
					+ "' ";
			stmt.executeUpdate(sql);

			logger.exit(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
		}
		logger.info("Records created successfully");
	}

	// Done
	private static void updateSetWhere(String table, String setCol,
			float setParam, String whereCol, String whereParam) {

		logger.entry(table, setCol, setParam, whereParam);
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			c.setAutoCommit(false);
			logger.info("Opened database successfully");

			stmt = c.createStatement();

			String sql = "UPDATE " + table + " SET " + setCol + " = "
					+ setParam + " WHERE " + whereCol + " = '" + whereParam
					+ "' ";
			stmt.executeUpdate(sql);

			logger.exit(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
		}
		logger.info("Records created successfully");
	}

	// Done
	private static Asset getAsset(String where, String whereParam) {
		logger.entry(where, whereParam);
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ASSETS WHERE "
					+ where + " = '" + whereParam + "';");

			Asset answer = null;

			while (rs.next()) {

				String transaction_from = rs.getString("transaction_from");
				int transaction_type = rs.getInt("transaction_type");
				int ecosystem = rs.getInt("ecosystem");
				int property_type = rs.getInt("property_type");
				int previous_property_id = rs.getInt("previous_property_id");
				String property_category = rs.getString("property_category");
				String property_subcategory = rs
						.getString("property_subcategory");
				String property_name = rs.getString("property_name");
				String property_url = rs.getString("property_url");
				String property_data = rs.getString("property_data");
				int number_properties = rs.getInt("number_properties");
				String tracking_status = rs.getString("tracking_status");
				int price_in_satoshi = rs.getInt("price_in_satoshi");
				int broadcast_status = rs.getInt("broadcast_status");
				int property_id = rs.getInt("property_id");
				int block_broadcasted_in = rs.getInt("block_broadcasted_in");
				String property_protocol = "MSC"; // rs.getString("property_protocol");

				answer = new Asset(transaction_from, transaction_type,
						ecosystem, property_type, previous_property_id,
						property_category, property_subcategory, property_name,
						property_url, property_data, number_properties,
						price_in_satoshi, tracking_status, property_protocol);

				answer.setBroadcast_status(broadcast_status);
				answer.setProperty_id(property_id);
				answer.setBlock_broadcasted_in(block_broadcasted_in);
			}
			rs.close();
			stmt.close();
			c.close();
			if (answer != null)
				logger.exit(answer.getProperty_name());
			else
				logger.exit("Not in database");
			return answer;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
			return null;
		}
	}

	// Done
	private static void changeTrackingStatus(String address, String status) {
		updateSetWhere("ASSETS", "tracking_status", status, "transaction_from",
				address);
	}

	// ///////////////////////////
	// /////////OTHERS////////////
	// ///////////////////////////

	// Done
	/**
	 * 
	 */
	public static void init() {
		logger.entry();
		Connection c = null;
		Statement stmt = null;

		Vector<String> neededTablesVector = new Vector<String>();
		for (int i = 0; i < neededTables.length; i++) {
			neededTablesVector.add(neededTables[i]);
		}

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database/vender.db");
			logger.info("Opened database successfully");
			// c.setAutoCommit(false);
			stmt = c.createStatement();
			DatabaseMetaData meta = c.getMetaData();
			ResultSet rs = meta.getTables(null, null, null,
					new String[] { "TABLE" });

			Vector<String> tables = new Vector<String>();

			while (rs.next()) {
				tables.add(rs.getString("TABLE_NAME"));
			}

			for (int i = 0; i < neededTablesVector.size(); i++) {
				for (int j = 0; j < tables.size(); j++) {
					if (neededTablesVector.get(i).equals(tables.get(j))) {
						neededTablesVector.remove(i);
						i = -1;
						j = tables.size();
					}
				}
			}

			for (int i = 0; i < neededTablesVector.size(); i++) {
				if (neededTablesVector.get(i).equals("BLOCKS")) {
					String sql = "CREATE TABLE BLOCKS "
							+ "(height INT PRIMARY KEY     NOT NULL,"
							+ " status           CHAR(50)    NOT NULL, "
							+ " time            INT     NOT NULL, "
							+ " hash        CHAR(50)    NOT NULL)";
					stmt.executeUpdate(sql);

					// sql = "INSERT INTO BLOCKS (height,status,time,hash) "
					// +
					// "VALUES (300000, 'processed', 1399703554, '000000000000000082ccf8f1557c5d40b21edabb18d2d691cfbf87118bac7254');";
					// stmt.executeUpdate(sql);

				} else if (neededTablesVector.get(i).equals("TRANSACTIONS")) {
					String sql = "CREATE TABLE TRANSACTIONS "
							+ "(txHash CHAR(50) PRIMARY KEY     NOT NULL,"
							+ " inputAddress           CHAR(50)    NOT NULL, "
							+ " assetAddress            CHAR(50)     NOT NULL, "
							+ " assetName            CHAR(50)     NOT NULL, "
							+ " totalAmountInBitcoins            float     NOT NULL, "
							+ " blockNumber            INT     NOT NULL, "
							+ " amountOfShares            INT     NOT NULL,"
							+ " rawTxHash  CHAR(50)    NOT NULL,"
							+ " status  CHAR(50)    NOT NULL)";
					stmt.executeUpdate(sql);
				} else if (neededTablesVector.get(i).equals("ASSETS")) {
					String sql = "CREATE TABLE ASSETS "
							+ "(transaction_from CHAR(50) PRIMARY KEY NOT NULL,"
							+ " transaction_type          INT  NOT NULL, "
							+ " ecosystem          INT   NOT NULL, "
							+ " property_type          INT   NOT NULL, "
							+ " previous_property_id           INT    NOT NULL, "
							+ " property_category           CHAR(50)    NOT NULL, "
							+ " property_subcategory           CHAR(50)    NOT NULL, "
							+ " property_name           CHAR(50)    NOT NULL, "
							+ " property_url           CHAR(50)    NOT NULL, "
							+ " property_data           CHAR(50)    NOT NULL, "
							+ " number_properties            INT     NOT NULL,"
							+ " tracking_status            CHAR(50)     NOT NULL,"
							+ " price_in_satoshi       INT    NOT NULL,"
							+ " property_id       float    NOT NULL,"
							+ " broadcast_status       INT     NOT NULL,"
							+ " block_broadcasted_in INT NOT NULL,"
							+ " property_protocol CHAR(50) NOT NULL)";
					stmt.executeUpdate(sql);
				}
			}

			stmt.close();
			c.close();
			logger.exit("Tables created successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			logger.catching(e);
			System.exit(0);
		}
	}
}
