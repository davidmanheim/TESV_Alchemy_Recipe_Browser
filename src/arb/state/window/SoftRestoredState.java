package arb.state.window;

import arb.core.ViewController;
import arb.view.util.ResourcePathConstants;
import javafx.stage.Stage;

/**
 * This class contains code to run when the user drags the title bar of the
 * view, thus invoking a "restore" action".
 */
public class SoftRestoredState extends ApplicationWindowState {

	public SoftRestoredState(final ApplicationWindowState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		final Stage stage = ViewController.getInstance().getStage();
		stage.setMaximized(false);
		this.restoreSavedSize(stage);
		this.setIcon(ViewController.getInstance().getMaximizeWindowButton(), ResourcePathConstants.MAXIMIZE_ICON);

	}

}
