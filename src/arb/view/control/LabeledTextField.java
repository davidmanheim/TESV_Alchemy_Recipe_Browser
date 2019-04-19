package arb.view.control;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/** This class pairs a text field with a label to the right of it. */
public class LabeledTextField extends HBox {

	private static final int TEXT_FIELD_DEFAULT_HEIGHT = 25;

	private static final int TEXT_FIELD_DEFAULT_WIDTH = 26;

	private static final double LABEL_TOP_PADDING = 1.5;

	private static final double LABEL_LEFT_PADDING = 7.5;
	
	private TextField textField;

	private Label label;

	public LabeledTextField() {
		super();
		this.configure();
	}

	public TextField getTextField() {
		return this.textField;
	}

	public Label getLabel() {
		return this.label;
	}

	private void configure() {
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("arb-labeled-text-field");
		this.addChildElements();
	}

	private void addChildElements() {
		ObservableList<Node> children = this.getChildren();
		this.textField = new TextField();
		this.textField.setMaxWidth(TEXT_FIELD_DEFAULT_WIDTH);
		this.textField.setMaxHeight(TEXT_FIELD_DEFAULT_HEIGHT);
		this.textField.setAlignment(Pos.CENTER);
		this.textField.setText(String.valueOf("0"));
		children.add(textField);
		this.label = new Label();
		children.add(label);
		// Space the text field and label out a little bit.
		HBox.setMargin(label, new Insets(LABEL_TOP_PADDING, 0, 0, LABEL_LEFT_PADDING));
	}
}
