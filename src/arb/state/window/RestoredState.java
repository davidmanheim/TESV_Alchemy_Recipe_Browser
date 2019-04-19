package arb.state.window;

import arb.core.ViewController;
import arb.util.ResourcePathConstants;
import javafx.stage.Stage;

/**
 * This class contains code to run when the user restores the application size
 * (from maximized).
 */
public class RestoredState extends ApplicationWindowState {

	public RestoredState(final ApplicationWindowState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		final Stage stage = ViewController.getInstance().getStage();
		stage.setMaximized(false);
		this.restoreSavedBounds(stage);
		this.setIcon(ViewController.getInstance().getMaximizeWindowButton(), ResourcePathConstants.MAXIMIZE_ICON);

	}

}
