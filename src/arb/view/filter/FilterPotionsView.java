package arb.view.filter;

import arb.factories.LayoutFactory;
import arb.util.LabelConstants;
import arb.util.TooltipConstants;
import arb.view.control.LabeledTextField;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

/**
 * This class represents a series of form elements the user can interact with to
 * alter the filtering state of the application.
 */
public class FilterPotionsView extends VBox {

	private static final double DEFAULT_PADDING = 7.5;

	private CheckBox harvestableOnlyCheckbox;

	private CheckBox pureResultsOnlyCheckbox;

	private FilterPotionByTypeRadioButtons filterPotionByTypeRadioButtons;

	private GameExtensionCheckBoxes gameExtensionCheckBoxes;

	private LabeledTextField matchIngredientLabeledTextField;

	private LabeledTextField matchEffectLabeledTextField;

	public FilterPotionsView() {
		super();
		this.configure();
	}

	public CheckBox getHarvestableOnlyCheckbox() {
		return harvestableOnlyCheckbox;
	}

	public CheckBox getPureResultsOnlyCheckbox() {
		return pureResultsOnlyCheckbox;
	}

	public FilterPotionByTypeRadioButtons getFilterPotionByTypeRadioButtons() {
		return filterPotionByTypeRadioButtons;
	}

	public GameExtensionCheckBoxes getGameExtensionCheckBoxes() {
		return gameExtensionCheckBoxes;
	}

	public LabeledTextField getMatchIngredientLabeledTextField() {
		return matchIngredientLabeledTextField;
	}

	public LabeledTextField getMatchEffectLabeledTextField() {
		return matchEffectLabeledTextField;
	}

	public void reset() {
		this.getChildren().clear();
		this.createChildElements();
	}

	private void configure() {
		this.setMaxHeight(150);
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER);
		this.createChildElements();
	}

	private void createChildElements() {
		// TODO - add the following:
		// 1) Form for black list of ingredients?

		ObservableList<Node> children = this.getChildren();
		this.gameExtensionCheckBoxes = LayoutFactory.getInstance().createGameExtensionCheckBoxes();
		VBox.setMargin(gameExtensionCheckBoxes,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(gameExtensionCheckBoxes);
		this.harvestableOnlyCheckbox = new CheckBox();
		this.harvestableOnlyCheckbox.setText(LabelConstants.HARVESTABLE);
		this.harvestableOnlyCheckbox.setTooltip(new Tooltip(TooltipConstants.HARVESTABLE));
		VBox.setMargin(this.harvestableOnlyCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.harvestableOnlyCheckbox);
		this.pureResultsOnlyCheckbox = new CheckBox();
		this.pureResultsOnlyCheckbox.setText(LabelConstants.PURE_RESULTS);
		this.pureResultsOnlyCheckbox.setTooltip(new Tooltip(TooltipConstants.PURE_RESULTS));
		VBox.setMargin(this.pureResultsOnlyCheckbox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.pureResultsOnlyCheckbox);

		this.filterPotionByTypeRadioButtons = LayoutFactory.getInstance().createFilterPotionByTypeRadioButtons();
		// Left and right padding trimmed to fit the full labels.
		VBox.setMargin(this.filterPotionByTypeRadioButtons, new Insets(DEFAULT_PADDING, 3, DEFAULT_PADDING, 3));
		children.add(this.filterPotionByTypeRadioButtons);

		this.matchIngredientLabeledTextField = LayoutFactory.getInstance().createLabeledTextField();
		this.matchIngredientLabeledTextField.getLabel().setText(LabelConstants.MATCH_INGREDIENT);
		TextField matchIngredientTextField = this.matchIngredientLabeledTextField.getTextField();
		matchIngredientTextField.setText("");
		matchIngredientTextField.setMaxWidth(100);
		matchIngredientTextField.setTooltip(new Tooltip(TooltipConstants.MATCH_INGREDIENT));
		VBox.setMargin(this.matchIngredientLabeledTextField,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.matchIngredientLabeledTextField);

		this.matchEffectLabeledTextField = LayoutFactory.getInstance().createLabeledTextField();
		this.matchEffectLabeledTextField.getLabel().setText(LabelConstants.MATCH_EFFECT);
		TextField matchEffectTextField = this.matchEffectLabeledTextField.getTextField();
		matchEffectTextField.setText("");
		matchEffectTextField.setMaxWidth(100);
		matchEffectTextField.setTooltip(new Tooltip(TooltipConstants.MATCH_EFFECT));
		VBox.setMargin(this.matchEffectLabeledTextField,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(this.matchEffectLabeledTextField);

		FilterPotionsActionButtons filterIngredientsButtonBox = LayoutFactory.getInstance()
				.createFilterIngredientsActionButtons();
		VBox.setMargin(filterIngredientsButtonBox,
				new Insets(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
		children.add(filterIngredientsButtonBox);
	}

}
