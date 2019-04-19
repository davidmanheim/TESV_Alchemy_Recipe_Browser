package arb.model.filter;

import java.util.Set;
import java.util.stream.Collectors;

import arb.model.entity.Potion;

/** This class provides methods to filter out potions which both positive and negative effects. */
public class PurePotionFilter implements Filter {

	public PurePotionFilter() {
		// Nothing to do.
	}

	public Set<Potion> applyFilter(Set<Potion> unfilteredPotions) {
		return unfilteredPotions.stream().filter(Potion::isPure).collect(Collectors.toSet());
	}

}
