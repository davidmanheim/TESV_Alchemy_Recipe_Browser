package arb.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.model.characterconfig.CharacterConfig;

/**
 * This class represents an effect of a particular potion. Note that this is not
 * the same as an general effect in-game. For this, see Effect.
 */
public class PotionEffect {

	private static final Logger LOG = LogManager.getLogger(PotionEffect.class);

	// A base multiplier in the magnitude and duration calculations.
	private static final int ALCHEMY_INIT_MULTIPLIER = 4;

	private final Set<Ingredient> ingredients;

	private final Effect baseEffect;

	// The magnitudeMultiplier on the highest cost (priority) ingredient for this
	// effect.
	private double potencyMultiplier;

	private double value;

	private int magnitude;

	private int duration;

	// A reference to the potion instance that uses this potion effect. This is
	// necessary because the poisoner perk only applies if the potion is a poison.
	private Potion potion;

	private final CharacterConfig characterConfig;

	public PotionEffect(final Set<Ingredient> ingredients, final Effect baseEffect,
			final CharacterConfig characterConfig) {
		final Set<Ingredient> ingredientsWithEffect = ingredients.stream()
				.filter(ingredient -> baseEffect.getIngredients().contains(ingredient))
				.collect(Collectors.toSet());
		this.ingredients = new HashSet<>(ingredientsWithEffect);
		this.baseEffect = baseEffect;
		this.characterConfig = characterConfig;
	}

	public Set<Ingredient> getIngredients() {
		return this.ingredients;
	}

	public Effect getBaseEffect() {
		return this.baseEffect;
	}

	public double getPotencyMultiplier() {
		return this.potencyMultiplier;
	}

	public int getPotency() {
		return this.baseEffect.isTimeScaling() ? this.duration : this.magnitude;
	}

	public double getValue() {
		return this.value;
	}

	public Potion getPotion() {
		return this.potion;
	}

	/** Sets the potion used by this class to calculate certain components. */
	public void setPotionAndCalculateComponents(final Potion potion) {
		this.potion = potion;
		final Ingredient ingredientWithHighestCost = this
				.getIngredientWithHighestCost(this.ingredients, this.baseEffect);
		this.potencyMultiplier = ingredientWithHighestCost
				.getMagnitudeMultiplierForEffect(this.baseEffect);
		// The magnitude and duration are calculated via the formulas at
		// https://en.uesp.net/wiki/Skyrim:Alchemy_Effects.
		// Currently, there are a few limitations:
		// 1) I don't know how the "Damage Health Gold cost Bug" plays into everthing.
		// My math turns up correct numbers, so I think the wiki has appropriately
		// adjusted the data I used.
		// 2) Vanilla calculations are not as well tested as ordinator, since I have not
		// used vanilla perks in a long time.
		this.magnitude = this.baseEffect.isTimeScaling() ? this.baseEffect.getBaseMag()
				: (int) Math.round(ALCHEMY_INIT_MULTIPLIER * this.baseEffect.getBaseMag()
						* this.potencyMultiplier
						* this.characterConfig.getAlchemySkillLevelMultiplier()
						* this.characterConfig.getCharacterConfigMultiplier(this.potion,
								this.baseEffect));
		this.duration = this.baseEffect.isTimeScaling()
				? (int) Math
						.round(ALCHEMY_INIT_MULTIPLIER * this.baseEffect.getBaseDur()
								* this.potencyMultiplier
								* this.characterConfig.getAlchemySkillLevelMultiplier()
								* this.characterConfig.getCharacterConfigMultiplier(
										this.potion, this.baseEffect))
				: this.baseEffect.getBaseDur();
		this.value = this.computePotionEffectValue();
	}

	@Override
	public String toString() {
		return this.baseEffect.getName();
	}

	private Ingredient getIngredientWithHighestCost(final Set<Ingredient> ingredients,
			final Effect baseEffect) {
		Ingredient highestCostIngredient = null;
		double maxCostMultiplier = -1;
		for (final Ingredient ingredient : ingredients) {
			final double costMultiplierForEffect = ingredient
					.getCostMultiplierForEffect(baseEffect);
			if (costMultiplierForEffect > maxCostMultiplier) {
				highestCostIngredient = ingredient;
				maxCostMultiplier = costMultiplierForEffect;
			} else if (costMultiplierForEffect - maxCostMultiplier < .01
					&& highestCostIngredient != null) {
				// In the event of a cost multiplier tie, the highest magnitude
				// multiplier is used.
				final double ingredientMagMultiplier = ingredient
						.getMagnitudeMultiplierForEffect(baseEffect);
				final double currentHighestCostIngredientMagMultiplier = highestCostIngredient
						.getMagnitudeMultiplierForEffect(baseEffect);
				if (currentHighestCostIngredientMagMultiplier < ingredientMagMultiplier) {
					highestCostIngredient = ingredient;
					maxCostMultiplier = costMultiplierForEffect;
				}
			}
		}
		Objects.requireNonNull(highestCostIngredient);
		return highestCostIngredient;
	}

	private double computePotionEffectValue() {
		final double baseCost = this.baseEffect.getBaseCost();
		final double magnitudeMultiplier = Math.max(1, Math.pow(this.magnitude, 1.1));
		final double durationMultiplier = this.duration == 0 ? 1
				: Math.pow(this.duration / 10.0, 1.1);
		LOG.debug(this.getIngredients() + " " + this.getBaseEffect());
		LOG.debug(baseCost);
		LOG.debug(magnitudeMultiplier);
		LOG.debug("->" + this.magnitude);
		LOG.debug(durationMultiplier);
		LOG.debug("->" + this.duration);
		LOG.debug("============================================================");
		return baseCost * magnitudeMultiplier * durationMultiplier;
	}

}
