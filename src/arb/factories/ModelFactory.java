package arb.factories;

import java.util.Map;
import java.util.Set;

import arb.model.characterconfig.BaseGameCharacterConfig;
import arb.model.characterconfig.CharacterConfig;
import arb.model.characterconfig.OrdinatorCharacterConfig;
import arb.model.characterconfig.SharedCharacterConfig;
import arb.model.computation.IngredientAndEffectGraph;
import arb.model.computation.PotionResultWrapper;
import arb.model.entity.Effect;
import arb.model.entity.GameExtension;
import arb.model.entity.Ingredient;
import arb.model.entity.Potion;
import arb.model.entity.PotionEffect;
import arb.model.entity.collection.Effects;
import arb.model.entity.collection.Ingredients;
import arb.model.filter.Filter;
import arb.model.filter.GameExtensionFilter;
import arb.model.filter.HarvestableFilter;
import arb.model.filter.MatchEffectFilter;
import arb.model.filter.MatchIngredientFilter;
import arb.model.filter.PurePotionFilter;
import arb.model.filter.ResultTypeFilter;

/**
 * This class contains methods to instantiation all models or entities shown in
 * the application.
 */
public class ModelFactory {

	private static ModelFactory modelFactoryInstance;

	public static ModelFactory getInstance() {
		if (modelFactoryInstance == null) {
			modelFactoryInstance = new ModelFactory();
		}
		return modelFactoryInstance;
	}

	public Ingredient createIngredient(final String name, final Effect firstEffect, final Effect secondEffect,
			final Effect thirdEffect, final Effect fourthEffect, final int value, final int harvestedAmount,
			final Map<String, Double> specialMagnitudes, final GameExtension gameExtension) {
		return new Ingredient(name, firstEffect, secondEffect, thirdEffect, fourthEffect, value, harvestedAmount,
				specialMagnitudes, gameExtension);
	}

	public Effect createEffect(final String name, final double baseCost, final int baseMag, final int baseDur,
			final int value, final boolean isTimeScaling, final boolean isPositive) {
		return new Effect(name, baseCost, baseMag, baseDur, value, isTimeScaling, isPositive);
	}

	public PotionEffect createPotionEffect(final Set<Ingredient> ingredients, final Effect baseEffect,
			final CharacterConfig characterConfig) {
		return new PotionEffect(ingredients, baseEffect, characterConfig);
	}

	public Potion createPotion(final Set<Ingredient> ingredients, final Set<PotionEffect> potionEffects) {
		return new Potion(ingredients, potionEffects);
	}

	public PotionResultWrapper createPotionResultWrapper(final Potion potion) {
		return new PotionResultWrapper(potion);
	}

	public IngredientAndEffectGraph createGraph(final Set<Effect> effects) {
		return new IngredientAndEffectGraph(effects);
	}

	public CharacterConfig createCharacterConfig() {
		return new CharacterConfig();
	}

	public Filter createGameExtensionFilter(final GameExtension disallowedGameExtension) {
		return new GameExtensionFilter(disallowedGameExtension);
	}

	public Filter createHarvestableFilter() {
		return new HarvestableFilter();
	}

	public Filter createPurePotionFilter() {
		return new PurePotionFilter();
	}

	public Filter createResultTypeFilter(final boolean shouldBePotion) {
		return new ResultTypeFilter(shouldBePotion);
	}

	public Filter createMatchIngredientFilter(final String contentToMatch) {
		return new MatchIngredientFilter(contentToMatch);
	}

	public Filter createMatchEffectFilter(final String contentToMatch) {
		return new MatchEffectFilter(contentToMatch);
	}

	public BaseGameCharacterConfig createBaseGameCharacterConfig() {
		return new BaseGameCharacterConfig();
	}

	public OrdinatorCharacterConfig createOrdinatorCharacterConfig() {
		return new OrdinatorCharacterConfig();
	}

	public SharedCharacterConfig createSharedCharacterConfig() {
		return new SharedCharacterConfig();
	}

	// Use ModelRegistry to get a reference to this; only one needs to be created.
	protected Ingredients createIngredients(final Set<Ingredient> createdIngredients) {
		return new Ingredients(createdIngredients);
	}

	// Use ModelRegistry to get a reference to this; only one needs to be created.
	protected Effects createEffects(final Set<Effect> createdEffects) {
		return new Effects(createdEffects);
	}

	private ModelFactory() {
		// Private constructor to prevent external instantiation.
	}

}
