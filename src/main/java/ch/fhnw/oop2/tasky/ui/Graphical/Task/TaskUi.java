package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.ui.Graphical.ApplicationUI;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TaskUi extends VBox {
	//private Task task; todo
	private Label labelTitle;
	private Label labelDescription;
	private static final int PADDING = 20;
	private Task task;

	public TaskUi(Task task) {
		this.task = task;
		initializeControls();
		layoutControls();
		initializeListeners();
	}

	private void initializeListeners() {
		setOnMouseClicked(event -> ApplicationUI.setSelectedId(task.id));
	}

	private void initializeControls() {
		labelTitle = new Label();
	}

	private void layoutControls() {
		labelTitle.setText(task.data.title);
		getChildren().addAll(labelTitle);
		setStyle("-fx-background-color: \n" +
				"       -fx-shadow-highlight-color, \n" +
				"       -fx-outer-border, \n" +
				"       -fx-inner-border, \n" +
				"       -fx-body-color;");
		//	setPrefWidth(Integer.MAX_VALUE);
		setPrefHeight(100);
		//setPadding(new Insets(PADDING));
	}
}
