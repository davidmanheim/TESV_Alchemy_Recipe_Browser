package arb.view.character;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * This class represents a view the user can use to configure their perks if
 * they use the base (un-modded) game.
 */
public abstract class AbstractCharacterConfigView extends VBox {

	protected static final double DEFAULT_PADDING = 7.5;

	public AbstractCharacterConfigView() {
		// Nothing to do.
	}


	public void reset() {
		this.getChildren().clear();
		this.createChildElements();
	}
	
	protected void configure() {
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(new Insets(10));
	}
	
	protected abstract void createChildElements();

}
