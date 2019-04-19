package arb.factories;

import javafx.util.Callback;
import arb.model.computation.PotionResultWrapper;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;

public class ResultCellFactory
		implements Callback<TableColumn<PotionResultWrapper, String>, TableCell<PotionResultWrapper, String>> {

	public ResultCellFactory() {
		// Nothing to do.
	}

	@Override
	public TableCell<PotionResultWrapper, String> call(TableColumn<PotionResultWrapper, String> param) {
		return new TableCell<PotionResultWrapper, String>() {

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (this.getTableColumn().getText().isEmpty()) {
					this.getStyleClass().add("arb-filler-table-cell");
				}
				if (empty || item == null) {
					this.setText(null);
				} else {
					this.setText(item);
					this.setTooltip(new Tooltip(item));
				}
			}

		};
	}

}
