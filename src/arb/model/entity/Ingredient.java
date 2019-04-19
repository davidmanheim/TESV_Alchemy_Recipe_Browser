package arb.model.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class contains fields and methods to represent an ingredient in-game.
 */
public class Ingredient {

	private static final Logger LOG = LogManager.getLogger(Ingredient.class);

	private String name;

	private Effect firstEffect;

	private Effect secondEffect;

	private Effect thirdEffect;

	private Effect fourthEffect;

	private double firstMagnitideMultiplier;

	private double secondMagnitideMultiplier;

	private double thirdMagnitideMultiplier;

	private double fourthMagnitideMultiplier;

	private double firstCostMultiplier;

	private double secondCostMultiplier;

	private double thirdCostMultiplier;

	private double fourthCostMultiplier;

	private boolean isHarvestable;

	private int value;

	private int harvestedAmount;

	private GameExtension gameExtension;

	public Ingredient(String name, Effect firstEffect, Effect secondEffect, Effect thirdEffect, Effect fourthEffect,
			int value, int harvestedAmount, Map<String, Double> specialMagnitudes, GameExtension gameExtension) {
		this.name = name;
		this.firstEffect = firstEffect;
		this.secondEffect = secondEffect;
		this.thirdEffect = thirdEffect;
		this.fourthEffect = fourthEffect;
		this.value = value;
		this.firstMagnitideMultiplier = 1;
		this.secondMagnitideMultiplier = 1;
		this.thirdMagnitideMultiplier = 1;
		this.fourthMagnitideMultiplier = 1;
		this.firstCostMultiplier = 1;
		this.secondCostMultiplier = 1;
		this.thirdCostMultiplier = 1;
		this.fourthCostMultiplier = 1;
		this.isHarvestable = harvestedAmount > 0;
		this.harvestedAmount = harvestedAmount;
		this.gameExtension = gameExtension;
		this.setSpecialMagnitudes(specialMagnitudes);
	}

	public String getName() {
		return name;
	}

	public Effect getFirstEffect() {
		return firstEffect;
	}

	public Effect getSecondEffect() {
		return secondEffect;
	}

	public Effect getThirdEffect() {
		return thirdEffect;
	}

	public Effect getFourthEffect() {
		return fourthEffect;
	}

	public Set<Effect> getAllEffects() {
		return new HashSet<>(Arrays.asList(firstEffect, secondEffect, thirdEffect, fourthEffect));
	}

	public double getFirstMagnitideMultiplier() {
		return firstMagnitideMultiplier;
	}

	public double getSecondMagnitideMultiplier() {
		return secondMagnitideMultiplier;
	}

	public double getThirdMagnitideMultiplier() {
		return thirdMagnitideMultiplier;
	}

	public double getFourthMagnitideMultiplier() {
		return fourthMagnitideMultiplier;
	}

	public double getFirstCostMultiplier() {
		return firstCostMultiplier;
	}

	public double getSecondCostMultiplier() {
		return secondCostMultiplier;
	}

	public double getThirdCostMultiplier() {
		return thirdCostMultiplier;
	}

	public double getFourthCostMultiplier() {
		return fourthCostMultiplier;
	}

	public boolean isHarvestable() {
		return isHarvestable;
	}

	public int getValue() {
		return value;
	}

	public int getHarvestedAmount() {
		return harvestedAmount;
	}

	public GameExtension getGameExtension() {
		return gameExtension;
	}

	public double getMagnitudeMultiplierForEffect(Effect effect) {
		if (effect.equals(firstEffect)) {
			return this.firstMagnitideMultiplier;
		} else if (effect.equals(secondEffect)) {
			return this.secondMagnitideMultiplier;
		} else if (effect.equals(thirdEffect)) {
			return this.thirdMagnitideMultiplier;
		} else if (effect.equals(fourthEffect)) {
			return this.fourthMagnitideMultiplier;
		} else {
			throw new IllegalStateException("Ingredient " + this + " does not contain effect " + effect + ".");
		}
	}

	public double getCostMultiplierForEffect(Effect effect) {
		if (effect.equals(firstEffect)) {
			return this.firstCostMultiplier;
		} else if (effect.equals(secondEffect)) {
			return this.secondCostMultiplier;
		} else if (effect.equals(thirdEffect)) {
			return this.thirdCostMultiplier;
		} else if (effect.equals(fourthEffect)) {
			return this.fourthCostMultiplier;
		} else {
			throw new IllegalStateException("Ingredient " + this + " does not contain effect " + effect + ".");
		}
	}

	/** Returns true iff the ingredient has one or more of the given effects. */
	public boolean matchesSomeEffect(List<Effect> effects) {
		if (effects.isEmpty()) {
			return false;
		}
		return this.matchesEffect(effects.get(0)) || this.matchesSomeEffect(effects.subList(1, effects.size()));
	}

	/** Returns true iff the ingredient has the given effect. */
	public boolean matchesEffect(Effect effect) {
		return this.firstEffect.equals(effect) || this.secondEffect.equals(effect) || this.thirdEffect.equals(effect)
				|| this.fourthEffect.equals(effect);
	}

	/** Returns a list of shared effects with the other ingredient. */
	public Set<Effect> getSharedEffects(Ingredient other) {
		Set<Effect> otherEffects = other.getAllEffects();
		return getAllEffects().stream().filter(otherEffects::contains).collect(Collectors.toSet());
	}

	public String toString() {
		return this.getName();
	}

	private void setSpecialMagnitudes(Map<String, Double> specialMagnitudes) {
		for (Entry<String, Double> specialMagnitude : specialMagnitudes.entrySet()) {
			switch (specialMagnitude.getKey()) {
			case "M1":
				this.firstMagnitideMultiplier = specialMagnitude.getValue();
				break;
			case "M2":
				this.secondMagnitideMultiplier = specialMagnitude.getValue();
				break;
			case "M3":
				this.thirdMagnitideMultiplier = specialMagnitude.getValue();
				break;
			case "M4":
				this.fourthMagnitideMultiplier = specialMagnitude.getValue();
				break;
			case "C1":
				this.firstCostMultiplier = specialMagnitude.getValue();
				break;
			case "C2":
				this.secondCostMultiplier = specialMagnitude.getValue();
				break;
			case "C3":
				this.thirdCostMultiplier = specialMagnitude.getValue();
				break;
			case "C4":
				this.fourthCostMultiplier = specialMagnitude.getValue();
				break;
			default:
				LOG.error("Special magnitude key {} not recognized", specialMagnitude.getKey());
			}
		}
	}
}
