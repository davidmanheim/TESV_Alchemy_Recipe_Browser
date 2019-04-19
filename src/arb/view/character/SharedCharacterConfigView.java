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
 * This class represents a view the user can use to configure their character
 * stats that are not mod specific, such as Alchemy skill level.
 */
public class SharedCharacterConfigView extends AbstractCharacterConfigView {

	private static final int TEXT_FIELD_WIDTH = 40;

	private LabeledTextField skillLevelInput;

	private LabeledTextField totalEnchantmentsInput;

	private CheckBox seekerOfShadowsCheckbox;

	public SharedCharacterConfigView() {
		super();
		this.configure();
	}

	public LabeledTextField getSkillLevelInput() {
		return this.skillLevelInput;
	}

	public LabeledTextField getTotalEnchantmentsInput() {
		return this.totalEnchantmentsInput;
	}

	public CheckBox getSeekerOfShadowsCheckbox() {
		return this.seekerOfShadowsCheckbox;
	}

	@Override
	protected void configure() {
		super.configure();
		this.createChildElements();
	}

	@Override
	protected void createChildElements() {
		final ObservableList<Node> children = this.getChildren();

		this.skillLevelInput = LayoutFactory.getInstance().createLabeledTextField();
		VBox.setMargin(this.skillLevelInput,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.skillLevelInput);
		this.skillLevelInput.getLabel().setText(LabelConstants.SKILL_LEVEL);
		final TextField skillLevelTextField = this.skillLevelInput.getTextField();
		final UnaryOperator<Change> skillLevelIntegerFilter = change -> {
			final String newText = change.getControlNewText();
			if (newText.matches("^$|\\d{1,3}")) {
				return change;
			}
			return null;
		};
		skillLevelTextField.setMaxWidth(TEXT_FIELD_WIDTH);
		skillLevelTextField
				.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 15, skillLevelIntegerFilter));
		skillLevelTextField.setTooltip(new Tooltip(TooltipConstants.SKILL_LEVEL));

		this.totalEnchantmentsInput = LayoutFactory.getInstance().createLabeledTextField();
		VBox.setMargin(this.totalEnchantmentsInput,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.totalEnchantmentsInput);
		this.totalEnchantmentsInput.getLabel().setText(LabelConstants.ENCHANTMENT);
		final TextField totalEnchantmentsTextField = this.totalEnchantmentsInput.getTextField();
		final UnaryOperator<Change> totalEnchantmentsIntegerFilter = change -> {
			final String newText = change.getControlNewText();
			if (newText.matches("^$|\\d{1,3}")) {
				return change;
			}
			return null;
		};
		totalEnchantmentsTextField.setMaxWidth(TEXT_FIELD_WIDTH);
		totalEnchantmentsTextField
				.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, totalEnchantmentsIntegerFilter));
		totalEnchantmentsTextField.setTooltip(new Tooltip(TooltipConstants.ENCHANTMENT));

		this.seekerOfShadowsCheckbox = new CheckBox();
		this.seekerOfShadowsCheckbox.setText(LabelConstants.SEEKER_OF_SHADOWS);
		VBox.setMargin(this.seekerOfShadowsCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		this.seekerOfShadowsCheckbox.setTooltip(new Tooltip(TooltipConstants.SEEKER_OF_SHADOWS));
		children.add(this.seekerOfShadowsCheckbox);

		final Button resetButton = new Button(LabelConstants.RESET);
		resetButton.setOnAction(this.onClickReset());
		resetButton.setTooltip(new Tooltip(TooltipConstants.RESET_SEARCH));
		VBox.setMargin(resetButton, new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(resetButton);
	}

	private EventHandler<ActionEvent> onClickReset() {
		return event -> ModelController.getInstance().transitionToResetSharedCharacterConfigState();
	}
}
