package arb.model.filter;

import java.util.Set;
import java.util.stream.Collectors;

import arb.model.entity.GameExtension;
import arb.model.entity.Ingredient;
import arb.model.entity.Potion;

/** This class provides methods to filter out potions which contain ingredient(s) from a disallowed game extension. */
public class GameExtensionFilter implements Filter {

	private GameExtension disallowedGameExtension;

	public GameExtensionFilter(GameExtension disallowedGameExtension) {
		this.disallowedGameExtension = disallowedGameExtension;
	}

	public Set<Potion> applyFilter(Set<Potion> unfilteredPotions) {
		return unfilteredPotions.stream().filter(potion -> potion.getIngredients().stream()
				.map(Ingredient::getGameExtension).filter(extension -> disallowedGameExtension == extension).count() == 0)
				.collect(Collectors.toSet());
	}

}
