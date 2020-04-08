package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.TaskyPM;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Footer extends HBox {
	private Button buttonNew, buttonRefresh;
	private TaskyPM pm;

	public Footer(TaskyPM pm) {
		this.pm = pm;
		initializeControls();
		layoutControls();
	}

	private void initializeControls() {
		buttonNew = new Button();
		buttonNew.textProperty().bind(pm.buttonNewProperty());
		buttonNew.setOnAction(b -> pm.createNewTask());
		buttonRefresh = new Button();
		buttonRefresh.textProperty().bind(pm.buttonRefreshProperty());
		buttonRefresh.setOnAction(b -> pm.refreshTasks());
	}

	private void layoutControls() {
		buttonNew.setFont(Starter.BUTTON_FONT);
		buttonRefresh.setFont(Starter.BUTTON_FONT);
		buttonNew.setPrefWidth(200);
		buttonRefresh.setPrefWidth(200);
		getChildren().addAll(buttonNew, buttonRefresh);

		setSpacing(10);
		setPadding(new Insets(10));
	}
}
