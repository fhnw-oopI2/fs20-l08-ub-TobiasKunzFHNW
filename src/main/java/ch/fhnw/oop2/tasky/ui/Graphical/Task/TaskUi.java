package ch.fhnw.oop2.tasky.ui.Graphical.Task;

import ch.fhnw.oop2.tasky.model.Task;
import ch.fhnw.oop2.tasky.model.TaskyPM;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TaskUi extends GridPane {
	private Label labelTitle;
	private Label labelDue;
	private Task task;
	private static final int TASK_HEIGHT = 80;
	private static final float HEIGHT_TITLE = 0.8f;
	private TaskyPM pm;

	public Task getTask() {
		return task;
	}

	public TaskUi(TaskyPM pm, Task task) {
		this.pm = pm;
		this.task = task;
		initializeControls();
		layoutControls();
		initializeListeners();
	}

	private void initializeListeners() {
		setOnMouseClicked(event -> pm.selectTask(task.id));
		pm.selectedTaskIdProperty().addListener((event, oldValue, newValue) -> {
			if (task.id == newValue.longValue()) {
				select();
			} else {
				deselect();
			}
		});
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
		if (pm.getSelectedTaskId() == task.id){
			select();
		}
		setPrefHeight(TASK_HEIGHT);
	}

	public void select() {
		getStyleClass().add("selected");
	}

	public void deselect() {
		getStyleClass().remove("selected");
	}
}
