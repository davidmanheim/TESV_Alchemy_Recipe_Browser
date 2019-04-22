package arb.state.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.core.ViewController;
import arb.model.characterconfig.CharacterConfigKey;
import arb.view.util.LabelConstants;
import arb.view.util.ResourcePathConstants;
import javafx.scene.control.TextArea;

/**
 * This class represents the state of the application on startup.
 */
public class SavedCharacterConfigState extends ApplicationModelState {

	private static final Logger LOG = LogManager
			.getLogger(SavedCharacterConfigState.class);

	public SavedCharacterConfigState(final ApplicationModelState lastState) {
		super(lastState);
	}

	@Override
	protected void onEnter() {
		this.updateCharacterConfig();
		boolean success = true;
		final File savedConfigFile = new File(ResourcePathConstants.SAVED_CONFIG);
		if (savedConfigFile.canWrite()) {
			success = this.tryWriteToFile(savedConfigFile);
		} else {
			success = false;
			LOG.error("Could not write to saved config file");
		}
		final TextArea logTextArea = ViewController.getInstance().getLogView()
				.getLogTextArea();
		if (success) {
			logTextArea.setText(
					logTextArea.getText() + "\n" + LabelConstants.SAVED_CHARACTER_CONFIG);
		} else {
			logTextArea.setText(logTextArea.getText() + "\n"
					+ LabelConstants.DID_NOT_SAVE_CHARACTER_CONFIG);
		}
	}

	private boolean tryWriteToFile(final File savedConfigFile) {
		final Map<CharacterConfigKey, String> storeMapping = this.characterConfig
				.getStoreMapping();
		boolean success = true;
		try (final BufferedWriter writer = new BufferedWriter(
				new FileWriter(savedConfigFile))) {
			for (final Entry<CharacterConfigKey, String> keyValuePair : storeMapping
					.entrySet()) {
				writer.append(keyValuePair + "\n");
			}
		} catch (final IOException e) {
			LOG.error(e);
			success = false;
		}
		return success;
	}

}
