package arb.view.util;

import arb.model.entity.GameExtension;

/**
 * This class contains constants that are shown as labels or titles in the
 * application.
 */
public class LabelConstants {

	private static final String RESET_CHARACTER_CONFIG = "character configuration tab has been reset. Please re-execute the search after the character configuration is as desired.";

	private static final String HELP_MESSAGE_COMPONENT = "Click the '";

	public static final String APPLICATION_TITLE = "TES V: Skyrim Alchemy Recipe Browser";

	public static final String BULLET_POINT = "◾ ";
	public static final String BULLET_POINT_2 = "\t◽ ";

	public static final String SEARCH_NO_FILTER_MESSAGE_SUFFIX = " potions found in search.";

	public static final String AFTER_FILTER_MESSAGE_SUFFIX = " potions found after filtering.";

	public static final String AFTER_FILTER_NO_SEARCH_MESSAGE_SUFFIX = BULLET_POINT
			+ "Filter(s) set. Execute search to observe results.";

	public static final String CHARACTER = "Character".toUpperCase();

	public static final String BASE_GAME = "Base Game".toUpperCase();

	public static final String ORDINATOR = "Ordinator".toUpperCase();

	public static final String SHARED = "Shared".toUpperCase();

	public static final String EFFECTS = "Effects".toUpperCase();

	public static final String SEARCH = "Search".toUpperCase();

	public static final String FILTER_POTIONS = "Filter Potions".toUpperCase();

	public static final String ALCHEMIST = "Alchemist".toUpperCase();

	public static final String BENEFACTOR = "Benefactor".toUpperCase();

	public static final String PHYSICIAN = "Physician".toUpperCase();

	public static final String POISONER = "Poisoner".toUpperCase();

	public static final String ALCHEMY_MASTERY = "Alchemy Mastery".toUpperCase();

	public static final String PHYSICIAN_HEALTH = "Health".toUpperCase();

	public static final String PHYSICIAN_MAGICKA = "Magicka".toUpperCase();

	public static final String PHYSICIAN_STAMINA = "Stamina".toUpperCase();

	public static final String ADVANCED_LAB = "Advanced Lab".toUpperCase();

	public static final String THAT_WHICH_DOES_NOT_KILL_YOU = "That Which Does Not Kill You..."
			.toUpperCase();

	public static final String SKILL_LEVEL = "Alchemy Level".toUpperCase();

	public static final String ENCHANTMENT = "Enchantment Bonus".toUpperCase();

	public static final String SEEKER_OF_SHADOWS = "Seeker of Shadows".toUpperCase();

	public static final String ALLOW_BASE_GAME = GameExtension.BASE_GAME.getEncoding();

	public static final String ALLOW_DAWNGUARD = GameExtension.DAWNGUARD.getEncoding();

	public static final String ALLOW_DRAGONBORN = GameExtension.DRAGONBORN.getEncoding();

	public static final String ALLOW_HEARTHFIRE = GameExtension.HEARTHFIRE.getEncoding();

	public static final String RESET = "Reset".toUpperCase();

	public static final String FILTER = "Filter".toUpperCase();

	public static final String HARVESTABLE = "Harvestable Ingredients".toUpperCase();

	public static final String PURE_RESULTS = "Pure potions".toUpperCase();

	public static final String POTIONS_AND_POISONS = "Both".toUpperCase();

	public static final String ONLY_POTIONS = "Potions".toUpperCase();

	public static final String ONLY_POISONS = "Poisons".toUpperCase();

	public static final String MATCH_INGREDIENT = "Match Ingredient".toUpperCase();

	public static final String MATCH_EFFECT = "Match Effect".toUpperCase();

	public static final String EMPTY_TABLE = "No potions to show. See directions in log view.";

	public static final String INGREDIENT = "Ingredient";

	public static final String VALUE = "Value";

	public static final String FILLER = "";

	public static final String EFFECT = "Effect";

	public static final String POTENCY = "Potency";

	public static final String HELP_MESSAGE = "Welcome to the Skyrim Alchemy Recipe Browser. To use "
			+ "the application, follow the below guidelines:\n\nCHARACTER TAB:\n"
			+ BULLET_POINT + "Define your character's stats in the '" + CHARACTER
			+ "' tab.\n" + BULLET_POINT_2
			+ "If you change your character configration after searching, you must re-execute the search.\n"
			+ BULLET_POINT
			+ "The 3 tabs correspond to different components of the game. The Base game and Ordinator tabs should not be used together, "
			+ "as this will make calculated strength and value of potions incorrect.\n"
			+ BULLET_POINT_2 + "Base game corresponds to unmodded Skyrim.\n"
			+ BULLET_POINT_2 + "Ordinator represents the Ordinator perk overhaul mod.\n"
			+ BULLET_POINT_2
			+ "Shared corresponds to aspects of the game which are always relevant, regardless of mods (or their absence).\n\nSEARCH TAB:\n"
			+ BULLET_POINT + "Select desired ingredients in the '" + EFFECTS
			+ "' sub-tab.\n" + BULLET_POINT_2 + HELP_MESSAGE_COMPONENT + SEARCH
			+ "' button to view all matching potions.\n" + BULLET_POINT_2
			+ HELP_MESSAGE_COMPONENT + RESET + "' button on the '" + EFFECTS
			+ "' sub-tab to clear the search and reset the effects view.\n" + BULLET_POINT
			+ "Select desired filters in the '" + FILTER_POTIONS + "' sub-tab.\n"
			+ BULLET_POINT_2 + HELP_MESSAGE_COMPONENT + FILTER
			+ "' button to reduce the table view to potions matching the defined criteria.\n"
			+ BULLET_POINT_2 + HELP_MESSAGE_COMPONENT + RESET + "' button on the '"
			+ FILTER
			+ "' tab to clear the filters and restore the original search.\n\nOTHER:\n"
			+ BULLET_POINT
			+ "Use the save button in the context menu to store the current character configuration.\n"
			+ BULLET_POINT_2
			+ "Only one configuration can be stored, and this configuration will be automatically loaded when the application starts.\n"
			+ BULLET_POINT
			+ "If you are unsure of what something does, hover over to it to see a more descriptive tooltip.";

	public static final String COMPUTING = BULLET_POINT
			+ "Computing the result. This may take up to 5 seconds if you ran a search with no effect requirements.";

	public static final String SUGGEST_CLEAR_FILTERS_MESSAGE = BULLET_POINT
			+ "Try resetting the '" + FILTER + "' tab.";

	public static final String NO_VIABLE_RESULTS_MESSAGE = BULLET_POINT
			+ "It appears this combination of effects is not possible in the game.";

	public static final String CLEARED_FILTER_MESSAGE = BULLET_POINT
			+ "Filter(s) cleared successfully.";

	public static final String BASE_CHARACTER_CONFIG_RESET = BULLET_POINT
			+ "The base game " + RESET_CHARACTER_CONFIG;
	public static final String ORDINATOR_CHARACTER_CONFIG_RESET = BULLET_POINT
			+ "The ordinator " + RESET_CHARACTER_CONFIG;
	public static final String SHARED_CHARACTER_CONFIG_RESET = BULLET_POINT
			+ "The shared " + RESET_CHARACTER_CONFIG;

	public static final String WELCOME_MESSAGE = BULLET_POINT
			+ "Welcome to the Skyrim Alchemy Recipe Browser. Please open the help dialog from the context menu for usage.";

	public static final String SAVED_CHARACTER_CONFIG = BULLET_POINT
			+ "Successfully saved character config. This configuration will automatically load when you load the application."
			+ " To reset this, either delete ..\\ARB_Data\\savedCharacterConfig.txt, or adjust the settings back to their defaults and re-save.";

	public static final String DID_NOT_SAVE_CHARACTER_CONFIG = BULLET_POINT
			+ "Failed to save character config. Please check the most recent log file at ..\\ARB_Data\\logs\\ for more info.";

	public static final String LOADED_CHARACTER_CONFIG = BULLET_POINT
			+ "Successfully loaded character config from the stored file.";

	public static final String DID_NOT_LOAD_CHARACTER_CONFIG = BULLET_POINT
			+ "No saved character config file found, loading default settings.";

	public static final String CLOSE = "Close";

	public static final String MINIMIZE = "Minimize";

	public static final String MAXIMIZE = "Maximize";

	public static final String RESTORE = "Restore";

	public static final String HELP = "Help";

	public static final String SAVE = "Save";

	private LabelConstants() {
		// Private constructor to prevent instantiation.
	}
}
