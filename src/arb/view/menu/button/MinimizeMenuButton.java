package arb.view.menu.button;

import arb.core.ModelController;
import arb.view.util.ResourcePathConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MinimizeMenuButton extends AbstractMenuButton {

	public MinimizeMenuButton() {
		super(ResourcePathConstants.MINIMIZE_ICON);
		this.configure();
	}

	@Override
	protected void configure() {
		super.configure();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickMinimize());
	}

	private EventHandler<MouseEvent> onClickMinimize() {
		return event -> ModelController.getInstance().transitionToMinimizedState();
	}

}
