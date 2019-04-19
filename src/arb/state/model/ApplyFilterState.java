package arb.state.model;

import java.util.HashSet;
import java.util.Set;

import arb.core.ViewController;
import arb.factories.ModelFactory;
import arb.model.computation.PotionResultWrapper;
import arb.model.entity.GameExtension;
import arb.model.filter.Filter;
import arb.util.LabelConstants;
import arb.view.control.LabeledTextField;
import arb.view.filter.FilterPotionByTypeRadioButtons;
import arb.view.filter.FilterPotionsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

/**
 * This class contains code to run when the user applies filters to the result
 * view.
 */
public class ApplyFilterState extends ApplicationModelState {

	public ApplyFilterState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		final FilterPotionsView filterIngredientsView = ViewController.getInstance().getFilterPotionsView();
		final Set<Filter> filters = new HashSet<>();

		final Set<CheckBox> gameExtensionCheckBoxes = filterIngredientsView.getGameExtensionCheckBoxes().getCheckBoxes();
		gameExtensionCheckBoxes.stream().filter(checkBox -> !checkBox.isSelected()).forEach(checkBox -> {
			final GameExtension disallowedGameExtension = GameExtension.getFromEncoding(checkBox.getText());
			final Filter gameExtensionFilter = ModelFactory.getInstance().createGameExtensionFilter(disallowedGameExtension);
			filters.add(gameExtensionFilter);
		});

		final CheckBox harvestableOnlyCheckbox = filterIngredientsView.getHarvestableOnlyCheckbox();
		if (harvestableOnlyCheckbox.isSelected()) {
			final Filter harvestableFilter = ModelFactory.getInstance().createHarvestableFilter();
			filters.add(harvestableFilter);
		}

		final CheckBox pureResultsOnlyCheckbox = filterIngredientsView.getPureResultsOnlyCheckbox();
		if (pureResultsOnlyCheckbox.isSelected()) {
			final Filter purePotionFilter = ModelFactory.getInstance().createPurePotionFilter();
			filters.add(purePotionFilter);
		}

		final FilterPotionByTypeRadioButtons filterPotionByTypeRadioButtons = filterIngredientsView
				.getFilterPotionByTypeRadioButtons();
		if (filterPotionByTypeRadioButtons.getOnlyPotionsRadioButton().isSelected()) {
			final Filter purePotionFilter = ModelFactory.getInstance().createResultTypeFilter(true);
			filters.add(purePotionFilter);
		} else if (filterPotionByTypeRadioButtons.getOnlyPoisonsRadioButton().isSelected()) {
			final Filter purePotionFilter = ModelFactory.getInstance().createResultTypeFilter(false);
			filters.add(purePotionFilter);
		}

		final LabeledTextField matchIngredientLabeledTextField = filterIngredientsView.getMatchIngredientLabeledTextField();
		final String matchIngredientText = matchIngredientLabeledTextField.getTextField().getText();
		if (matchIngredientText != null && !matchIngredientText.isEmpty()) {
			final Filter matchIngredientFilter = ModelFactory.getInstance().createMatchIngredientFilter(matchIngredientText);
			filters.add(matchIngredientFilter);
		}

		final LabeledTextField matchEffectLabeledTextField = filterIngredientsView.getMatchEffectLabeledTextField();
		final String matchEffectText = matchEffectLabeledTextField.getTextField().getText();
		if (matchEffectText != null && !matchEffectText.isEmpty()) {
			final Filter matchEffectFilter = ModelFactory.getInstance().createMatchEffectFilter(matchEffectText);
			filters.add(matchEffectFilter);
		}

		this.appliedFilters = filters;
		final TextArea logTextArea = ViewController.getInstance().getLogView().getLogTextArea();
		if (!this.baseResultSet.isEmpty()) {
			this.applyFilters();
			this.tableViewEntries.clear();
			this.filteredResultSet
					.forEach(potion -> this.tableViewEntries.add(ModelFactory.getInstance().createPotionResultWrapper(potion)));
			final ObservableList<PotionResultWrapper> tableData = FXCollections.observableList(this.tableViewEntries);
			ViewController.getInstance().getResultsTableView().setItems(tableData);
			final int resultSetSize = this.filteredResultSet.size();
			final String newLogText = LabelConstants.BULLET_POINT + resultSetSize
					+ LabelConstants.AFTER_FILTER_MESSAGE_SUFFIX;
			logTextArea.setText(logTextArea.getText() + "\n" + newLogText);
		} else {
			logTextArea.setText(LabelConstants.AFTER_FILTER_NO_SEARCH_MESSAGE_SUFFIX);
		}
	}
}
