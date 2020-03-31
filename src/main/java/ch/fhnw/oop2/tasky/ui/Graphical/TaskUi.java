package ch.fhnw.oop2.tasky.ui.Graphical;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TaskUi extends VBox {
	//private Task task; todo
	private Label id;
	private Label text;
	private static final int PADDING = 10;

	public TaskUi() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		id = new Label("10");
		text = new Label("Do laundry");
	}

	private void layoutControls() {
		getChildren().addAll(id, text);
		setStyle("-fx-background-color: \n" +
				"       -fx-shadow-highlight-color, \n" +
				"       -fx-outer-border, \n" +
				"       -fx-inner-border, \n" +
				"       -fx-body-color;");
		setPrefWidth(Integer.MAX_VALUE);
		setPadding(new Insets(PADDING));
	}
}
