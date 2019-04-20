package arb.state.model;

import arb.core.ViewController;
import arb.model.computation.PotionResultWrapper;
import arb.view.character.BaseGameCharacterConfigView;
import arb.view.util.LabelConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains code to run when the user clears the existing search
 * (presses "Reset" on the effects tab.)
 */
public class ResetBaseGameCharacterConfigState extends ApplicationModelState {

	public ResetBaseGameCharacterConfigState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		this.characterConfig.resetBaseGameCharacterConfig();
		this.tableViewEntries.clear();
		final ObservableList<PotionResultWrapper> tableData = FXCollections.observableList(this.tableViewEntries);
		ViewController.getInstance().getResultsTableView().setItems(tableData);
		ViewController.getInstance().getLogView().getLogTextArea().setText(LabelConstants.BASE_CHARACTER_CONFIG_RESET);
		final BaseGameCharacterConfigView baseGameCharacterConfigView = ViewController.getInstance()
				.getBaseGameCharacterConfigView();
		baseGameCharacterConfigView.reset();
	}

}
