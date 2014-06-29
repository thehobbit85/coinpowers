package casimir.cryptoAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MastercoinAPI {
	static final Logger logger = LogManager.getLogger(MastercoinAPI.class.getName());

	// Done
	/**
	 * @param JsonFile
	 * @return
	 */
	public static String getAssetRawTransactionHex(String JsonFile) {
		logger.entry(JsonFile);
		try {

			String[] cmd = { "/bin/sh", "-c",
					"cat assets/"+JsonFile +".json | python generateTX50_SP.py" };
			ProcessBuilder pb = new ProcessBuilder(cmd);
			Process process = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			StringBuilder everything = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				everything.append(line);
			}

			String result = everything.toString();
			JSONObject pythonResponse = new JSONObject(result);
			
			
			if (pythonResponse.has("rawtransaction") == true) {
				JSONObject a = new JSONObject(pythonResponse.get("rawtransaction")
						.toString());
				
				//System.out.println(a.get("hex"));
				String hex = a.get("hex").toString();
				logger.exit(hex);
				return hex;
				
			}
			else if (pythonResponse.has("error") == true) {
				JSONObject a = new JSONObject(pythonResponse.toString());
				
				//System.out.println(a.get("hex"));
				String error = a.get("error").toString();
				logger.exit(error);
				return error;
			
			}
				
			

		} catch (Exception e) {
			logger.catching(e);
			System.out.println("There is a problam running the python script: "
					+ e.toString());

		}
		logger.exit("");
		return "";

	}

	// Done
	/**
	 * @param JsonFile
	 * @return
	 */
	public static String createTransaction(String JsonFile) {
			logger.entry(JsonFile);
			try {
				
				String[] cmd = { "/bin/sh", "-c",
						"cat transactions/"+JsonFile +".json | python generateTX00.py" };
				ProcessBuilder pb = new ProcessBuilder(cmd);
				Process process = pb.start();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				StringBuilder everything = new StringBuilder();
				String line;
				while ((line = in.readLine()) != null) {
					everything.append(line);
				}

				String result = everything.toString();
				JSONObject pythonResponse = new JSONObject(result);
				
				
				if (pythonResponse.has("rawtransaction") == true) {
					JSONObject a = new JSONObject(pythonResponse.get("rawtransaction")
							.toString());
					
					//System.out.println(a.get("hex"));
					String hex = a.get("hex").toString();
					logger.exit(hex);
					return hex;
					
				}
				else if (pythonResponse.has("error") == true) {
					JSONObject a = new JSONObject(pythonResponse.toString());
					
					//System.out.println(a.get("hex"));
					String error = a.get("error").toString();
					logger.exit(error);
					return error;
				
				}
			} catch (Exception e) {
				logger.catching(e);
				System.out.println("There is a problam running the python script: "
						+ e.toString());

			}
			logger.exit("");
			return "";

		}
}
