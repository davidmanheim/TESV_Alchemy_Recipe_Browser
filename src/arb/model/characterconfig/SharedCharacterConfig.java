package arb.model.characterconfig;

/**
 * This class acts as a facade for all other character config classes. This
 * avoids the need to instantiate one of each.
 */
public class SharedCharacterConfig {

	// A multiplier related to skill level in the magnitude calculation.
	private static final double ALCHEMY_SKILL_MULTIPLIER = 1.5;

	private int skillLevel;

	private double totalEnchantmentBonus;

	private boolean isSeekerOfShadowsChecked;

	public SharedCharacterConfig() {
		this.reset();
	}

	public void reset() {
		this.skillLevel = 15;
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
