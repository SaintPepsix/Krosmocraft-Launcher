package fr.trxyy.alternative.alternative_api.minecraft.utils;

public enum EnumJavaOS {
	WIN_64_GAMMA("https://piston-meta.mojang.com/v1/packages/3355e10f323c52ed92c1e477e0b3d12dbcbdb596/manifest.json"),
	WIN_32_GAMMA("https://piston-meta.mojang.com/v1/packages/5c9039c8f794a07a213b91fee846ed7376017731/manifest.json"),
	LINUX_GAMMA("https://piston-meta.mojang.com/v1/packages/f3e086257efca1a662f7276c415e3a9879c90ee9/manifest.json"),
	MACOS_GAMMA("https://piston-meta.mojang.com/v1/packages/e9f3153d8426a44410c9e8ceba185ebc81f5780c/manifest.json");
	
	private String url;
	
	EnumJavaOS(String u) {
		this.url = u;
	}

	public String getUrl() {
		return this.url;
	}
}
