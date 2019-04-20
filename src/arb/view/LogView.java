package arb.view;

import arb.view.util.LabelConstants;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class LogView extends BorderPane {

	private TextArea logTextField;

	public LogView() {
		this.configure();
	}

	public TextArea getLogTextArea() {
		return this.logTextField;
	}

	private void configure() {
		this.createChildElements();
	}

	private void createChildElements() {
		this.logTextField = new TextArea();
		this.logTextField.setText(LabelConstants.WELCOME_MESSAGE);
		this.logTextField.setEditable(false);
		this.logTextField.setWrapText(true);
		this.setCenter(this.logTextField);
	}
}
