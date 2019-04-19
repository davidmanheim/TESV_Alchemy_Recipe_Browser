package arb.view.control;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** This class pairs a text field with a label to the right of it. */
public class LabeledComboBox extends HBox {

	private static final int COMBO_BOX_HEIGHT = 67;

	private static final int COMBO_BOX_WIDTH = 110;

	private static final double LABEL_TOP_PADDING = 1.5;

	private static final double LABEL_LEFT_PADDING = 7.5;
	
	private ComboBox<String> comboBox;

	private Label label;
	
	public LabeledComboBox() {
		super();
		this.configure();
	}

	public ComboBox<String> getComboBox() {
		return this.comboBox;
	}

	public Label getLabel() {
		return this.label;
	}

	private void configure() {
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("labeledTextField");
		this.addChildElements();
	}

	private void addChildElements() {
		ObservableList<Node> children = this.getChildren();
		this.comboBox = new ComboBox<>();
		this.comboBox.setEditable(true);
		this.comboBox.setMinWidth(COMBO_BOX_WIDTH);
		this.comboBox.setMaxWidth(COMBO_BOX_HEIGHT);
		children.add(comboBox);
		this.label = new Label();
		children.add(label);
		// Space the text field and label out a little bit.
		HBox.setMargin(label, new Insets(LABEL_TOP_PADDING, 0, 0, LABEL_LEFT_PADDING));
	}
}
