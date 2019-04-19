package arb.model.characterconfig;

import java.util.Arrays;
import java.util.List;

import arb.model.entity.Effect;
import arb.model.entity.Potion;
import arb.util.LabelConstants;

/**
 * This class acts as a facade for all other character config classes. This
 * avoids the need to instantiate one of each.
 */
public class OrdinatorCharacterConfig {

	private static final List<String> EXCEPTIONS_TO_POISONER = Arrays.asList("Fear", "Frenzy");

	private int alchemyMasteryLevel;

	private String physicianBonusType;

	private boolean isAdvancedLabChecked;

	private boolean isPoisonerChecked;

	// Stands for That Which Does Not Kill You.
	private boolean isTWDNKYChecked;

	public OrdinatorCharacterConfig() {
		this.reset();
	}

	public void reset() {
		this.alchemyMasteryLevel = 0;
		this.physicianBonusType = "";
		this.isAdvancedLabChecked = false;
		this.isPoisonerChecked = false;
		this.isTWDNKYChecked = false;
	}

	public double getAlchemyMasteryMultiplier() {
		return .20 * Double.valueOf(this.alchemyMasteryLevel) + 1;
	}

	public double getPhysicianMultiplier(final Effect effect) {
		final String effectName = effect.getName();
		if (effectName.contains("Health") && effect.isPositive()
				&& this.physicianBonusType.equals(LabelConstants.PHYSICIAN_HEALTH)
				|| effectName.contains("Magicka") && effect.isPositive()
						&& this.physicianBonusType.equals(LabelConstants.PHYSICIAN_MAGICKA)
				|| effectName.contains("Stamina") && effect.isPositive()
						&& this.physicianBonusType.equals(LabelConstants.PHYSICIAN_STAMINA)) {
			return 1.5;
		} else {
			return 1;
		}
	}

	public double getAdvancedLabMultiplier() {
		return this.isAdvancedLabChecked ? 1.25 : 1;
	}

	public double getPoisonerMultiplier(final Potion potion, final Effect effect, final int skillLevel) {
		return this.isPoisonerChecked && !potion.isPotion() && !effect.isPositive() && !this.isExceptionToPoisoner(effect)
				? 1 + .01 * skillLevel : 1;
	}

	public double getTWDNKYMultiplier() {
		return this.isTWDNKYChecked ? 1.25 : 1;
	}

	/**
	 * Some poisons do not seem affected by the poisoner perk. These exceptions
	 * are checked for here. Returns true iff the effect is an exception.
	 */
	private boolean isExceptionToPoisoner(final Effect effect) {
		return EXCEPTIONS_TO_POISONER.contains(effect.getName());
	}

}
