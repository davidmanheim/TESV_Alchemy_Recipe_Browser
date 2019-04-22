package arb.state.model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.core.ViewController;
import arb.model.characterconfig.CharacterConfigKey;
import arb.view.character.BaseGameCharacterConfigView;
import arb.view.character.OrdinatorCharacterConfigView;
import arb.view.character.SharedCharacterConfigView;
import arb.view.util.ResourcePathConstants;

/**
 * This class represents the state of the application on startup.
 */
public class DefaultApplicationModelState extends ApplicationModelState {

	private static final Logger LOG = LogManager
			.getLogger(DefaultApplicationModelState.class);

	public DefaultApplicationModelState() {
		super();
		this.onEnter();
	}

	@Override
	protected void onEnter() {
		final Map<CharacterConfigKey, String> characterConfigMapping = this
				.updateCharacterConfigModel();
		this.updateCharacterConfigView(characterConfigMapping);

	}

	private Map<CharacterConfigKey, String> updateCharacterConfigModel() {
		if (new File(ResourcePathConstants.SAVED_CONFIG).exists()) {
			try {
				return this.readCharacterConfigStateFromFile();
			} catch (final URISyntaxException | IOException e) {
				LOG.error("Could not read config file: {}", e);
			}
		} else {
			LOG.warn(
					"No character config file existed; you must first save from the context menu.");
		}
		return new HashMap<>();
	}

	private void updateCharacterConfigView(
			Map<CharacterConfigKey, String> characterConfigMapping) {
		this.updateBaseGameCharacterConfigView(characterConfigMapping);
		this.updateOrdinatorCharacterConfigView(characterConfigMapping);
		this.updateSharedCharacterConfigView(characterConfigMapping);
	}

	private Map<CharacterConfigKey, String> readCharacterConfigStateFromFile()
			throws URISyntaxException, IOException {
		final Path characterConfigPath = Paths
				.get(new File(ResourcePathConstants.SAVED_CONFIG).toURI());
		final List<String> allConfigFileLines = Files.readAllLines(characterConfigPath);
		// Can't figure out how to map from a list of strings to a map of
		// CharacterConfigKey -> string, so this is a slightly lengthier approach.
		final Map<String, String> tempMapping = allConfigFileLines.stream()
				.collect(Collectors.toMap(line -> line.substring(0, line.indexOf('=')),
						line -> line.substring(line.indexOf('=') + 1)));
		final Map<CharacterConfigKey, String> mapping = new HashMap<>();
		tempMapping.forEach((key, value) -> {
			mapping.put(CharacterConfigKey.valueOf(key), value);
		});
		// This results in the same mapping being used for each of the 3 sub-classes,
		// but the extra parameters will just get ignored.
		this.characterConfig.update(mapping, mapping, mapping);
		return mapping;
	}

	private void updateBaseGameCharacterConfigView(
			Map<CharacterConfigKey, String> characterConfigMapping) {
		final BaseGameCharacterConfigView baseGameCharacterConfigView = ViewController
				.getInstance().getBaseGameCharacterConfigView();
		if (characterConfigMapping.containsKey(CharacterConfigKey.ALCHEMIST)) {
			baseGameCharacterConfigView.getAlchemistLevelInput().getTextField()
					.setText(characterConfigMapping.get(CharacterConfigKey.ALCHEMIST));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.PHYSICIAN_BASE_GAME)) {
			baseGameCharacterConfigView.getPhysicianCheckbox()
					.setSelected(Boolean.parseBoolean(characterConfigMapping
							.get(CharacterConfigKey.PHYSICIAN_BASE_GAME)));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.BENEFACTOR)) {
			baseGameCharacterConfigView.getBenefactorCheckbox()
					.setSelected(Boolean.parseBoolean(
							characterConfigMapping.get(CharacterConfigKey.BENEFACTOR)));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.POISONER_BASE_GAME)) {
			baseGameCharacterConfigView.getPoisonerCheckbox()
					.setSelected(Boolean.parseBoolean(characterConfigMapping
							.get(CharacterConfigKey.POISONER_BASE_GAME)));
		}
	}

	private void updateOrdinatorCharacterConfigView(
			Map<CharacterConfigKey, String> characterConfigMapping) {
		final OrdinatorCharacterConfigView ordinatorCharacterConfigView = ViewController
				.getInstance().getOrdinatorCharacterConfigView();
		if (characterConfigMapping.containsKey(CharacterConfigKey.ALCHEMY_MASTERY)) {
			ordinatorCharacterConfigView.getAlchemyMasteryLevelInput().getTextField()
					.setText(characterConfigMapping
							.get(CharacterConfigKey.ALCHEMY_MASTERY));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.PHYSICIAN_ORDINATOR)) {
			ordinatorCharacterConfigView.getPhysicianComboBox().getComboBox()
					.getSelectionModel().select(characterConfigMapping
							.get(CharacterConfigKey.PHYSICIAN_ORDINATOR));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.ADVANCED_LAB)) {
			ordinatorCharacterConfigView.getAdvancedLabCheckbox()
					.setSelected(Boolean.parseBoolean(
							characterConfigMapping.get(CharacterConfigKey.ADVANCED_LAB)));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.POISONER_ORDINATOR)) {
			ordinatorCharacterConfigView.getPoisonerCheckbox()
					.setSelected(Boolean.parseBoolean(characterConfigMapping
							.get(CharacterConfigKey.POISONER_ORDINATOR)));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.TWDNKY)) {
			ordinatorCharacterConfigView.getTwdnkyCheckbox().setSelected(Boolean
					.parseBoolean(characterConfigMapping.get(CharacterConfigKey.TWDNKY)));
		}
	}

	private void updateSharedCharacterConfigView(
			Map<CharacterConfigKey, String> characterConfigMapping) {
		final SharedCharacterConfigView sharedCharacterConfigView = ViewController
				.getInstance().getSharedCharacterConfigView();
		if (characterConfigMapping.containsKey(CharacterConfigKey.SKILL_LEVEL)) {
			sharedCharacterConfigView.getSkillLevelInput().getTextField()
					.setText(characterConfigMapping.get(CharacterConfigKey.SKILL_LEVEL));
		}
		if (characterConfigMapping
				.containsKey(CharacterConfigKey.TOTAL_ENCHANTMENT_BONUS)) {
			sharedCharacterConfigView.getTotalEnchantmentsInput().getTextField()
					.setText(characterConfigMapping
							.get(CharacterConfigKey.TOTAL_ENCHANTMENT_BONUS));
		}
		if (characterConfigMapping.containsKey(CharacterConfigKey.SEEKER_OF_SHADOWS)) {
			sharedCharacterConfigView.getSeekerOfShadowsCheckbox()
					.setSelected(Boolean.parseBoolean(characterConfigMapping
							.get(CharacterConfigKey.SEEKER_OF_SHADOWS)));
		}
	}

}
