package casimir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import casimir.dataBase.DatabaseAdapter;
import casimir.dataBase.objects.Asset;
import casimir.dataBase.objects.Transaction;

public class Cheat {

	public static void addTransactionsToDatabase(String address,int returnableFee) {

		try {
			Asset asset = DatabaseAdapter.getAssetByAddress(address);

			String transaction_from = asset.getTransaction_from();
			String property_name = asset.getProperty_name();
			int price_in_satoshi = asset.getPrice_in_satoshi();

			JSONArray transactionsJsonArray = Cheat.getTxArray(address);

			for (int i = 0; i < transactionsJsonArray.length(); i++) {

				JSONObject transactionJson = transactionsJsonArray
						.getJSONObject(i);
				String txHash = transactionJson.get("hash").toString();
				if (transactionJson.has("block_height")) {
					String blockhehh = transactionJson.get("block_height")
							.toString();

					int blockNumber = Integer.parseInt(blockhehh);

					if (blockNumber > asset.getBlock_broadcasted_in()) {

						JSONArray inputs = transactionJson
								.getJSONArray("inputs");

						for (int j = 0; j < inputs.length(); j++) {

							JSONObject inputAddressJSON = inputs.getJSONObject(
									j).getJSONObject("prev_out");
							String inputAddress = inputAddressJSON.get("addr")
									.toString();

							if (!inputAddress.equals(address)) {
								JSONArray outputs = transactionJson
										.getJSONArray("out");
								for (int k = 0; k < outputs.length(); k++) {
									String outputAddress = outputs
											.getJSONObject(k).get("addr")
											.toString();
									if (outputAddress.equals(address)) {

										float totalAmountInBitcoins = Float
												.parseFloat(outputs
														.getJSONObject(k)
														.get("value")
														.toString()) - returnableFee;

										int amountOfShares = (int) (totalAmountInBitcoins / price_in_satoshi);

										Transaction trans = new Transaction(
												txHash, inputAddress,
												transaction_from,
												property_name,
												totalAmountInBitcoins,
												blockNumber, amountOfShares,
												"created", "");
										DatabaseAdapter.addTransaction(trans);
										break;
									}
								}
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static JSONArray getTxArray(String address) {
		try {
			JSONObject json = readJsonFromUrl("https://blockchain.info/address/"
					+ address + "?format=json");

			return json.getJSONArray("txs");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static String readAll(Reader rd) {
		try {
			StringBuilder sb = new StringBuilder();
			int cp;

			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			return sb.toString();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;

	}

	public static JSONObject readJsonFromUrl(String url) {

		try {
			InputStream is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			is.close();
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
