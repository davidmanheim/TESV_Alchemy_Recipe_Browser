package arb.view.filter;

import arb.util.LabelConstants;
import arb.util.TooltipConstants;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * This class represents the series of buttons the user can interact with in the
 * filter ingredients view.
 */
public class FilterPotionByTypeRadioButtons extends HBox {

	private static final int PADDING_BETWEEN_RADIO_BUTTONS = 2;

	private RadioButton bothRadioButton;

	private RadioButton onlyPotionsRadioButton;

	private RadioButton onlyPoisonsRadioButton;

	public FilterPotionByTypeRadioButtons() {
		super();
		this.configure();
	}

	public RadioButton getBothRadioButton() {
		return bothRadioButton;
	}

	public RadioButton getOnlyPotionsRadioButton() {
		return onlyPotionsRadioButton;
	}

	public RadioButton getOnlyPoisonsRadioButton() {
		return onlyPoisonsRadioButton;
	}

	private void configure() {
		this.setMaxHeight(150);
		this.setAlignment(Pos.CENTER);
		this.createChildElements();
	}

	public void createChildElements() {
		ObservableList<Node> children = this.getChildren();
		this.bothRadioButton = new RadioButton(LabelConstants.POTIONS_AND_POISONS);
		bothRadioButton.setTooltip(new Tooltip(TooltipConstants.POTIONS_AND_POISONS));
		bothRadioButton.setSelected(true);
		children.add(bothRadioButton);

		this.onlyPotionsRadioButton = new RadioButton(LabelConstants.ONLY_POTIONS);
		onlyPotionsRadioButton.setTooltip(new Tooltip(TooltipConstants.ONLY_POTIONS));
		children.add(onlyPotionsRadioButton);

		this.onlyPoisonsRadioButton = new RadioButton(LabelConstants.ONLY_POISONS);
		onlyPoisonsRadioButton.setTooltip(new Tooltip(TooltipConstants.ONLY_POISONS));
		children.add(onlyPoisonsRadioButton);

		// Add spacing between radio buttons.
		HBox.setMargin(bothRadioButton, new Insets(0, PADDING_BETWEEN_RADIO_BUTTONS, 0, 0));
		HBox.setMargin(onlyPotionsRadioButton, new Insets(0, PADDING_BETWEEN_RADIO_BUTTONS, 0, 0));

		// Make buttons mutually exclusive.
		ToggleGroup toggleGroup = new ToggleGroup();
		bothRadioButton.setToggleGroup(toggleGroup);
		onlyPotionsRadioButton.setToggleGroup(toggleGroup);
		onlyPoisonsRadioButton.setToggleGroup(toggleGroup);
	}
}
