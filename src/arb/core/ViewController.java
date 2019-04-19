package arb.core;

import arb.view.LogView;
import arb.view.character.BaseGameCharacterConfigView;
import arb.view.character.OrdinatorCharacterConfigView;
import arb.view.character.SharedCharacterConfigView;
import arb.view.effects.EffectsActionButtons;
import arb.view.effects.EffectsView;
import arb.view.filter.FilterPotionByTypeRadioButtons;
import arb.view.filter.FilterPotionsActionButtons;
import arb.view.filter.FilterPotionsView;
import arb.view.filter.GameExtensionCheckBoxes;
import arb.view.menu.button.MaximizeMenuButton;
import arb.view.result.ResultTableView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * A controller class to facilitate view changes, as result of a model change.
 * This class stores references to key existing views for updating. A reference
 * to static (container) views is not stored.
 */
public class ViewController {

	private static ViewController viewControllerInstance;

	public static ViewController getInstance() {
		if (viewControllerInstance == null) {
			viewControllerInstance = new ViewController();
		}
		return viewControllerInstance;
	}

	private Stage stage;

	private EffectsActionButtons effectsActionButtons;

	private EffectsView effectsView;

	private FilterPotionsActionButtons filterPotionsActionButtons;

	private FilterPotionsView filterPotionsView;

	private GameExtensionCheckBoxes gameExtensionCheckBoxes;

	private ResultTableView resultsTableView;

	private LogView logView;

	private SharedCharacterConfigView sharedCharacterConfigView;

	private BaseGameCharacterConfigView baseGameCharacterConfigView;

	private OrdinatorCharacterConfigView ordinatorCharacterConfigView;

	private FilterPotionByTypeRadioButtons filterPotionByTypeRadioButtons;

	private Button maximizeWindowButton;

	private Stage helpStage;

	private ViewController() {
		this.stage = null;
		this.effectsActionButtons = null;
		this.effectsView = null;
		this.filterPotionsActionButtons = null;
		this.filterPotionByTypeRadioButtons = null;
		this.filterPotionsView = null;
		this.gameExtensionCheckBoxes = null;
		this.resultsTableView = null;
		this.maximizeWindowButton = null;
		this.helpStage = null;
	}

	public Stage getStage() {
		return this.stage;
	}

	public EffectsActionButtons getEffectsActionButtons() {
		return this.effectsActionButtons;
	}

	public EffectsView getEffectsView() {
		return this.effectsView;
	}

	public FilterPotionsActionButtons getFilterPotionsActionButtons() {
		return this.filterPotionsActionButtons;
	}

	public FilterPotionByTypeRadioButtons getFilterPotionByTypeRadioButtons() {
		return this.filterPotionByTypeRadioButtons;
	}

	public FilterPotionsView getFilterPotionsView() {
		return this.filterPotionsView;
	}

	public GameExtensionCheckBoxes getGameExtensionCheckBoxes() {
		return this.gameExtensionCheckBoxes;
	}

	public ResultTableView getResultsTableView() {
		return this.resultsTableView;
	}

	public LogView getLogView() {
		return this.logView;
	}

	public SharedCharacterConfigView getSharedCharacterConfigView() {
		return this.sharedCharacterConfigView;
	}

	public BaseGameCharacterConfigView getBaseGameCharacterConfigView() {
		return this.baseGameCharacterConfigView;
	}

	public OrdinatorCharacterConfigView getOrdinatorCharacterConfigView() {
		return this.ordinatorCharacterConfigView;
	}

	public Button getMaximizeWindowButton() {
		return this.maximizeWindowButton;
	}

	public Stage getHelpStage() {
		return this.helpStage;
	}

	public void setStage(final Stage stage) {
		this.stage = stage;
	}

	public void setEffectsActionButtons(final EffectsActionButtons effectsActionButtons) {
		this.effectsActionButtons = effectsActionButtons;
	}

	public void setEffectsView(final EffectsView effectsView) {
		this.effectsView = effectsView;
	}

	public void setFilterPotionByTypeRadioButtons(final FilterPotionByTypeRadioButtons filterPotionByTypeRadioButtons) {
		this.filterPotionByTypeRadioButtons = filterPotionByTypeRadioButtons;
	}

	public void setFilterPotionsActionButtons(final FilterPotionsActionButtons filterPotionsActionButtons) {
		this.filterPotionsActionButtons = filterPotionsActionButtons;
	}

	public void setFilterPotionsView(final FilterPotionsView filterPotionsView) {
		this.filterPotionsView = filterPotionsView;
	}

	public void setGameExtensionCheckBoxes(final GameExtensionCheckBoxes gameExtensionCheckBoxes) {
		this.gameExtensionCheckBoxes = gameExtensionCheckBoxes;
	}

	public void setResultsTableView(final ResultTableView resultsTableView) {
		this.resultsTableView = resultsTableView;
	}

	public void setLogView(final LogView logView) {
		this.logView = logView;
	}

	public void setSharedCharacterConfigView(final SharedCharacterConfigView sharedCharacterConfigView) {
		this.sharedCharacterConfigView = sharedCharacterConfigView;
	}

	public void setBaseGameCharacterConfigView(final BaseGameCharacterConfigView baseGameCharacterConfigView) {
		this.baseGameCharacterConfigView = baseGameCharacterConfigView;
	}

	public void setOrdinatorCharacterConfigView(final OrdinatorCharacterConfigView ordinatorCharacterConfigView) {
		this.ordinatorCharacterConfigView = ordinatorCharacterConfigView;
	}

	public void setMaximizeWindowButton(final MaximizeMenuButton maximizeMenuButton) {
		this.maximizeWindowButton = maximizeMenuButton;
	}

	public void setHelpStage(final Stage helpStage) {
		this.helpStage = helpStage;
	}

}
