package arb.view.menu.button;

import arb.factories.MenuFactory;
import arb.view.menu.ActionMenu;
import arb.view.util.ResourcePathConstants;
import javafx.geometry.Side;

public class ContextMenuButton extends AbstractMenuButton {

	public ContextMenuButton() {
		super(ResourcePathConstants.CONTEXT_MENU_ICON);
		this.configure();
	}

	@Override
	protected void configure() {
		super.configure();
		final ActionMenu contextMenu = MenuFactory.getInstance().createActionMenu();
		this.setOnMousePressed(event -> {
			if (contextMenu.isShowing()) {
				contextMenu.hide();
			} else {
				contextMenu.show(this, Side.BOTTOM, 0, 0);
			}
		});
		this.setContextMenu(contextMenu);
	}
}
