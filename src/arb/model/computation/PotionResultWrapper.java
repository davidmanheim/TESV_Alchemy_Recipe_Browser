package arb.model.computation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import arb.model.entity.Ingredient;
import arb.model.entity.Potion;
import arb.model.entity.PotionEffect;

/**
 * This class servers as a wrapper to tie the ResultTableView to a set of
 * Potions. This is due to certain intricacies and limitations of how data is
 * set in the table view.
 */
public class PotionResultWrapper {

	private static final String EMPTY_STRING = "";

	private PotionEffect firstEffect;

	private PotionEffect secondEffect;

	private PotionEffect thirdEffect;

	private PotionEffect fourthEffect;

	private PotionEffect fifthEffect;

	private Ingredient firstIngredient;

	private Ingredient secondIngredient;

	private Ingredient thirdIngredient;

	private int totalValue;

	public PotionResultWrapper(Potion potion) {
		List<PotionEffect> potionEffects = potion.getPotionEffects();
		this.firstEffect = !potionEffects.isEmpty() ? potionEffects.get(0) : null;
		this.secondEffect = potionEffects.size() > 1 ? potionEffects.get(1) : null;
		this.thirdEffect = potionEffects.size() > 2 ? potionEffects.get(2) : null;
		this.fourthEffect = potionEffects.size() > 3 ? potionEffects.get(3) : null;
		this.fifthEffect = potionEffects.size() > 4 ? potionEffects.get(4) : null;
		this.totalValue = (int) Math.floor(Arrays.asList(firstEffect, secondEffect, thirdEffect, fourthEffect, fifthEffect)
				.stream().filter(Objects::nonNull).mapToDouble(PotionEffect::getValue).sum());
		List<Ingredient> ingredients = potion.getIngredients();
		firstIngredient = !ingredients.isEmpty() ? ingredients.get(0) : null;
		secondIngredient = ingredients.size() > 1 ? ingredients.get(1) : null;
		thirdIngredient = ingredients.size() > 2 ? ingredients.get(2) : null;
	}

	public String getFirstEffectName() {
		return firstEffect == null ? EMPTY_STRING : firstEffect.getBaseEffect().getName();
	}

	public String getSecondEffectName() {
		return secondEffect == null ? EMPTY_STRING : secondEffect.getBaseEffect().getName();
	}

	public String getThirdEffectName() {
		return thirdEffect == null ? EMPTY_STRING : thirdEffect.getBaseEffect().getName();
	}

	public String getFourthEffectName() {
		return fourthEffect == null ? EMPTY_STRING : fourthEffect.getBaseEffect().getName();
	}

	public String getFifthEffectName() {
		return fifthEffect == null ? EMPTY_STRING : fifthEffect.getBaseEffect().getName();
	}

	public String getFirstEffectPotency() {
		return firstEffect == null ? EMPTY_STRING : String.valueOf(firstEffect.getPotency());
	}

	public String getSecondEffectPotency() {
		return secondEffect == null ? EMPTY_STRING : String.valueOf(secondEffect.getPotency());
	}

	public String getThirdEffectPotency() {
		return thirdEffect == null ? EMPTY_STRING : String.valueOf(thirdEffect.getPotency());
	}

	public String getFourthEffectPotency() {
		return fourthEffect == null ? EMPTY_STRING : String.valueOf(fourthEffect.getPotency());
	}

	public String getFifthEffectPotency() {
		return fifthEffect == null ? EMPTY_STRING : String.valueOf(fifthEffect.getPotency());
	}

	public String getFirstIngredientName() {
		return firstIngredient == null ? EMPTY_STRING : firstIngredient.getName();
	}

	public String getSecondIngredientName() {
		return secondIngredient == null ? EMPTY_STRING : secondIngredient.getName();
	}

	public String getThirdIngredientName() {
		return thirdIngredient == null ? EMPTY_STRING : thirdIngredient.getName();
	}

	public String getValue() {
		return String.valueOf(totalValue);
	}
}
