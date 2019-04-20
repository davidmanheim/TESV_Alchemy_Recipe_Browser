package arb.state.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import arb.core.ViewController;
import arb.factories.ModelFactory;
import arb.factories.ModelRegistry;
import arb.model.characterconfig.CharacterConfigKey;
import arb.model.computation.PotionResultWrapper;
import arb.model.entity.Effect;
import arb.model.entity.Ingredient;
import arb.model.entity.Potion;
import arb.model.entity.PotionEffect;
import arb.view.character.BaseGameCharacterConfigView;
import arb.view.character.OrdinatorCharacterConfigView;
import arb.view.character.SharedCharacterConfigView;
import arb.view.result.ResultTableView;
import arb.view.util.LabelConstants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * This class contains code to run when the user executes a search.
 */
public class ExecuteSearchState extends ApplicationModelState {

	private static final Logger LOG = LogManager.getLogger(ExecuteSearchState.class);

	public ExecuteSearchState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		this.logComputingResultMessage();
		this.updateCharacterConfig();
		this.createAndStartComputationThread();
	}

	/**
	 * Displays a log message to the user that the request has been received. In
	 * some cases, this can take 2-3 seconds, especially if the user set no required
	 * effects.
	 */
	private void logComputingResultMessage() {
		final TextArea logTextArea = ViewController.getInstance().getLogView().getLogTextArea();
		// If filters exist, we want to leave log data to make it clear that the
		// filters were not reset. Otherwise, the log data is cleared first.
		if (this.appliedFilters.isEmpty()) {
			logTextArea.setText(LabelConstants.COMPUTING);
		} else {
			final String existingLogText = logTextArea.getText();
			logTextArea.setText(existingLogText + "\n" + LabelConstants.COMPUTING);
		}
	}

	private void updateCharacterConfig() {
		final Map<CharacterConfigKey, String> baseGameCharacterConfigMapping = this
				.getNewBaseGameCharacterConfigMapping();
		final Map<CharacterConfigKey, String> ordinatorCharacterConfigMapping = this
				.getNewOrdinatorCharacterConfigMapping();
		final Map<CharacterConfigKey, String> sharedCharacterConfigMapping = this.getNewSharedCharacterConfigMapping();
		LOG.info(baseGameCharacterConfigMapping);
		LOG.info(ordinatorCharacterConfigMapping);
		LOG.info(sharedCharacterConfigMapping);

		this.characterConfig.update(baseGameCharacterConfigMapping, ordinatorCharacterConfigMapping,
				sharedCharacterConfigMapping);
	}

	private Map<CharacterConfigKey, String> getNewBaseGameCharacterConfigMapping() {
		final BaseGameCharacterConfigView baseGameCharacterConfigView = ViewController.getInstance()
				.getBaseGameCharacterConfigView();
		String alchemistLevel = baseGameCharacterConfigView.getAlchemistLevelInput().getTextField().getText();
		if (alchemistLevel == null || alchemistLevel.isEmpty()) {
			alchemistLevel = Strings.EMPTY;
		}
		final Boolean physicianSelected = baseGameCharacterConfigView.getPhysicianCheckbox().isSelected();
		final Boolean benefactorSelected = baseGameCharacterConfigView.getBenefactorCheckbox().isSelected();
		final Boolean poisonerSelected = baseGameCharacterConfigView.getPoisonerCheckbox().isSelected();
		final Map<CharacterConfigKey, String> baseGameCharacterConfigMapping = new HashMap<>();
		baseGameCharacterConfigMapping.put(CharacterConfigKey.ALCHEMIST, alchemistLevel);
		baseGameCharacterConfigMapping.put(CharacterConfigKey.PHYSICIAN_BASE_GAME, physicianSelected.toString());
		baseGameCharacterConfigMapping.put(CharacterConfigKey.BENEFACTOR, benefactorSelected.toString());
		baseGameCharacterConfigMapping.put(CharacterConfigKey.POISONER_BASE_GAME, poisonerSelected.toString());
		return baseGameCharacterConfigMapping;
	}

	private Map<CharacterConfigKey, String> getNewOrdinatorCharacterConfigMapping() {
		final OrdinatorCharacterConfigView ordinatorCharacterConfigView = ViewController.getInstance()
				.getOrdinatorCharacterConfigView();
		String alchemyMastery = ordinatorCharacterConfigView.getAlchemyMasteryLevelInput().getTextField().getText();
		if (alchemyMastery == null || alchemyMastery.isEmpty()) {
			alchemyMastery = Strings.EMPTY;
		}
		String physicianSelection = ordinatorCharacterConfigView.getPhysicianComboBox().getComboBox()
				.getSelectionModel().getSelectedItem();
		if (physicianSelection == null || physicianSelection.isEmpty()) {
			physicianSelection = Strings.EMPTY;
		}
		final Boolean advancedLabSelected = ordinatorCharacterConfigView.getAdvancedLabCheckbox().isSelected();
		final Boolean poisonerSelected = ordinatorCharacterConfigView.getPoisonerCheckbox().isSelected();
		final Boolean twdnkySelected = ordinatorCharacterConfigView.getTwdnkyCheckbox().isSelected();
		final Map<CharacterConfigKey, String> ordinatorCharacterConfigMapping = new HashMap<>();
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.ALCHEMY_MASTERY, alchemyMastery);
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.PHYSICIAN_ORDINATOR, physicianSelection);
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.ADVANCED_LAB, advancedLabSelected.toString());
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.POISONER_ORDINATOR, poisonerSelected.toString());
		ordinatorCharacterConfigMapping.put(CharacterConfigKey.TWDNKY, twdnkySelected.toString());
		return ordinatorCharacterConfigMapping;
	}

	private Map<CharacterConfigKey, String> getNewSharedCharacterConfigMapping() {
		final SharedCharacterConfigView sharedCharacterConfigView = ViewController.getInstance()
				.getSharedCharacterConfigView();
		String skillLevel = sharedCharacterConfigView.getSkillLevelInput().getTextField().getText();
		if (skillLevel == null || skillLevel.isEmpty()) {
			skillLevel = Strings.EMPTY;
		}
		String totalEnchantments = sharedCharacterConfigView.getTotalEnchantmentsInput().getTextField().getText();
		if (totalEnchantments == null || totalEnchantments.isEmpty()) {
			totalEnchantments = Strings.EMPTY;
		}
		final Boolean seekerOfShadowsSelected = sharedCharacterConfigView.getSeekerOfShadowsCheckbox().isSelected();
		final Map<CharacterConfigKey, String> sharedCharacterConfigMapping = new HashMap<>();
		sharedCharacterConfigMapping.put(CharacterConfigKey.SKILL_LEVEL, skillLevel);
		sharedCharacterConfigMapping.put(CharacterConfigKey.TOTAL_ENCHANTMENT_BONUS, totalEnchantments);
		sharedCharacterConfigMapping.put(CharacterConfigKey.SEEKER_OF_SHADOWS, seekerOfShadowsSelected.toString());
		return sharedCharacterConfigMapping;
	}

	/**
	 * Creates a Task object and runs the main graph computation in its own thread.
	 * This is required to keep the UI responsive (i.e. show the log message).
	 */
	private void createAndStartComputationThread() {
		final Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				ExecuteSearchState.this.runComputation();
				return null;
			}
		};
		final Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	/** This method is the computation, split into high level components. */
	private void runComputation() {
		final long startTime = System.currentTimeMillis();
		this.updateSelectedEffects();
		this.performModelingAndComputation();
		this.queueResultTableUpdate();
		this.logFinishedComputationMessage();
		LOG.info("total computation time {} ms", System.currentTimeMillis() - startTime);
	}

	/** Reads the UI and updates the model with the input effects, if any. */
	private Set<Effect> updateSelectedEffects() {
		final Set<ComboBox<String>> effectComboBoxes = ViewController.getInstance().getEffectsView().getComboBoxes();
		final Set<Effect> selectedEffects = effectComboBoxes.stream()
				.map(comboBox -> comboBox.getSelectionModel().getSelectedItem())
				.map(effectName -> ModelRegistry.getInstance().getEffects().getEffectByName(effectName))
				.filter(Objects::nonNull).distinct().collect(Collectors.toSet());
		this.selectedEffects = new HashSet<>(selectedEffects);
		return selectedEffects;
	}

	/**
	 * Performs a variety of modeling and graph computations to settle on results to
	 * show the user.
	 */
	private void performModelingAndComputation() {
		this.ingredientsAndEffectGraph = ModelFactory.getInstance().createGraph(this.selectedEffects);
		final Set<Set<Ingredient>> results = this.ingredientsAndEffectGraph.computeResult();
		results.forEach(this::addPotionToResultSet);
		// Could be empty, but there is no harm in just calling it regardless.
		this.applyFilters();
		this.tableViewEntries.clear();
		this.filteredResultSet.forEach(
				potion -> this.tableViewEntries.add(ModelFactory.getInstance().createPotionResultWrapper(potion)));
	}

	private void addPotionToResultSet(final Set<Ingredient> ingredients) {
		final Map<Effect, Long> occurencesPerEffect = ingredients.stream().map(Ingredient::getAllEffects)
				.flatMap(Set::stream).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		// This list filters out the non-repeated effects, i.e. it only includes
		// effects which will be in the potion.
		final Set<Effect> matchedEffects = occurencesPerEffect.entrySet().stream().filter(entry -> entry.getValue() > 1)
				.map(Entry::getKey).collect(Collectors.toSet());
		final Set<PotionEffect> potionEffects = matchedEffects.stream().map(potionEffect -> ModelFactory.getInstance()
				.createPotionEffect(ingredients, potionEffect, this.characterConfig)).collect(Collectors.toSet());
		final Potion newPotion = ModelFactory.getInstance().createPotion(ingredients, potionEffects);
		this.baseResultSet.add(newPotion);
	}

	/**
	 * This method updates the UI with the new results. This must be run back on the
	 * main (JavaFX UI) thread.
	 */
	private void queueResultTableUpdate() {
		Platform.runLater(() -> {
			final ObservableList<PotionResultWrapper> tableData = FXCollections.observableList(this.tableViewEntries);
			final ResultTableView resultsTableView = ViewController.getInstance().getResultsTableView();
			resultsTableView.setItems(tableData);
			resultsTableView.refreshSort();
		});
	}

	/**
	 * Logs that the computation is finished, with the number of results included.
	 */
	private void logFinishedComputationMessage() {
		final int resultSetSize = this.filteredResultSet.size();
		String finishedComputingLogMessage = LabelConstants.BULLET_POINT + resultSetSize;
		if (this.appliedFilters.isEmpty() && resultSetSize == 0) {
			finishedComputingLogMessage += LabelConstants.SEARCH_NO_FILTER_MESSAGE_SUFFIX + "\n"
					+ LabelConstants.NO_VIABLE_RESULTS_MESSAGE;
		} else if (!this.appliedFilters.isEmpty() && resultSetSize == 0) {
			finishedComputingLogMessage += LabelConstants.AFTER_FILTER_MESSAGE_SUFFIX + "\n"
					+ LabelConstants.SUGGEST_CLEAR_FILTERS_MESSAGE;
		} else if (this.appliedFilters.isEmpty()) {
			finishedComputingLogMessage += LabelConstants.SEARCH_NO_FILTER_MESSAGE_SUFFIX;
		} else {
			finishedComputingLogMessage += LabelConstants.AFTER_FILTER_MESSAGE_SUFFIX;
		}

		final TextArea logTextArea = ViewController.getInstance().getLogView().getLogTextArea();
		final String existingLogText = logTextArea.getText();
		logTextArea.setText(existingLogText + "\n" + finishedComputingLogMessage);
	}
}
