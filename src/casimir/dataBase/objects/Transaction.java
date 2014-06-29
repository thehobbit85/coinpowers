package casimir.dataBase.objects;

public class Transaction {

	private String txHash;
	private String rawTxHash;
	private String  inputAddress;
	private String  assetAddress;
	private String  assetName;
	private float  totalAmountInBitcoins;
	private int  blockNumber;
	private int  amountOfShares;
	private String status;
	
	/**
	 * @param txHash
	 * @param inputAddress
	 * @param assetAddress
	 * @param assetName
	 * @param totalAmountInBitcoins
	 * @param blockNumber
	 * @param amountOfShares
	 * @param status
	 * @param rawTxHash
	 */
	public Transaction(String txHash, String inputAddress, String assetAddress,
			String assetName, float totalAmountInBitcoins, int blockNumber,
			int amountOfShares,String status,String rawTxHash) {
		super();
		this.txHash = txHash;
		this.inputAddress = inputAddress;
		this.assetAddress = assetAddress;
		this.assetName = assetName;
		this.totalAmountInBitcoins = totalAmountInBitcoins;
		this.blockNumber = blockNumber;
		this.amountOfShares = amountOfShares;
		this.status = status;
		this.rawTxHash = rawTxHash;
	}

	/**
	 * @return
	 */
	public String getTxHash() {
		return txHash;
	}

	/**
	 * @param txHash
	 */
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	/**
	 * @return
	 */
	public String getInputAddress() {
		return inputAddress;
	}

	/**
	 * @param inputAddress
	 */
	public void setInputAddress(String inputAddress) {
		this.inputAddress = inputAddress;
	}

	/**
	 * @return
	 */
	public String getAssetAddress() {
		return assetAddress;
	}

	/**
	 * @param assetAddress
	 */
	public void setAssetAddress(String assetAddress) {
		this.assetAddress = assetAddress;
	}

	/**
	 * @return
	 */
	public String getAssetName() {
		return assetName;
	}

	/**
	 * @param assetName
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	/**
	 * @return
	 */
	public float getTotalAmountInBitcoins() {
		return totalAmountInBitcoins;
	}

	/**
	 * @param totalAmountInBitcoins
	 */
	public void setTotalAmountInBitcoins(float totalAmountInBitcoins) {
		this.totalAmountInBitcoins = totalAmountInBitcoins;
	}

	/**
	 * @return
	 */
	public int getBlockNumber() {
		return blockNumber;
	}

	/**
	 * @param blockNumber
	 */
	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}

	/**
	 * @return
	 */
	public int getAmountOfShares() {
		return amountOfShares;
	}

	/**
	 * @param amountOfShares
	 */
	public void setAmountOfShares(int amountOfShares) {
		this.amountOfShares = amountOfShares;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public String getRawTxHash() {
		return rawTxHash;
	}

	/**
	 * @param rawTxHash
	 */
	public void setRawTxHash(String rawTxHash) {
		this.rawTxHash = rawTxHash;
	}

}
