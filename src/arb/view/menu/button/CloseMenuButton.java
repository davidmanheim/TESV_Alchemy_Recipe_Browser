package arb.view.menu.button;

import arb.core.ModelController;
import arb.util.ResourcePathConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CloseMenuButton extends AbstractMenuButton {

	public CloseMenuButton() {
		super(ResourcePathConstants.CLOSE_ICON);
		this.configure();
	}

	@Override
	protected void configure() {
		super.configure();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickClose());
	}

	private EventHandler<MouseEvent> onClickClose() {
		return event -> ModelController.getInstance().transitionToClosedState();
	}
}
