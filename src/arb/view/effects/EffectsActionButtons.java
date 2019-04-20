package arb.view.effects;

import arb.core.ModelController;
import arb.view.util.LabelConstants;
import arb.view.util.TooltipConstants;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * This class represents the series of buttons the user can interact with in the
 * effects view.
 */
public class EffectsActionButtons extends HBox {

	public EffectsActionButtons() {
		super();
		this.configure();
	}

	private void configure() {
		this.setMaxHeight(150);
		this.setAlignment(Pos.CENTER);
		this.createChildElements();
	}

	private void createChildElements() {
		final ObservableList<Node> children = this.getChildren();
		final Button resetButton = new Button(LabelConstants.RESET);
		resetButton.setOnAction(this.onClickReset());
		resetButton.setTooltip(new Tooltip(TooltipConstants.RESET_SEARCH));
		final Button searchButton = new Button(LabelConstants.SEARCH);
		searchButton.setOnAction(this.onClickSearch());
		searchButton.setTooltip(new Tooltip(TooltipConstants.DO_SEARCH));
		children.add(resetButton);
		// Add spacing between buttons.
		HBox.setMargin(resetButton, new Insets(0, 50, 0, 0));
		children.add(searchButton);
	}

	private EventHandler<ActionEvent> onClickSearch() {
		return event -> ModelController.getInstance().transitionToExecuteSearchState();
	}

	private EventHandler<ActionEvent> onClickReset() {
		return event -> ModelController.getInstance().transitionToResetSearchState();
	}
}
