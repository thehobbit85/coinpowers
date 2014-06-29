package casimir.dataBase.objects;

public class Block {

	private String hash;
	private int time;
	private int height;
	private String status;
	
	/**
	 * @param height
	 * @param hash
	 * @param time
	 * @param status
	 */
	public Block(int height,String hash, int time,String status) {
		super();
		this.hash = hash;
		this.time = time;
		this.height = height;
		this.status = status;
	}

	/**
	 * @return
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	/**
	 * @return
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
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
	
	
	
}
