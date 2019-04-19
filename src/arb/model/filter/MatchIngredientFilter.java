package arb.model.filter;

import java.util.Set;
import java.util.stream.Collectors;

import arb.model.entity.Ingredient;
import arb.model.entity.Potion;

/**
 * This class provides methods to filter out results based on whether they are
 * defined as a potion or a poison. (Note that colloquially, potion is used as
 * an umbrella term for both potions and poisons, but with context, it refers to
 * a result with a dominant positive effect (the game uses the effect with the
 * highest value to define a result as a potion or poison).
 */
public class MatchIngredientFilter implements Filter {

	private String contentToMatch;

	public MatchIngredientFilter(String contentToMatch) {
		this.contentToMatch = contentToMatch.toLowerCase();
	}

	public Set<Potion> applyFilter(Set<Potion> unfilteredPotions) {
		return unfilteredPotions.stream().filter(potion -> potion.getIngredients().stream().map(Ingredient::getName)
				.filter(name -> name.toLowerCase().contains(this.contentToMatch)).count() > 0).collect(Collectors.toSet());
	}

}
