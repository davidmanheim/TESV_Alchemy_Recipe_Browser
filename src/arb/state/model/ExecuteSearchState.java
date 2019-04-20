package arb.state.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.core.ViewController;
import arb.factories.ModelFactory;
import arb.factories.ModelRegistry;
import arb.model.computation.PotionResultWrapper;
import arb.model.entity.Effect;
import arb.model.entity.Ingredient;
import arb.model.entity.Potion;
import arb.model.entity.PotionEffect;
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
		this.updateCharacterConfig();
		this.logComputingResultMessage();
		this.createAndStartComputationThread();
	}

	/**
	 * Displays a log message to the user that the request has been received. In
	 * some cases, this can take 2-3 seconds, especially if the user set no required
	 * effects.
	 */
	private void logComputingResultMessage() {
		final TextArea logTextArea = ViewController.getInstance().getLogView()
				.getLogTextArea();
		// If filters exist, we want to leave log data to make it clear that the
		// filters were not reset. Otherwise, the log data is cleared first.
		if (this.appliedFilters.isEmpty()) {
			logTextArea.setText(LabelConstants.COMPUTING);
		} else {
			final String existingLogText = logTextArea.getText();
			logTextArea.setText(existingLogText + "\n" + LabelConstants.COMPUTING);
		}
		// TODO - check character config and warn user if both vanilla + ordinator are
		// used.
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
		final Set<ComboBox<String>> effectComboBoxes = ViewController.getInstance()
				.getEffectsView().getComboBoxes();
		final Set<Effect> selectedEffects = effectComboBoxes.stream()
				.map(comboBox -> comboBox.getSelectionModel().getSelectedItem())
				.map(effectName -> ModelRegistry.getInstance().getEffects()
						.getEffectByName(effectName))
				.filter(Objects::nonNull).distinct().collect(Collectors.toSet());
		this.selectedEffects = new HashSet<>(selectedEffects);
		return selectedEffects;
	}

	/**
	 * Performs a variety of modeling and graph computations to settle on results to
	 * show the user.
	 */
	private void performModelingAndComputation() {
		this.ingredientsAndEffectGraph = ModelFactory.getInstance()
				.createGraph(this.selectedEffects);
		final Set<Set<Ingredient>> results = this.ingredientsAndEffectGraph
				.computeResult();
		results.forEach(this::addPotionToResultSet);
		// Could be empty, but there is no harm in just calling it regardless.
		this.applyFilters();
		this.tableViewEntries.clear();
		this.filteredResultSet.forEach(potion -> this.tableViewEntries
				.add(ModelFactory.getInstance().createPotionResultWrapper(potion)));
	}

	private void addPotionToResultSet(final Set<Ingredient> ingredients) {
		final Map<Effect, Long> occurencesPerEffect = ingredients.stream()
				.map(Ingredient::getAllEffects).flatMap(Set::stream).collect(Collectors
						.groupingBy(Function.identity(), Collectors.counting()));
		// This list filters out the non-repeated effects, i.e. it only includes
		// effects which will be in the potion.
		final Set<Effect> matchedEffects = occurencesPerEffect.entrySet().stream()
				.filter(entry -> entry.getValue() > 1).map(Entry::getKey)
				.collect(Collectors.toSet());
		final Set<PotionEffect> potionEffects = matchedEffects.stream()
				.map(potionEffect -> ModelFactory.getInstance().createPotionEffect(
						ingredients, potionEffect, this.characterConfig))
				.collect(Collectors.toSet());
		final Potion newPotion = ModelFactory.getInstance().createPotion(ingredients,
				potionEffects);
		this.baseResultSet.add(newPotion);
	}

	/**
	 * This method updates the UI with the new results. This must be run back on the
	 * main (JavaFX UI) thread.
	 */
	private void queueResultTableUpdate() {
		Platform.runLater(() -> {
			final ObservableList<PotionResultWrapper> tableData = FXCollections
					.observableList(this.tableViewEntries);
			final ResultTableView resultsTableView = ViewController.getInstance()
					.getResultsTableView();
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
			finishedComputingLogMessage += LabelConstants.SEARCH_NO_FILTER_MESSAGE_SUFFIX
					+ "\n" + LabelConstants.NO_VIABLE_RESULTS_MESSAGE;
		} else if (!this.appliedFilters.isEmpty() && resultSetSize == 0) {
			finishedComputingLogMessage += LabelConstants.AFTER_FILTER_MESSAGE_SUFFIX
					+ "\n" + LabelConstants.SUGGEST_CLEAR_FILTERS_MESSAGE;
		} else if (this.appliedFilters.isEmpty()) {
			finishedComputingLogMessage += LabelConstants.SEARCH_NO_FILTER_MESSAGE_SUFFIX;
		} else {
			finishedComputingLogMessage += LabelConstants.AFTER_FILTER_MESSAGE_SUFFIX;
		}

		final TextArea logTextArea = ViewController.getInstance().getLogView()
				.getLogTextArea();
		final String existingLogText = logTextArea.getText();
		logTextArea.setText(existingLogText + "\n" + finishedComputingLogMessage);
	}
}
