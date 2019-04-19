package arb.model.entity.collection;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import arb.model.entity.Ingredient;

/**
 * This class corresponds to a set of all ingredients possible in-game. Note that
 * this is more or less a singleton, as there is no need to create multiple
 * instances of this class. To obtain the instance, see
 * ModelRegistry::getIngredients.
 */
public class Ingredients {

	private Map<String, Ingredient> ingredientsByName;

	public Ingredients(Set<Ingredient> ingredients) {
		this.ingredientsByName = ingredients.stream().collect(Collectors.toMap(Ingredient::getName, i -> i));
	}

	public Ingredient getIngredientByName(String name) {
		return this.ingredientsByName.get(name);
	}

	public Set<Ingredient> getAllIngredients() {
		return Collections.unmodifiableSet(this.ingredientsByName.values().stream().collect(Collectors.toSet()));
	}

	public Set<String> getAllIngredientNames() {
		return Collections.unmodifiableSet(this.ingredientsByName.keySet());
	}
}
