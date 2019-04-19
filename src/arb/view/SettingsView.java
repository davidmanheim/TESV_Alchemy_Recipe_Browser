package arb.view;

import arb.factories.LayoutFactory;
import arb.util.LabelConstants;
import arb.util.TooltipConstants;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;

/**
 * This class represents the top level tab pane in the application (on the left
 * side of the view).
 */
public class SettingsView extends TabPane {

	public SettingsView() {
		super();
		this.configure();
	}

	private void configure() {
		this.getStyleClass().add("arb-top-level-tab-pane");
		this.setMinHeight(425);
		this.setMaxHeight(425);
		this.createChildElements();
		this.getSelectionModel().select(1);
	}

	private void createChildElements() {
		ObservableList<Tab> tabs = this.getTabs();
		Tab characterTab = new Tab();
		characterTab.setClosable(false);
		characterTab.setText(LabelConstants.CHARACTER);
		characterTab.setContent(LayoutFactory.getInstance().createCharacterConfigView());
		characterTab.setTooltip(new Tooltip(TooltipConstants.CHARACTER));
		Tab searchTab = new Tab();
		searchTab.setClosable(false);
		searchTab.setText(LabelConstants.SEARCH);
		searchTab.setContent(LayoutFactory.getInstance().createSearchView());
		searchTab.setTooltip(new Tooltip(TooltipConstants.SEARCH));
		tabs.add(characterTab);
		tabs.add(searchTab);
	}
}
