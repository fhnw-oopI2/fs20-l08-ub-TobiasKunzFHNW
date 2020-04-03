package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.ui.Graphical.ApplicationUI;
import ch.fhnw.oop2.tasky.ui.Graphical.Starter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class TaskUi extends GridPane {
	//private Task task; todo
	private Label labelTitle;
	private Label labelDue;
	private Task task;
	private static final int TASK_HEIGHT = 80;
	private static final float HEIGHT_TITLE = 0.8f;
	//private static final float WIDTH = 0.8f;


	public Task getTask() {
		return task;
	}

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
		labelTitle = new Label(task.data.title);
		labelDue = new Label(task.data.dueDate.toString());
		getStylesheets().add("CSS/TaskUI.css");
	}

	private void layoutControls() {
		getRowConstraints().add(new RowConstraints((HEIGHT_TITLE) * TASK_HEIGHT));
		getRowConstraints().add(new RowConstraints((1 - HEIGHT_TITLE) * TASK_HEIGHT));
		add(labelTitle, 0, 0);
		add(labelDue, 0, 1);
		getStyleClass().add("TaskUI");
		setPrefHeight(TASK_HEIGHT);
	}

	public void select() {
		getStyleClass().add("selected");
	}

	public void deselect() {
		getStyleClass().remove("selected");
	}
}
