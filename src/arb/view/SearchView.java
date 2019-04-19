package arb.view;

import arb.factories.LayoutFactory;
import arb.util.LabelConstants;
import arb.util.TooltipConstants;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;

/**
 * This class represents the search tab pane in the application.
 */
public class SearchView extends TabPane {

	public SearchView() {
		super();
		this.configure();
	}

	private void configure() {
		this.createChildElements();
	}

	private void createChildElements() {
		ObservableList<Tab> tabs = this.getTabs();
		Tab effectsTab = new Tab();
		effectsTab.setClosable(false);
		effectsTab.setText(LabelConstants.EFFECTS);
		effectsTab.setContent(LayoutFactory.getInstance().createEffectsView());
		effectsTab.setTooltip(new Tooltip(TooltipConstants.EFFECTS));
		Tab filterPotionsTab = new Tab();
		filterPotionsTab.setClosable(false);
		filterPotionsTab.setText(LabelConstants.FILTER_POTIONS);
		filterPotionsTab.setContent(LayoutFactory.getInstance().createFilterPotionsView());
		filterPotionsTab.setTooltip(new Tooltip(TooltipConstants.FILTER_INGREDIENTS));
		tabs.add(effectsTab);
		tabs.add(filterPotionsTab);
	}
}
