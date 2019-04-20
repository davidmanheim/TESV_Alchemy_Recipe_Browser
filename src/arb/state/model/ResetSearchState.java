package arb.state.model;

import java.util.HashSet;

import arb.core.ViewController;
import arb.model.computation.PotionResultWrapper;
import arb.view.effects.EffectsView;
import arb.view.util.LabelConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains code to run when the user clears the existing search
 * (presses "Reset" on the effects tab.)
 */
public class ResetSearchState extends ApplicationModelState {

	public ResetSearchState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		this.selectedEffects = new HashSet<>();
		final EffectsView effectsView = ViewController.getInstance().getEffectsView();
		effectsView.reset();
		this.baseResultSet.clear();
		this.filteredResultSet.clear();
		this.tableViewEntries.clear();
		final ObservableList<PotionResultWrapper> tableData = FXCollections.observableList(this.tableViewEntries);
		ViewController.getInstance().getResultsTableView().setItems(tableData);
		ViewController.getInstance().getLogView().getLogTextArea().setText(LabelConstants.WELCOME_MESSAGE);
	}

}
