package arb.view;

import arb.core.ViewController;
import arb.factories.MenuFactory;
import arb.view.menu.ApplicationMenuSpacer;
import arb.view.menu.button.AbstractMenuButton;
import arb.view.menu.button.ApplicationMenuButtonType;
import arb.view.util.LabelConstants;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class HelpView extends BorderPane {

	private static final String MENU_BAR_CSS_CLASS = "menu-bar";

	private final Stage stage;

	public HelpView(final Stage stage) {
		super();
		this.stage = stage;
		this.configure();
	}

	private void configure() {
		this.createChildElements();
	}

	private void createChildElements() {
		final AbstractMenuButton closeButton = MenuFactory.getInstance()
				.createApplicationMenuButton(ApplicationMenuButtonType.CLOSE);
		closeButton.setOnAction(event -> {
			this.stage.close();
			ViewController.getInstance().setHelpStage(null);
		});
		final ApplicationMenuSpacer applicationMenuSpacer = MenuFactory.getInstance().createApplicationMenuSpacer();
		final HBox systemMenuBar = new HBox();
		systemMenuBar.getStyleClass().add(MENU_BAR_CSS_CLASS);
		HBox.setHgrow(applicationMenuSpacer, Priority.SOMETIMES);
		this.setTop(systemMenuBar);
		systemMenuBar.getChildren().addAll(applicationMenuSpacer, closeButton);
		final TextArea helpMessageTextArea = new TextArea();
		helpMessageTextArea.setEditable(false);
		helpMessageTextArea.setWrapText(true);
		helpMessageTextArea.setText(LabelConstants.HELP_MESSAGE);
		this.setCenter(helpMessageTextArea);

	}

}
