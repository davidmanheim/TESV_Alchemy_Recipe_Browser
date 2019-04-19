package arb.model.filter;

import java.util.Set;
import java.util.stream.Collectors;

import arb.model.entity.Ingredient;
import arb.model.entity.Potion;

/** This class provides methods to filter out potions which contain ingredient(s) that cannot be harvested. */
public class HarvestableFilter implements Filter {

	public Set<Potion> applyFilter(Set<Potion> unfilteredPotions) {
		return unfilteredPotions.stream().filter(potion -> potion.getIngredients().stream()
				.filter(Ingredient::isHarvestable).count() == potion.getIngredients().size()).collect(Collectors.toSet());
	}

}
