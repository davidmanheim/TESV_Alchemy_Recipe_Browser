package arb.view.effects;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import arb.factories.LayoutFactory;
import arb.factories.ModelRegistry;
import arb.view.util.TooltipConstants;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

/**
 * This class represents a series of form elements the user can interact with to
 * alter the active search in the application.
 */
public class EffectsView extends VBox {

	private static final double FORM_ELEMENT_PADDING = 7.5;

	private Set<ComboBox<String>> comboBoxes;

	public EffectsView() {
		super();
		this.comboBoxes = new HashSet<>();
		this.configure();
	}

	public Set<ComboBox<String>> getComboBoxes() {
		return comboBoxes;
	}

	private void configure() {
		this.setMaxHeight(150);
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER);
		this.createChildElements();
	}

	private void createChildElements() {
		ObservableList<Node> children = this.getChildren();
		List<String> effectNames = ModelRegistry.getInstance().getEffects().getAllEffectNames().stream().sorted()
				.collect(Collectors.toList());
		for (int i = 0; i < 5; i++) {
			ObservableList<String> inputOptions = FXCollections.observableArrayList(effectNames);
			final ComboBox<String> comboBox = new ComboBox<>();
			comboBox.setMinWidth(200);
			comboBox.setEditable(true);
			FilteredList<String> filteredItems = new FilteredList<>(inputOptions, p -> true);
			comboBox.setItems(filteredItems);
			comboBox.getEditor().textProperty().addListener(onValueChanged(comboBox, filteredItems));
			comboBoxes.add(comboBox);
			comboBox.setTooltip(new Tooltip(TooltipConstants.INPUT_EFFECT));
			VBox.setMargin(comboBox,
					new Insets(FORM_ELEMENT_PADDING, FORM_ELEMENT_PADDING, FORM_ELEMENT_PADDING, FORM_ELEMENT_PADDING));
			children.add(comboBox);
		}
		EffectsActionButtons effectsButtonBox = LayoutFactory.getInstance().createEffectsActionButtons();
		VBox.setMargin(effectsButtonBox,
				new Insets(FORM_ELEMENT_PADDING, FORM_ELEMENT_PADDING, FORM_ELEMENT_PADDING, FORM_ELEMENT_PADDING));
		children.add(effectsButtonBox);
	}

	private ChangeListener<String> onValueChanged(final ComboBox<String> comboBox, FilteredList<String> filteredItems) {
		return (obs, oldValue, newValue) -> {
			final TextField editor = comboBox.getEditor();
			final String selected = comboBox.getSelectionModel().getSelectedItem();

			// This needs run on the GUI thread to avoid the error described
			// here: https://bugs.openjdk.java.net/browse/JDK-8081700.
			Platform.runLater(() -> {
				// If the no item in the list is selected or the selected item
				// isn't equal to the current input, we re-filter the list.
				if (selected == null || !selected.equals(editor.getText())) {
					filteredItems.setPredicate(item -> item.toUpperCase().contains(newValue.toUpperCase()));
				}
			});
		};
	}

	public void reset() {
		this.getChildren().clear();
		this.comboBoxes.clear();
		this.createChildElements();
	}
}
