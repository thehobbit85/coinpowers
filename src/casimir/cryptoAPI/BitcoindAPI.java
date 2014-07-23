/**
# Now, create a transaction that will spend that multisig transaction. First, I need the txid
# of the transaction I just created, so:
./bitcoind decoderawtransaction 010000000189632848f99722915727c5c75da8db2dbf194342a0429828f66ff88fab2af7d6000000008b483045022100abbc8a73fe2054480bda3f3281da2d0c51e2841391abd4c09f4f908a2034c18d02205bc9e4d68eafb918f3e9662338647a4419c0de1a650ab8983f1d216e2a31d8e30141046f55d7adeff6011c7eac294fe540c57830be80e9355c83869c9260a4b8bf4767a66bacbd70b804dc63d5beeb14180292ad7f3b083372b1d02d7a37dd97ff5c9effffffff0140420f000000000017a914f815b036d9bbbce5e9f2a00abd1bf3dc91e955108700000000
 
{
    "txid" : "3c9018e8d5615c306d72397f8f5eef44308c98fb576a88e030c25456b4f3a7ac",
    ... etc, rest omitted to make this shorter
}
 
# Create the spend-from-multisig transaction. Since the fund-the-multisig transaction
# hasn't been sent yet, I need to give txid, scriptPubKey and redeemScript:
./bitcoind createrawtransaction '[{"txid":"3c9018e8d5615c306d72397f8f5eef44308c98fb576a88e030c25456b4f3a7ac","vout":0,"scriptPubKey":"a914f815b036d9bbbce5e9f2a00abd1bf3dc91e9551087","redeemScript":"52410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353ae"}]' '{"1GtpSrGhRGY5kkrNz4RykoqRQoJuG2L6DS":0.01}'
 
0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c0000000000ffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000
 
# ... Now I can partially sign it using one private key:
./bitcoind signrawtransaction '0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c0000000000ffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000' '[{"txid":"3c9018e8d5615c306d72397f8f5eef44308c98fb576a88e030c25456b4f3a7ac","vout":0,"scriptPubKey":"a914f815b036d9bbbce5e9f2a00abd1bf3dc91e9551087","redeemScript":"52410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353ae"}]' '["5JaTXbAUmfPYZFRwrYaALK48fN6sFJp4rHqq2QSXs8ucfpE4yQU"]'
 
{
    "hex" : "0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c00000000fd15010048304502200187af928e9d155c4b1ac9c1c9118153239aba76774f775d7c1f9c3e106ff33c0221008822b0f658edec22274d0b6ae9de10ebf2da06b1bbdaaba4e50eb078f39e3d78014cc952410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353aeffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000",
    "complete" : false
}
 
# ... and then take the "hex" from that and complete the 2-of-3 signatures using one of
# the other private keys (note the "hex" result getting longer):
./bitcoind signrawtransaction '0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c00000000fd15010048304502200187af928e9d155c4b1ac9c1c9118153239aba76774f775d7c1f9c3e106ff33c0221008822b0f658edec22274d0b6ae9de10ebf2da06b1bbdaaba4e50eb078f39e3d78014cc952410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353aeffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000' '[{"txid":"3c9018e8d5615c306d72397f8f5eef44308c98fb576a88e030c25456b4f3a7ac","vout":0,"scriptPubKey":"a914f815b036d9bbbce5e9f2a00abd1bf3dc91e9551087","redeemScript":"52410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353ae"}]' '["5JFjmGo5Fww9p8gvx48qBYDJNAzR9pmH5S389axMtDyPT8ddqmw"]'
 
{
    "hex" : "0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c00000000fd5d010048304502200187af928e9d155c4b1ac9c1c9118153239aba76774f775d7c1f9c3e106ff33c0221008822b0f658edec22274d0b6ae9de10ebf2da06b1bbdaaba4e50eb078f39e3d78014730440220795f0f4f5941a77ae032ecb9e33753788d7eb5cb0c78d805575d6b00a1d9bfed02203e1f4ad9332d1416ae01e27038e945bc9db59c732728a383a6f1ed2fb99da7a4014cc952410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353aeffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000",
    "complete" : true
}
 
# And I can send the funding and spending transactions:
./bitcoind sendrawtransaction 010000000189632848f99722915727c5c75da8db2dbf194342a0429828f66ff88fab2af7d6000000008b483045022100abbc8a73fe2054480bda3f3281da2d0c51e2841391abd4c09f4f908a2034c18d02205bc9e4d68eafb918f3e9662338647a4419c0de1a650ab8983f1d216e2a31d8e30141046f55d7adeff6011c7eac294fe540c57830be80e9355c83869c9260a4b8bf4767a66bacbd70b804dc63d5beeb14180292ad7f3b083372b1d02d7a37dd97ff5c9effffffff0140420f000000000017a914f815b036d9bbbce5e9f2a00abd1bf3dc91e955108700000000
 
3c9018e8d5615c306d72397f8f5eef44308c98fb576a88e030c25456b4f3a7ac
 
./bitcoind sendrawtransaction 0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c00000000fd5d010048304502200187af928e9d155c4b1ac9c1c9118153239aba76774f775d7c1f9c3e106ff33c0221008822b0f658edec22274d0b6ae9de10ebf2da06b1bbdaaba4e50eb078f39e3d78014730440220795f0f4f5941a77ae032ecb9e33753788d7eb5cb0c78d805575d6b00a1d9bfed02203e1f4ad9332d1416ae01e27038e945bc9db59c732728a383a6f1ed2fb99da7a4014cc952410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353aeffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000
 
837dea37ddc8b1e3ce646f1a656e79bbd8cc7f558ac56a169626d649ebe2a3ba
 
# You can see these transactions at:
# http://blockchain.info/address/3QJmV3qfvL9SuYo34YihAf3sRCW3qSinyC
**/



/**
decodescript "hex"
dumpwallet "filename"
getaccount "bitcoinaddress"
getaccountaddress "account"
backupwallet "destination"
importwallet "filename"
validateaddress "bitcoinaddress"
settxfee amount

addnode "node" "add|remove|onetry"
createrawtransaction [{"txid":"id","vout":n},...] {"address":amount,...}
getaddednodeinfo dns ( "node" )
getbalance ( "account" minconf )
getblocktemplate ( "jsonrequestobject" )
getnetworkhashps ( blocks height )

getreceivedbyaccount "account" ( minconf )
getreceivedbyaddress "bitcoinaddress" ( minconf )
gettxout "txid" n ( includemempool )
getwork ( "data" )
help ( "command" )
importprivkey "bitcoinprivkey" ( "label" rescan )
keypoolrefill ( newsize )
listaccounts ( minconf )
listreceivedbyaccount ( minconf includeempty )
listsinceblock ( "blockhash" target-confirmations )
listtransactions ( "account" count from )
listunspent ( minconf maxconf ["address",...] )
lockunspent unlock [{"txid":"txid","vout":n},...]
move "fromaccount" "toaccount" amount ( minconf "comment" )
sendfrom "fromaccount" "tobitcoinaddress" amount ( minconf "comment" "comment-to" )
sendmany "fromaccount" {"address":amount,...} ( minconf "comment" )
sendtoaddress "bitcoinaddress" amount ( "comment" "comment-to" )
setaccount "bitcoinaddress" "account"
setgenerate generate ( genproclimit )
signmessage "bitcoinaddress" "message"
signrawtransaction "hexstring" ( [{"txid":"id","vout":n,"scriptPubKey":"hex","redeemScript":"hex"},...] ["privatekey1",...] sighashtype )
submitblock "hexdata" ( "jsonparametersobject" )
verifychain ( checklevel numblocks )
verifymessage "bitcoinaddress" "signature" "message"
 */

package casimir.cryptoAPI;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import casimir.JSON;

public class BitcoindAPI implements Bitcoin{

	static final Logger logger = LogManager.getLogger(BitcoindAPI.class
			.getName());

	private String rpcuser = "...";
	private String rpcpassword = "...";
	private String rpcurl = "...";
	private String rpcport = "...";
	private String walletpassphrase = "...";

	/**
	 * 
	 */
	public BitcoindAPI() {
		logger.entry();
		try {
			Properties p = new Properties();
			p.loadFromXML(new FileInputStream("config/bitcoinAPIsettings.xml"));
			this.rpcuser = p.getProperty("rpcuser");
			this.rpcpassword = p.getProperty("rpcpassword");
			this.rpcurl = p.getProperty("rpcurl");
			this.rpcport = p.getProperty("rpcport");
			this.walletpassphrase = p.getProperty("walletpassphrase");

			// System.out.println(getbalance());
			// System.out.println(listreceivedbyaddress());
		} catch (Exception e) {
			logger.catching(e);
		}
		logger.exit(this);
	}

	// //////////////////////////
	// /Zero Parameters Calls////
	// //////////////////////////

	/**
	 * @return
	 */
	public String getblockcount() {
		logger.entry();
		String returnAnswer = generateRequest("getblockcount");
		logger.exit(returnAnswer);
		return returnAnswer;
	}

	/**
	 * @return
	 */
	public String getdifficulty() {
		logger.entry();
		String returnAnswer = generateRequest("getdifficulty");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getgenerate() {
		logger.entry();
		String returnAnswer = generateRequest("getgenerate");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getinfo() {
		logger.entry();
		String returnAnswer = generateRequest("getinfo");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getbestblockhash() {
		logger.entry();
		String returnAnswer = generateRequest("getrawchangeaddress");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getrawchangeaddress() {
		logger.entry();
		String returnAnswer = generateRequest("getrawchangeaddress");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getbalance() {
		logger.entry();
		String returnAnswer = generateRequest("getbalance");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String listreceivedbyaddress() {
		logger.entry();
		String returnAnswer = generateRequest("listreceivedbyaddress");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String openWallet() {
		logger.entry();
		String returnAnswer = generateRequest("walletpassphrase",
				this.walletpassphrase, 600);
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String closeWallet() {

		logger.entry();
		String returnAnswer = this.generateRequest("walletlock");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getconnectioncount() {
		logger.entry();
		String returnAnswer = generateRequest("getconnectioncount");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String gethashespersec() {
		logger.entry();
		String returnAnswer = generateRequest("gethashespersec");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getmininginfo() {
		logger.entry();
		String returnAnswer = generateRequest("getmininginfo");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getnettotals() {
		logger.entry();
		String returnAnswer = generateRequest("getnettotals");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getpeerinfo() {
		logger.entry();
		String returnAnswer = generateRequest("getpeerinfo");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String listaddressgroupings() {
		logger.entry();
		String returnAnswer = generateRequest("listaddressgroupings");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String listlockunspent() {
		logger.entry();
		String returnAnswer = generateRequest("listlockunspent");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String ping() {
		logger.entry();
		String returnAnswer = generateRequest("ping");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String stop() {
		logger.entry();
		String returnAnswer = generateRequest("stop");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String gettxoutsetinfo() {
		logger.entry();
		String returnAnswer = generateRequest("gettxoutsetinfo");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @return
	 */
	public String getunconfirmedbalance() {
		logger.entry();
		String returnAnswer = generateRequest("getunconfirmedbalance");
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	// //////////////////////////
	// /Single Parameter Calls//
	// //////////////////////////

	/**
	 * @param txid
	 * @return
	 */
	public String gettransaction(String txid) {
		logger.entry(txid);
		String returnAnswer = generateRequest("gettransaction", txid);
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	public String getrawmempool(boolean... verbose) {
		String returnAnswer = "";
		if (verbose.length > 0) {
			logger.entry(verbose);
			returnAnswer = generateRequest("getrawmempool", verbose[0]);
		} else {
			returnAnswer = generateRequest("getrawmempool", false);
		}
		logger.exit(returnAnswer);
		return returnAnswer;
	}

	/**
	 * @param txid
	 * @return
	 */
	public String getrawtransaction(String txid) {
		logger.entry(txid);
		String returnAnswer = generateRequest("getrawtransaction", txid);
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @param hexstring
	 * @return
	 */
	public String decoderawtransaction(String hexstring) {
		logger.entry(hexstring);
		String returnAnswer = generateRequest("decoderawtransaction", hexstring);
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @param hash
	 * @return
	 */
	public String getblock(String hash) {
		logger.entry(hash);
		String returnAnswer = generateRequest("getblock", hash);
		logger.exit(hash);
		return returnAnswer;

	}

	/**
	 * @param index
	 * @return
	 */
	public String getblockhash(int index) {
		logger.entry(index);
		String returnAnswer = this.generateRequest("getblockhash", index);
		logger.exit(returnAnswer);
		return returnAnswer;
	}

	/**
	 * @param label
	 * @return
	 */
	public String getnewaddress(String label) {
		logger.entry(label);
		String returnAnswer = generateRequest("getnewaddress", label);
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @param account
	 * @return
	 */
	public String getaddressesbyaccount(String account) {
		logger.entry(account);
		String returnAnswer = generateRequest("getaddressesbyaccount", account);
		logger.exit(returnAnswer);
		return returnAnswer;

	}

	/**
	 * @param address
	 * @return
	 */
	public String dumpPrivateKey(String address) {
		logger.entry(address);
		this.closeWallet();
		this.openWallet();
		String privateKey = generateRequest("dumpprivkey", address);
		this.closeWallet();
		logger.exit();
		return privateKey;
	}

	/**
	 * @param rawTransactionHex
	 * @return
	 */
	public String sendRawTransaction(String rawTransactionHex) {
		logger.entry(rawTransactionHex);
		String returnAnswer = generateRequest("sendrawtransaction",
				rawTransactionHex);
		logger.exit(returnAnswer);
		return returnAnswer;
	}

	// //////////////////////////
	// /Two Parameters Calls//
	// //////////////////////////
	
	public String walletpassphrasechange(String oldpassphrase, String newpassphrase) {
		logger.entry();
		String returnAnswer = generateRequest("walletpassphrasechange",
				oldpassphrase,newpassphrase);
		logger.exit(returnAnswer);
		return returnAnswer;
	}
	
	// //////////////////////////
	// /Unkown Parameters Calls//
	// //////////////////////////
	public String createmultisig(int nrequired, String... key) {
		logger.entry(nrequired);

		JSONArray address = new JSONArray();
		if (key.length > 0) {
			for (int i = 0; i < key.length; i++) {

				address.put(key[i]);
			}
		}
		return generateRequest("createmultisig", nrequired, address);

	}

	public String addmultisigaddress(int nrequired, String label, String... key) {
		logger.entry(nrequired);

		JSONArray address = new JSONArray();
		if (key.length > 0) {
			for (int i = 0; i < key.length; i++) {

				address.put(key[i]);
			}
		}
		if (label.equals(""))
			return generateRequest("addmultisigaddress", nrequired, address);
		else
			return generateRequest("addmultisigaddress", nrequired, address,
					label);

	}

	public String createrawtransaction(JSONArray txs, JSONObject sendAddresses) {
		logger.entry(sendAddresses.toString());
		String returnAnswer = generateRequest("createrawtransaction", txs,
				sendAddresses);
		logger.exit(returnAnswer);
		return generateRequest("createrawtransaction", txs, sendAddresses);
	}

	
	// ////////////////Sending RPC calls/////////////////////////////////

	private static final Charset QUERY_CHARSET = Charset.forName("ISO8859-1");

	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	private byte[] prepareRequest(final String method, final Object... params) {
		return JSON.stringify(new LinkedHashMap() {
			{
				put("jsonrpc", "1.0");
				put("id", "1");
				put("method", method);
				put("params", params);
			}
		}).getBytes(QUERY_CHARSET);
	}

	private String generateRequest(String method, Object... param) {

		String requestBody = new String(this.prepareRequest(method, param));

		if (method.equals("dumpprivkey"))
			logger.entry("dumpprivkey so no logging");
		else if (method.equals("getblock"))
			logger.entry("It's to long to log an entire block");
		else if (method.equals("walletpassphrasechange"))
			logger.entry("walletpassphrasechange so no logging");
		else
			logger.entry(requestBody);

		final PasswordAuthentication temp = new PasswordAuthentication(
				this.rpcuser, this.rpcpassword.toCharArray());
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return temp;
			}
		});
		String uri = "http://" + this.rpcurl + ":" + this.rpcport;

		String contentType = "application/json";
		HttpURLConnection connection = null;
		try {
			URL url = new URL(uri);
			connection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", contentType);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Length",
					Integer.toString(requestBody.getBytes().length));
			connection.setUseCaches(true);
			connection.setDoInput(true);
			OutputStream out = connection.getOutputStream();
			out.write(requestBody.getBytes());
			out.flush();
			out.close();
		} catch (Exception ioE) {
			logger.catching(ioE);
			connection.disconnect();
			ioE.printStackTrace();
		}

		try {
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = connection.getInputStream();
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(is));
				String line;
				StringBuffer response = new StringBuffer();
				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
				// System.out.println(response);

				String responseToString = response.toString();
				try {
					JSONObject json = new JSONObject(responseToString);
					String returnAnswer = json.get("result").toString();

					if (method.equals("dumpprivkey"))
						logger.exit("dumpprivkey so no logging");
					else if (method.equals("getblock"))
						logger.exit("It's to long to log an entire block");
					else if (method.equals("walletpassphrasechange"))
						logger.exit("walletpassphrasechange so no logging");
					else
						logger.exit(returnAnswer);

					return returnAnswer;
				} catch (Exception e) {
					logger.catching(e);
					return "";
				}
			} else {
				System.out.println("Coudln't connet to Bitcoind!");
				logger.exit("Coudln't connet to Bitcoind!");
				connection.disconnect();
			}
		} catch (Exception e) {
			logger.catching(e);
		}
		logger.exit("Couldn't get a decent answer");
		return "";
	}

	// //////////////////////////////////////////////////////////////////

}
