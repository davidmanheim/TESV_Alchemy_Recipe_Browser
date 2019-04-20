package arb.state.model;

import arb.core.ViewController;
import arb.model.computation.PotionResultWrapper;
import arb.view.character.OrdinatorCharacterConfigView;
import arb.view.util.LabelConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains code to run when the user clears the existing search
 * (presses "Reset" on the effects tab.)
 */
public class ResetOrdinatorCharacterConfigState extends ApplicationModelState {

	public ResetOrdinatorCharacterConfigState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		this.characterConfig.resetOrdinatorCharacterConfig();
		this.tableViewEntries.clear();
		final ObservableList<PotionResultWrapper> tableData = FXCollections.observableList(this.tableViewEntries);
		ViewController.getInstance().getResultsTableView().setItems(tableData);
		ViewController.getInstance().getLogView().getLogTextArea().setText(LabelConstants.ORDINATOR_CHARACTER_CONFIG_RESET);
		final OrdinatorCharacterConfigView ordinatorCharacterConfigView = ViewController.getInstance()
				.getOrdinatorCharacterConfigView();
		ordinatorCharacterConfigView.reset();
	}

}
