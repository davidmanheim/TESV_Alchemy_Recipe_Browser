package arb.state.model;

import arb.core.ViewController;
import arb.factories.ModelFactory;
import arb.model.computation.PotionResultWrapper;
import arb.view.filter.FilterPotionsView;
import arb.view.util.LabelConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains code to run when the user clears filters (presses "Reset"
 * on the filter tab).
 */
public class ResetFilterState extends ApplicationModelState {

	public ResetFilterState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		this.appliedFilters.clear();
		final FilterPotionsView filterIngredientsView = ViewController.getInstance().getFilterPotionsView();
		filterIngredientsView.reset();
		if (!this.baseResultSet.isEmpty()) {
			this.clearFilters();
			this.tableViewEntries.clear();
			this.filteredResultSet
					.forEach(potion -> this.tableViewEntries.add(ModelFactory.getInstance().createPotionResultWrapper(potion)));
			final ObservableList<PotionResultWrapper> tableData = FXCollections.observableList(this.tableViewEntries);
			ViewController.getInstance().getResultsTableView().setItems(tableData);
			final int resultSetSize = this.filteredResultSet.size();
			ViewController.getInstance().getLogView().getLogTextArea()
					.setText(LabelConstants.BULLET_POINT + resultSetSize + LabelConstants.SEARCH_NO_FILTER_MESSAGE_SUFFIX);
		} else {
			ViewController.getInstance().getLogView().getLogTextArea().setText(LabelConstants.CLEARED_FILTER_MESSAGE);
		}
	}
}
