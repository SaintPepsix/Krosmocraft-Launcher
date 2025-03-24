package fr.trxyy.alternative.alternative_api.utils.config;

public enum EnumConfig {
	
	USERNAME("username", ""),
	PASSWORD("password", ""),
	ISPASSWORDSAVED("isPasswordSaved", false),
	RAM("allocatedram", 4.0),
	GAME_SIZE("gamesize", "0"),
	MUTED("muted", false),
	VM_ARGUMENTS("vmarguments", ""),
	FULLSCREEN("fullscreen", true),
	BORDERLESS("borderless", true),
	QUALITY("quality", ""),
	SERVER("server", "Live");

	public String option;
	public Object def;
	
	EnumConfig(String opt, Object d) {
		this.option = opt;
		this.def = d;
	}
	
	public String getOption() {
		return this.option;
	}
	
	public Object getDefault() {
		return this.def;
	}
	
}
