package arb.view;

import arb.factories.LayoutFactory;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

/**
 * This class corresponds to the side bar on the left side of the screen. It
 * contains all other view elements on this side of the application.
 */
public class SideBarView extends SplitPane {

	public SideBarView() {
		this.configure();
	}
	

	private void configure() {
		this.setOrientation(Orientation.VERTICAL);
		this.getStyleClass().add("arb-side-bar-view");
		this.setMinWidth(240);
		this.setMaxWidth(240);
		this.createChildElements();
	}

	private void createChildElements() {
		SettingsView settingsView = LayoutFactory.getInstance().createSettingsView();
		LogView logView = LayoutFactory.getInstance().createLogView();
		ObservableList<Node> children = this.getItems();
		children.add(settingsView);
		children.add(logView);
	}
}
