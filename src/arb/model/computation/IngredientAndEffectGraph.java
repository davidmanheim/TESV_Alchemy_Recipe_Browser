package arb.model.computation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.factories.ModelRegistry;
import arb.model.entity.Effect;
import arb.model.entity.Ingredient;

/**
 * This class models all the ingredients and effects in the game, linking them
 * together based on shared effects. It contains methods to obtain a set of
 * distinct potions (i.e. a set of ingredients) which contain at least the
 * provided effects.
 */
public class IngredientAndEffectGraph {

	private static final Logger LOG = LogManager.getLogger(IngredientAndEffectGraph.class);

	private final Set<Ingredient> ingredientNodes;

	private final Map<Ingredient, Set<Ingredient>> sharedEffectEdges;

	private final Set<Effect> effects;

	public IngredientAndEffectGraph(final Set<Effect> effects) {
		this.effects = effects;
		this.ingredientNodes = ModelRegistry.getInstance().getIngredients().getAllIngredients();
		this.sharedEffectEdges = new HashMap<>();
		this.createEffectEdges();
	}

	/**
	 * Computes the graph / result of the current setup and stores it in the
	 * ApplicationState.
	 */
	public Set<Set<Ingredient>> computeResult() {
		final Set<Set<Ingredient>> ingredientSets = this.ingredientNodes.stream().map(this::getDistinctIngredientSets)
				.flatMap(Set::stream).collect(Collectors.toSet());
		ingredientSets.forEach(LOG::debug);
		return ingredientSets;
	}

	/** Makes an edge between every pair of ingredients that share an effect. */
	private void createEffectEdges() {
		this.ingredientNodes.forEach(ingredient -> this.ingredientNodes.forEach(otherIngredient -> {
			if (!ingredient.equals(otherIngredient) && !ingredient.getSharedEffects(otherIngredient).isEmpty()) {
				this.createEdge(ingredient, otherIngredient);
			}
		}));
	}

	private void createEdge(final Ingredient ingredient, final Ingredient otherIngredient) {
		this.sharedEffectEdges.putIfAbsent(ingredient, new HashSet<>());
		this.sharedEffectEdges.get(ingredient).add(otherIngredient);
	}

	/**
	 * Gets and returns all sets of ingredients of size 2 or 3 using the given
	 * ingredient as a starting point in the graph. Larger sets don't matter
	 * because a potion can have at most 3 ingredients.
	 *
	 * Note that by nature of sets, a few rules apply to the return value:
	 * different permutations of the same ingredients are considered equal and
	 * therefore are not included in the result, i.e. A->B->C is considered the
	 * same as B->C->A. Also, duplicate elements are not permitted in a single
	 * set, i.e. A->B-A> will be treated as A->B.
	 */
	private Set<Set<Ingredient>> getDistinctIngredientSets(final Ingredient ingredient) {
		// In reality, the order of ingredients from a path doesn't matter: the
		// ingredients share the same effects no matter which order they're returned
		// in.
		final Set<Set<Ingredient>> paths = new HashSet<>();
		final Set<Ingredient> edgesFromSourceIngredient = this.sharedEffectEdges.get(ingredient);
		edgesFromSourceIngredient.forEach(secondIngredient -> {
			// Add paths of size 2.
			paths.add(new HashSet<>(Arrays.asList(ingredient, secondIngredient)));
			// Add paths of size 3.
			final Set<Ingredient> edgesFromSecondIngredient = this.sharedEffectEdges.get(secondIngredient);
			edgesFromSecondIngredient.stream().filter(thirdIngredient -> !thirdIngredient.equals(ingredient)).forEach(
					thirdIngredient -> paths.add(new HashSet<>(Arrays.asList(ingredient, secondIngredient, thirdIngredient))));
		});
		return paths.stream().filter(this::ingredientSetMatchesCriteria).collect(Collectors.toSet());
	}

	/**
	 * Returns true iff the given ingredientSet has all effects defined in
	 * this.effects, and the ingredientSet does not contain redundant effects
	 * (i.e. 3 ingredients with the same effect).
	 */
	private boolean ingredientSetMatchesCriteria(final Set<Ingredient> ingredientSet) {
		if (this.effects.isEmpty()) {
			// If no ingredients were specified, the other algorithm will not work
			// since it iterates over selected effects.
			return this.setMatchesEmptyCase(ingredientSet);
		} else {
			return this.setMatchesNonEmptyCase(ingredientSet);
		}
	}

	private boolean setMatchesEmptyCase(final Set<Ingredient> ingredientSet) {
		if (ingredientSet.size() == 2) {
			// With 2 ingredients, you can't have ingredient redundancies.
			return true;
		}
		// Look at all effects shared by 2 or more ingredients. If removing any one
		// ingredient does not reduce the number of shared effects, the potion is
		// redundant.
		final List<Ingredient> ingredientList = new ArrayList<>(ingredientSet);
		final Set<Effect> sharedEffects = ingredientList.stream().map(Ingredient::getAllEffects)
				.collect(Collectors.toList()).stream().flatMap(Set::stream)
				.collect(Collectors.groupingBy(e -> e, Collectors.counting())).entrySet().stream()
				.filter((entry -> entry.getValue() > 1)).map(Entry::getKey).collect(Collectors.toSet());
		for (int index = 0; index < ingredientList.size(); index++) {
			final List<Ingredient> tempList = Arrays.asList(ingredientList.get(index),
					ingredientList.get((index + 1) % ingredientSet.size()));
			if (tempList.get(0).getSharedEffects(tempList.get(1)).size() == sharedEffects.size()) {
				return false;
			}
		}
		return true;
	}

	private boolean setMatchesNonEmptyCase(final Set<Ingredient> ingredientSet) {
		final Set<Set<Ingredient>> ingredientsForEachEffect = this.effects.stream().map(Effect::getIngredients)
				.collect(Collectors.toSet());
		final AtomicBoolean matchesCriteria = new AtomicBoolean(true);
		ingredientsForEachEffect.forEach(ingredientsForEffect -> {
			final long count = ingredientsForEffect.stream().filter(ingredientSet::contains).count();
			// - Count < 2 means that only 0 or 1 ingredients have this effect, and
			// thus the produced potion would not have this effect.
			// - Otherwise, with this.effects.size() as 1 and a count of 3 means there
			// are redundant ingredients, so this is filtered out.
			if (count < 2 || this.effects.size() == 1 && count != 2) {
				matchesCriteria.set(false);
			}
		});
		return matchesCriteria.get();
	}
}
