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

public class CounterPartyAPI {

	static final Logger logger = LogManager.getLogger(CounterPartyAPI.class
			.getName());

	private String rpcuser = "...";
	private String rpcpassword = "...";
	private String rpcurl = "...";
	private String rpcport = "...";
	private String walletpassphrase = "...";

	/**
	 * 
	 */
	public CounterPartyAPI() {
		logger.entry();
		try {
			Properties p = new Properties();
			p.loadFromXML(new FileInputStream("config/CounterPartyAPIsettings.xml"));
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

	

	// //////////////////////////
	// /Single Parameter Calls//
	// //////////////////////////


	// //////////////////////////
	// /Two Parameters Calls//
	// //////////////////////////
	
	
	
	// //////////////////////////
	// /Unkown Parameters Calls//
	// //////////////////////////
	
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
