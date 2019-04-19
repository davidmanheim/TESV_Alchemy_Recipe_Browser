package arb.view;

import arb.factories.LayoutFactory;
import arb.view.result.ResultTableView;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

/**
 * This class represents the root view of the application.
 */
public class MainApplicationView extends SplitPane {

	public MainApplicationView() {
		super();
		this.configure();
	}

	private void configure() {
		this.setOrientation(Orientation.HORIZONTAL);
		this.createChildElements();
	}

	private void createChildElements() {
		SideBarView sideBarView = LayoutFactory.getInstance().createSideBarView();
		ResultTableView resultsTableView = LayoutFactory.getInstance().createResultTableView();
		ObservableList<Node> items = this.getItems();
		items.add(sideBarView);
		items.add(resultsTableView);
	}
}
