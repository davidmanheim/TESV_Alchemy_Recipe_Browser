package arb.model.characterconfig;

import java.util.Map;

/**
 * This class acts as a facade for all other character config classes. This
 * avoids the need to instantiate one of each.
 */
public class SharedCharacterConfig {

	private static final int DEFAULT_SKILL_LEVEL = 15;

	private static final int DEFAULT_ENCHANTMENT_BONUS = 1;

	private static final boolean DEFAULT_SEEKER_OF_SHADOWS = false;

	// A multiplier related to skill level in the magnitude calculation.
	private static final double ALCHEMY_SKILL_MULTIPLIER = 1.5;

	private int skillLevel;

	private double totalEnchantmentBonus;

	private boolean isSeekerOfShadowsChecked;

	private Map<CharacterConfigKey, String> mapping;

	public SharedCharacterConfig() {
		this.reset();
	}

	public void update(Map<CharacterConfigKey, String> mapping) {
		this.mapping = mapping;
		this.skillLevel = mapping.containsKey(CharacterConfigKey.SKILL_LEVEL)
				? Integer.parseInt(mapping.get(CharacterConfigKey.SKILL_LEVEL))
				: DEFAULT_SKILL_LEVEL;
		this.totalEnchantmentBonus = mapping.containsKey(CharacterConfigKey.TOTAL_ENCHANTMENT_BONUS)
				? Double.parseDouble(mapping.get(CharacterConfigKey.TOTAL_ENCHANTMENT_BONUS)) / 100.0 + 1
				: DEFAULT_ENCHANTMENT_BONUS;
		this.isSeekerOfShadowsChecked = mapping.containsKey(CharacterConfigKey.SEEKER_OF_SHADOWS)
				? Boolean.parseBoolean(mapping.get(CharacterConfigKey.SEEKER_OF_SHADOWS))
				: DEFAULT_SEEKER_OF_SHADOWS;
	}

	public Map<CharacterConfigKey, String> getStoreMapping() {
		return this.mapping;
	}

	public void reset() {
		this.skillLevel = DEFAULT_SKILL_LEVEL;
		this.totalEnchantmentBonus = 1;
		this.isSeekerOfShadowsChecked = false;
	}

	public int getSkillLevel() {
		return this.skillLevel;
	}

	public double getAlchemySkillLevelMultiplier() {
		return 1 + (ALCHEMY_SKILL_MULTIPLIER - 1) * this.skillLevel / 100;
	}

	public double getTotalEnchantmentMultiplier() {
		return this.totalEnchantmentBonus;
	}

	public double getSeekerOfShadowsMultiplier() {
		return this.isSeekerOfShadowsChecked ? 1.1 : 1;
	}

}
