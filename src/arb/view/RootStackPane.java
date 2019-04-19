package arb.view;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/** This class is the top level root element of any stage. */
public class RootStackPane extends StackPane {

	private static String SHADOW_RECTANGLE_STYLE_CLASS = "decoration-shadow";

	private static final int ROUNDED_DELTA = 0;

	private static final int SHADOW_WIDTH = 15;

	private final Rectangle shadowRectangle;

	private final Rectangle internal;

	private final Rectangle external;

	private final Region applicationView;

	public RootStackPane(Region applicationView) {
		this.applicationView = applicationView;
		this.internal = new Rectangle();
		this.external = new Rectangle();
		// TODO - refactor shadow rectangle into its own class.
		// TODO - make shadow rectangle's color a darak gray if not focused.
		this.shadowRectangle = new Rectangle();
		this.shadowRectangle.layoutBoundsProperty()
				.addListener((ChangeListener<Bounds>) (observable, oldBounds, newBounds) -> {
					RootStackPane.this.shadowRectangle.setVisible(true);
					this.setShadowClip(newBounds);
				});
		this.shadowRectangle.setMouseTransparent(true);
		final DropShadow dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, SHADOW_WIDTH, 0.1, 0, 0);
		this.shadowRectangle.setEffect(dropShadow);
		this.setStyle("-fx-background-color:transparent;");
		this.getChildren().addAll(this.shadowRectangle, applicationView);
	}

	/** Resizes elements to make the shadow visible. */
	@Override
	public void layoutChildren() {
		final Bounds b = super.getLayoutBounds();
		final double w = b.getWidth();
		final double h = b.getHeight();
		this.shadowRectangle.setWidth(w - SHADOW_WIDTH * 2);
		this.shadowRectangle.setHeight(h - SHADOW_WIDTH * 2);
		this.shadowRectangle.setX(SHADOW_WIDTH);
		this.shadowRectangle.setY(SHADOW_WIDTH);
		this.applicationView.resize(w - SHADOW_WIDTH * 2 - ROUNDED_DELTA * 2, h - SHADOW_WIDTH * 2 - ROUNDED_DELTA * 2);
		this.applicationView.setLayoutX(SHADOW_WIDTH + ROUNDED_DELTA);
		this.applicationView.setLayoutY(SHADOW_WIDTH + ROUNDED_DELTA);
	}

	/**
	 * Compute the needed clip for stage's shadow border
	 *
	 * @param newBounds
	 * @param shadowVisible
	 */
	private void setShadowClip(Bounds newBounds) {
		this.external.relocate(newBounds.getMinX() - SHADOW_WIDTH, newBounds.getMinY() - SHADOW_WIDTH);
		this.internal.setX(SHADOW_WIDTH);
		this.internal.setY(SHADOW_WIDTH);
		this.internal.setWidth(newBounds.getWidth());
		this.internal.setHeight(newBounds.getHeight());
		this.internal.setArcWidth(this.shadowRectangle.getArcWidth()); // shadowRectangle CSS cannot be applied on this
		this.internal.setArcHeight(this.shadowRectangle.getArcHeight());

		this.external.setWidth(newBounds.getWidth() + SHADOW_WIDTH * 2);
		this.external.setHeight(newBounds.getHeight() + SHADOW_WIDTH * 2);
		final Shape clip = Shape.subtract(this.external, this.internal);
		this.shadowRectangle.setClip(clip);

	}

}
