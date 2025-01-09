package fr.trxyy.alternative.alternative_api.minecraft.utils;

public enum EnumJavaVersion {
	JAVA_RUNTIME_GAMMA("java-runtime-gamma", "Java 17, run minecraft versions over 1.18");
	
	private String code;
	private String description;
	
	EnumJavaVersion(String c, String desc) {
		this.code = c;
		this.description = desc;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}
