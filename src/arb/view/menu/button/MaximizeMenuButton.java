package arb.view.menu.button;

import arb.core.ModelController;
import arb.core.ViewController;
import arb.view.util.ResourcePathConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MaximizeMenuButton extends AbstractMenuButton {

	public MaximizeMenuButton() {
		super(ResourcePathConstants.MAXIMIZE_ICON);
		ViewController.getInstance().setMaximizeWindowButton(this);
		this.configure();
	}

	@Override
	protected void configure() {
		super.configure();
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this.onClickMaximize());
	}

	private EventHandler<MouseEvent> onClickMaximize() {
		return event -> {
			if (ViewController.getInstance().getStage().isMaximized()) {
				ModelController.getInstance().transitionToRestoredState();
			} else {
				ModelController.getInstance().transitionToMaximizedState();
			}
		};
	}
}
