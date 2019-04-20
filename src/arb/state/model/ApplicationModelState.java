package arb.state.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import arb.core.ViewController;
import arb.factories.ModelFactory;
import arb.model.characterconfig.CharacterConfig;
import arb.model.characterconfig.CharacterConfigKey;
import arb.model.computation.IngredientAndEffectGraph;
import arb.model.computation.PotionResultWrapper;
import arb.model.entity.Effect;
import arb.model.entity.Potion;
import arb.model.filter.Filter;
import arb.view.character.BaseGameCharacterConfigView;
import arb.view.character.OrdinatorCharacterConfigView;
import arb.view.character.SharedCharacterConfigView;

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
		this.appliedFilters.forEach(filter -> this.filteredResultSet = filter
				.applyFilter(this.filteredResultSet));
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

	protected void updateCharacterConfig() {
		final Map<CharacterConfigKey, String> baseGameCharacterConfigMapping = this
				.getNewBaseGameCharacterConfigMapping();
		final Map<CharacterConfigKey, String> ordinatorCharacterConfigMapping = this
				.getNewOrdinatorCharacterConfigMapping();
		final Map<CharacterConfigKey, String> sharedCharacterConfigMapping = this
				.getNewSharedCharacterConfigMapping();
		this.characterConfig.update(baseGameCharacterConfigMapping,
				ordinatorCharacterConfigMapping, sharedCharacterConfigMapping);
	}

	private Map<CharacterConfigKey, String> getNewBaseGameCharacterConfigMapping() {
		final BaseGameCharacterConfigView baseGameCharacterConfigView = ViewController
				.getInstance().getBaseGameCharacterConfigView();
		String alchemistLevel = baseGameCharacterConfigView.getAlchemistLevelInput()
				.getTextField().getText();
		if (alchemistLevel == null || alchemistLevel.isEmpty()) {
			alchemistLevel = Strings.EMPTY;
		}
		final Boolean physicianSelected = baseGameCharacterConfigView
				.getPhysicianCheckbox().isSelected();
		final Boolean benefactorSelected = baseGameCharacterConfigView
				.getBenefactorCheckbox().isSelected();
		final Boolean poisonerSelected = baseGameCharacterConfigView.getPoisonerCheckbox()
				.isSelected();
		final Map<CharacterConfigKey, String> baseGameCharacterConfigMapping = new HashMap<>();
		baseGameCharacterConfigMapping.put(CharacterConfigKey.ALCHEMIST, alchemistLevel);
		baseGameCharacterConfigMapping.put(CharacterConfigKey.PHYSICIAN_BASE_GAME,
				physicianSelected.toString());
		baseGameCharacterConfigMapping.put(CharacterConfigKey.BENEFACTOR,
				benefactorSelected.toString());
		baseGameCharacterConfigMapping.put(CharacterConfigKey.POISONER_BASE_GAME,
				poisonerSelected.toString());
		return baseGameCharacterConfigMapping;
	}

	private Map<CharacterConfigKey, String> getNewOrdinatorCharacterConfigMapping() {
		final OrdinatorCharacterConfigView ordinatorCharacterConfigView = ViewController
				.getInstance().getOrdinatorCharacterConfigView();
		String alchemyMastery = ordinatorCharacterConfigView.getAlchemyMasteryLevelInput()
				.getTextField().getText();
		if (alchemyMastery == null || alchemyMastery.isEmpty()) {
			alchemyMastery = Strings.EMPTY;
		}
		String physicianSelection = ordinatorCharacterConfigView.getPhysicianComboBox()
				.getComboBox().getSelectionModel().getSelectedItem();
		if (physicianSelection == null || physicianSelection.isEmpty()) {
			physicianSelection = Strings.EMPTY;
		}
		final Boolean advancedLabSelected = ordinatorCharacterConfigView
				.getAdvancedLabCheckbox().isSelected();
		final Boolean poisonerSelected = ordinatorCharacterConfigView
				.getPoisonerCheckbox().isSelected();
		final Boolean twdnkySelected = ordinatorCharacterConfigView.getTwdnkyCheckbox()
				.isSelected();
		final Map<CharacterConfigKey, String> ordinatorCharacterConfigMapping = new HashMap<>();
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.ALCHEMY_MASTERY,
				alchemyMastery);
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.PHYSICIAN_ORDINATOR,
				physicianSelection);
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.ADVANCED_LAB,
				advancedLabSelected.toString());
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.POISONER_ORDINATOR,
				poisonerSelected.toString());
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.TWDNKY,
				twdnkySelected.toString());
		return ordinatorCharacterConfigMapping;
	}

	private Map<CharacterConfigKey, String> getNewSharedCharacterConfigMapping() {
		final SharedCharacterConfigView sharedCharacterConfigView = ViewController
				.getInstance().getSharedCharacterConfigView();
		String skillLevel = sharedCharacterConfigView.getSkillLevelInput().getTextField()
				.getText();
		if (skillLevel == null || skillLevel.isEmpty()) {
			skillLevel = Strings.EMPTY;
		}
		String totalEnchantments = sharedCharacterConfigView.getTotalEnchantmentsInput()
				.getTextField().getText();
		if (totalEnchantments == null || totalEnchantments.isEmpty()) {
			totalEnchantments = Strings.EMPTY;
		}
		final Boolean seekerOfShadowsSelected = sharedCharacterConfigView
				.getSeekerOfShadowsCheckbox().isSelected();
		final Map<CharacterConfigKey, String> sharedCharacterConfigMapping = new HashMap<>();
		sharedCharacterConfigMapping.put(CharacterConfigKey.SKILL_LEVEL, skillLevel);
		sharedCharacterConfigMapping.put(CharacterConfigKey.TOTAL_ENCHANTMENT_BONUS,
				totalEnchantments);
		sharedCharacterConfigMapping.put(CharacterConfigKey.SEEKER_OF_SHADOWS,
				seekerOfShadowsSelected.toString());
		return sharedCharacterConfigMapping;
	}

}
