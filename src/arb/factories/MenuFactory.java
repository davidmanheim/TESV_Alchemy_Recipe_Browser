package arb.factories;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.view.menu.ActionMenu;
import arb.view.menu.ApplicationMenu;
import arb.view.menu.ApplicationMenuSpacer;
import arb.view.menu.ApplicationTitleBar;
import arb.view.menu.SystemMenu;
import arb.view.menu.button.AbstractMenuButton;
import arb.view.menu.button.ApplicationMenuButtonType;
import javafx.stage.Stage;

/**
 * This class contains methods to create menus and their subcomponents.
 */
public class MenuFactory {

	private static final Logger LOG = LogManager.getLogger(MenuFactory.class);

	private static MenuFactory menuFactoryInstance;

	public static MenuFactory getInstance() {
		if (menuFactoryInstance == null) {
			menuFactoryInstance = new MenuFactory();
		}
		return menuFactoryInstance;
	}

	public ApplicationTitleBar createApplicationTitleBar(final Stage stage) {
		return new ApplicationTitleBar(stage);
	}

	public SystemMenu createSystemMenu() {
		return new SystemMenu();
	}

	public ApplicationMenu createApplicationMenu() {
		return new ApplicationMenu();
	}

	public ActionMenu createActionMenu() {
		return new ActionMenu();
	}

	public ApplicationMenuSpacer createApplicationMenuSpacer() {
		return new ApplicationMenuSpacer();
	}

	public AbstractMenuButton createApplicationMenuButton(final ApplicationMenuButtonType type) {
		try {
			return (AbstractMenuButton) type.getClassType().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	private MenuFactory() {
		// Private constructor to prevent external instantiation.
	}
}
