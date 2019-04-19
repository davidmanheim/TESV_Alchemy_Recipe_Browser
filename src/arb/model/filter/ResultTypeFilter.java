package arb.model.filter;

import java.util.Set;
import java.util.stream.Collectors;

import arb.model.entity.Potion;

/**
 * This class provides methods to filter out results based on whether they are
 * defined as a potion or a poison. (Note that colloquially, potion is used as
 * an umbrella term for both potions and poisons, but with context, it refers to
 * a result with a dominant positive effect (the game uses the effect with the
 * highest value to define a result as a potion or poison).
 */
public class ResultTypeFilter implements Filter {

	private boolean mustBePotion;

	public ResultTypeFilter(boolean shouldBePotion) {
		this.mustBePotion = shouldBePotion;
	}

	public Set<Potion> applyFilter(Set<Potion> unfilteredPotions) {
		return unfilteredPotions.stream().filter(potion -> this.mustBePotion == potion.isPotion())
				.collect(Collectors.toSet());
	}

}
