package arb.view.character;

import java.util.function.UnaryOperator;

import arb.core.ModelController;
import arb.factories.LayoutFactory;
import arb.util.LabelConstants;
import arb.util.TooltipConstants;
import arb.view.control.LabeledTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

/**
 * This class represents a view the user can use to configure their perks if
 * they use the base (un-modded) game.
 */
public class BaseGameCharacterConfigView extends AbstractCharacterConfigView {

	private LabeledTextField alchemistLevelInput;

	private CheckBox physicianCheckbox;

	private CheckBox benefactorCheckbox;

	private CheckBox poisonerCheckbox;

	public BaseGameCharacterConfigView() {
		super();
		this.configure();
	}

	public LabeledTextField getAlchemistLevelInput() {
		return this.alchemistLevelInput;
	}

	public CheckBox getPhysicianCheckbox() {
		return this.physicianCheckbox;
	}

	public CheckBox getBenefactorCheckbox() {
		return this.benefactorCheckbox;
	}

	public CheckBox getPoisonerCheckbox() {
		return this.poisonerCheckbox;
	}

	@Override
	protected void configure() {
		super.configure();
		this.createChildElements();
	}

	@Override
	protected void createChildElements() {
		final ObservableList<Node> children = this.getChildren();

		this.alchemistLevelInput = LayoutFactory.getInstance().createLabeledTextField();
		VBox.setMargin(this.alchemistLevelInput,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.alchemistLevelInput);
		this.alchemistLevelInput.getLabel().setText(LabelConstants.ALCHEMIST);
		final TextField textField = this.alchemistLevelInput.getTextField();
		final UnaryOperator<Change> integerFilter = change -> {
			final String newText = change.getControlNewText();
			if (newText.matches("^$|0|1|2|3|4|5")) {
				return change;
			}
			return null;
		};
		textField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		textField.setTooltip(new Tooltip(TooltipConstants.ALCHEMIST));

		this.physicianCheckbox = new CheckBox();
		this.physicianCheckbox.setText(LabelConstants.PHYSICIAN);
		VBox.setMargin(this.physicianCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		this.physicianCheckbox.setTooltip(new Tooltip(TooltipConstants.PHYSICIAN));
		children.add(this.physicianCheckbox);

		this.benefactorCheckbox = new CheckBox();
		this.benefactorCheckbox.setText(LabelConstants.BENEFACTOR);
		VBox.setMargin(this.benefactorCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		this.benefactorCheckbox.setTooltip(new Tooltip(TooltipConstants.BENEFACTOR));
		children.add(this.benefactorCheckbox);

		this.poisonerCheckbox = new CheckBox();
		this.poisonerCheckbox.setText(LabelConstants.POISONER);
		VBox.setMargin(this.poisonerCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		this.poisonerCheckbox.setTooltip(new Tooltip(TooltipConstants.POISONER));
		children.add(this.poisonerCheckbox);

		final Button resetButton = new Button(LabelConstants.RESET);
		resetButton.setOnAction(this.onClickReset());
		resetButton.setTooltip(new Tooltip(TooltipConstants.RESET_SEARCH));
		VBox.setMargin(resetButton, new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(resetButton);
	}

	private EventHandler<ActionEvent> onClickReset() {
		return event -> ModelController.getInstance().transitionToResetBaseGameCharacterConfigState();
	}
}
