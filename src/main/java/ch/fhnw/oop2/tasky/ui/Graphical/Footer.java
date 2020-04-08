package ch.fhnw.oop2.tasky.ui.Graphical;

import ch.fhnw.oop2.tasky.model.TaskComparator;
import ch.fhnw.oop2.tasky.model.TaskyPM;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Footer extends HBox {
	private Button buttonNew;
	private ComboBox<TaskComparator> comboBoxSort;
	private Label sortLabel;

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
		comboBoxSort = new ComboBox<>();
		comboBoxSort.getItems().setAll(TaskComparator.values());
		comboBoxSort.valueProperty().bindBidirectional(pm.taskSortPropertyProperty());
		comboBoxSort.setOnAction(e -> pm.refreshTasks());
		sortLabel = new Label("Sort Tasks By:");
	}

	private void layoutControls() {
		buttonNew.setFont(Starter.BUTTON_FONT);
		comboBoxSort.getEditor().setFont(Starter.BUTTON_FONT);
		buttonNew.setPrefWidth(200);
		comboBoxSort.setPrefWidth(200);
		sortLabel.setPrefHeight(200);
		sortLabel.setFont(Starter.LABEL_FONT);
		comboBoxSort.setPrefHeight(200);
		getChildren().addAll(buttonNew, sortLabel,comboBoxSort);
		setSpacing(20);
		setPadding(new Insets(30));
	}
}
