package arb.state.model;

/**
 * This class represents the state of the application on startup.
 */
public class SavedCharacterConfigState extends ApplicationModelState {

	public SavedCharacterConfigState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		// TODO - implement. Read this.characterConfig into a set of key-value string
		// pairs, then write them to a file.
	}

}
