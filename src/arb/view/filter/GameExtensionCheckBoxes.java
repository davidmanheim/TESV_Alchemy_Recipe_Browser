package arb.view.filter;

import java.util.HashSet;
import java.util.Set;

import arb.view.util.LabelConstants;
import arb.view.util.TooltipConstants;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * This class represents a series of check boxes which the user can interact
 * with in the filter ingredients view.
 */
public class GameExtensionCheckBoxes extends HBox {

	private static final int PADDING_BETWEEN_CHECKBOXES = 12;

	private final Set<CheckBox> checkBoxes;

	public GameExtensionCheckBoxes() {
		super();
		checkBoxes = new HashSet<>();
		this.configure();
	}

	public Set<CheckBox> getCheckBoxes() {
		return checkBoxes;
	}

	private void configure() {
		this.setMaxHeight(150);
		this.setAlignment(Pos.CENTER);
		this.createChildElements();
	}

	private void createChildElements() {
		ObservableList<Node> children = this.getChildren();
		CheckBox baseGameCheckbox = new CheckBox();
		baseGameCheckbox.setSelected(true);
		baseGameCheckbox.setText(LabelConstants.ALLOW_BASE_GAME);
		baseGameCheckbox.setTooltip(new Tooltip(TooltipConstants.ALLOW_BASE_GAME));
		this.checkBoxes.add(baseGameCheckbox);
		children.add(baseGameCheckbox);
		CheckBox dawnguardCheckbox = new CheckBox();
		dawnguardCheckbox.setSelected(true);
		dawnguardCheckbox.setText(LabelConstants.ALLOW_DAWNGUARD);
		dawnguardCheckbox.setTooltip(new Tooltip(TooltipConstants.ALLOW_DAWNGUARD));
		this.checkBoxes.add(dawnguardCheckbox);
		children.add(dawnguardCheckbox);
		CheckBox dragonbornCheckbox = new CheckBox();
		dragonbornCheckbox.setSelected(true);
		dragonbornCheckbox.setText(LabelConstants.ALLOW_DRAGONBORN);
		dragonbornCheckbox.setTooltip(new Tooltip(TooltipConstants.ALLOW_DRAGONBORN));
		this.checkBoxes.add(dragonbornCheckbox);
		children.add(dragonbornCheckbox);
		CheckBox hearthfireCheckbox = new CheckBox();
		hearthfireCheckbox.setSelected(true);
		hearthfireCheckbox.setText(LabelConstants.ALLOW_HEARTHFIRE);
		hearthfireCheckbox.setTooltip(new Tooltip(TooltipConstants.ALLOW_HEARTHFIRE));
		this.checkBoxes.add(hearthfireCheckbox);
		children.add(hearthfireCheckbox);

		// Add spacing between check boxes.
		HBox.setMargin(baseGameCheckbox, new Insets(0, PADDING_BETWEEN_CHECKBOXES, 0, 0));
		HBox.setMargin(dawnguardCheckbox, new Insets(0, PADDING_BETWEEN_CHECKBOXES, 0, 0));
		HBox.setMargin(dragonbornCheckbox, new Insets(0, PADDING_BETWEEN_CHECKBOXES, 0, 0));
	}
}
