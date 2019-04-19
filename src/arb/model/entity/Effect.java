package arb.model.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * This class contains fields and methods to represent an effect-game.
 * Note that this is not the same as the effect of a Potion. For this, see
 * PotionEffect.
 */
public class Effect {

	private String name;

	private boolean isPositive;

	private Set<Ingredient> ingredients;

	private double baseCost;

	private int baseMag;

	private int baseDur;

	private int value;

	private boolean isTimeScaling;

	public Effect(String name, double baseCost, int baseMag, int baseDur, int value, boolean isTimeScaling,
			boolean isPositive) {
		this.name = name;
		this.baseCost = baseCost;
		this.baseMag = baseMag;
		this.baseDur = baseDur;
		this.value = value;
		this.isTimeScaling = isTimeScaling;
		this.isPositive = isPositive;
		// Empty at first, but will be populated later.
		this.ingredients = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public boolean isPositive() {
		return isPositive;
	}

	public double getBaseCost() {
		return baseCost;
	}

	public int getBaseMag() {
		return baseMag;
	}

	public int getBaseDur() {
		return baseDur;
	}

	public int getValue() {
		return value;
	}

	public boolean isTimeScaling() {
		return isTimeScaling;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Adds an ingredient to the set of ingredients considered to have this
	 * effect. It is up to the caller to ensure the ingredient actually has the
	 * effect; there is no validation!
	 */
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public String toString() {
		return this.getName();
	}
}
