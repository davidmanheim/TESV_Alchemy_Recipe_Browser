package arb.core;

import arb.state.model.ApplicationModelState;
import arb.state.model.ApplyFilterState;
import arb.state.model.DefaultApplicationModelState;
import arb.state.model.ExecuteSearchState;
import arb.state.model.ResetBaseGameCharacterConfigState;
import arb.state.model.ResetFilterState;
import arb.state.model.ResetOrdinatorCharacterConfigState;
import arb.state.model.ResetSearchState;
import arb.state.model.ResetSharedCharacterConfigState;
import arb.state.window.ApplicationWindowState;
import arb.state.window.ClosedState;
import arb.state.window.MaximizedState;
import arb.state.window.MinimizedState;
import arb.state.window.RestoredState;
import arb.state.window.SoftRestoredState;

/** A controller class to facilitate state (model) changes. */
public class ModelController {

	private static ModelController modelStateControllerInstance;

	public static ModelController getInstance() {
		if (modelStateControllerInstance == null) {
			modelStateControllerInstance = new ModelController();
		}
		return modelStateControllerInstance;
	}

	/**
	 * Contains info about the state of the model (i.e. the data behind the result
	 * view and the input elements).
	 */
	private ApplicationModelState applicationModelState;

	/**
	 * Contains info about the state of the window (i.e. maximized, window bounds,
	 * etc.).
	 */
	private ApplicationWindowState applicationWindowState;

	private ModelController() {
		this.applicationModelState = new DefaultApplicationModelState();
		this.applicationWindowState = new MaximizedState();
	}

	public void transitionToResetSearchState() {
		this.applicationModelState = new ResetSearchState(this.applicationModelState);
	}

	public void transitionToExecuteSearchState() {
		this.applicationModelState = new ExecuteSearchState(this.applicationModelState);
	}

	public void transitionToResetFilterState() {
		this.applicationModelState = new ResetFilterState(this.applicationModelState);
	}

	public void transitionToApplyFilterState() {
		this.applicationModelState = new ApplyFilterState(this.applicationModelState);
	}

	public void transitionToResetBaseGameCharacterConfigState() {
		this.applicationModelState = new ResetBaseGameCharacterConfigState(this.applicationModelState);
	}

	public void transitionToResetOrdinatorCharacterConfigState() {
		this.applicationModelState = new ResetOrdinatorCharacterConfigState(this.applicationModelState);
	}

	public void transitionToResetSharedCharacterConfigState() {
		this.applicationModelState = new ResetSharedCharacterConfigState(this.applicationModelState);
	}

	public void transitionToMinimizedState() {
		this.applicationWindowState = new MinimizedState(this.applicationWindowState);
	}

	public void transitionToMaximizedState() {
		this.applicationWindowState = new MaximizedState(this.applicationWindowState);
	}

	public void transitionToRestoredState() {
		this.applicationWindowState = new RestoredState(this.applicationWindowState);
	}

	public void transitionToSoftRestoredState() {
		this.applicationWindowState = new SoftRestoredState(this.applicationWindowState);
	}

	public void transitionToClosedState() {
		this.applicationWindowState = new ClosedState(this.applicationWindowState);
	}

	public void transitionToSavedCharacterState() {
		// TODO - implement
	}

}
