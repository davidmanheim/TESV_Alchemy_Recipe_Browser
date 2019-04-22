package arb.core;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import arb.factories.LayoutFactory;
import arb.factories.MenuFactory;
import arb.view.MainApplicationView;
import arb.view.RootStackPane;
import arb.view.menu.ApplicationTitleBar;
import arb.view.util.ResourcePathConstants;
import arb.view.util.WindowHelper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/** Entry point for the execution of the project. */
public class ARB extends Application {

	private static final Logger LOG = LogManager.getLogger(ARB.class);

	@Override
	public void start(final Stage stage) throws IOException {
		final BorderPane root = this.createRootPane(stage);
		final RootStackPane rootStackPane = LayoutFactory.getInstance()
				.createRootStackPane(root);
		final Scene scene = this.createScene(rootStackPane);
		this.configureStage(stage, scene, root);
		// These messages are just to separate executions if a log file gets re-used.
		LOG.info("==============================================");
		LOG.info("Application started successfully.");
	}

	/**
	 * Creates and returns a Scene, using the given Parent object as a root element.
	 */
	private Scene createScene(final Region root) {
		final Scene scene = new Scene(root);
		final String applicationCSS = this.getClass()
				.getResource(ResourcePathConstants.APPLICATION_CSS).toExternalForm();
		scene.getStylesheets().add(applicationCSS);
		scene.setFill(Color.TRANSPARENT);
		return scene;
	}

	/**
	 * Creates and returns a BorderPane which acts as the root element for the
	 * scene.
	 */
	private BorderPane createRootPane(final Stage stage) {
		final BorderPane root = new BorderPane();
		final ApplicationTitleBar menuContainer = MenuFactory.getInstance()
				.createApplicationTitleBar(stage);
		root.setTop(menuContainer);
		final MainApplicationView topLevelSplitPane = LayoutFactory.getInstance()
				.createMainApplicationView();
		root.setCenter(topLevelSplitPane);
		return root;
	}

	private void configureStage(final Stage stage, final Scene scene, Region root) {
		ViewController.getInstance().setStage(stage);
		stage.getIcons().add(new Image(this.getClass()
				.getResourceAsStream(ResourcePathConstants.APPLICATION_ICON)));
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.setMinHeight(600);
		// Note - the order is important here. first, the stage is shown while not
		// maximized. Then, the model controller is initialized to set the default
		// window state to maximized. On entering this state, the current window
		// bounds are set to restore later. If the window is already maximized, the
		// system gets out of sync. Then, the stage is maximized to put the model
		// and view in sync.
		stage.setMaximized(true);
		stage.show();
		WindowHelper.addResizeAndDragListener(stage, root);
		ModelController.getInstance();
	}

	public static void main(final String[] args) {
		launch(args);
	}

}
