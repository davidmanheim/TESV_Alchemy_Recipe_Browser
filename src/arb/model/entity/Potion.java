package arb.model.entity;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class contains fields and methods to represent an ingredient in-game.
 */
public class Potion {

	private List<PotionEffect> potionEffects;

	private List<Ingredient> ingredients;

	public Potion(Set<Ingredient> ingredients, Set<PotionEffect> potionEffects) {
		// Ingredients are in a sorted list for consistency's sake.
		this.ingredients = ingredients.stream().sorted((i1, i2) -> i1.getName().compareTo(i2.getName()))
				.collect(Collectors.toList());
		// Potion effects are sorted by positive effects, then negative effects.
		// There is code which calls isPotion, which requires the value of each
		// component. So, we unfortunately must calculate all the positive effects
		// first, then all negative effects twice. (For instance, say 3 effects, e1,
		// e2, e3 are positive, negative and negative, with values s.t. e3 > e1 >
		// e2. In this case, the potion would be considered a potion for e2, but a
		// poison for e3.
		this.potionEffects = potionEffects.stream()
				.sorted((pe1, pe2) -> -1 * Boolean.compare(pe1.getBaseEffect().isPositive(), pe2.getBaseEffect().isPositive()))
				.collect(Collectors.toList());
		this.potionEffects.forEach(potionEffect -> potionEffect.setPotionAndCalculateComponents(this));
		this.potionEffects.stream().filter(potionEffect -> !potionEffect.getBaseEffect().isPositive())
				.forEach(potionEffect -> potionEffect.setPotionAndCalculateComponents(this));
	}

	/**
	 * Gets a set of all ingredients in the potion.
	 */
	public List<Ingredient> getIngredients() {
		return Collections.unmodifiableList(this.ingredients);
	}

	/**
	 * Gets a set of all effects in the potion.
	 */
	public List<PotionEffect> getPotionEffects() {
		return Collections.unmodifiableList(this.potionEffects);
	}

	/**
	 * Gets a set of all effects in the potion.
	 */
	public Set<Effect> getEffects() {
		return this.potionEffects.stream().map(PotionEffect::getBaseEffect).collect(Collectors.toSet());
	}

	public boolean isPure() {
		Set<Effect> allEffects = this.getEffects();
		long positiveEffectCount = allEffects.stream().filter(Effect::isPositive).count();
		return positiveEffectCount == 0 || positiveEffectCount == allEffects.size();
	}

	/**
	 * Returns true if this is a potion (or poison). This is determined by the
	 * highest value effect in the potion.
	 */
	public boolean isPotion() {
		PotionEffect mostValuableEffect = null;
		for (PotionEffect potionEffect : this.potionEffects) {
			if (mostValuableEffect == null || mostValuableEffect.getValue() < potionEffect.getValue()) {
				mostValuableEffect = potionEffect;
			}
		}
		// mostValuableEffect really can't be null here since a potion must always
		// have an effect. Also, the first boolean expression return option could
		// really just be "true" but sonar will complain about redundant
		// expressions.
		return mostValuableEffect == null ? mostValuableEffect == null : mostValuableEffect.getBaseEffect().isPositive();
	}

	public String toString() {
		return "Potion[Ingredients:" + this.ingredients + " Effects:" + this.potionEffects + "]";
	}
}
