package arb.model.entity;

import java.util.Arrays;

/** This enum represents a component of the game. */
public enum GameExtension {
	BASE_GAME("BG"), DAWNGUARD("DG"), DRAGONBORN("DB"), HEARTHFIRE("HF");
	
	private final String encoding;

	private GameExtension(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}
	
	public static GameExtension getFromEncoding(String encodingToFind) {
		return Arrays.asList(GameExtension.values()).stream().filter(gameExtension -> gameExtension.encoding.equals(encodingToFind)).findFirst().orElse(null);
	}
}
