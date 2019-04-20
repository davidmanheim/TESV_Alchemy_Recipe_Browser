package arb.model.characterconfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;

import arb.model.entity.Effect;
import arb.model.entity.Potion;
import arb.view.util.LabelConstants;

/**
 * This class acts as a facade for all other character config classes. This
 * avoids the need to instantiate one of each.
 */
public class OrdinatorCharacterConfig {

	private static final int DEFAULT_ALCHEMY_MASTERY = 0;

	private static final boolean DEFAULT_CHECK_BOX = false;

	private static final List<String> EXCEPTIONS_TO_POISONER = Arrays.asList("Fear", "Frenzy");

	private int alchemyMasteryLevel;

	private String physicianBonusType;

	private boolean isAdvancedLabChecked;

	private boolean isPoisonerChecked;

	// Stands for That Which Does Not Kill You.
	private boolean isTWDNKYChecked;

	private Map<CharacterConfigKey, String> mapping;

	public OrdinatorCharacterConfig() {
		this.reset();
	}

	public void update(Map<CharacterConfigKey, String> mapping) {
		this.mapping = mapping;
		this.alchemyMasteryLevel = mapping.containsKey(CharacterConfigKey.ALCHEMY_MASTERY)
				? Integer.parseInt(mapping.get(CharacterConfigKey.ALCHEMY_MASTERY))
				: DEFAULT_ALCHEMY_MASTERY;
		this.physicianBonusType = mapping.containsKey(CharacterConfigKey.PHYSICIAN_ORDINATOR)
				? mapping.get(CharacterConfigKey.PHYSICIAN_ORDINATOR)
				: Strings.EMPTY;
		this.isAdvancedLabChecked = mapping.containsKey(CharacterConfigKey.ADVANCED_LAB)
				? Boolean.parseBoolean(mapping.get(CharacterConfigKey.ADVANCED_LAB))
				: DEFAULT_CHECK_BOX;
		this.isPoisonerChecked = mapping.containsKey(CharacterConfigKey.POISONER_ORDINATOR)
				? Boolean.parseBoolean(mapping.get(CharacterConfigKey.POISONER_ORDINATOR))
				: DEFAULT_CHECK_BOX;
		this.isTWDNKYChecked = mapping.containsKey(CharacterConfigKey.TWDNKY)
				? Boolean.parseBoolean(mapping.get(CharacterConfigKey.TWDNKY))
				: DEFAULT_CHECK_BOX;
	}

	public Map<CharacterConfigKey, String> getStoreMapping() {
		return this.mapping;
	}

	public void reset() {
		this.alchemyMasteryLevel = DEFAULT_ALCHEMY_MASTERY;
		this.physicianBonusType = Strings.EMPTY;
		this.isAdvancedLabChecked = DEFAULT_CHECK_BOX;
		this.isPoisonerChecked = DEFAULT_CHECK_BOX;
		this.isTWDNKYChecked = DEFAULT_CHECK_BOX;
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
		return this.isPoisonerChecked && !potion.isPotion() && !effect.isPositive()
				&& !this.isExceptionToPoisoner(effect) ? 1 + .01 * skillLevel : 1;
	}

	public double getTWDNKYMultiplier() {
		return this.isTWDNKYChecked ? 1.25 : 1;
	}

	/**
	 * Some poisons do not seem affected by the poisoner perk. These exceptions are
	 * checked for here. Returns true iff the effect is an exception.
	 */
	private boolean isExceptionToPoisoner(final Effect effect) {
		return EXCEPTIONS_TO_POISONER.contains(effect.getName());
	}

}
