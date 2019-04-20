package arb.view;

import arb.factories.LayoutFactory;
import arb.view.roothelper.ShadowRectangle;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/** This class is the top level root element of any stage. */
public class RootStackPane extends StackPane {

	private static final int ROUNDED_DELTA = 0;

	private static final int SHADOW_WIDTH = 15;

	private final ShadowRectangle shadowRectangle;

	private final Region applicationView;

	public RootStackPane(Region applicationView) {
		this.applicationView = applicationView;
		applicationView.setPadding(new Insets(10));
		this.shadowRectangle = LayoutFactory.getInstance().createShadowRectangle();
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
}
