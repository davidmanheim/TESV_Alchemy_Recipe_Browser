package arb.model.characterconfig;

import java.util.Arrays;

import arb.model.entity.Effect;

/**
 * This class acts as a facade for all other character config classes. This
 * avoids the need to instantiate one of each.
 */
public class BaseGameCharacterConfig {

	private int alchemistLevel;

	private boolean isPhysicianChecked;

	private boolean isBenefactorChecked;

	private boolean isPoisonerChecked;

	public BaseGameCharacterConfig() {
		this.reset();
	}

	public void reset() {
		this.alchemistLevel = 0;
		this.isPhysicianChecked = false;
		this.isBenefactorChecked = false;
		this.isPoisonerChecked = false;
	}

	public double getAlchemistMultiplier() {
		return .20 * Double.valueOf(this.alchemistLevel) + 1;
	}

	public double getPhysicianMultiplier(final Effect effect) {
		return Arrays.asList("Restore Health", "Restore Magicka", "Restore Stamina").contains(effect)
				&& this.isPhysicianChecked ? 1.25 : 1;
	}

	public double getBenefactorMultiplier() {
		return this.isBenefactorChecked ? 1.25 : 1;
	}

	public double getPoisonerMultiplier() {
		return this.isPoisonerChecked ? 1.25 : 1;
	}
}
