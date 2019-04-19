package arb.state.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.factories.ModelFactory;
import arb.model.characterconfig.CharacterConfig;
import arb.model.computation.IngredientAndEffectGraph;
import arb.model.computation.PotionResultWrapper;
import arb.model.entity.Effect;
import arb.model.entity.Potion;
import arb.model.filter.Filter;

/**
 * This class is a representation of the current state of the application model,
 * with methods to invoke when a state change occurs.
 */
public abstract class ApplicationModelState {

	private static final String END_COMPUTATION_LOG_CONTENT = "======================================================================";

	private static final Logger LOG = LogManager.getLogger(ApplicationModelState.class);

	// A variety of key model components, in the order they would usually be
	// created in the normal workflow.

	protected CharacterConfig characterConfig;

	protected Set<Effect> selectedEffects;

	protected IngredientAndEffectGraph ingredientsAndEffectGraph;

	protected Set<Potion> baseResultSet;

	protected Set<Potion> filteredResultSet;

	protected List<PotionResultWrapper> tableViewEntries;

	protected Set<Filter> appliedFilters;

	/** Constructor for the initialization of the application. */
	protected ApplicationModelState() {
		this.characterConfig = ModelFactory.getInstance().createCharacterConfig();
		this.selectedEffects = new HashSet<>();
		this.ingredientsAndEffectGraph = null;
		this.baseResultSet = new HashSet<>();
		this.filteredResultSet = new HashSet<>();
		this.appliedFilters = new HashSet<>();
		this.tableViewEntries = new ArrayList<>();
	}

	/** Constructor for state transitions. */
	protected ApplicationModelState(final ApplicationModelState lastState) {
		this.characterConfig = lastState.characterConfig;
		this.selectedEffects = lastState.selectedEffects;
		this.ingredientsAndEffectGraph = lastState.ingredientsAndEffectGraph;
		this.baseResultSet = lastState.baseResultSet;
		this.filteredResultSet = lastState.filteredResultSet;
		this.appliedFilters = lastState.appliedFilters;
		this.tableViewEntries = lastState.tableViewEntries;
		this.onEnter();
	}

	protected abstract void onEnter();

	protected void applyFilters() {
		this.filteredResultSet = new HashSet<>(this.baseResultSet);
		this.appliedFilters.forEach(filter -> this.filteredResultSet = filter.applyFilter(this.filteredResultSet));
		this.filteredResultSet.forEach(LOG::debug);
		LOG.info(this.filteredResultSet.size() + " potions found after filtering.");
		LOG.debug(END_COMPUTATION_LOG_CONTENT);
	}

	protected void clearFilters() {
		this.filteredResultSet = new HashSet<>(this.baseResultSet);
		this.filteredResultSet.forEach(LOG::debug);
		LOG.info(this.filteredResultSet.size() + " potions found clearing filters.");
		LOG.debug(END_COMPUTATION_LOG_CONTENT);
	}

}
