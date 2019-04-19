package arb.view.filter;

import arb.core.ModelController;
import arb.util.LabelConstants;
import arb.util.TooltipConstants;
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
 * filter ingredients view.
 */
public class FilterPotionsActionButtons extends HBox {

	public FilterPotionsActionButtons() {
		super();
		this.configure();
	}

	private void configure() {
		this.setMaxHeight(150);
		this.setAlignment(Pos.CENTER);
		this.createChildElements();
	}

	public void createChildElements() {
		final ObservableList<Node> children = this.getChildren();
		final Button resetButton = new Button(LabelConstants.RESET);
		resetButton.setOnAction(this.onClickReset());
		resetButton.setTooltip(new Tooltip(TooltipConstants.RESET_FILTER));
		final Button filterButton = new Button(LabelConstants.FILTER);
		filterButton.setOnAction(this.onClickFilter());
		filterButton.setTooltip(new Tooltip(TooltipConstants.APPLY_FILTER));
		children.add(resetButton);
		// Add spacing between buttons.
		HBox.setMargin(resetButton, new Insets(0, 50, 0, 0));
		children.add(filterButton);
	}

	private EventHandler<ActionEvent> onClickFilter() {
		return event -> ModelController.getInstance().transitionToApplyFilterState();
	}

	private EventHandler<ActionEvent> onClickReset() {
		return event -> ModelController.getInstance().transitionToResetFilterState();
	}
}
