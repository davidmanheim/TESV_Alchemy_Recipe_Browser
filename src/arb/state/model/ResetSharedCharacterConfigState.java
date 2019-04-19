package arb.state.model;

import arb.core.ViewController;
import arb.model.computation.PotionResultWrapper;
import arb.util.LabelConstants;
import arb.view.character.SharedCharacterConfigView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains code to run when the user clears the existing search
 * (presses "Reset" on the effects tab.)
 */
public class ResetSharedCharacterConfigState extends ApplicationModelState {

	public ResetSharedCharacterConfigState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		this.characterConfig.resetSharedCharacterConfig();
		this.tableViewEntries.clear();
		final ObservableList<PotionResultWrapper> tableData = FXCollections.observableList(this.tableViewEntries);
		ViewController.getInstance().getResultsTableView().setItems(tableData);
		ViewController.getInstance().getLogView().getLogTextArea().setText(LabelConstants.SHARED_CHARACTER_CONFIG_RESET);
		final SharedCharacterConfigView sharedCharacterConfigView = ViewController.getInstance()
				.getSharedCharacterConfigView();
		sharedCharacterConfigView.reset();
	}

}
