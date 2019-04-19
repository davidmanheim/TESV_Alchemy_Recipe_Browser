package arb.view.menu;

import javafx.scene.layout.Region;

/**
 * This class fills the gap between two elements in an HBox. It is used by
 * ApplicationTitleBar to have a left and a right menu.
 */
public class ApplicationMenuSpacer extends Region {
	
	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	public ApplicationMenuSpacer() {
		this.configure();
	}

	private void configure() {
		this.setMaxHeight(50);
		this.getStyleClass().add(MENU_BAR_CSS_CLASS);
	}
}
