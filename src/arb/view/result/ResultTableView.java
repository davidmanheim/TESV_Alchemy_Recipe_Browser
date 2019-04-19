package arb.view.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import arb.factories.ResultCellFactory;
import arb.model.computation.PotionResultWrapper;
import arb.util.LabelConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;

/**
 * This class represents a table or grid containing all the current results (on
 * the right view of the application).
 */
public class ResultTableView extends TableView<PotionResultWrapper> {

	private static final int MAX_PLACEHOLDER_LABEL_WIDTH = 300;

	private static final int INGREDIENT_COLUMN_WIDTH = 125;

	private static final int VALUE_COLUMN_WIDTH = 40;

	private static final int EFFECT_COLUMN_WIDTH = 125;

	private static final int POTENCY_COLUMN_WIDTH = 60;

	// The width is actually much higher; there appears to be some minimum width,
	// but this is OK. We only want it as thin as possible.
	private static final int FILLER_COLUMN_WIDTH = 1;

	private ObservableList<PotionResultWrapper> resultData;

	private Label placeholderLabel;

	public ResultTableView() {
		super();
		this.resultData = FXCollections.observableList(new ArrayList<PotionResultWrapper>());
		this.configure();
	}

	public ObservableList<PotionResultWrapper> getResultData() {
		return resultData;
	}

	private void configure() {
		this.placeholderLabel = new Label(LabelConstants.EMPTY_TABLE);
		this.placeholderLabel.setWrapText(true);
		this.placeholderLabel.setMaxWidth(MAX_PLACEHOLDER_LABEL_WIDTH);
		this.placeholderLabel.setTextAlignment(TextAlignment.JUSTIFY);
		this.setPlaceholder(this.placeholderLabel);
		this.configureColumns();
		this.setItems(resultData);
	}

	/** This method creates and adds all columns to table. */
	// columns.setAll(firstEffectColumn); will warn of some type safety issues,
	// but this is a false positive. Only Strings are used as the value of a table
	// column, so there is no guesswork as to what the type is.
	@SuppressWarnings("unchecked")
	private void configureColumns() {
		// Ingredient columns.
		TableColumn<PotionResultWrapper, String> firstIngredientColumn = new TableColumn<>(LabelConstants.INGREDIENT);
		firstIngredientColumn.setPrefWidth(INGREDIENT_COLUMN_WIDTH);
		firstIngredientColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("firstIngredientName"));
		firstIngredientColumn.setCellFactory(new ResultCellFactory());
		TableColumn<PotionResultWrapper, String> secondIngredientColumn = new TableColumn<>(LabelConstants.INGREDIENT);
		secondIngredientColumn.setPrefWidth(INGREDIENT_COLUMN_WIDTH);
		secondIngredientColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("secondIngredientName"));
		secondIngredientColumn.setCellFactory(new ResultCellFactory());
		TableColumn<PotionResultWrapper, String> thirdIngredientColumn = new TableColumn<>(LabelConstants.INGREDIENT);
		thirdIngredientColumn.setPrefWidth(INGREDIENT_COLUMN_WIDTH);
		thirdIngredientColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("thirdIngredientName"));
		thirdIngredientColumn.setCellFactory(new ResultCellFactory());

		TableColumn<PotionResultWrapper, String> valueColumn = new TableColumn<>(LabelConstants.VALUE);
		valueColumn.setPrefWidth(VALUE_COLUMN_WIDTH);
		valueColumn.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("value"));
		valueColumn.setCellFactory(new ResultCellFactory());
		valueColumn.setCellFactory(new ResultCellFactory());

		// Filler columns to provide visual break between sections.
		TableColumn<PotionResultWrapper, String> filler1Column = new TableColumn<>(LabelConstants.FILLER);
		filler1Column.setPrefWidth(FILLER_COLUMN_WIDTH);
		filler1Column.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>(LabelConstants.FILLER));
		filler1Column.setCellFactory(new ResultCellFactory());
		TableColumn<PotionResultWrapper, String> filler2Column = new TableColumn<>(LabelConstants.FILLER);
		filler2Column.setPrefWidth(FILLER_COLUMN_WIDTH);
		filler2Column.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>(LabelConstants.FILLER));
		filler2Column.setCellFactory(new ResultCellFactory());
		TableColumn<PotionResultWrapper, String> filler3Column = new TableColumn<>(LabelConstants.FILLER);
		filler3Column.setPrefWidth(FILLER_COLUMN_WIDTH);
		filler3Column.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>(LabelConstants.FILLER));
		filler3Column.setCellFactory(new ResultCellFactory());
		TableColumn<PotionResultWrapper, String> filler4Column = new TableColumn<>(LabelConstants.FILLER);
		filler4Column.setPrefWidth(FILLER_COLUMN_WIDTH);
		filler4Column.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>(LabelConstants.FILLER));
		filler4Column.setCellFactory(new ResultCellFactory());
		TableColumn<PotionResultWrapper, String> filler5Column = new TableColumn<>(LabelConstants.FILLER);
		filler5Column.setPrefWidth(FILLER_COLUMN_WIDTH);
		filler5Column.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>(LabelConstants.FILLER));
		filler5Column.setCellFactory(new ResultCellFactory());

		// Effect and Potency columns, paired by index.
		TableColumn<PotionResultWrapper, String> firstEffectColumn = new TableColumn<>(LabelConstants.EFFECT);
		firstEffectColumn.setPrefWidth(EFFECT_COLUMN_WIDTH);
		firstEffectColumn.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("firstEffectName"));
		TableColumn<PotionResultWrapper, String> firstEffectPotencyColumn = new TableColumn<>(LabelConstants.POTENCY);
		firstEffectPotencyColumn.setPrefWidth(POTENCY_COLUMN_WIDTH);
		firstEffectPotencyColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("firstEffectPotency"));
		firstEffectPotencyColumn.setCellFactory(new ResultCellFactory());

		TableColumn<PotionResultWrapper, String> secondEffectColumn = new TableColumn<>(LabelConstants.EFFECT);
		secondEffectColumn.setPrefWidth(EFFECT_COLUMN_WIDTH);
		secondEffectColumn.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("secondEffectName"));
		TableColumn<PotionResultWrapper, String> secondEffectPotencyColumn = new TableColumn<>(LabelConstants.POTENCY);
		secondEffectPotencyColumn.setPrefWidth(POTENCY_COLUMN_WIDTH);
		secondEffectPotencyColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("secondEffectPotency"));
		secondEffectPotencyColumn.setCellFactory(new ResultCellFactory());

		TableColumn<PotionResultWrapper, String> thirdEffectColumn = new TableColumn<>(LabelConstants.EFFECT);
		thirdEffectColumn.setPrefWidth(EFFECT_COLUMN_WIDTH);
		thirdEffectColumn.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("thirdEffectName"));
		TableColumn<PotionResultWrapper, String> thirdEffectPotencyColumn = new TableColumn<>(LabelConstants.POTENCY);
		thirdEffectPotencyColumn.setPrefWidth(POTENCY_COLUMN_WIDTH);
		thirdEffectPotencyColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("thirdEffectPotency"));
		thirdEffectPotencyColumn.setCellFactory(new ResultCellFactory());

		TableColumn<PotionResultWrapper, String> fourthEffectColumn = new TableColumn<>(LabelConstants.EFFECT);
		fourthEffectColumn.setPrefWidth(EFFECT_COLUMN_WIDTH);
		fourthEffectColumn.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("fourthEffectName"));
		TableColumn<PotionResultWrapper, String> fourthEffectPotencyColumn = new TableColumn<>(LabelConstants.POTENCY);
		fourthEffectPotencyColumn.setPrefWidth(POTENCY_COLUMN_WIDTH);
		fourthEffectPotencyColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("fourthEffectPotency"));
		fourthEffectPotencyColumn.setCellFactory(new ResultCellFactory());

		TableColumn<PotionResultWrapper, String> fifthEffectColumn = new TableColumn<>(LabelConstants.EFFECT);
		fifthEffectColumn.setPrefWidth(EFFECT_COLUMN_WIDTH);
		fifthEffectColumn.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("fifthEffectName"));
		TableColumn<PotionResultWrapper, String> fifthEffectPotencyColumn = new TableColumn<>(LabelConstants.POTENCY);
		fifthEffectPotencyColumn.setPrefWidth(POTENCY_COLUMN_WIDTH);
		fifthEffectPotencyColumn
				.setCellValueFactory(new PropertyValueFactory<PotionResultWrapper, String>("fifthEffectPotency"));
		fifthEffectColumn.setCellFactory(new ResultCellFactory());

		ObservableList<TableColumn<PotionResultWrapper, ?>> columns = this.getColumns();
		columns.setAll(firstIngredientColumn, secondIngredientColumn, thirdIngredientColumn, valueColumn, filler1Column,
				firstEffectColumn, firstEffectPotencyColumn, filler2Column, secondEffectColumn, secondEffectPotencyColumn,
				filler3Column, thirdEffectColumn, thirdEffectPotencyColumn, filler4Column, fourthEffectColumn,
				fourthEffectPotencyColumn, filler5Column, fifthEffectColumn, fifthEffectPotencyColumn);
		refreshSort();
	}

	public void refreshSort() {
		ObservableList<TableColumn<PotionResultWrapper, ?>> sortOrder = this.getSortOrder();
		sortOrder.clear();
		ObservableList<TableColumn<PotionResultWrapper, ?>> columns = this.getColumns();
		IntStream.rangeClosed(0, 2).forEach(index -> columns.get(index).setSortType(SortType.ASCENDING));
		sortOrder.addAll(Arrays.asList(this.getColumns().get(0), this.getColumns().get(1), this.getColumns().get(2)));
	}

}
