package fr.trxyy.alternative.alternative_api.utils.config;

public class ConfigVersion {

	
	private static ConfigVersion instance;
	
	/**
	 * The username
	 */
	public String username;
	/**
	 * The encrypted password
	 */
	public String password;
	/**
	 * Is password saved ?
	 */
	public boolean isPasswordSaved;
	/**
	 * Server choice
	 */
	public String server;
	/**
	 * The RAM
	 */
	public String allocatedram;
	 /* The game size
	 */
	public String gamesize;
	/**
	 * Is muted
	 */
	public boolean muted;
	/**
	 * The VM arguments
	 */
	public String vmarguments;
	/**
	 * Is fullscreen
	 */
	public boolean fullscreen;
	/**
	 * Is borderless
	 */
	public boolean borderless;
	/**
	 * The quality setting
	 */
	public String quality;
	/**
	 * The Constructor
	 */
	public ConfigVersion(ConfigVersion o) {
		instance = o;
		this.username = o.username;
		this.allocatedram = o.allocatedram;
		this.gamesize = o.gamesize;
		this.muted = o.muted;
		this.vmarguments = o.vmarguments;
		this.fullscreen = o.fullscreen;
		this.borderless = o.borderless;
		this.quality = o.quality;
		this.password = o.password;
		this.isPasswordSaved = o.isPasswordSaved;
		this.server = o.server;

	}

	/**
	 * Update multiple values in the config json
	 */
	public String getAllocatedRam() {
		return this.allocatedram;
	}
	
	/**
	 * Get the game size
	 */
	public String getGameSize() {
		return this.gamesize;
	}

	/**
	 * Is sound muted
	 */
	public boolean isMuted() {
		return this.muted;
	}

	/**
	 * Is fullscreen
	 */
	public boolean isFullscreen() {
		return this.fullscreen;
	}

	/**
	 * Get the username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Get the VM arguments
	 */
	public String getVMArguments() {
		return this.vmarguments;
	}
	
	/**
	 * Get the quality
	 */
	public String getQuality() {
		return this.quality;
	}

	public static ConfigVersion getInstance() {
		return instance;
	}
	
}
