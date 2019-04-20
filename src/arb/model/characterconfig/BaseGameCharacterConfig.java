package arb.model.characterconfig;

import java.util.Arrays;
import java.util.Map;

import arb.model.entity.Effect;

/**
 * This class acts as a facade for all other character config classes. This
 * avoids the need to instantiate one of each.
 */
public class BaseGameCharacterConfig {

	private static final int DEFAULT_ALCHEMIST = 0;

	private static final boolean DEFAULT_CHECK_BOX = false;

	private int alchemistLevel;

	private boolean isPhysicianChecked;

	private boolean isBenefactorChecked;

	private boolean isPoisonerChecked;

	private Map<CharacterConfigKey, String> mapping;

	public BaseGameCharacterConfig() {
		this.reset();
	}

	public void update(Map<CharacterConfigKey, String> mapping) {
		this.mapping = mapping;
		this.alchemistLevel = mapping.containsKey(CharacterConfigKey.ALCHEMY_MASTERY)
				? Integer.parseInt(mapping.get(CharacterConfigKey.ALCHEMY_MASTERY))
				: DEFAULT_ALCHEMIST;
		this.isPhysicianChecked = mapping.containsKey(CharacterConfigKey.PHYSICIAN_BASE_GAME)
				? Boolean.parseBoolean(mapping.get(CharacterConfigKey.PHYSICIAN_BASE_GAME))
				: DEFAULT_CHECK_BOX;
		this.isBenefactorChecked = mapping.containsKey(CharacterConfigKey.ADVANCED_LAB)
				? Boolean.parseBoolean(mapping.get(CharacterConfigKey.ADVANCED_LAB))
				: DEFAULT_CHECK_BOX;
		this.isPoisonerChecked = mapping.containsKey(CharacterConfigKey.POISONER_BASE_GAME)
				? Boolean.parseBoolean(mapping.get(CharacterConfigKey.POISONER_BASE_GAME))
				: DEFAULT_CHECK_BOX;
	}

	public Map<CharacterConfigKey, String> getStoreMapping() {
		return this.mapping;
	}

	public void reset() {
		this.alchemistLevel = DEFAULT_ALCHEMIST;
		this.isPhysicianChecked = DEFAULT_CHECK_BOX;
		this.isBenefactorChecked = DEFAULT_CHECK_BOX;
		this.isPoisonerChecked = DEFAULT_CHECK_BOX;
	}

	public double getAlchemistMultiplier() {
		return .20 * Double.valueOf(this.alchemistLevel) + 1;
	}

	public double getPhysicianMultiplier(final Effect effect) {
		return Arrays.asList("Restore Health", "Restore Magicka", "Restore Stamina").contains(effect.getName())
				&& this.isPhysicianChecked ? 1.25 : 1;
	}

	public double getBenefactorMultiplier() {
		return this.isBenefactorChecked ? 1.25 : 1;
	}

	public double getPoisonerMultiplier() {
		return this.isPoisonerChecked ? 1.25 : 1;
	}
}
