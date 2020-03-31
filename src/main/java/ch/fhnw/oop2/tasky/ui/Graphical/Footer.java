package ch.fhnw.oop2.tasky.ui.Graphical;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Footer extends HBox {
	private Button buttonNew, buttonRefresh;

	public Footer() {
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		buttonNew = new Button("New");
		buttonRefresh = new Button("Refresh");
	}

	private void layoutControls() {
		getChildren().addAll(buttonNew, buttonRefresh);
		buttonNew.setFont(Starter.BUTTON_FONT);
		buttonRefresh.setFont(Starter.BUTTON_FONT);
		setSpacing(20);
	}
}
