package fr.trxyy.alternative.alternative_api.utils.file;

/**
 * @author Trxyy
 */
public class LauncherFile {

	/**
	 * The file size
	 */
	public double size;
	/**
	 * The file url
	 */
	public String url;
	/**
	 * The file path
	 */
	public String path;

	public String hash;

	/**
	 * The Constructor
	 * @param size_ The file size
	 * @param url_ The file url
	 * @param path_ The file path
	 */
	public LauncherFile(double size_, String url_, String path_, String hash_) {
		this.size = size_;
		this.url = url_;
		this.path = path_;
		this.hash = hash_;
	}

	/**
	 * @return The file size
	 */
	public double getSize() {
		return this.size;
	}

	/**
	 * @return The file Url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @return The file path
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * @return The file hash
	 */
	public String getHash() {
		return this.hash;
	}

}
