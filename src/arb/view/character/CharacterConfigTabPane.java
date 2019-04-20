package arb.view.character;

import arb.factories.LayoutFactory;
import arb.view.util.LabelConstants;
import arb.view.util.TooltipConstants;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;

/**
 * This class represents the character configuration tab pane in the
 * application.
 */
public class CharacterConfigTabPane extends TabPane {

	public CharacterConfigTabPane() {
		super();
		this.configure();
	}

	private void configure() {
		this.createChildElements();
	}

	private void createChildElements() {
		ObservableList<Tab> tabs = this.getTabs();
		Tab baseGameTab = new Tab();
		baseGameTab.setText(LabelConstants.BASE_GAME);
		baseGameTab.setTooltip(new Tooltip(TooltipConstants.BASE_GAME));
		baseGameTab.setContent(LayoutFactory.getInstance().createBaseGameCharacterConfigView());
		Tab ordinatorTab = new Tab();
		ordinatorTab.setText(LabelConstants.ORDINATOR);
		ordinatorTab.setTooltip(new Tooltip(TooltipConstants.ORDINATOR));
		ordinatorTab.setContent(LayoutFactory.getInstance().createOrdinatorCharacterConfigView());
		Tab sharedTab = new Tab();
		sharedTab.setText(LabelConstants.SHARED);
		sharedTab.setTooltip(new Tooltip(TooltipConstants.SHARED));
		sharedTab.setContent(LayoutFactory.getInstance().createSharedCharacterConfigView());
		tabs.add(baseGameTab);
		tabs.add(ordinatorTab);
		tabs.add(sharedTab);
		tabs.forEach(tab -> tab.setClosable(false));
	}
}
