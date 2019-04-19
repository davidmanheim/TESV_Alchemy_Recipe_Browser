package arb.factories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.model.entity.Effect;
import arb.model.entity.GameExtension;
import arb.model.entity.Ingredient;
import arb.model.entity.collection.Effects;
import arb.model.entity.collection.Ingredients;

/**
 * This class represents a single access point to the model. Generally speaking,
 * there is no need to instantiate more than one copy of some things; this class
 * contains references to all of these types of objects.
 */
public class ModelRegistry {

	private static final Logger LOG = LogManager.getLogger(ModelRegistry.class);

	private static final String EFFECTS_FILE_PATH = "data/effects.csv";

	private static final String INGREDIENTS_FILE_PATH = "data/ingredients.csv";

	private static ModelRegistry modelRegistryInstance;

	public static ModelRegistry getInstance() {
		if (modelRegistryInstance == null) {
			modelRegistryInstance = new ModelRegistry();
		}
		return modelRegistryInstance;
	}

	private Effects effects;

	private Ingredients ingredients;

	private ModelRegistry() {
		Set<Effect> createdEffects = this.parseEffectsFromFileSystem();
		this.effects = ModelFactory.getInstance().createEffects(createdEffects);
		Set<Ingredient> createdIngredients = this.parseIngredientsFromFileSystem();
		this.ingredients = ModelFactory.getInstance().createIngredients(createdIngredients);
	}

	public Effects getEffects() {
		return effects;
	}

	public Ingredients getIngredients() {
		return ingredients;
	}

	private Set<Effect> parseEffectsFromFileSystem() {
		Set<Effect> createdEffects = new HashSet<>();
		InputStream effectsFileStream = this.getClass().getClassLoader().getResourceAsStream(EFFECTS_FILE_PATH);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(effectsFileStream))) {
			// Skip the first row, which is a header row.
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(";");
				String name = values[0];
				double baseCost = Double.parseDouble(values[1]);
				int baseMag = Integer.parseInt(values[2]);
				int baseDur = Integer.parseInt(values[3]);
				int value = Integer.parseInt(values[4]);
				boolean isTimeScaling = "T".equals(values[5]);
				boolean isPositive = "T".equals(values[6]);
				Effect effect = ModelFactory.getInstance().createEffect(name, baseCost, baseMag, baseDur, value, isTimeScaling,
						isPositive);
				createdEffects.add(effect);
			}
		} catch (IOException ioe) {
			LOG.error(ioe);
		}
		return createdEffects;
	}

	private Set<Ingredient> parseIngredientsFromFileSystem() {
		Set<Ingredient> createdIngredients = new HashSet<>();
		InputStream ingredientsFileStream = this.getClass().getClassLoader().getResourceAsStream(INGREDIENTS_FILE_PATH);
		try (BufferedReader br = new BufferedReader(new InputStreamReader(ingredientsFileStream))) {
			// Skip the first row, which is a header row.
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(";");
				String name = values[0];
				Effect firstEffect = this.effects.getEffectByName(values[1]);
				Effect secondEffect = this.effects.getEffectByName(values[2]);
				Effect thirdEffect = this.effects.getEffectByName(values[3]);
				Effect fourthEffect = this.effects.getEffectByName(values[4]);
				int value = Integer.parseInt(values[5]);
				int harvestedAmount = values[6].isEmpty() ? 0 : Integer.parseInt(values[6]);
				Map<String, Double> specialMagnitudes = getSpecialMagnitudes(values[7]);
				GameExtension gameExtension = getExtension(values[8]);
				Ingredient ingredient = ModelFactory.getInstance().createIngredient(name, firstEffect, secondEffect,
						thirdEffect, fourthEffect, value, harvestedAmount, specialMagnitudes, gameExtension);
				// Now that ingredients exist, we must tie them back to the already
				// created effects.
				firstEffect.addIngredient(ingredient);
				secondEffect.addIngredient(ingredient);
				thirdEffect.addIngredient(ingredient);
				fourthEffect.addIngredient(ingredient);
				createdIngredients.add(ingredient);
			}
		} catch (IOException ioe) {
			LOG.info(ioe);
		}
		return createdIngredients;
	}

	private Map<String, Double> getSpecialMagnitudes(String rawMagnitudesMapping) {
		Map<String, Double> specialMagnitudes = new HashMap<>();
		if (!rawMagnitudesMapping.isEmpty()) {
			String[] mappings = rawMagnitudesMapping.split("\\|");
			for (String specialMagnitude : mappings) {
				String[] pair = specialMagnitude.split(":");
				specialMagnitudes.put(pair[0], Double.parseDouble(pair[1]));
			}
		}
		return specialMagnitudes;
	}

	private GameExtension getExtension(String rawExtension) {
		return GameExtension.getFromEncoding(rawExtension);
	}
}
