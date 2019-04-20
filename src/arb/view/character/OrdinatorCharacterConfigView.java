package arb.view.character;

import java.util.function.UnaryOperator;

import arb.core.ModelController;
import arb.factories.LayoutFactory;
import arb.view.control.LabeledComboBox;
import arb.view.control.LabeledTextField;
import arb.view.util.LabelConstants;
import arb.view.util.TooltipConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

/**
 * This class represents a view the user can use to configure their perks if
 * they use the ordinator perk mod.
 */
public class OrdinatorCharacterConfigView extends AbstractCharacterConfigView {

	private LabeledTextField alchemyMasteryLevelInput;

	private LabeledComboBox physicianComboBox;

	private CheckBox advancedLabCheckbox;

	private CheckBox poisonerCheckbox;

	// That which does not kill you... is unnecessarily long for a variable name,
	// so it's abbreviated.
	private CheckBox twdnkyCheckbox;

	public OrdinatorCharacterConfigView() {
		super();
		this.configure();
	}

	public LabeledTextField getAlchemyMasteryLevelInput() {
		return this.alchemyMasteryLevelInput;
	}

	public LabeledComboBox getPhysicianComboBox() {
		return this.physicianComboBox;
	}

	public CheckBox getAdvancedLabCheckbox() {
		return this.advancedLabCheckbox;
	}

	public CheckBox getPoisonerCheckbox() {
		return this.poisonerCheckbox;
	}

	public CheckBox getTwdnkyCheckbox() {
		return this.twdnkyCheckbox;
	}

	@Override
	protected void configure() {
		super.configure();
		this.createChildElements();
	}

	@Override
	protected void createChildElements() {
		final ObservableList<Node> children = this.getChildren();

		this.alchemyMasteryLevelInput = LayoutFactory.getInstance().createLabeledTextField();
		VBox.setMargin(this.alchemyMasteryLevelInput,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.alchemyMasteryLevelInput);
		this.alchemyMasteryLevelInput.getLabel().setText(LabelConstants.ALCHEMY_MASTERY);
		final TextField textField = this.alchemyMasteryLevelInput.getTextField();
		final UnaryOperator<Change> integerFilter = change -> {
			final String newText = change.getControlNewText();
			if (newText.matches("^$|0|1|2")) {
				return change;
			}
			return null;
		};
		textField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		textField.setTooltip(new Tooltip(TooltipConstants.ALCHEMY_MASTERY));

		this.physicianComboBox = LayoutFactory.getInstance().createLabeledComboBox();
		VBox.setMargin(this.physicianComboBox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.physicianComboBox);
		this.physicianComboBox.getLabel().setText(LabelConstants.PHYSICIAN);
		final ComboBox<String> comboBox = this.physicianComboBox.getComboBox();
		comboBox.setItems(FXCollections.observableArrayList("", LabelConstants.PHYSICIAN_HEALTH,
				LabelConstants.PHYSICIAN_MAGICKA, LabelConstants.PHYSICIAN_STAMINA));
		comboBox.setTooltip(new Tooltip(TooltipConstants.PHYSICIAN_ORDINATOR));

		this.advancedLabCheckbox = new CheckBox();
		this.advancedLabCheckbox.setText(LabelConstants.ADVANCED_LAB);
		VBox.setMargin(this.advancedLabCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		this.advancedLabCheckbox.setTooltip(new Tooltip(TooltipConstants.ADVANCED_LAB));
		children.add(this.advancedLabCheckbox);

		this.poisonerCheckbox = new CheckBox();
		this.poisonerCheckbox.setText(LabelConstants.POISONER);
		VBox.setMargin(this.poisonerCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		this.poisonerCheckbox.setTooltip(new Tooltip(TooltipConstants.POISONER));
		children.add(this.poisonerCheckbox);

		this.twdnkyCheckbox = new CheckBox();
		this.twdnkyCheckbox.setText(LabelConstants.THAT_WHICH_DOES_NOT_KILL_YOU);
		// Left and right padding trimmed slightly to fit the full label.
		VBox.setMargin(this.twdnkyCheckbox, new Insets(DEFAULT_PADDING, 5, DEFAULT_PADDING, 5));
		this.twdnkyCheckbox.setTooltip(new Tooltip(TooltipConstants.THAT_WHICH_DOES_NOT_KILL_YOU));
		children.add(this.twdnkyCheckbox);

		final Button resetButton = new Button(LabelConstants.RESET);
		resetButton.setOnAction(this.onClickReset());
		resetButton.setTooltip(new Tooltip(TooltipConstants.RESET_SEARCH));
		VBox.setMargin(resetButton, new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(resetButton);
	}

	private EventHandler<ActionEvent> onClickReset() {
		return event -> ModelController.getInstance().transitionToResetOrdinatorCharacterConfigState();
	}
}
